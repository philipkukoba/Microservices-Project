package be.ugent.systemdesign.group6.verzendingsdienst.API;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

//voor elke event en command
public interface Channels {

    //events
    static final String ORDERVERZONDEN_EVENT = "orderverzonden_event";
    static final String ORDERCOMPLEET_EVENT = "bestelling_compleet_event"; //veranderd voor orderService

    //commands
    static final String PLAATSGEANNULEERDEBESTELLINGTERUG_COMMAND = "plaatsgeannuleerdebestellingterug_command";
    static final String BESTELLINGGEANNULEERD_COMMAND = "bestellinggeannuleerd_command";
    static final String ANNULEERBESTELLING_COMMAND = "annuleer_bestelling_request";

    //antw op annuleerBestellingCommand
    static final String ANNULEER_BESTELLING_RESPONSE = "annuleer_bestelling_response";

    //test haalrolcontainerop
    static final String HAAL_ROLCONTAINER_OP_COMMAND = "haal_rolcontainer_op_command";

    //events
    @Input(ORDERCOMPLEET_EVENT)
    SubscribableChannel orderCompleet();

    @Output(ORDERVERZONDEN_EVENT)
    MessageChannel orderVerzonden();

    //commands
    @Input(ANNULEERBESTELLING_COMMAND)
    SubscribableChannel annuleerBestelling();

    @Output(PLAATSGEANNULEERDEBESTELLINGTERUG_COMMAND)
    MessageChannel plaatsGeannuleerdeBestellingTerug();

    @Output(BESTELLINGGEANNULEERD_COMMAND)
    MessageChannel bestellingGeannuleerd();

    @Output(ANNULEER_BESTELLING_RESPONSE)
    MessageChannel annuleerBestellingResponse();

    //testing
    @Input(HAAL_ROLCONTAINER_OP_COMMAND)
    SubscribableChannel haalRolContainerOp();
}
