package be.ugent.systemdesign.group6.medicijnen.domain;

import be.ugent.systemdesign.group6.medicijnen.domain.seedwork.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MedicijnCatalogusVerwijderdEvent extends DomainEvent {
    private int id;  // van CatalogusItem
}
