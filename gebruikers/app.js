// gebaseerd op https://www.callicoder.com/node-js-express-mongodb-restful-crud-api-tutorial/
const express = require('express');
const app = express();

const mongoose = require('mongoose');

// https://stackabuse.com/command-line-arguments-in-node-js/
const minimist = require('minimist');

/*
const args=minimist(process.argv.slice(2), {
    default:{
        db: 'mongodb://admin:admin@localhost:27017/gebruikers'
    }
});
*/

const url=process.env.URL || 'mongodb://localhost:27017/gebruikers';

setTimeout(()=>{
    mongoose.connect(url, {
        "auth": { "authSource": "admin", "authdb":"admin" },
        "user": "admin",
        "pass": "admin"
    }).then(() => {
    }).catch(e=>console.log(e));
},10*1000);
    
const GebruikerSchema = mongoose.Schema({
    email: { type: String, unique: true },
    naam: String
});

const Gebruiker = mongoose.model('Gebruiker', GebruikerSchema);   

// https://stackoverflow.com/questions/4295782/how-to-process-post-data-in-node-js
// om JSON van POST body te verwerken
app.use(express.json());

app.post('/api/gebruikers/maakaccount', function (req, res) {
    const body = req.body;
/*
    mongoose.connect(args['db'], {
        "auth": { "authSource": "admin" },
        "user": "admin",
        "pass": "admin"
    }).then(() => {
    });
*/
    const gebruiker = new Gebruiker({
        email: body.email,
        naam: body.naam
    });

    gebruiker.save().then(g => {
        res.send(g);
    }).catch(e => {
        res.status(404).send('Mogelijks duplicaat');
    });
});

app.get('/api/gebruikers/:id', function (req, res) {
    const id = req.params.id;
/*
    mongoose.connect(args['db'], {
        "auth": { "authSource": "admin" },
        "user": "admin",
        "pass": "admin"
    }).then(() => {
    }); 
*/
    Gebruiker.findById(id)
        .then(g => {
            res.send({'mail': g.email, 'naam':g.naam});
        }).catch(e => {
            res.status(404);
            res.end();
        });
});


app.listen(3000, function () {
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
    const body = req.body;
/*
    mongoose.connect(args['db'], {
        "auth": { "authSource": "admin" },
        "user": "admin",
        "pass": "admin"
    }).then(() => {
    });
*/
    Gebruiker.find().then(gebruikers => {
        const mails = gebruikers.map(g => g.email);
    
        mail.sendMail({
            from: 'sysdes6@gmail.com',
            to: mails.join(", "),
            subject: 'Nieuwsbrief',
            text: body.text
        });

        res.end();
    });
});