//todo URLs aanvullen
const accountAanmakenURL = "./api/gebruikers/maakaccount"
const bestellingenURL = './api/bestellingen/';
const plaatsBestellingURL = "./api/bestellingen/";
const annuleerBestellingURL = "./api/bestellingen/annuleer/";
const MedicijnenURL;
const voorraadOverzichtURL = "./api/voorraad/overzicht/"
const orderURL = "./api/order/afval/haalAfvalOp/";
const ticketURL = "./api/ticket/";
const statistiekenURL = './api/statistieken/';
const boekhoudURL = "./api/boekhoud";
const bestelBoekhoudURL = boekhoudURL + '/bestel';
const betaalBoekhoudURL = boekhoudURL + '/betaalLeverancier';
const voorraadURL = ".../api/voorraad"
const geefOverzichtURL = voorraadURL + "/overzicht";
const verwerkLadingURL = voorraadURL + "/lading";
const nieuwsbriefURL = "./api/gebruikers/nieuwsbrief";
const koerierURL = "./api/bpost";
const catalogusURL = ".../api/catalogus";

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
    for (i = 0; i < medicijnen.size() / 2; i++) {
        medcijnenStr += '{ "' + medicijnen[i] + '":' + medicijnen[i + 1] + '},';
    }
    medicijnenStr += '}';

    fetch(plaatsBestellingURL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json;charset=utf-8' },
        body: JSON.stringify({
            "klantenId": document.getElementById("klantenId").value,
            "thuisAdres": document.getElementById("thuisadres").value,
            "apotheekAdres": document.getElementById("apotheekadres").value,
            "medicijnen": medicijnenStr
        })
    }).then(response => document.getElementById("").value(response.text()))
        .catch(e => console.log(e));
};


let toonStats = fetch(statistiekenURL).then((res) => document.getElementById("statistiekenResponse").value = res.json())

// ################## BESTELLING ANULLEREN ##################

document.getElementById("bestellingAnnulerenKlantForm").addEventListener("submit", annuleerBestelling);

let annuleerBestelling = () => {
    let id = document.getElementById("bestellingsIdAnnulerenKlant").text;
    fetch(annuleerBestellingURL + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    }).then((res) => { document.getElementById("bestellingAnnulerenKlantResponse").value = res.json(); })
        .catch(e => console.log(e));
}

// ################## MEDICIJNEN ##################

document.getElementById("geefOverzichtButton").addEventListener("click", geefOverzicht);

let geefOverzicht = () => {
    fetch(voorraadOverzichtURL, {
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
        method: 'PUT'
    })
        .then(response => document.getElementById("afvalOpgehaaldResponse").value(response.text()))
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
        .catch(e => console.log(e))
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
        .catch(e => console.log(e));


document.getElementById("bestelMedicijnBtn").addEventListener("click", bestelMedicijnBij)

let betaalLeverancier =
    fetch(betaalBoekhoudURL, {
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



// json() testen
let geefOverzicht = fetch(geefOverzichtURL, { method: 'GET' }).then(res => {
    document.getElementById('geefOverzichtResponse').text = res.text()
}).catch(e => console.log(e));

document.getElementById("geefOverzichtButton").addEventListener("click", geefOverzicht);



// TODO body maken
let verwerkLading = fetch(verwerkLading, {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json;charset=utf-8'
    },
    body: JSON.stringify(document.getElementById('lading').innerText)
}).then(res => {
    document.getElementById('verwerkLadingResponse').text = res.text();
}).catch(e => console.log(e));

document.getElementById('verwerkLading').addEventListener("click", verwerkLading);


// ################## GEBRUIKERS ##################

let stuurNieuwsbrief = fetch(stuurNieuwsbrief, {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json;charset=utf-8'
    },
    body: JSON.sstringify(document.getElementById('context').innerText)

})
    .then(res => { document.getElementById('nieuwsbriefResponse').text = res.text() })
    .catch(e => console.log(e));

document.getElementById('verzendNieuwsbrief').addEventListener("click", stuurNieuwsbrief);

// ################## KOERIER ##################

let koerierHaalRolcontainerOp = () => {
    fetch(koerierURL, {
        method: "POST"
    })
        .then(response => { document.getElementById("rolcontainerOpgehaaldResponse").text = response.text(); })
        .catch(e => console.log(e));
}

document.getElementById("haalRolcontainerOp").addEventListener("click", koerierHaalRolcontainerOp);

let verwijderUitCatalogus = ()=>{
    const id = document.getElementById("teVerwijderenId").value;
    fetch(catalogusURL+'/'+id,{
        method: 'DELETE'
    }).then(res => {
        document.getElementById('verwijderUitCatalogusResponse').innerText = res.text();
    }).catch(e=> console.log(e));
};

let voegToeAanCatalogus = () => {
    fetch(catalogusURL, {
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


