package be.ugent.systemdesign.group6.order.API.messaging;

import be.ugent.systemdesign.group6.order.application.command.HaalAfvalOpCommand;
import be.ugent.systemdesign.group6.order.application.event.EventDispatcher;
import be.ugent.systemdesign.group6.order.domain.OrderCompleetEvent;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;


@MessagingGateway
public interface MessageOutputGateway extends EventDispatcher {
	
	@Gateway(requestChannel = Channels.BESTELLING_COMPLEET_EVENT)
	void publishOrderCompleetEvent(OrderCompleetEvent event);

	@Gateway(requestChannel = Channels.HAAL_AFVAL_OP)
	void stuurHaalAfvalOpCommand(HaalAfvalOpCommand command);
}

