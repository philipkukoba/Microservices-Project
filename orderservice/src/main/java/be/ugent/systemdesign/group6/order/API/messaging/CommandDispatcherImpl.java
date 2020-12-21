package be.ugent.systemdesign.group6.order.API.messaging;

import be.ugent.systemdesign.group6.order.application.command.CommandDispatcher;
import be.ugent.systemdesign.group6.order.application.command.HaalAfvalOpCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandDispatcherImpl implements CommandDispatcher {

    @Autowired
    MessageOutputGateway outputGateway;

    @Override
    public void stuurHaalAfvalOpCommand(HaalAfvalOpCommand command) {
        outputGateway.stuurHaalAfvalOpCommand(command);
    }
}
