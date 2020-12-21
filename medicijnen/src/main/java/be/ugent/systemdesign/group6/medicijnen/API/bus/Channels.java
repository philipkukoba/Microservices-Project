package be.ugent.systemdesign.group6.medicijnen.API.bus;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

    String MEDICIJN_KRITISCHE_WAARDE = "medicijn_kritische_waarde";

    String PLAATS_BIJ_AFVAL = "plaats_bij_afval";

    String AFWIJKENDE_TEMPERATUUR = "afwijkende_temperatuur";

    String RESERVEER_BESTELLING = "reserveer_bestelling_request";

    String BEVESTIG_BESTELLING = "bevestig_bestelling";

    String GEEF_VRIJ_BESTELLING = "geef_vrij_bestelling";

    String BESTELLING_ANNULEREN_VOLTOOID = "bestelling_annuleren_voltooid";

    String RESERVEER_BESTELLING_ANTWOORD = "reserveer_bestelling_response";

    String MEDICIJN_CATALOGUS_VERWIJDERD = "medicijn_catalogus_verwijderd";

    @Output(MEDICIJN_KRITISCHE_WAARDE)
    MessageChannel medicijnKritischeWaarde();

    @Output(PLAATS_BIJ_AFVAL)
    MessageChannel plaatsBijAfval();

    @Input(AFWIJKENDE_TEMPERATUUR)
    SubscribableChannel afwijkendeTemperatuur();

    @Input(RESERVEER_BESTELLING)
    SubscribableChannel reserveerBestelling();

    @Input(BEVESTIG_BESTELLING)
    SubscribableChannel bevestigBestelling();

    @Input(GEEF_VRIJ_BESTELLING)
    SubscribableChannel geefVrijBestelling();

    @Output(RESERVEER_BESTELLING_ANTWOORD)
    MessageChannel reserveerBestellingAntwoord();

    @Input(BESTELLING_ANNULEREN_VOLTOOID)
    SubscribableChannel bestellingAnnulerenVoltooid();

    @Output(MEDICIJN_CATALOGUS_VERWIJDERD)
    MessageChannel medicijnCatalogusVerwijderd();
}

