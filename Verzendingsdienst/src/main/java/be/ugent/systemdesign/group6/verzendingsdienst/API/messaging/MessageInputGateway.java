package be.ugent.systemdesign.group6.verzendingsdienst.API.messaging;

import be.ugent.systemdesign.group6.verzendingsdienst.API.messaging.Channels;
import be.ugent.systemdesign.group6.verzendingsdienst.application.ResponseStatus;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.AnnuleerBestellingCommand;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.AnnuleerBestellingResponse;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.CommandHandler;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.HaalRolContainerOpCommand;
import be.ugent.systemdesign.group6.verzendingsdienst.application.event.EventHandler;
import be.ugent.systemdesign.group6.verzendingsdienst.application.event.OrderCompleetEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MessageInputGateway {

    private final Logger logger = Logger.getLogger(Logger.class.getName());

    @Autowired
    EventHandler eventHandler;

    @Autowired
    CommandHandler commandHandler;

    @Autowired
    Channels channels;

    //event
    @StreamListener(Channels.ORDERCOMPLEET_EVENT)
    public void consumeOrderCompleetEvent(OrderCompleetEvent event){
        eventHandler.handleOrderCompleet(event);
    }

    //command
    @StreamListener(Channels.ANNULEERBESTELLING_COMMAND)
    public void receiveAnnuleerBestellingCommand(AnnuleerBestellingCommand command) throws InterruptedException {
        logger.info("net voor commandHandler.handleAnnuleerBestelling");
        AnnuleerBestellingResponse r = commandHandler.handleAnnuleerBestelling(command);
        if (r.status == ResponseStatus.GELUKT){
            channels.annuleerBestellingResponse().send(
                            MessageBuilder
                            .withPayload(r)
                            .setHeader("spring.cloud.stream.sendto.destination", command.getResponseDestination() )
                            .build()
            );
        }
    }

    //testing haalrolcontainerop
    /*
    @StreamListener(Channels.HAAL_ROLCONTAINER_OP_COMMAND)
    public void haalRolContainerOpCommand(HaalRolContainerOpCommand command){
        commandHandler.haalRolcontainerOpCommand();
    } */

}
