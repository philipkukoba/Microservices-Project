// https://www.tutorialspoint.com/nodejs/nodejs_restful_api.htm
var express = require('express');
var app = express();
// CONFIGURATIE

// https://stackabuse.com/command-line-arguments-in-node-js/
const minimist = require('minimist');

let args = minimist(process.argv.slice(2), {
    default: {
        p: 8080,
        topic: 'afwijkende_temperatuur'
    },
});

// instellingen voor de random events
const interval = args['i'] *1000; // s
const chanceOnEvent = args['c']; // [0-1]

const port = args['p'];

const kafkaHost = args['broker'];
const topic = args['topic'];

// EINDE CONFIGURATIE

// https://hmh.engineering/experimenting-with-apache-kafka-and-nodejs-5c0604211196
// https://technology.amis.nl/2017/02/09/nodejs-publish-messages-to-apache-kafka-topic-with-random-delays-to-generate-sample-events-based-on-records-in-csv-file/
// https://stackoverflow.com/questions/57854803/apache-kafka-2-3-node-js-10-15-consumer-producer
// https://www.educba.com/kafka-node/
var kafka = require('kafka-node');
var Producer = kafka.Producer;

const client = new kafka.KafkaClient({ 'kafkaHost': kafkaHost });
const producer = new Producer(client);

function rand() {
    let r = Math.random();
    while (r == 0) {
        r = Math.random();
    }
    return r;
}

const data_push = function () {
    const id = Math.floor(Math.random() * cellen.length);

    let afwijking = rand() * rand() * rand() * 2.5 + rand() * rand() * rand() * 2.5;

    const temp = cellen[id].doel + 2.5 + Math.round(afwijking * 100) / 100;

    const data = [{
        'topic': topic, messages: [
            // temperatuur niet te grote afwijking van grenzen van de cel
            JSON.stringify({ 'koelCelId': id, 'temperatuur': temp })
        ]
    }]

    producer.send(data, function (error, data) {

    });
};

const cellen = [
    { 'id': 0, 'doel': -7.5 }, // -10 -5
    { 'id': 1, 'doel': -2.5 }, // -5 0
    { 'id': 2, 'doel': 2.5 }, // 0 5
    { 'id': 3, 'doel': 7.5 }, // 5 10
    { 'id': 4, 'doel': 12.5 } // 10 15
];

app.get('api/koelcellen', function (req, res) {

    const responsedata = cellen.map(cel => {
        return { 'id': cel.id, 'min': (cel.doel - 2.5), 'max': (cel.doel + 2.5) }
    });

    // https://stackoverflow.com/questions/51661744/how-to-set-content-type-when-doing-res-send/51661772
    res.setHeader('content-type', 'application/json');
    res.end(JSON.stringify(responsedata));
});

app.listen(port, function () {

});

let pushInterval;

producer.on('ready', function () {
    console.log('Kafka bus is ready')
});

app.get('/start', function (req, res) {
    pushInterval = setInterval(data_push, interval);
    res.end();
});

app.get('/stop', function (req, res) {
    clearInterval(pushInterval);
    res.end();
});

// node app.js -p 8081 -i 5 -c 0.85 --broker 192.168.0.210:9092