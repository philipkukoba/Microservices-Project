# sysdes6

## Afval (poort 8082)
-- Afval ophalen via put op /api/order/afval/haalAfvalOp: **GELUKT** <br>
--> zijn volle containers leeg & andere niet leeg: **GELUKT** <br>
-- weggooien van medicijn dat overtijd is: **GELUKT** <br>
-- weggooien als reactie op ticketdienst: **GELUKT** <br>

## Bestelling plaatsen

Hiervoor eerst medicijnen aan catalogus toevoegen, lading verwerken en klant moet in klantenbestand zitten.
<br>
-- medicijnen verwerken
<br>
-- klant in gebruikers toevoegen op poort 3000 /api/gebruikers/maakaccount 
<br>

<br> 
Uitvoeren als klant: **Testing**
<br> 
Uitvoeren al ticketdienstmedewerker: **Testing**
<br>
1. Klant
<br>
--Geval wanneer betaling mislukt: **GELUKT**
<br>
Gevolg : er is niet gereserveerd en er is niets te zien in de bestelling_db
<br>
--Geval wanneer er niet genoeg voorraad is: **GELUKT**
  <br>
  --Geval wanneer alles zou moeten lukken: Testing
  <br>
	--> direct in bestellingen_db geplaatst: **GELUKT** <br>
	--> command naar medicijnen: **GELUKT** <br>
	--> verandring in medicijnen_db, 'GERESERVEERD': **GELUKT** <br>
	--> veranderingeng in bestellingendb: **GELUKT** <br>
	--> maak order op maak_order channel
		--> order_db aangepast: **GELUKT** <br>
		--> order compleet sturen: **GELUKT** <br>
			--> bestelling db aanpepast: **GELUKT** <br>
			--> verzendingsdienst krijgt 1/2 events: **GELUKT** <br>
				-->  verzendings dienst db bevat gelabeld pakket: **NIeT BEkEKEN** <br>
	--> maak facuur (moet gebeuren op maak_factuur kanaal)
		--> klant krijgt email: **MISLUKT** <br>

2. TicketDienst
--bestellen: **GELUKT** <br>


## verzenden

--rolcontainer is opgehaald (via kafka command): **GELUKT** <br>
	-- verzending stuurt orderverzonden uit: **GELUKT** <br>
	-- bestellings db aangepast: **GELUKT** <br>
	-- mail vertuurd: **MISLUKT** <br>
	
## statistieken

-- gelukt: **GELUKT** <br>

## gebruikers
-- account aanmaken op /api/maakAccount: **GELUKT** <br>


## medicijnen

-- voeg medicijnen toe: **GELUKT** <br>
--verwerk lading: **GELUKT** <br>
--geef oevrzicht: **GELUKT** <br>

## tickets
-- /api/ticket: **GELUKT** <br>

--ticket aanmaken <br>: **GELUKT** <br>
--> staat nu bij get? <br>: **GELUKT** <br>
-- behandel: **GELUKT** <br>
--> kleine kans retour bij afval: **GELUKT** <br>
--> check afval containers?? <br>: **GELUKT** <br>
-- sluiten <br>: **GELUKT** <br>

## annuleren

-- annuleren van een bestelling
	kan gedaan worden door klant en tcketdienstmedewerker
	-- komt binnen bij bestelilingen
		status = nog niet op verzonden
			nog in magazijn
			nog bij verzending
				medicijnen_db checken (terug open gezet)
				boekhoud zal loggen 

			toch al verzonden
		status == verzonden
			geeft direct antw dit het niet gaat 
