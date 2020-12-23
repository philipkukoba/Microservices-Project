//todo URLs aanvullen
const BASE_URL = "http://localhost:8080/http://localhost:8089";
const accountAanmakenURL = BASE_URL + "/api/gebruikers/maakaccount"
const bestellingenURL = BASE_URL + "/api/bestellingen/";
const annuleerBestellingURL = BASE_URL + "/api/bestellingen/annuleer/";
const voorraadOverzichtURL = BASE_URL + "/api/voorraad/overzicht/"
const orderURL = BASE_URL + "/api/order/afval/haalAfvalOp/";
const ticketURL = BASE_URL + "/api/ticket/";
const statistiekenURL = BASE_URL + "/api/statistieken/";
const boekhoudURL = BASE_URL + "/api/boekhoud";
const bestelBoekhoudURL = boekhoudURL + '/bestel';
const betaalBoekhoudURL = boekhoudURL + '/betaalLeverancier';
const voorraadURL = BASE_URL + "/api/voorraad"
const geefOverzichtURL = voorraadURL + "/overzicht";
const verwerkLadingURL = voorraadURL + "/lading";
const nieuwsbriefURL = BASE_URL + "/api/gebruikers/nieuwsbrief";
const koerierURL = BASE_URL + "/api/bpost";
const catalogusURL = BASE_URL + "/api/catalogus";

// ################## KLANT ##################

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
        .then(json => document.getElementById("accountAanmakenResponse").innerText=json.id)
        .catch(e => console.log(e));
}

document.getElementById("accountAanmakenForm").addEventListener("click", accountAanmaken);

let plaatsBestellingAlsKlant = () => {
    let medicijnenString = document.getElementById("medicijnen").value;
    let medicijnen = medicijnenString.split(" ");
    let map = {};
    for (i = 0; i < medicijnen.length / 2; i++) {
        map[medicijnen[i]] = medicijnen[i+1];
    }

    fetch(bestellingenURL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            "klantenId": document.getElementById("klantenId").value,
            "thuisAdres": document.getElementById("thuisadres").value,
            "apotheekAdres": document.getElementById("apotheekadres").value,
            "medicijnen": map
        })
    })
    .then(response => response.text())
    .then(response => document.getElementById("bestellingPlaatsenKlantResponse").innerText=response)
        .catch(e => document.getElementById("bestellingPlaatsenKlantResponse").innerText=e);
};

document.getElementById("plaatsBestellingKlantKnop").addEventListener("click", plaatsBestellingAlsKlant);

let annuleerBestelling = () => {
    let id = document.getElementById("bestellingsIdAnnulerenKlant").value;
    fetch(annuleerBestellingURL + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.text())
    .then((res) => { document.getElementById("bestellingAnnulerenKlantResponse").innerText = res; })
        .catch(e => document.getElementById("bestellingAnnulerenKlantResponse").innerText = e.text());
}

document.getElementById("bestellingAnnulerenKlantButton").addEventListener("click", annuleerBestelling);

let stuurTerug = () => {
    fetch(ticketURL + 'stuurBestellingTerug', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "id": document.getElementById("bestellingsIdTerugsturenKlant").value
        })
    })
    .then(res => res.text())
    .then(res => document.getElementById("bestellingTerugsturenKlantResponse").innerText=res)
    .catch(e => console.log(e));
}

document.getElementById("stuurBestellingTerugKnop").addEventListener("click", stuurTerug);

// ################## KLANTENDIENSTMEDEWERKER ##################

let plaatsTicket = () => {
    fetch(ticketURL + "open", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "klantenId": "" + document.getElementById("klantenIdOpenTicket").value,
            "bestellingsId": ""+document.getElementById("bestellingsIdOpenTicket").value,
            "probleem": ""+document.getElementById("probleemOpenTicket").value
        })
    }).then(response => response.text())
        .then(text => document.getElementById("openTicketResponse").innerText=text)
        .catch(e => console.log(e))
};

document.getElementById("submitOpenTicket").addEventListener("click", plaatsTicket);

let behandelTicket = () => {
    fetch(ticketURL + "behandel", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "id": ""+document.getElementById("ticketId").value
        })
    }).then(response => response.text())
    .then(text => document.getElementById("behandelTicketResponse").innerText=text)
    .catch(e => console.log(e));
};

document.getElementById("behandelTicketId").addEventListener("click", behandelTicket);

let sluitTicket = () => {
    fetch(ticketURL + "sluit", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "id": ""+document.getElementById("id").value
        })
    }).then(response => response.text())
    .then(text => document.getElementById("sluitTicketResponse").innerText=text)
    .catch(e => console.log(e));
}

document.getElementById("sluitTicketButton").addEventListener("click", sluitTicket);

let geefTickets = () => {
    fetch(ticketURL, {
        method: 'GET'
    }).then(response => response.json())
    .then(json => {let res = ""; json.forEach(ticket => {
            res += "id: " + ticket.ticketId + ", probleem: " + ticket.probleem + ", status: " + ticket.status + ", klantenId: " + ticket.klantenId + ", bestellingsId: " + ticket.bestellingsId + "&#13;&#10;";
        }); return res;
    })
    .then(res => document.getElementById("haalTicketsOpResponse").innerHTML=res)
    .catch(e => console.log(e));
}

document.getElementById("haalTicketsOp").addEventListener("click", geefTickets);

let plaatsBestellingAlsTicket = () => {
    let medicijnenString = document.getElementById("medicijnenbestellingPlaatsenMedewerker").value;
    let medicijnen = medicijnenString.split(" ");
    let map = {};
    for (i = 0; i < medicijnen.length / 2; i++) {
        map[medicijnen[i]] = medicijnen[i+1];
    }

    fetch(bestellingenURL + 'ticket', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            "klantenId": document.getElementById("klantenIdbestellingPlaatsenMedewerker").value,
            "thuisAdres": document.getElementById("thuisadresbestellingPlaatsenMedewerker").value,
            "apotheekAdres": document.getElementById("apotheekadresbestellingPlaatsenMedewerker").value,
            "medicijnen": map
        })
    })
    .then(response => response.text())
    .then(response => document.getElementById("bestellingPlaatsenMedewerkerResponse").innerText=response)
        .catch(e => document.getElementById("bestellingPlaatsenMedewerkerResponse").innerText=e);
};

document.getElementById("bestellingPlaatsenMedewerkerButton").addEventListener("click", plaatsBestellingAlsTicket);

let annuleerBestellingTicket = () => {
    let id = document.getElementById("bestellingsId").value;
    fetch(annuleerBestellingURL + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.text())
    .then((res) => { document.getElementById("bestellingAnnulerenMedewerkerResponse").innerText = res; })
        .catch(e => document.getElementById("bestellingAnnulerenMedewerkerResponse").innerText = e.text());
}

document.getElementById("bestellingAnnulerenTicket").addEventListener("click", annuleerBestellingTicket);

// ################## MARKETINGMEDEWERKER #######################

let geefStatistieken = () => {
    fetch(statistiekenURL, {
        method: 'GET'
    })
    .then(response => response.json())
    .then(json => {
        let res = "";
        json.forEach(med => {
            res += "id: "+ med.id + ", naam: " + med.naam + ", aantal: " + med.aantal + "&#13;&#10;";
        });
        document.getElementById("statistiekenResponse").innerHTML=res;
    })
    .catch(e => console.log(e));
}

document.getElementById("statistieken").addEventListener("click", geefStatistieken);

let stuurNieuwsbrief =  () => {
    fetch(nieuwsbriefURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "content": document.getElementById('content').value
        })
    })
    .then(res => res.text())
    .then(res => { document.getElementById('nieuwsbriefResponse').innerText = res })
    .catch(e => console.log(e));
}

document.getElementById('verzendNieuwsbrief').addEventListener("click", stuurNieuwsbrief);

let voegToeAanCatalogus = () => {
    fetch(catalogusURL, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "naam": document.getElementById("catalogusNaam").value,
            "kritischeWaarde": document.getElementById('kritischeWaarde').value,
            "beschrijving": document.getElementById("beschrijving").value,
            "voorschriftNoodzakelijk": document.getElementById("voorschriftNoodzakelijk").value,
            "prijs": document.getElementById("prijs").value,
            "gewensteTemperatuur": document.getElementById("gewensteTemperatuur").value
        })
    })
    .then(res => res.text())
    .then(res => {
        document.getElementById('voegToeAanCatalogusResponse').innerText=res;
    })
    .catch(e => console.log(e));
}

document.getElementById("voegToeAanCatalogus").addEventListener("click", voegToeAanCatalogus);

let verwijderUitCatalogus = ()=>{
    const id = document.getElementById("teVerwijderenId").value;
    fetch(catalogusURL+'/'+id,{
        method: 'DELETE'
    }).then(res => {
        document.getElementById('verwijderUitCatalogusResponse').innerText = res.text();
    }).catch(e=> console.log(e));
};

document.getElementById("verwijderUitCatalogus").addEventListener("click", verwijderUitCatalogus);

let bestelMedicijnBij = () => {
    fetch(bestelBoekhoudURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "medicijn": document.getElementById("medicijn").value,
            "aantal": document.getElementById("aantal").value
        })
    })
    .then(res => res.text())
    .then(response => { document.getElementById("bestelMedicijnResponse").innerText = response; })
        .catch(e => console.log(e));
}

document.getElementById("bestelMedicijnBtn").addEventListener("click", bestelMedicijnBij);

// ############### KOERIER #####################

let koerierHaalRolcontainerOp = () => {
    fetch(koerierURL, {
        method: 'POST'
    })
    .then(res => res.text())
        .then(response => { document.getElementById("rolcontainerOpgehaaldResponse").innerText = response; })
        .catch(e => console.log(e));
}

document.getElementById("haalRolcontainerOp").addEventListener("click", koerierHaalRolcontainerOp);

// ############## TRANSPORTEUR ####################

let verwerkLading = () => { 
    fetch(verwerkLadingURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: document.getElementById('lading').value
    })
    .then(res => {
        if(res.ok){
            return "medicijnen geleverd, zijn toegevoegd in de databank"
        }
        return res.text();
    })
    .then(res => {
        document.getElementById('verwerkLadingResponse').innerText = res;
    }).catch(e => console.log(e));
}

document.getElementById('verwerkLading').addEventListener("click", verwerkLading);

// ############### AFVALOPHAALDIENST ############################

let haalAfValOp = () => {
    fetch(orderURL, {
        method: 'PUT'
    })
    .then(response => response.text())
    .then(response => document.getElementById("afvalOpgehaaldResponse").innerText=response)
    .catch(e => console.log(e));
}

document.getElementById("haalAfvalOpButton").addEventListener("click", haalAfValOp);

// #################### MAGAZIJNBEHEERDER ######################################

let geefOverzicht = () => {
    fetch(voorraadOverzichtURL, {
        method: 'GET'
    })
    .then(res => res.json())
    .then(json => {
        let resultString = "";
        json.forEach(el => {
            resultString += "medicijnId: " + el.medicijnId + ", aantal: " + el.aantal + "&#13;&#10;";
        });
        document.getElementById("geefOverzichtResponse").innerHTML=resultString;
    })
    .catch(e => console.log(e));
}

document.getElementById("geefOverzichtButton").addEventListener("click", geefOverzicht);

// ##################### BOEKHOUDER #############################

let betaalLeverancier = () => {
    fetch(betaalBoekhoudURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "leverancier": document.getElementById("leverancier").value,
            "bedrag": document.getElementById("bedrag").value
        })
    })
    .then(res => res.text())
    .then(response => { document.getElementById("betaalLeverancierResponse").innerText = response; })
    .catch(e => console.log(e));
}

document.getElementById("betaalLeverancierBtn").addEventListener("click", betaalLeverancier);


