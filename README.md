# Volledig uitgewerkte projecten volgens de normen
- Bestellingen
- Medicijnen
- Order
- Verzendingsdienst

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
De gateway werkt niet op kubernetes. Om de services toch te kunnen testen hebben we de services hun poort laten 'open staan'.
Om de frontend te doen werken met de gateway (via docker) maken we gebruik van een CORS proxy. 
Deze is toegevoegd aan docker.

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

### Bestelling plaatsen als ticketdienstmedewerker

Hierbij wordt geen betaling gesimuleerd omdat er vanuit gegaan wordt dat een ticketdienstmedewerker een bestelling zou plaatsen wanneer er een fout is gebeurd en er dus niet opnieuw betaald moet worden.

## Ticketdienst service

## Medicijnen service

## Verzendingsdienst service

## Orderdienst service

## Boekhoudsdienst service
