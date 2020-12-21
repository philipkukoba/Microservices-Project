package be.ugent.systemdesign.group6.bestellingen.domain;

import be.ugent.systemdesign.group6.bestellingen.domain.seedwork.DomainEvent;
import lombok.Getter;

public class BestellingGeannuleerdEvent extends DomainEvent {

    @Getter
    private String id;

    public BestellingGeannuleerdEvent(String id){
        super();
        this.id = id;
    }
}
