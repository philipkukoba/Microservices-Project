package be.ugent.systemdesign.group6.verzendingsdienst.API;

import be.ugent.systemdesign.group6.verzendingsdienst.application.command.BestellingGeannuleerdCommand;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.CommandDispatcher;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.PlaatsGeannuleerdeBestellingTerugCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandDispatcherImpl implements CommandDispatcher {

    @Autowired
    MessageOutputGateway outputGateway; //negeer rode onderlijning

    @Override
    public void plaatsGeannuleerdeBestellingTerugCommand(PlaatsGeannuleerdeBestellingTerugCommand command) {
        outputGateway.sendPlaatsGeannuleerdeBestellingTerugCommand(command);
    }

    @Override
    public void bestellingGeannuleerdCommand(BestellingGeannuleerdCommand command) {
        outputGateway.sendBestellingGeannuleerdCommand(command);
    }
}
