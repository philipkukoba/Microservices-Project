package be.ugent.systemdesign.group6.medicijnen.application.repositories;

import be.ugent.systemdesign.group6.medicijnen.domain.CatalogusItem;

import java.util.Optional;

public interface CatalogusItemRepository {
    Optional<CatalogusItem> zoekOpNaam(String naam);
}
