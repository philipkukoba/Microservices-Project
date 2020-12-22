//todo URLs aanvullen
const accountAanmakenURL = "./api/gebruikers/maakaccount"
const bestellingenURL = './api/bestellingen/';
const plaatsBestellingURL = "./api/bestellingen/";
const annuleerBestellingURL = "./api/bestellingen/annuleer/";
const MedicijnenURL;
const catalogusOverzichtURL = "./api/voorraad/overzicht/"
const orderURL = "./api/order/afval/haalAfvalOp/";
const ticketURL = "./api/ticket/";
const statistiekenURL = './api/statistieken/';
const boekhoudURL = "./api/boekhoud";
const bestelBoekhoudURL = boekhoudURL + '/bestel';
const betaalBoekhoudURL = boekhoudURL + '/betaalLeverancier';

// ################## ACCOUNT AANMAKEN ##################

document.getElementById("accountAanmakenForm").addEventListener("submit", accountAanmaken);

let accountAanmaken = () => {
    fetch(accountAanmakenURL, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
                "naam": document.getElementById("klantenId").value,
                "email": document.getElementById("thuisadres").value,               
                })      
    })
    .then(response => { document.getElementById("accountAanmakenResponse").value(response.text()); })
    .catch(e => console.log(e));
}

// ################## BESTELLING PLAATSEN ##################

document.getElementById("...").addEventListener("click", plaatsBestellingAlsKlant);
document.getElementById("statistieken").addEventListener("click", toonStats);

let plaatsBestellingAlsKlant = () => {
    let medicijnenString = document.getElementById("medicijnen").value; 
    let medicijnen = medicijnenString.split(" ");
    medicijnenStr = '{';
    for (i = 0; i < medicijnen.size() / 2; i++){
        medcijnenStr += '{ "' + medicijnen[i] + '":' + medicijnen[i+1] +'},';
    }
    medicijnenStr += '}';
    
    fetch(plaatsBestellingURL, {
        method: 'POST'
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(
            {
                "klantenId": document.getElementById("klantenId").value,
                "thuisAdres": document.getElementById("thuisadres").value,
                "apotheekAdres": document.getElementById("apotheekadres").value,
                "medicijnen": {
                        {"key": value}, (key = medicijnid, value = aantal)
                        {"key": value}
                    }
                }
        )
        })
        .then( response => document.getElementById("").value(response.text()))
        .catch(e => console.log(e));   
}

let toonStats = fetch(statistiekenURL).then((res) => document.getElementById("statistiekenResponse").value = res.json())

// ################## BESTELLING ANULLEREN ##################

document.getElementById("bestellingAnnulerenKlantForm").addEventListener("submit", annuleerBestelling);

let annuleerBestelling = () => {
    let id = document.getElementById("bestellingsIdAnnulerenKlant").text;
    fetch(annuleerBestellingURL + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(
            {
                
            }
        )
    }).then(...)
    .catch(e => console.log(e));
}


// ################## MEDICIJNEN ##################

document.getElementById("geefOverzichtButton").addEventListener("click", geefOverzicht);

let geefOverzicht = () => {
    fetch(catalogusOverzichtURL, {
        method: 'GET'
        //wrs geen headers en body nodig 
    })
    .then(res => res.json())
    .then(json => {
        let resultString = "";
        json.forEach(el => {
            resultString += "medicijn: " + el.medicijn + "\n";
            resultString += "Id: " + el.Id + "\n";
            resultString += "aantal: " + el.aantal + "\n";
            resultString += "\n";
        });
        document.getElementById("geefOverzichtResponse").value(resultString);
    })
    .catch(e => console.log(e));
}

// ################## ORDER ##################

document.getElementById("haalAfvalOpButton").addEventListener("click", haalAfValOp);

let haalAfValOp = () => {
    fetch(orderURL, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: "no body"
        })
        .then( response => document.getElementById("afvalOpgehaaldResponse").value(response.text()))
        .catch(e => console.log(e));    
}

// ################## TICKET ##################

document.getElementById("submitOpenTicket").addEventListener("click", plaatsTicket);

let plaatsTicket = () => {
    fetch(ticketURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
                "klantenId": document.getElementById("klantenIdOpenTicket").value,
                "bestellingsId": document.getElementById("bestellingsIdOpenTicket").value,
                "probleem": document.getElementById("probleemOpenTicket").value
                })
        })
        .then(response => { document.getElementById("openTicketResponse").text = response.text(); })
        .catch( e => console.log(e))
    };

// ################## BOEKHOUD ##################

document.getElementById("bestelMedicijnBtn").addEventListener("click", bestelMedicijnBij)

let bestelMedicijnBij = 
    fetch(bestelBoekhoudURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
                "medicijn": document.getElementById("medicijn").value,
                "aantal": document.getElementById("aantal").value
                })
        }).then(response => { document.getElementById("bestelMedicijnResponse").text = response.text(); })
        .catch( e => console.log(e));

