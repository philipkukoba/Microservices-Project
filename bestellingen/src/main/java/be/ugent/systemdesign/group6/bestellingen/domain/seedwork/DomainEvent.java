package be.ugent.systemdesign.group6.bestellingen.domain.seedwork;

import lombok.Getter;

import java.time.LocalDateTime;

public abstract class DomainEvent {

    @Getter
    private LocalDateTime createdTime;

    public DomainEvent(){
        this.createdTime = LocalDateTime.now();
    }
}
