# sysdes6

## Afval
-- Afval ophalen via put op /api/order/afval/haalAfvalOp 
--> zijn volle containers leeg & andere niet leeg
<br>
weggooien van medicijn dat overtijd was: **GELUKT**

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
Uitvoren al ticketdienstmedewerker: **Testing**
<br>
1. Klant
(note: betaling lukt vaak niet)
<br>
--Geval wanneer betaling mislukt: **GELUKT**
<br>
Gevolg : er is niet gereserveerd en er is niets te zien in de bestelling_db
<br>
--Geval wanneer er niet genoeg voorraad is: Testing
  <br>
  --Geval wanneer alles zou moeten lukken: Testing
  <br>
	direct in bestellingen_db geplaatst: **GELUKT** <br>
	--> command naar medicijnen: **GELUKT** <br>
	--> verandring in medicijnen_db, 'GERESERVEERD': **GELUKT** <br>
	--> veranderingeng in bestellingendb: **GELUKT** <br>
	--> maak order op maak_order channel
		--> order_db aangepast: **GELUKT** <br>
		--> order compleet sturen: **GELUKT** <br>
			--> bestelling db aanpepast
			--> verzendingsdienst krijgt 1/2 events 
				-->  verzendings dienst db bevat gelabeld pakket
	--> maak facuur: **GELUKT** <br>
		--> klant krijgt email

--rolcontainer is opgehaald (via kafka command)
	-- verzending stuurt orderverzonden uit
	-- bestellings db aangepast
	-- mail vertuurd 

--ticket aanmaken
	--> staat nu bij get?
	-- behandel --> kleine kans retour bij afval --> check afval containers??
	-- sluiten



-- annuleren van een bestelling
	kan gedaan worden door klant en tcketdienstmedewerker
	-- komt binnen bij bestelilingen
		status == nog niet op verzonden
			nog in magazijn
			nog bij verzending
				medicijnen_db checken (terug open gezet)
				boekhoud zal loggen 

			toch al verzonden
		status == verzonden
			geeft direct antw dit het niet gaat 
