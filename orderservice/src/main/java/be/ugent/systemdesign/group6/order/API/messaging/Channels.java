package be.ugent.systemdesign.group6.order.API.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
    // Communicatie met medicijnen service
    static final String PLAATS_BIJ_AFVAL_REQUEST = "plaats_bij_afval_request";

    //Communicatie met Ticketdienst service
    static final String PLAATS_RETOUR_BIJ_AFVAL_REQUEST = "plaats_retour_bij_afval_request";

    static final String MEDICIJN_CATALOGUS_VERWIJDERD_EVENT = "medicijn_catalogus_verwijderd_event";

    static final String BESTELLING_COMPLEET_EVENT ="bestelling_compleet_event";

    static final String MAAK_ORDER = "maak_order";

    static final String ANNULEER_BESTELLING_REQUEST = "annuleer_bestelling_request";

    static final String ANNULEER_BESTELLING_RESPONSE = "annuleer_bestelling_response";

    static final String HAAL_AFVAL_OP = "haal_afval_op";

    @Input(PLAATS_BIJ_AFVAL_REQUEST)
    SubscribableChannel plaatsBijAfvalResponse();

    @Input(PLAATS_RETOUR_BIJ_AFVAL_REQUEST)
    SubscribableChannel plaatsRetourBijAfvalResponse();

    @Input(MEDICIJN_CATALOGUS_VERWIJDERD_EVENT)
    SubscribableChannel catalogusEvent();

    @Output(BESTELLING_COMPLEET_EVENT)
    MessageChannel bestellingCompleetEvent();

    @Input(MAAK_ORDER)
    SubscribableChannel maakOrderCommand();

    @Input(ANNULEER_BESTELLING_REQUEST)
    SubscribableChannel annuleerBestellingCommand();

    @Output(ANNULEER_BESTELLING_RESPONSE)
    MessageChannel annuleerBestellingResponse();

    @Output(HAAL_AFVAL_OP)
    MessageChannel haalAfvalOpCommand();

}
