package be.ugent.systemdesign.group6.order.API.messaging;

import be.ugent.systemdesign.group6.order.application.ResponseStatus;
import be.ugent.systemdesign.group6.order.application.command.*;
import be.ugent.systemdesign.group6.order.application.event.EventHandler;
import be.ugent.systemdesign.group6.order.application.event.MedicijnCatalogusVerwijderdEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MessageInputGateway {

    @Autowired
    CommandHandler commandhandler;

    @Autowired
    EventHandler eventHandler;

    @Autowired
    Channels channels;


    @StreamListener(Channels.PLAATS_BIJ_AFVAL_REQUEST)
    public void ontvangPlaatsBijAfvalCommand(PlaatsBijAfvalCommand command) {
        commandhandler.plaatsBijAfval(command);
    }

    @StreamListener(Channels.PLAATS_RETOUR_BIJ_AFVAL_REQUEST)
    public void ontvangPlaatsRetourBijAfvalCommand(PlaatsRetourBijAfvalCommand command) {
        commandhandler.plaatsRetourBijAfval(command);
    }

    @StreamListener(Channels.MEDICIJN_CATALOGUS_VERWIJDERD_EVENT)
    public void verwerkcatalogusEvent(MedicijnCatalogusVerwijderdEvent event){
        eventHandler.handleMedicijnCatalogusVerwijderd(event);
    }

    @StreamListener(Channels.MAAK_ORDER)
    public void maakOrderCommand(MaakOrderCommand command){
        commandhandler.maakOrder(command);
    }

    @StreamListener(Channels.ANNULEER_BESTELLING_REQUEST)
    public void annuleerBestellingCommand(AnnuleerBestellingCommand command){
        AnnuleerBestellingResponse r = commandhandler.annuleerBestelling(command);
        if( r.status == ResponseStatus.SUCCESS){
            channels.annuleerBestellingResponse().send(
                    MessageBuilder
                    .withPayload(r)
                    .setHeader("spring.cloud.stream.sendto.destination", command.getResponseDestination() )
                    .build()
            );
        }
    }
}
