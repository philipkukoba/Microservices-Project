const express = require('express');
const app = express();
const nSQL = require("@nano-sql/core").nSQL;


nSQL().createDatabase({
    id: "gebruikers",
    mode: "PERM",
    tables: [
        {
            name: "gebruikers",
            model: {
                "id:int": { pk: true, ai: true },
                "email:string": {},
                "naam:string": {},
            }
        }
    ]
});

// https://stackoverflow.com/questions/4295782/how-to-process-post-data-in-node-js
// om JSON van POST body te verwerken
app.use(express.json());

app.use((req, res, next) => {
    res.append('Access-Control-Allow-Origin', ['*']);
    res.append('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
    res.append('Access-Control-Allow-Headers', 'Content-Type');
    
    if(req.method === 'OPTIONS') {
        res.send(200);
    } else {
        next();
    }
});


app.post('/api/gebruikers/maakaccount', function (req, res) {
    const body = req.body;

    nSQL("gebruikers").query("upsert", { "email": body.email, "naam": body.naam }).exec().then(r => {
        let antwoord = r[0];
        antwoord.id="z"+antwoord.id+"";
        res.send(antwoord);
        res.end();
    }).catch(e => res.status(404).send('mogelijks duplicaat').end());
});


app.get('/api/gebruikers/:id', function (req, res) {
    const id = req.params.id.substr(1);

    nSQL("gebruikers").query("select", ["id","email","naam"]).where(["id", "=", parseInt(id)]).exec().then(r => {
        let antwoord = r[0];
        antwoord.id="z"+antwoord.id+"";
        res.send(antwoord);
        res.end();
    }).catch(e => {
        res.status(404);
        res.end();
    });
});

// mails sturen
// gebaseerd op https://morioh.com/p/ca75996654d1
const nodemailer = require('nodemailer');

const mail = nodemailer.createTransport(
    {
        service: 'gmail',
        auth: {
            user: 'sysdes6@gmail.com',
            pass: 'SysDes6Covid19'
        }
    }
);

app.post('/api/gebruikers/nieuwsbrief', function (req, res) {
    const inhoud = req.body.content;

    nSQL("gebruikers").query("select").exec().then(r => {
        r=r.map(r=>r.email).join(', ');
        
        mail.sendMail({
            from: 'sysdes6@gmail.com',
            to: r,
            subject: 'Nieuwsbrief',
            text: inhoud
        });

        res.send(r).end();
    }).catch(e => {
        res.end();
    });

});

app.listen(3000, function () {
});