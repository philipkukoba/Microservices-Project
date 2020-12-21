package be.ugent.systemdesign.group6.medicijnen.API.bus;

import be.ugent.systemdesign.group6.medicijnen.application.command.in.BevestigBestellingCommand;
import be.ugent.systemdesign.group6.medicijnen.application.command.in.CommandHandler;
import be.ugent.systemdesign.group6.medicijnen.application.command.in.GeefVrijBestellingCommand;
import be.ugent.systemdesign.group6.medicijnen.application.command.in.ReserveerBestellingCommand;
import be.ugent.systemdesign.group6.medicijnen.application.command.out.ReserveerBestellingAntwoord;
import be.ugent.systemdesign.group6.medicijnen.application.event.EventHandler;
import be.ugent.systemdesign.group6.medicijnen.application.event.in.AfwijkendeTemperatuurEvent;
import be.ugent.systemdesign.group6.medicijnen.application.event.in.BestellingAnnulerenVoltooidEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MessageInputGateway {
    @Autowired
    private EventHandler eventHandler;

    @Autowired
    private Channels channels;

    @Autowired
    private CommandHandler commandHandler;

    @StreamListener(Channels.AFWIJKENDE_TEMPERATUUR)
    public void verwerkAfwijkendeTemperatuurEvent(AfwijkendeTemperatuurEvent e) {
        eventHandler.verwerkAfwijkendeTemperatuur(e);
    }

    @StreamListener(Channels.BESTELLING_ANNULEREN_VOLTOOID)
    public void verwerkBestellingAnnulerenVoltooid(BestellingAnnulerenVoltooidEvent e) {
        eventHandler.verwerkBestellingAnnulerenVoltooid(e);
    }

    @StreamListener(Channels.RESERVEER_BESTELLING)
    public void verwerkReserveerBestellingCommand(ReserveerBestellingCommand c) {
        ReserveerBestellingAntwoord antwoord = commandHandler.reserveerBestelling(c);
        channels.reserveerBestellingAntwoord().send(
                MessageBuilder.withPayload(antwoord).setHeader("spring.cloud.stream.sendto.destination",
                        c.getAntwoordKanaal()).build()
        );
    }

    @StreamListener(Channels.BEVESTIG_BESTELLING)
    public void verwerkBevestigBestellingCommand(BevestigBestellingCommand c) {
        commandHandler.bevestigBestelling(c);
    }

    @StreamListener(Channels.GEEF_VRIJ_BESTELLING)
    public void verwerkGeefVrijBestellingCommand(GeefVrijBestellingCommand c) {
        commandHandler.geefVrijBestelling(c);
    }
}
