package be.ugent.systemdesign.group6.bestellingen.API.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

    static final String BESTELLING_GEANNULEERD = "bestelling_geannuleerd_event";
    static final String BESTELLING_COMPLEET = "bestelling_compleet_event";
    static final String BESTELLING_VERZONDEN = "bestelling_verzonden_event";
    static final String GEEF_STATUS_REQUEST = "geef_status_request";
    static final String GEEF_STATUS_RESPONSE = "geef_status_response";
    static final String ANNULEER_BESTELLING_REQUEST = "annuleer_bestelling_request";
    static final String ANNULEER_BESTELLING_RESPONSE = "annuleer_bestelling_response";
    static final String RESERVEER_BESTELLING_REQUEST = "reserveer_bestelling_request";
    static final String RESERVEER_BESTELLING_RESPONSE = "reserveer_bestelling_response";
    static final String GEEF_VRIJ_BESTELLING = "geef_vrij_bestelling";
    static final String BEVESTIG_BESTELLING = "bevestig_bestelling";
    static final String MAAK_ORDER = "maak_order";
    static final String MAAK_FACTUUR = "maak_factuur";

    @Output(BESTELLING_GEANNULEERD)
    MessageChannel bestellingGeannuleerd();

    @Input(BESTELLING_COMPLEET)
    SubscribableChannel bestellingCompleet();

    @Input(BESTELLING_VERZONDEN)
    SubscribableChannel bestellingVerzonden();

    @Input(GEEF_STATUS_REQUEST)
    SubscribableChannel geefStatusRequest();

    @Output(GEEF_STATUS_RESPONSE)
    MessageChannel geefStatusResponse();

    @Output(ANNULEER_BESTELLING_REQUEST)
    MessageChannel annuleerBestellingRequest();

    @Input(ANNULEER_BESTELLING_RESPONSE)
    SubscribableChannel annuleerBestellingResponse();

    @Output(RESERVEER_BESTELLING_REQUEST)
    MessageChannel reserveerBestellingRequest();

    @Input(RESERVEER_BESTELLING_RESPONSE)
    SubscribableChannel reserveerBestellingResponse();

    @Output(GEEF_VRIJ_BESTELLING)
    MessageChannel geefVrijBestelling();

    @Output(BEVESTIG_BESTELLING)
    MessageChannel bevestigBestelling();

    @Output(MAAK_ORDER)
    MessageChannel maakOrder();

    @Output(MAAK_FACTUUR)
    MessageChannel maakFactuur();
}
