const nSQL = require("@nano-sql/core").nSQL;
const { Kafka } = require('kafkajs');
const json = require('json');
const express = require('express')
const cors = require('cors');
const bodyParser = require("body-parser");
const random = require('random')
const minimist = require('minimist');
const args = minimist(process.argv.slice(2));

/********************************************
 * NANOSQL
 ********************************************/

nSQL().createDatabase({
    id: "ticketdienst",
    mode: "PERM",
    tables: [
        {
            name: "tickets",
            model: {
                "ticketId:int": { pk: true, ai: true },
                "klantenId:string": {},
                "bestellingsId:string": {},
                "probleem:string": {},
                "status:string": {}
            }
        }
    ]
});

/**********************************************
 * KAFKA
**********************************************/

const kafka = new Kafka({
    clientId: 'ticketdienst',
    brokers: [args['broker']]
});

async function vraagStatus(bestellingsId) {
    const producer = kafka.producer()
    await producer.connect()
    await producer.send({
        topic: 'geef_status_request',
        messages: [
            { value: JSON.stringify({ "id": bestellingsId, "responseDestination": "geef_status_response" }) }
        ],
    });
    await producer.disconnect();
}

async function retourBijAfval(id, aantal) {
    const producer = kafka.producer()
    await producer.connect();
    await producer.send({
        topic: 'plaats_retour_bij_afval_request',
        messages: [
            { value: JSON.stringify({ "id": id, "aantal": aantal }) }
        ]
    });
    await producer.disconnect();
}

async function statusconsumer() {
    const consumer_status = kafka.consumer({ groupId: 'geefstatustest' });
    await consumer_status.connect();
    await consumer_status.subscribe({ topic: 'geef_status_response', from_beginning: false });

    await consumer_status.run({
        eachMessage: async ({ topic, partition, message }) => {
            console.log(message.value.toString());
            var m = JSON.parse(message.value.toString());
            if (m.status == "GELUKT") {
                statusOntvangen(m.id, m.bestellingStatus);
            }
        },
    });
}

function statusOntvangen(id, status) {
    console.log(id, status);
}

/****************************************************************
 * PROBLEEM STATUS
 ***************************************************************/

const Status = {
    OPEN: 'open',
    IN_BEHANDELING: 'in behandeling',
    AFGESLOTEN: 'afgesloten'
}

/***************************************************************
 * REST API
 ******************************************************************/

const app = express()

app.use(cors());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.get('/api/ticket', function (req, res) {
    nSQL("tickets").query("select").where(["status", "=", Status.OPEN]).exec().then((rows) => {
        res.send(rows);
        res.end();
    });
});


app.post('/api/ticket/open', function (req, res) {
    //bij openen ticket wordt klantenId, bestellingId en probleem gegeven
    console.log(req.body.klantenId, req.body.bestellingsId, req.body.probleem);
    nSQL("tickets").query("upsert", { "klantenId": req.body.klantenId, "bestellingsId": req.body.bestellingsId, "probleem": req.body.probleem, "status": Status.OPEN }).exec().then((rows) => {
        console.log(rows);
        res.send(JSON.stringify({ "ticketId": rows[0].ticketId }));
        res.end();
    });
});

app.post('/api/ticket/behandel', function (req, res) {
    //bij behandel ticket wordt een ticket id gegeven
    var ticketId = req.body.id;

    var retour = random.int(0, 100);
    if (retour < 30) {
        res.send("behandel " + ticketId + ", bestelling teruggestuurd");
        res.end();
    }
    else {
        res.send("behandel " + ticketId);
        res.end();
    }

    nSQL("tickets").query("upsert", { "status": Status.IN_BEHANDELING }).where(["ticketId", "=", ticketId]).exec().then((rows) => {
        console.log(rows);
    });

    nSQL("tickets").query("select", ["bestellingsId"]).where(["ticketId", "=", ticketId]).exec().then((rows) => {
        vraagStatus(rows[0].bestellingsId);
        if (retour < 30) {
            retourBijAfval(0, retour);
        }
    });
});

app.post('/api/ticket/sluit', function (req, res) {
    //bij sluiten ticket wordt ticket id gegeven
    var ticketId = req.body.id;

    nSQL("tickets").query("upsert", { "status": Status.AFGESLOTEN }).where(["ticketId", "=", ticketId]).exec().then((rows) => {
        console.log(rows);
        res.send("ticket is afgesloten");
        res.end();
    })
});

app.post('/api/ticket/stuurBestellingTerug', function (req, res) {
    var bestellingsId = req.body.id;
    var aantal = random.int(10, 50);
    retourBijAfval(0, aantal);
    res.send("Bestelling is succesvol ontvangen en zal bij het afval geplaatst worden");
    res.end();
});

app.listen(3000);

/////////////////// consumers aanmaken ///////////////////////////

statusconsumer();