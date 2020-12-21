package be.ugent.systemdesign.group6.medicijnen.domain.seedwork;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {
    private List<DomainEvent> events = new ArrayList<>();

    public List<DomainEvent> getDomainEvents() {
        return events;
    }

    public void voegDomainEventToe(DomainEvent domainEvent) {
        events.add(domainEvent);
    }

    public void verwijderAlleDomainEvents() {
        events = new ArrayList<>();
    }
}
