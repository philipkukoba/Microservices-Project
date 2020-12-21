package be.ugent.systemdesign.group6.order.application.event;

import be.ugent.systemdesign.group6.order.domain.OrderCompleetEvent;

public interface EventDispatcher {
	
	void publishOrderCompleetEvent(OrderCompleetEvent event);
	
}
