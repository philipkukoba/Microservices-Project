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