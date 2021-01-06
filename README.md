# Volledig uitgewerkte projecten volgens de normen
- Bestellingen (De Baer Brecht)
- Medicijnen (Vaneeckhoutte Gauthier)
- Order (Callebaut Paulien)
- Verzendingsdienst (Kukoba Philip)

# JARs compileren
Om de jars te compileren, is er een script `make-jars.sh` voorzien. Dat kan je terugvinden
in de root van dit project. Bij het uitvoeren kan het zijn dat er problemen optreden in
verband met mnvw. Dit kan opgelost worden door de line endings van de `mvnw` bestanden in
elk afzonderlijk project steeds op LF te plaatsen. 

# Docker-compose
Indien je problemen ervaart bij het bouwen van Java applicaties, zorg er dan
voor dat de line endings bij de `mvnw` bestanden in de root directories van deze
mappen op LF staat. Dit kan met visual studio code.

# Gebreken
De gateway werkt niet op kubernetes, maar wel op docker. We hebben hiervoor reeds contact opgenomen met Van Havermaet Stef. Daaruit bleek dat we dit achterwege mogen laten en zal er rekening mee gehouden worden. Bijgevolg werkt dus de frontend ook niet met kubernetes.

De reply channel bij een commando werkt niet. De reply channel wordt wel meegegeven maar het mechanisme werkt enkel als we een vooraf afgesproken channel meegeven. 

Op de frontend traden enkele problemen op in verband met CORS aangezien we op elke service all origins toelaten, hebben we dit dan maar eenvoudigweg
opgelost door een CORS proxy te gebruiken aangezien dit niet de kern van de opdracht was.

# Testen op frontend (en docker)
De frontend staat niet op de cluster aangezien daarvoor alle applicaties moeten samen komen op de ene poort die je door ssh kan forwarden wat niet mogelijk is aangezien de gateway daar niet werkt.

De frontend kan je bereiken op localhost:80.

Er is een rubriek aangemaakt per actor. Per actor zijn de systeemoperaties uitgewerkt op de frontend. Extra info per operatie is ook weergegeven op de frontend.

# Testen op kubernetes cluster
Op de kubernetes cluster is er geen gateway, requests moeten dus rechtstreeks naar de juiste service gestuurd worden. Hiervoor zullen we curl gebruiken. Hieronder vind je de commandos die je op de cluster kan uitvoeren.

## Gebruikers service

### Account aanmaken

`curl -X POST -H 'Content-Type: application/json' -d '{"naam": "naam", "email": "email"}' 10.2.0.179:3000/api/gebruikers/maakaccount`

Na het uitvoeren van dit commando wordt de gebruiker weergegeven in JSON formaat samen met zijn id.

### Nieuwsbrief sturen

`curl -X POST -H 'Content-Type: application/json' -d '{"content": "inhoudNieuwsbrief"}' 10.2.0.179:3000/api/gebruikers/nieuwsbrief`

Deze request geeft de emails terug waarnaar de nieuwsbrief verstuurd is.

## Bestellingen service

### Bestelling plaatsen als klant

De medicijnen worden in json voorgesteld als een hashmap van: {"id": "aantal", "id": "aantal"}.
Onderstaande request zal dus 5 producten van medicijn met id 1, en 5 producten van medicijn met id 2 bestellen.
Let wel op dat de medicijnen die je bestelt op voorraad zijn, dit kan je controleren bij de medicijnservice door de voorraad op te vragen.

`curl -X POST -H 'Content-Type: application/json' -d '{"klantenId": "klantenId", "thuisAdres": "thuis", "apotheekAdres": "apotheek", "medicijnen": {"1": "5", "2": "5"}}' 10.2.0.179:8080/api/bestellingen`

Dit commando returnt in normale omstandigheden dat de bestelling met id ... in behandeling is. Dit wil niet zeggen dat het plaatsen van de bestelling ook effectief zal lukken omdat hier gebruikgemaakt wordt van het vroegtijdig returnen van een synchrone operatie.

### Bestelling plaatsen als ticketdienstmedewerker

Hierbij wordt geen betaling gesimuleerd omdat er vanuit gegaan wordt dat een ticketdienstmedewerker een bestelling zou plaatsen wanneer er een fout is gebeurd en er dus niet opnieuw betaald moet worden.

`curl -X POST -H 'Content-Type: application/json' -d '{"klantenId": "klantenId", "thuisAdres": "thuis", "apotheekAdres": "apotheek", "medicijnen": {"1": "5", "2": "5"}}' 10.2.0.179:8080/api/bestellingen/ticket`

### Bestelling annuleren

`curl -X PUT 10.2.0.179:8080/api/bestellingen/annuleer/{id}`

### Statistieken opvragen

`curl 10.2.0.179:8080/api/statistieken`

## Ticketdienst service

### Ticket openen

`curl -X POST -H 'Content-Type: application/json' -d '{"klantenId": "klantenId", "bestellingsId": "bestellingsId", "probleem": "beschrijving probleem"}' 10.2.0.179:3002/api/ticket/open`

### Ticket behandelen

Let op, ticketid is een integer.

`curl -X POST -H 'Content-Type: application/json' -d '{"id": ticketId}' 10.2.0.179:3002/api/ticket/behandel`

### Ticket sluiten

Let op, ticketid is een integer.

`curl -X POST -H 'Content-Type: application/json' -d '{"id": ticketId}' 10.2.0.179:3002/api/ticket/sluit`

### Tickets opvragen

Dit commando geeft alle openstaande tickets terug

`curl 10.2.0.179:3002/api/ticket`

## Medicijnen service

### Overzicht van voorraad opvragen

`curl 10.2.0.179:8081/api/voorraad/overzicht`

### Nieuw medicijn aan de catalogus toevoegen

De waarde voor voorschriftNoodzakelijk moet 'true' of 'false' zijn.
Indien de gewensteTemperatuur minder dan 16 graden is, zal het in een koelcel bewaard worden.

`curl -X POST -H 'Content-Type: application/json' -d '{"naam": "naam", "beschrijving": "beschrijving", "kritischeWaarde": "200", "voorschriftNoodzakelijk": "true", "prijs": "35","gewensteTemperatuur": "20"}' 10.2.0.179:8081/api/catalogus`

### Medicijn uit de catalogus verwijderen

`curl -X DELETE 10.2.0.179:8081/api/catalogus/{id}`

### Toegekomen lading toevoegen aan de voorraad

`curl -X POST -H 'Content-Type: application/json' -d '{"naam":{"2030-10-05":"3", "2073-05-21":"3" }}' 10.2.0.179:8081/api/voorraad/lading`

## Verzendingsdienst service

### Rolcontainer ophalen

Pas als de rolcontainers opgehaald worden, is de bestelling verzonden.

`curl -X POST 10.2.0.179:8082/api/bpost`

## Orderdienst service

### Afval ophalen

`curl -X PUT 10.2.0.179:2222/api/order/afval/haalAfvalOp/`

## Boekhoudsdienst service

### Bestel nieuw medicijn

`curl -X POST -H 'Content-Type: application/json' -d '{"medicijn":"medicijnId", "aantal":5}' 10.2.0.179:3001/api/boekhoud/bestel`

### Betaal leverancier

`curl -X POST -H 'Content-Type: application/json' -d '{"leverancier":"leverancier", "bedrag":10}' 10.2.0.179:3001/api/boekhoud/betaalLeverancier`
