package be.ugent.systemdesign.group6.medicijnen.application.servicesimpl;

import be.ugent.systemdesign.group6.medicijnen.application.Antwoord;
import be.ugent.systemdesign.group6.medicijnen.application.Status;
import be.ugent.systemdesign.group6.medicijnen.application.command.out.CommandDispatcher;
import be.ugent.systemdesign.group6.medicijnen.application.command.out.PlaatsBijAfvalCommand;
import be.ugent.systemdesign.group6.medicijnen.application.repositories.VoorraadRepository;
import be.ugent.systemdesign.group6.medicijnen.application.services.MonitorService;
import be.ugent.systemdesign.group6.medicijnen.domain.MedicijnProduct;
import be.ugent.systemdesign.group6.medicijnen.domain.Voorraad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private VoorraadRepository voorraadRepo;

    @Autowired
    private CommandDispatcher dispatcher;

    // https://spring.io/guides/gs/scheduling-tasks/
    // 60 * 60
    @Scheduled(fixedRate = 5 * 1000) // elk uur
    @Override
    public void checkVervalData() {

        List<Voorraad> voorraaden = voorraadRepo.geefAlles();
        voorraaden.forEach(voorraad -> {
            List<MedicijnProduct> medicijnen = voorraad.getNietVerkochteMedicijnen().stream().filter(medicijnProduct ->
                    !medicijnProduct.isGereserveerd()).collect(Collectors.toList());

            // deel van de voorraad die vervallen is
            List<MedicijnProduct> medicijnenVervalDatumOverschreden = medicijnen.stream().
                    filter(MedicijnProduct::isVervalDatumOverschreden).
                    collect(Collectors.toList());

            voorraad.verwijder(medicijnenVervalDatumOverschreden);

            // bij afval laten plaatsen
            if (medicijnenVervalDatumOverschreden.size() > 0) {
                PlaatsBijAfvalCommand afvalCommand = new PlaatsBijAfvalCommand(voorraad.getCatalogusItem().getId(),
                        medicijnenVervalDatumOverschreden.size());
                dispatcher.stuurPlaatsBijAfval(afvalCommand);
            }

            voorraadRepo.slaOp(voorraad);
        });
    }

    @Override
    public Antwoord verwijderTeHogeTemperatuur(int koelCelId, double temperatuur) {
        List<Voorraad> voorraaden = voorraadRepo.geefAlles();

        voorraaden.forEach(voorraad -> {
            if (!voorraad.isHetNogGoed(temperatuur)) {
                List<MedicijnProduct> nietVerkochteMedicijnen = voorraad.getNietVerkochteMedicijnen().stream().
                        filter(medicijnProduct -> !medicijnProduct.isGereserveerd()).collect(Collectors.toList());

                List<MedicijnProduct> afval = nietVerkochteMedicijnen.stream().
                        filter(medicijnProduct -> medicijnProduct.getKoelCelId() != null &&
                                medicijnProduct.getKoelCelId() == koelCelId).collect(Collectors.toList());

                if (afval.size() > 0) {
                    PlaatsBijAfvalCommand afvalCommand = new PlaatsBijAfvalCommand(voorraad.getCatalogusItem().getId(), afval.size());
                    dispatcher.stuurPlaatsBijAfval(afvalCommand);

                    voorraad.verwijder(afval);
                    voorraadRepo.slaOp(voorraad);
                }
            }
        });

        /*
        List<MedicijnProduct> inhoud = voorraadRepo.geefInhoudKoelcel(koelCelId);
        // https://stackoverflow.com/questions/10637369/compact-way-to-create-guava-multimaps

        // vervallen producten per catalogus id opslaan
        Multimap<Integer, MedicijnProduct> productPerCatalogusItemId = HashMultimap.create();

        inhoud.forEach(medicijnProduct ->
                productPerCatalogusItemId.put(medicijnProduct.getCatalogusItemId(), medicijnProduct));

        // catalogus id vertalen naar een catalogus item
        Multimap<CatalogusItem, MedicijnProduct> productPerCatalogusItem = HashMultimap.create();

        productPerCatalogusItemId.keys().stream().distinct().forEach(id -> {
            Collection<MedicijnProduct> producten = productPerCatalogusItemId.get(id);
            Optional<CatalogusItem> catalogusItemOptional = catalogusItemRepo.zoekOpId(id);
            if (catalogusItemOptional.isPresent()) {
                CatalogusItem item = catalogusItemOptional.get();
                productPerCatalogusItem.putAll(item, producten);
            }
        });

        // afval per catalogus id bijhouden
        Multimap<Integer, MedicijnProduct> afval = HashMultimap.create();

        productPerCatalogusItem.keys().forEach(catalogusItem -> {
            double gewensteTemperatuur = catalogusItem.getGewensteTemperatuur();
            if (gewensteTemperatuur + TOEGELATEN_TEMPERATUUR_AFWIJKING < temperatuur) {
                // product is slecht
                afval.putAll(catalogusItem.getId(), productPerCatalogusItem.get(catalogusItem));
            }
        });

        List<Integer> uniekeKeys = afval.keys().stream().distinct().collect(Collectors.toList());

        // bij afval plaatsen en doorvoeren naar de databank
        uniekeKeys.forEach(catalogusItemId -> {
            List<MedicijnProduct> producten = afval.get(catalogusItemId).stream().
                    filter(medicijnProduct -> !medicijnProduct.isGereserveerd() && !medicijnProduct.isVerkocht()).collect(Collectors.toList());

            Optional<Voorraad> voorraadOptional = voorraadRepo.geef(catalogusItemId);

            if (voorraadOptional.isPresent()) {
                Voorraad voorraad = voorraadOptional.get();
                voorraad.verwijder(producten);
                voorraadRepo.slaOp(voorraad);

                if (producten.size() > 0) {
                    PlaatsBijAfvalCommand afvalCommand = new PlaatsBijAfvalCommand(catalogusItemId, producten.size());
                    dispatcher.stuurPlaatsBijAfval(afvalCommand);
                }
            }
        });
*/
        return new Antwoord(Status.GELUKT, "");
    }
}
