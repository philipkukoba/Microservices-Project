package be.ugent.systemdesign.group6.medicijnen.application.repositories;

import be.ugent.systemdesign.group6.medicijnen.domain.Voorraad;

import java.util.List;
import java.util.Optional;


public interface VoorraadRepository {
    Optional<Voorraad> geef(int catalogusMedicijnId);

    Voorraad slaOp(Voorraad voorraad);

    void verwijderUitCatalogus(int catalogusItemId);

    List<Voorraad> geefAlles();
}
