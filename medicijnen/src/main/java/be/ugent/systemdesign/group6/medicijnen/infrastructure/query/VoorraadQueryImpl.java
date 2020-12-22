package be.ugent.systemdesign.group6.medicijnen.infrastructure.query;

import be.ugent.systemdesign.group6.medicijnen.application.query.VoorraadQuery;
import be.ugent.systemdesign.group6.medicijnen.application.query.VoorraadReadModel;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.Mapper;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.datamodel.MedicijnProductDataModel;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.repositories.MedicijnProductDataModelRepository;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoorraadQueryImpl implements VoorraadQuery {

    @Autowired
    private MedicijnProductDataModelRepository repo;

    @Override
    public List<VoorraadReadModel> geefCompleteVoorraad() {
        // de volledige voorraad ophalen
        List<MedicijnProductDataModel> producten = repo.findAllByBestellingsIdNullAndVerkochtFalse();

        // producten per catalogus item bijhouden
        Multimap<Integer, MedicijnProductDataModel> productenPerCatalogusItemId = HashMultimap.create();
        producten.forEach(medicijnProductDataModel ->
                productenPerCatalogusItemId.put(medicijnProductDataModel.getCatalogusItemId(), medicijnProductDataModel));

        List<VoorraadReadModel> readModels = new ArrayList<>();

        // readmodels construeren
        productenPerCatalogusItemId.keys().stream().distinct().collect(Collectors.toList()).forEach(id -> {
            VoorraadReadModel readModel = Mapper.mapToReadModel(id, productenPerCatalogusItemId.get(id).size());
            readModels.add(readModel);
        });

        return readModels;
    }

}
