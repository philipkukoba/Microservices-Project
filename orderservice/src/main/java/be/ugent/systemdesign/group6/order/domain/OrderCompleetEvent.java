package be.ugent.systemdesign.group6.order.domain;

import be.ugent.systemdesign.group6.order.domain.seedwork.DomainEvent;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class OrderCompleetEvent extends DomainEvent {
    private String id;

    private static final Logger log = LoggerFactory.getLogger(OrderCompleetEvent.class);

    public OrderCompleetEvent(String id) {
        super();
        this.id = id;
        log.info("Nieuw order compleet event is aangemaakt.");
    }
}
