package be.ugent.systemdesign.group6.verzendingsdienst.API.messaging;

import be.ugent.systemdesign.group6.verzendingsdienst.API.messaging.Channels;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.BestellingGeannuleerdCommand;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.PlaatsGeannuleerdeBestellingTerugCommand;
import be.ugent.systemdesign.group6.verzendingsdienst.application.event.EventDispatcher;
import be.ugent.systemdesign.group6.verzendingsdienst.application.event.OrderVerzondenEvent;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageOutputGateway extends EventDispatcher {

    @Gateway(requestChannel = Channels.ORDERVERZONDEN_EVENT)
    void publishOrderVerzondenEvent(OrderVerzondenEvent event);

    @Gateway(requestChannel = Channels.PLAATSGEANNULEERDEBESTELLINGTERUG_COMMAND)
    void sendPlaatsGeannuleerdeBestellingTerugCommand(PlaatsGeannuleerdeBestellingTerugCommand command);

    @Gateway(requestChannel = Channels.BESTELLINGGEANNULEERD_COMMAND)
    void sendBestellingGeannuleerdCommand(BestellingGeannuleerdCommand command);
}
