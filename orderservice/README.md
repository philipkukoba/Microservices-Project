
op maak_order kanaal: <br>
in: <br>
{"id": "baa", "thuisAdres": "thuisadres", "apotheekAdres":"apotheek adres" , "medicijnen": [{"id":"3","naam":"Medicijn","voorschrift":"true","prijs":"0.1","aantal":"2"}]}
{"id": "afvgdf", "thuisAdres": "thuisadres", "apotheekAdres":"apotheek adres" , "medicijnen": [{"id":"3","naam":"Medicijn","voorschrift":"true","prijs":"0.1","aantal":"2"}]}

als gevolg op bestelling_compleet_event kanaal
{"id":"afvgdf"}


annuleren:
in:
{"id":"aaaa", "responseDestination":"annuleer_bestelling"}
{"id":"baa", "responseDestination":"annuleer_bestelling_response"}

eventueel uit: 
{"message":"Pakket was nog niet samengesteld kan nog succesvol geannuleerd worden.","status":"SUCCESS","id":"baa"}
