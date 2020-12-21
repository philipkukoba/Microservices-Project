package be.ugent.systemdesign.group6.verzendingsdienst.application;

import be.ugent.systemdesign.group6.verzendingsdienst.application.command.AnnuleerBestellingResponse;

public interface AnnuleringsDienst {

    //binnenkomend
    AnnuleerBestellingResponse annuleerBestelling(String bestellingId);

    //uitgaand
    Response plaatsGeannulleerdeBestellingTerug(String bestellingId);
    Response bestellingGeannuleerd(String bestellingId);
}
