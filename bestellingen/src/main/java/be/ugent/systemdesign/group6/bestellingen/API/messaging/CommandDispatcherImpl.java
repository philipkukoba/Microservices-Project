package be.ugent.systemdesign.group6.bestellingen.API.messaging;

import be.ugent.systemdesign.group6.bestellingen.application.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommandDispatcherImpl implements CommandDispatcher {

    @Autowired
    MessageOutputGateway outputGateway;

    @Value("${spring.cloud.stream.bindings." + Channels.ANNULEER_BESTELLING_RESPONSE + ".destination}")
    String annuleerBestellingResponseDestination;

    @Value("${spring.cloud.stream.bindings." + Channels.RESERVEER_BESTELLING_RESPONSE + ".destination}")
    String reserveerBestellingResponseDestination;

    @Override
    public void sendAnnuleerBestellingCommand(AnnuleerBestellingCommand command) {
        outputGateway.sendAnnuleerbestellingCommand(
                new AnnuleerBestellingCommand(command.getId(), annuleerBestellingResponseDestination)
        );
    }

    @Override
    public void sendReserveerBestellingCommand(ReserveerBestellingCommand command) {
        outputGateway.sendReserveerBestellingCommand(
                new ReserveerBestellingCommand(command.getId(), command.getMedicijnen(), reserveerBestellingResponseDestination)
        );
    }

    @Override
    public void sendGeefVrijBestellingCommand(GeefVrijBestellingCommand command){
        outputGateway.sendGeefVrijBestellingCommand(command);
    }

    @Override
    public void sendBevestigBestellingCommand(BevestigBestellingCommand command) {
        outputGateway.sendBevestigBestellingCommand(command);
    }

    @Override
    public void sendMaakOrderCommand(MaakOrderCommand command) {
        outputGateway.sendMaakOrderCommand(command);
    }

    @Override
    public void sendMaakFactuurCommand(MaakFactuurCommand command) {
        outputGateway.sendMaakFactuurCommand(command);
    }
}
