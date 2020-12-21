package be.ugent.systemdesign.group6.medicijnen.infrastructure.repositoryImpl;

import be.ugent.systemdesign.group6.medicijnen.application.repositories.BestellingsRepository;
import be.ugent.systemdesign.group6.medicijnen.domain.Bestelling;
import be.ugent.systemdesign.group6.medicijnen.domain.MedicijnProduct;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.Mapper;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.datamodel.MedicijnProductDataModel;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.repositories.MedicijnProductDataModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BestellingsRepositoryImpl implements BestellingsRepository {

    @Autowired
    private MedicijnProductDataModelRepository repo;

    @Override
    public Optional<Bestelling> geefBestelling(String bestellingsId) {
        List<MedicijnProductDataModel> models = repo.findAllByBestellingsId(bestellingsId);

        if (models.size() == 0)
            return Optional.empty();

        Bestelling bestelling = Bestelling.builder().bestellingsId(bestellingsId).
                medicijnen(models.stream().map(Mapper::mapToDomain).
                        collect(Collectors.toList())).build();

        return Optional.of(bestelling);
    }

    @Override
    public Bestelling slaOp(Bestelling bestelling) {
        List<MedicijnProductDataModel> models = bestelling.getMedicijnen().stream().
                map(Mapper::mapToDataModel).collect(Collectors.toList());

        List<MedicijnProductDataModel> opgeslagenModels = repo.saveAll(models);

        List<MedicijnProduct> medicijnen = opgeslagenModels.stream().map(Mapper::mapToDomain).collect(Collectors.toList());

        return Bestelling.builder().bestellingsId(bestelling.getBestellingsId()).medicijnen(medicijnen).build();
    }
}
