package be.ugent.systemdesign.group6.medicijnen.API.bus;

import be.ugent.systemdesign.group6.medicijnen.application.command.out.CommandDispatcher;
import be.ugent.systemdesign.group6.medicijnen.application.command.out.PlaatsBijAfvalCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandDispatcherImpl implements CommandDispatcher {

    private static final Logger log = LoggerFactory.getLogger(CommandDispatcherImpl.class);

    @Autowired
    private MessageOutputGateway messageOutputGateway;

    @Override
    public void stuurPlaatsBijAfval(PlaatsBijAfvalCommand c) {
        log.info("OUT -> command: plaats {} bij afval {} eenheden", c.getId(), c.getAantal());
        messageOutputGateway.stuurPlaatsBijAfvalCommand(c);
    }
}
