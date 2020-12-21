package be.ugent.systemdesign.group6.bestellingen.domain.seedwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot {

    private final List<DomainEvent> events = new ArrayList<>();

    public List<DomainEvent> getDomainEvents(){
        return Collections.unmodifiableList(events);
    }

    public void clearDomainEvents(){
        events.clear();
    }

    public void addDomainEvent(DomainEvent event){
        events.add(event);
    }
}
