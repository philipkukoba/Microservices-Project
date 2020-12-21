package be.ugent.systemdesign.group6.bestellingen.API.messaging;

import be.ugent.systemdesign.group6.bestellingen.application.command.*;
import be.ugent.systemdesign.group6.bestellingen.application.event.BestellingCompleetEvent;
import be.ugent.systemdesign.group6.bestellingen.application.event.BestellingVerzondenEvent;
import be.ugent.systemdesign.group6.bestellingen.application.event.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MessageInputGateway {

    @Autowired
    EventHandler eventHandler;

    @Autowired
    CommandHandler commandHandler;

    @Autowired
    Channels channels;

    @StreamListener(Channels.BESTELLING_COMPLEET)
    public void consumeBestellingCompleetEvent(BestellingCompleetEvent e){
        eventHandler.handleBestellingCompleet(e);
    }

    @StreamListener(Channels.BESTELLING_VERZONDEN)
    public void consumeBestellingVerzondenEvent(BestellingVerzondenEvent e){
        eventHandler.handleBestellingVerzonden(e);
    }

    @StreamListener(Channels.GEEF_STATUS_REQUEST)
    public void receiveGeefStatusCommand(GeefStatusCommand command){
        GeefStatusResponse response = commandHandler.geefStatus(command);
        channels.geefStatusResponse().send(
                MessageBuilder
                .withPayload(response)
                .setHeader("spring.cloud.stream.sendto.destination", command.getResponseDestination())
                .build()
        );
    }

    @StreamListener(Channels.ANNULEER_BESTELLING_RESPONSE)
    public void receiveAnnuleerBestellingResponse(AnnuleerBestellingResponse response){
        commandHandler.bestellingGeannuleerd(response);
    }

    @StreamListener(Channels.RESERVEER_BESTELLING_RESPONSE)
    public void receiveReserveerBestellingResponse(ReserveerBestellingResponse response){
        commandHandler.bestellingGereserveerd(response);
    }
}
