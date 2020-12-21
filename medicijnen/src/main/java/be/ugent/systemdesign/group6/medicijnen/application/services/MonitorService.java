package be.ugent.systemdesign.group6.medicijnen.application.services;

import be.ugent.systemdesign.group6.medicijnen.application.Antwoord;

public interface MonitorService {
    /* Deze methode wordt gebruikt bij de scheduled task om te controleren
    of er geen vervallen producten in de voorraad zitten
    */
    Antwoord checkVervalData();

    Antwoord verwijderTeHogeTemperatuur(int koelCelId, double temperatuur);
}
