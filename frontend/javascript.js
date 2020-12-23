//todo URLs aanvullen
const accountAanmakenURL = "http://localhost:8089/api/gebruikers/maakaccount"
const bestellingenURL = 'http://localhost:8089/api/bestellingen/';
const plaatsBestellingURL = "http://localhost:8089/api/bestellingen/";
const annuleerBestellingURL = "http://localhost:8089/api/bestellingen/annuleer/";
//const MedicijnenURL;
const voorraadOverzichtURL = "http://localhost:8089/api/voorraad/overzicht/"
const orderURL = "http://localhost:8089/api/order/afval/haalAfvalOp/";
const ticketURL = "http://localhost:8089/api/ticket/";
const statistiekenURL = 'http://localhost:8089/api/statistieken/';
const boekhoudURL = "http://localhost:8089/api/boekhoud";
const bestelBoekhoudURL = boekhoudURL + '/bestel';
const betaalBoekhoudURL = boekhoudURL + '/betaalLeverancier';
const voorraadURL = "http://localhost:8089/api/voorraad"
const geefOverzichtURL = voorraadURL + "/overzicht";
const verwerkLadingURL = voorraadURL + "/lading";
const nieuwsbriefURL = "http://localhost:8089/api/gebruikers/nieuwsbrief";
const koerierURL = "http://localhost:8089/api/bpost";
const catalogusURL = "http://localhost:8089/api/catalogus";

// ################## ACCOUNT AANMAKEN ##################

let accountAanmaken = () => {
    fetch(accountAanmakenURL, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body:
            JSON.stringify({
                "naam": document.getElementById("naam").value,
            "email": document.getElementById("email").value
        })
    })
        .then(response => response.json())
        .then(json => { console.log(json);
                     document.getElementById("accountAanmakenResponse").innerText=response;
         })
        .catch(e => console.log(e));
}

document.getElementById("accountAanmakenForm").addEventListener("click", accountAanmaken);

// ################## BESTELLING PLAATSEN ##################

let plaatsBestellingAlsKlant = () => {
    let medicijnenString = document.getElementById("medicijnen").value;
    let medicijnen = medicijnenString.split(" ");
    let medicijnenStr = '{';
    for (i = 0; i < medicijnen.length / 2; i++) {
        medicijnenStr += '{ "' + medicijnen[i] + '":' + medicijnen[i + 1] + '},';
    }
    medicijnenStr += '}';

    fetch(plaatsBestellingURL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            "klantenId": document.getElementById("klantenId").value,
            "thuisAdres": document.getElementById("thuisadres").value,
            "apotheekAdres": document.getElementById("apotheekadres").value,
            "medicijnen": medicijnenStr
        })
    })
    .then(response => response.text())
    .then(response => document.getElementById("bestellingPlaatsenKlantResponse").innerText=response)
        .catch(e => console.log(e));
};


let toonStats = () => {
    fetch(statistiekenURL, {
        method: "GET"
    }).then((res) => document.getElementById("statistiekenResponse").value = res.json());
}

document.getElementById("plaatsBestellingKlantKnop").addEventListener("click", plaatsBestellingAlsKlant);
document.getElementById("statistieken").addEventListener("click", toonStats);

// ################## BESTELLING ANULLEREN ##################

let annuleerBestelling = () => {
    let id = document.getElementById("bestellingsIdAnnulerenKlant").value;
    fetch(annuleerBestellingURL + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((res) => { document.getElementById("bestellingAnnulerenKlantResponse").innerText = res.text(); })
        .catch(e => console.log(e));
}

document.getElementById("bestellingAnnulerenKlantButton").addEventListener("click", annuleerBestelling);

// ################## MEDICIJNEN ##################

let geefOverzicht = () => {
    fetch(voorraadOverzichtURL, {
        method: 'GET'
        //wrs geen headers en body nodig 
    })
        .then(res => {console.log(res); return res.json();})
        .then(json => {
            let resultString = "";
            json.forEach(el => {
                resultString += "medicijn: " + el.medicijn + "\n";
                resultString += "Id: " + el.Id + "\n";
                resultString += "aantal: " + el.aantal + "\n";
                resultString += "\n";
            }); console.log(resultString);
            document.getElementById("geefOverzichtResponse").innerText=resultString;
        })
        .catch(e => console.log(e));
}

document.getElementById("geefOverzichtButton").addEventListener("click", geefOverzicht);

// ################## ORDER ##################

let haalAfValOp = () => {
    fetch(orderURL, {
        mode: "no-cors",
        method: 'PUT'
    })
        .then(response => document.getElementById("afvalOpgehaaldResponse").value(response.text()))
        .catch(e => console.log(e));
}

document.getElementById("haalAfvalOpButton").addEventListener("click", haalAfValOp);

// ################## TICKET ##################

let plaatsTicket = () => {
    fetch(ticketURL + "open", {
        mode: 'no-cors',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "klantenId": "" + document.getElementById("klantenIdOpenTicket").value,
            "bestellingsId": ""+document.getElementById("bestellingsIdOpenTicket").value,
            "probleem": ""+document.getElementById("probleemOpenTicket").value
        })
    }).then(response => {console.log(response); return response.text();})
        .then(text => { console.log(text); document.getElementById("openTicketResponse").text = text; })
        .catch(e => console.log(e))
};

document.getElementById("submitOpenTicket").addEventListener("click", plaatsTicket);

// ################## BOEKHOUD ##################

let bestelMedicijnBij = () => {
    fetch(bestelBoekhoudURL, {
        mode: "no-cors",
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            "medicijn": document.getElementById("medicijn").value,
            "aantal": document.getElementById("aantal").value
        })
    }).then(response => { document.getElementById("bestelMedicijnResponse").text = response.text(); })
        .catch(e => console.log(e));
}

let betaalLeverancier = () => {
    fetch(betaalBoekhoudURL, {
        mode: "no-cors",
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            "leverancier": document.getElementById("leverancier").value,
            "bedrag": document.getElementById("bedrag").value
        })
    }).then(response => { document.getElementById("betaalLeverancierResponse").text = response.text(); })
        .catch(e => console.log(e));
}


// TODO body maken
let verwerkLading = () => { 
    fetch(verwerkLadingURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(document.getElementById('lading').innerText)
    }).then(res => {
        document.getElementById('verwerkLadingResponse').text = res.text();
    }).catch(e => console.log(e));
}

document.getElementById('verwerkLading').addEventListener("click", verwerkLading);
document.getElementById("bestelMedicijnBtn").addEventListener("click", bestelMedicijnBij)
document.getElementById("bestelMedicijnBtn").addEventListener("click", bestelMedicijnBij)
document.getElementById("geefOverzichtButton").addEventListener("click", geefOverzicht);


// ################## GEBRUIKERS ##################

let stuurNieuwsbrief =  () => {
    fetch(nieuwsbriefURL, {
        mode: "no-cors",
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(document.getElementById('content').innerText)
    })
    .then(res => { document.getElementById('nieuwsbriefResponse').text = res.text() })
    .catch(e => console.log(e));
}

document.getElementById('verzendNieuwsbrief').addEventListener("click", stuurNieuwsbrief);

// ################## KOERIER ##################

let koerierHaalRolcontainerOp = () => {
    fetch(koerierURL, {
        mode: "no-cors",
        method: "POST"
    })
        .then(response => { document.getElementById("rolcontainerOpgehaaldResponse").text = response.text(); })
        .catch(e => console.log(e));
}

document.getElementById("haalRolcontainerOp").addEventListener("click", koerierHaalRolcontainerOp);

let verwijderUitCatalogus = ()=>{
    const id = document.getElementById("teVerwijderenId").value;
    fetch(catalogusURL+'/'+id,{
        mode: "no-cors",
        method: 'DELETE'
    }).then(res => {
        document.getElementById('verwijderUitCatalogusResponse').innerText = res.text();
    }).catch(e=> console.log(e));
};

let voegToeAanCatalogus = () => {
    fetch(catalogusURL, {
        mode: "no-cors",
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            "naam": document.getElementById("catalogusNaam").innerText,
            "kritischeWaarde": document.getElementById('kritischeWaarde').innerText,
            "beschrijving": document.getElementById("beschrijving").innerText,
            "voorschriftNoodzakelijk": document.getElementById("voorschriftNoodzakelijk").innerText,
            "prijs": document.getElementById("prijs").innerText,
            "gewensteTemperatuur": document.getElementById("gewensteTemperatuur").innerText
        })
    })
    .then(res => {
        document.getElementById('voegToeAanCatalogusResponse')
    })
    .catch()
}


