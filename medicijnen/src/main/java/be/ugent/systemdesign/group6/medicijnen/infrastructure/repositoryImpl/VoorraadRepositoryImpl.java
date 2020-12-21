package be.ugent.systemdesign.group6.medicijnen.infrastructure.repositoryImpl;

import be.ugent.systemdesign.group6.medicijnen.application.repositories.VoorraadRepository;
import be.ugent.systemdesign.group6.medicijnen.domain.CatalogusItem;
import be.ugent.systemdesign.group6.medicijnen.domain.MedicijnProduct;
import be.ugent.systemdesign.group6.medicijnen.domain.Voorraad;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.Mapper;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.datamodel.CatalogusItemDataModel;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.datamodel.MedicijnProductDataModel;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.repositories.CatalogusItemDataModelRepository;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.repositories.MedicijnProductDataModelRepository;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class VoorraadRepositoryImpl implements VoorraadRepository {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MedicijnProductDataModelRepository repo;

    @Autowired
    private CatalogusItemDataModelRepository catalogusRepo;

    @Override
    public Optional<Voorraad> geef(int catalogusItemId) {
        List<MedicijnProduct> producten = repo.findAllByCatalogusItemId(catalogusItemId).stream().
                map(Mapper::mapToDomain).collect(Collectors.toList());

        Optional<CatalogusItemDataModel> catalogusItemDataModel = catalogusRepo.findById(catalogusItemId);

        // als het medicijn niet in de catalogus zit
        if (catalogusItemDataModel.isEmpty())
            return Optional.empty();

        /*
        // als het medicijn niet in de catalogus zit en er geen medicijnen van zijn
        if (catalogusItemDataModel.isEmpty() && producten.size() == 0)
            return Optional.empty();

        // als het medicijn niet in de catalogus zit en er toch medicijnen van zijn
        if (catalogusItemDataModel.isEmpty())
            return Optional.of(Voorraad.builder().catalogusItem(null).
                    medicijnen(producten).build());
        */

        return Optional.of(Voorraad.builder().catalogusItem(Mapper.mapToDomain(catalogusItemDataModel.get())).
                medicijnen(producten).build());
    }

    @Override
    @Transactional
    public Voorraad slaOp(Voorraad productVoorraad) {
        // als het medicijn niet in de catalogus zit
        if (productVoorraad.getCatalogusItem() == null) {
            List<Integer> ids = productVoorraad.getNietVerkochteMedicijnen().stream().
                    map(MedicijnProduct::getId).collect(Collectors.toList());

            repo.deleteAllByIdIn(ids);

            return productVoorraad;
        }

        // controleren of het geen nieuw catalogus item is
        Optional<CatalogusItemDataModel> itemDataModel = catalogusRepo.findByNaam(productVoorraad.getCatalogusItem().getNaam());
        CatalogusItemDataModel item;
        if (itemDataModel.isEmpty()) {
            item = catalogusRepo.save(Mapper.mapToDataModel(productVoorraad.getCatalogusItem()));
        } else {
            item = itemDataModel.get();
        }

        Optional<Voorraad> optionalVorigeVoorraad = geef(productVoorraad.getCatalogusItem().getId());

        List<MedicijnProductDataModel> models = productVoorraad.getMedicijnen().stream().
                map(Mapper::mapToDataModel).collect(Collectors.toList());
        List<MedicijnProductDataModel> opgeslagenModels = repo.saveAll(models);

        if (optionalVorigeVoorraad.isPresent()) {
            // nagaan wat verwijderd werd uit het voorraad object en dit doorvoeren naar de databank

            List<MedicijnProduct> vorigeMedicijnen = optionalVorigeVoorraad.get().getMedicijnen();
            List<MedicijnProduct> huidigeMedicijnen = productVoorraad.getMedicijnen();
            // https://www.baeldung.com/java-lists-difference
            vorigeMedicijnen.removeAll(huidigeMedicijnen);
            // vorigeMedicijnen bevat alle verwijderde medicijnen
            vorigeMedicijnen.forEach(medicijnProduct -> {
                MedicijnProductDataModel model = Mapper.mapToDataModel(medicijnProduct);
                repo.deleteById(model.getId());
            });
        }

        productVoorraad.getDomainEvents().forEach(domainEvent -> publisher.publishEvent(domainEvent));
        productVoorraad.verwijderAlleDomainEvents();

        return Voorraad.builder().catalogusItem(Mapper.mapToDomain(item)).
                medicijnen(opgeslagenModels.stream().map(Mapper::mapToDomain).collect(Collectors.toList())).build();
    }

    // https://stackoverflow.com/questions/32269192/spring-no-entitymanager-with-actual-transaction-available-for-current-thread
    // Bij delete moet er transactional staan
    @Override
    @Transactional
    public void verwijderUitCatalogus(int catalogusItemId) {
        //repo.deleteAllByCatalogusItemIdAndBestellingsIdNull(catalogusItemId);
        List<MedicijnProductDataModel> models = repo.findAllByCatalogusItemId(catalogusItemId);

        List<Integer> ids = models.stream().
                filter(medicijnProductDataModel -> medicijnProductDataModel.getBestellingsId() == null).
                map(MedicijnProductDataModel::getId).collect(Collectors.toList());


        repo.deleteAllByCatalogusItemIdAndBestellingsIdNull(catalogusItemId);

        catalogusRepo.deleteById(catalogusItemId);
    }

    @Override
    public List<Voorraad> geefAlles() {
        List<MedicijnProduct> producten = repo.findAll().stream().map(Mapper::mapToDomain).collect(Collectors.toList());

        Multimap<Integer, MedicijnProduct> voorraadPerMedicijn = HashMultimap.create();
        producten.forEach(medicijnProduct -> voorraadPerMedicijn.
                put(medicijnProduct.getCatalogusItemId(), medicijnProduct));

        List<Voorraad> voorraden = new ArrayList<>();

        voorraadPerMedicijn.keys().stream().distinct().forEach(id -> {
            Optional<CatalogusItemDataModel> catalogusItemDataModel = catalogusRepo.findById(id);
            CatalogusItem item;
            if (catalogusItemDataModel.isPresent()) {
                item = Mapper.mapToDomain(catalogusItemDataModel.get());
                Voorraad voorraad = Voorraad.builder().catalogusItem(item).medicijnen(new ArrayList<>(voorraadPerMedicijn.get(id))).build();
                voorraden.add(voorraad);
            }
        });

        return voorraden;
    }
}
