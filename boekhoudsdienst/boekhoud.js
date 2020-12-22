const nSQL = require("@nano-sql/core").nSQL;
const { Kafka } = require('kafkajs');
var json = require('json');
const express = require('express');
const request = require('request');
const nodemailer = require("nodemailer");
const minimist = require('minimist');
const cors = require('cors');
const bodyParser = require("body-parser");

const transporteur = nodemailer.createTransport(
    {
        service: 'gmail',
        auth: {
            user: 'sysdes6@gmail.com',
            pass: 'SysDes6Covid19'
        }
    }
);

const args = minimist(process.argv.slice(2));
/********************************************
 * NANOSQL
 ********************************************/

nSQL().createDatabase({
    id: "boekhoud",
    mode: "PERM",
    tables: [
        {
            name: "bestellingen",
            model: {
                "bestellingsId:string": { pk: true },
                "klantenId:string": {},
                "prijs:float": {}
            }
        }
    ]
});


/**********************************************
 * KAFKA
**********************************************/

const kafka = new Kafka({
    clientId: 'boekhoud',
    brokers: [args["broker"]]
});

async function orderverzondenconsumer() {
    const consumer_order_verzonden = kafka.consumer({ groupId: 'orderverzondentest' });
    await consumer_order_verzonden.connect();
    await consumer_order_verzonden.subscribe({ topic: 'orderverzonden_event', from_beginning: false });

    await consumer_order_verzonden.run({
        eachMessage: async ({ topic, partition, message }) => {
            console.log(message.value.toString());
            var m = JSON.parse(message.value.toString());
            orderVerzonden(m.id);
        },
    });
}

async function factuurconsumer() {
    const consumer_maak_factuur = kafka.consumer({ groupId: 'factuurtest' });
    await consumer_maak_factuur.connect();
    await consumer_maak_factuur.subscribe({ topic: 'maak_factuur', from_beginning: false });

    await consumer_maak_factuur.run({
        eachMessage: async ({ topic, partition, message }) => {
            console.log(message.value.toString());
            var m = JSON.parse(message.value.toString());
            maakFactuur(m.id, m.klantenId, m.medicijnen);
        },
    });
}

async function annuleerconsumer() {
    const consumer_annuleren = kafka.consumer({ groupId: 'annulerentest' });
    await consumer_annuleren.connect();
    await consumer_annuleren.subscribe({ topic: 'bestelling_geannuleerd_event', from_beginning: false });

    await consumer_annuleren.run({
        eachMessage: async ({ topic, partition, message }) => {
            console.log(message.value.toString());
            var m = JSON.parse(message.value.toString());
            betaalTerug(m.id);
        },
    });
}

async function kritischewaardeconsumer() {
    const consumer_kritische_waarde = kafka.consumer({ groupId: 'medicijnkritischewaardetest' });
    await consumer_kritische_waarde.connect();
    await consumer_kritische_waarde.subscribe({ topic: 'medicijn_kritische_waarde', from_beginning: false });

    await consumer_kritische_waarde.run({
        eachMessage: async ({ topic, partition, message }) => {
            console.log(message.value.toString());
            var m = JSON.parse(message.value.toString());
            bestelBij(m.catalogusId, m.aantalBijTeBestellen);
        },
    });
}



function orderVerzonden(id) {
    nSQL("bestellingen").query("select", ["klantenId"]).where(["bestellingsId", "=", id]).exec().then((rows) => {
        console.log(rows[0].klantenId);
        if (rows[0].klantenId != undefined) {
            //email opvragen van deze klant en email sturen
            console.log('Order verzonden ontvangen bij de boekhoudsdienst');
            
            request('http://' + args['gebruikersAPI'] +'/'+ rows[0].klantenId, function (error, response, body) {
                console.error('error:', error); // Print the error if one occurred
                console.log('statusCode:', response && response.statusCode); // Print the response status code if a response was received
                console.log('body:', body); // Print the HTML for the Google homepage.
                //Email uit body halen en email sturen
                email = body.email;
                naam = body.naam;
                let info = transporteur.sendMail({
                    from: "sysdes6@gmail.com",
                    to: email,
                    subject: "✔ Uw order is verzonden",
                    text: "Beste " + naam + ", Uw order met id " + id + " is verzonden!"
                }).catch(e=>console.log(e));

            });
        }
    });
}

function maakFactuur(bestellingsId, klantenId, medicijnen) {
    var prijs = 0.0;
    for (medicijn of medicijnen) {
        console.log(medicijn);
        prijs += (medicijn.prijs * medicijn.aantal);
    }
    console.log(prijs);
    console.log({ "bestellingsId": bestellingsId, "klantenId": klantenId, "prijs": prijs });
    nSQL("bestellingen").query("upsert", { "bestellingsId": bestellingsId, "klantenId": klantenId, "prijs": prijs }).exec().then((rows) => {
        console.log(rows);
    });

    console.log('Maak factuur ontvangen bij de boekhoudsdienst');

    request('http://' +args['gebruikersAPI'] + '/' + klantenId, function (error, response, body) {
        console.error('error:', error); // Print the error if one occurred
        console.log('statusCode:', response && response.statusCode); // Print the response status code if a response was received
        console.log('body:', body); // Print the HTML for the Google homepage.
        //Email uit body halen en email sturen
        let email = body.email;
        let info = transporteur.sendMail({
            from: "sysdes6@gmail.com",
            to: email,
            subject: "Factuur order ",
            text: "Factuur met prijs " + prijs + " voor de bestelling met id " + bestellingsId + " bij ons."
        }).catch();

    });
}

function betaalTerug(bestellingsId) {
    nSQL("bestellingen").query("select", ["prijs"]).where(["bestellingsId", "=", bestellingsId]).exec().then((rows) => {
        //betaal klant terug
        console.log(rows[0].prijs);
    });
}

function bestelBij(id, aantal) {
    console.log(id, aantal);
}

/*********************************
 * REST API
 **********************************/

const app = express()

app.use(cors());
app.use(express.json());

app.post('/api/boekhoud/bestel', function (req, res) {
    console.log("Medicijn met id " + req.body.medicijn + " wordt bijbesteld [hoeveelheid:" + req.body.aantal + "].");
    res.send("Medicijn met id " + req.body.medicijn + " wordt bijbesteld [hoeveelheid:" + req.body.aantal + "].");
    res.end();
});

app.post('/api/boekhoud/betaalLeverancier', function (req, res) {
    console.log("Betaal leverancier " + req.body.leverancier + " €" + req.body.bedrag);
    res.send("Betaal leverancier " + req.body.leverancier + " €" + req.body.bedrag);
    res.end();
});

app.listen(3000);

///////////////////////// consumers opstarten ////////////////////////////////////



annuleerconsumer();
orderverzondenconsumer();
factuurconsumer();
kritischewaardeconsumer();