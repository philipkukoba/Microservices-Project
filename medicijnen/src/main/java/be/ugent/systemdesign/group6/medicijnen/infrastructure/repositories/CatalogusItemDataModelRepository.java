package be.ugent.systemdesign.group6.medicijnen.infrastructure.repositories;

import be.ugent.systemdesign.group6.medicijnen.infrastructure.datamodel.CatalogusItemDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogusItemDataModelRepository extends JpaRepository<CatalogusItemDataModel, Integer> {
    Optional<CatalogusItemDataModel> findByNaam(String naam);
}
