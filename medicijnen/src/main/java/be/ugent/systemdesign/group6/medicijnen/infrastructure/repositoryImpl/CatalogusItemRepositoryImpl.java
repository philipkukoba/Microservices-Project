package be.ugent.systemdesign.group6.medicijnen.infrastructure.repositoryImpl;

import be.ugent.systemdesign.group6.medicijnen.application.repositories.CatalogusItemRepository;
import be.ugent.systemdesign.group6.medicijnen.domain.CatalogusItem;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.Mapper;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.datamodel.CatalogusItemDataModel;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.repositories.CatalogusItemDataModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CatalogusItemRepositoryImpl implements CatalogusItemRepository {

    @Autowired
    private CatalogusItemDataModelRepository repo;

    @Override
    public Optional<CatalogusItem> zoekOpNaam(String naam) {
        Optional<CatalogusItemDataModel> modelOptional = repo.findByNaam(naam);
        return modelOptional.isEmpty() ? Optional.empty() : Optional.of(Mapper.mapToDomain(modelOptional.get()));
    }
}
