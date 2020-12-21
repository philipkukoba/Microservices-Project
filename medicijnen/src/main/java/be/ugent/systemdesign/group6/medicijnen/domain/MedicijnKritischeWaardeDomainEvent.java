package be.ugent.systemdesign.group6.medicijnen.domain;

import be.ugent.systemdesign.group6.medicijnen.domain.seedwork.DomainEvent;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class MedicijnKritischeWaardeDomainEvent extends DomainEvent {
    private int catalogusId;
    private int aantalBijTeBestellen;
}
