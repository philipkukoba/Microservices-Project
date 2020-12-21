package be.ugent.systemdesign.group6.medicijnen.application.event;

import be.ugent.systemdesign.group6.medicijnen.domain.MedicijnCatalogusVerwijderdEvent;
import be.ugent.systemdesign.group6.medicijnen.domain.MedicijnKritischeWaardeDomainEvent;

public interface EventDispatcher {
    void publishMedicijnKritischeWaardeDomainEvent(MedicijnKritischeWaardeDomainEvent e);

    void publishMedicijnCatalogusVerwijderdEvent(MedicijnCatalogusVerwijderdEvent e);
}
