package be.ugent.systemdesign.group6.bestellingen.API.messaging;

import be.ugent.systemdesign.group6.bestellingen.application.command.*;
import be.ugent.systemdesign.group6.bestellingen.application.event.EventDispatcher;
import be.ugent.systemdesign.group6.bestellingen.domain.BestellingGeannuleerdEvent;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageOutputGateway extends EventDispatcher {

    @Gateway(requestChannel = Channels.BESTELLING_GEANNULEERD)
    void publishBestellingEvent(BestellingGeannuleerdEvent e);

    @Gateway(requestChannel = Channels.ANNULEER_BESTELLING_REQUEST)
    void sendAnnuleerbestellingCommand(AnnuleerBestellingCommand command);

    @Gateway(requestChannel = Channels.RESERVEER_BESTELLING_REQUEST)
    void sendReserveerBestellingCommand(ReserveerBestellingCommand command);

    @Gateway(requestChannel = Channels.GEEF_VRIJ_BESTELLING)
    void sendGeefVrijBestellingCommand(GeefVrijBestellingCommand command);

    @Gateway(requestChannel = Channels.BEVESTIG_BESTELLING)
    void sendBevestigBestellingCommand(BevestigBestellingCommand command);

    @Gateway(requestChannel = Channels.MAAK_ORDER)
    void sendMaakOrderCommand(MaakOrderCommand command);

    @Gateway(requestChannel = Channels.MAAK_FACTUUR)
    void sendMaakFactuurCommand(MaakFactuurCommand command);
}
