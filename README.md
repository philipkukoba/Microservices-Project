# Volledig uitgewerkte projecten volgens de normen
- Bestellingen: Brecht De Baer
- Medicijnen: Gauthier Vaneeckhoutte
- Order: Paulien Callebaut
- Verzendingsdienst: Philip Kukoba

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
De gateway werkt niet op kubernetes, maar wel op docker. We hebben hiervoor reeds contact opgenomen met Stef Van Havermaet. Daaruit bleek dat we dit achterwege mogen laten en zal er rekening mee gehouden worden. Bijgevolg werkt dus de frontend ook niet met kubernetes.

De reply channel bij een commando werkt niet. De reply channel wordt wel meegegeven maar het mechanisme werkt enkel als we een vooraf afgesproken channel meegeven. 

Op de frontend traden enkele problemen op in verband met CORS aangezien we op elke service all origins toelaten, hebben we dit dan maar eenvoudigweg
opgelost door een CORS proxy te gebruiken aangezien dit niet de kern van de opdracht was.

# Testen op frontend en docker
Er is een rubriek aangemaakt per actor. Per actor zijn de systeemoperaties uitgewerkt op de frontend. Extra info per operatie is ook weergegeven op de frontend.

# Testen op kubernetes cluster
Op de kubernetes cluster is er geen gateway, requests moeten dus rechtstreeks naar de juiste service gestuurd worden. Hiervoor zullen we curl gebruiken.

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


### Statistieken opvragen


## Ticketdienst service


## Medicijnen service


## Verzendingsdienst service


## Orderdienst service
Afval ophalen 
`curl -X PUT 10.2.0.179:2222/api/order/afval/haalAfvalOp/`



## Boekhoudsdienst service
bestelNieuwMedicijn
`curl -X POST -H 'Content-Type: application/json' -d '{"medicijn":"medicijn", "aantal":5}' 10.2.0.179:3001/api/boekhoud/bestel`

betaalLeverancier
`curl -X POST -H 'Content-Type: application/json' -d '{"leverancier":"leverancier", "bedrag":10}' 10.2.0.179:3001/api/boekhoud/betaalLeverancier`
