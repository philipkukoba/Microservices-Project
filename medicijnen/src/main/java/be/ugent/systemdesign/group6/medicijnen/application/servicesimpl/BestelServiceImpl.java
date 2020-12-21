package be.ugent.systemdesign.group6.medicijnen.application.servicesimpl;

import be.ugent.systemdesign.group6.medicijnen.application.Status;
import be.ugent.systemdesign.group6.medicijnen.application.command.out.CommandDispatcher;
import be.ugent.systemdesign.group6.medicijnen.application.command.out.PlaatsBijAfvalCommand;
import be.ugent.systemdesign.group6.medicijnen.application.command.out.ReserveerBestellingAntwoord;
import be.ugent.systemdesign.group6.medicijnen.application.repositories.BestellingsRepository;
import be.ugent.systemdesign.group6.medicijnen.application.repositories.VoorraadRepository;
import be.ugent.systemdesign.group6.medicijnen.application.services.BestelService;
import be.ugent.systemdesign.group6.medicijnen.domain.Bestelling;
import be.ugent.systemdesign.group6.medicijnen.domain.CatalogusItem;
import be.ugent.systemdesign.group6.medicijnen.domain.MedicijnProduct;
import be.ugent.systemdesign.group6.medicijnen.domain.Voorraad;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional
public class BestelServiceImpl implements BestelService {

    @Autowired
    private BestellingsRepository bestellingsRepo;

    @Autowired
    private VoorraadRepository voorraadRepo;

    @Autowired
    private CommandDispatcher dispatcher;

    @Override
    public ReserveerBestellingAntwoord reserveer(String bestellingsId, HashMap<Integer, Integer> medicijnenIds) {
        HashMap<CatalogusItem, Integer> aantalPerMedicijn = new HashMap<>();

        List<Voorraad> voorraadList = new ArrayList<>();

        AtomicBoolean ontoereikend = new AtomicBoolean(false);
        AtomicBoolean nietBestaand = new AtomicBoolean(false);

        // Voorraad object van alle verschillende medicijnen ophalen
        // Vervolgens proberen te reserveren, indien dat niet lukt -> aangeven dat het fout liep
        medicijnenIds.forEach((key, value) -> {
            int medicijnId = key;
            int aantal = value;

            Optional<Voorraad> voorraadOptional = voorraadRepo.geef(medicijnId);
            voorraadOptional.ifPresentOrElse(voorraad -> {
                boolean succes = voorraad.reserveer(bestellingsId, aantal);
                if (!succes) {
                    ontoereikend.set(true);
                }
                voorraadList.add(voorraad);
                aantalPerMedicijn.put(voorraad.getCatalogusItem(), aantal);
            }, () -> {
                nietBestaand.set(true);
            });
        });

        // Indien niet bestaand product gevraagd wordt
        if (nietBestaand.get()) {
            return new ReserveerBestellingAntwoord(bestellingsId, Status.NIET_GELUKT,
                    new HashMap<>(), "Reservatie bevat niet bestaand product");
        }

        // Indien niet genoeg voorraad, fout geven
        if (ontoereikend.get()) {
            return new ReserveerBestellingAntwoord(bestellingsId, Status.NIET_GELUKT,
                    new HashMap<>(), "Voorraad is ontoereikend");
        }
        // Indien reservatie gelukt is, opslaan in de databank
        voorraadList.forEach(voorraad -> voorraadRepo.slaOp(voorraad));

        return new ReserveerBestellingAntwoord(bestellingsId, Status.GELUKT, aantalPerMedicijn, "");
    }


    // Controle of deze producten terug bij de voorraad mogen gereken worden
    // (bij verval datum overschreden en uit catalogus verwijderd niet)
    public void checkNaToevoegen(List<MedicijnProduct> medicijnen) {
        // producten die uit de catalogus zijn verwijderd
        List<MedicijnProduct> medicijnenUitCatalogusVerwijderd = medicijnen.stream().
                filter(medicijnProduct -> voorraadRepo.geef(medicijnProduct.getCatalogusItemId()).
                        isEmpty()).collect(Collectors.toList());

        // {key: catalogus id, value: [doosjes van het medicijn]}
        Multimap<Integer, MedicijnProduct> medicijnenPerCatalogusItem = HashMultimap.create();

        medicijnenUitCatalogusVerwijderd.forEach(medicijnProduct -> {
            medicijnenPerCatalogusItem.put(medicijnProduct.getCatalogusItemId(), medicijnProduct);
        });

        // verwijderen van medicijnen
        medicijnenPerCatalogusItem.keys().stream().distinct().forEach(id -> {

            PlaatsBijAfvalCommand afvalCommand = new PlaatsBijAfvalCommand(id,
                    medicijnenPerCatalogusItem.get(id).size());
            dispatcher.stuurPlaatsBijAfval(afvalCommand);

            // alles moet door aggregate en hier dus een aggregate met catalogusitem=null
            // hierdoor weet men dat deze medicijnen nergens bij horen en verwijderd mogen worden
            Voorraad voorraad = Voorraad.builder().medicijnen(new ArrayList<>(medicijnenPerCatalogusItem.get(id))).
                    catalogusItem(null).build();

            voorraadRepo.slaOp(voorraad);
        });

        // producten die hun vervaldatum zijn overschreden
        List<MedicijnProduct> vervallenProducten = medicijnen.stream().
                filter(MedicijnProduct::isVervalDatumOverschreden).collect(Collectors.toList());

        // {key: catalogus id, value: [doosjes van het medicijn]}
        Multimap<Integer, MedicijnProduct> medicijnenPerCatalogusItemId = HashMultimap.create();
        vervallenProducten.forEach(medicijnProduct ->
                medicijnenPerCatalogusItemId.put(medicijnProduct.getCatalogusItemId(), medicijnProduct));

        // verwijderen van medicijnen
        medicijnenPerCatalogusItemId.keys().stream().distinct().forEach(id -> {
            PlaatsBijAfvalCommand afvalCommand = new PlaatsBijAfvalCommand(id, medicijnenPerCatalogusItem.get(id).size());
            dispatcher.stuurPlaatsBijAfval(afvalCommand);

            Collection<MedicijnProduct> producten = medicijnenPerCatalogusItemId.get(id);

            Optional<Voorraad> voorraadOptional = voorraadRepo.geef(id);
            if (voorraadOptional.isPresent()) {
                Voorraad voorraad = voorraadOptional.get();
                voorraad.verwijder(new ArrayList<>(producten));
                voorraadRepo.slaOp(voorraad);
            }
        });

    }

    @Override
    public void geefVrij(String bestellingsId) {
        Optional<Bestelling> bestellingOptional = bestellingsRepo.geefBestelling(bestellingsId);
        if (bestellingOptional.isPresent()) {
            Bestelling bestelling = bestellingOptional.get();

            List<MedicijnProduct> medicijnen = bestelling.getMedicijnen();

            bestelling.geefVrij();
            bestellingsRepo.slaOp(bestelling);

            checkNaToevoegen(medicijnen);
        }
    }

    @Override
    public void bevestig(String bestellingsId) {
        Optional<Bestelling> bestellingOptional = bestellingsRepo.geefBestelling(bestellingsId);

        if (bestellingOptional.isPresent()) {
            Bestelling bestelling = bestellingOptional.get();
            bestelling.bevestig();
            bestellingsRepo.slaOp(bestelling);
        }

        if (bestellingOptional.isPresent()) {
            // voorraad object per medicijn uit catalogus bijhouden
            HashMap<Integer, Voorraad> voorraadPerMedicijn = new HashMap<>();

            Bestelling bestelling = bestellingOptional.get();

            List<Integer> catalogusItemIds = bestelling.getMedicijnen().stream().
                    map(MedicijnProduct::getCatalogusItemId).collect(Collectors.toList());

            // De voorraad moet overlopen worden om eventueel het medicijnKritischeWaarde event te kunnen opgooien.

            // voorraad per medicijn uit catalogus ophalen
            catalogusItemIds.forEach(id -> {
                Optional<Voorraad> voorraadOptional = voorraadRepo.geef(id);

                voorraadOptional.ifPresent(voorraad -> {
                    if (!voorraadPerMedicijn.containsKey(id)) {
                        voorraadPerMedicijn.put(id, voorraad);
                    }
                });
            });

            // op voorraad object aangeven dat je de huidige bestelling bevestigd en daarna opslaan
            voorraadPerMedicijn.forEach((id, voorraad) -> {
                voorraad.bevestig(bestellingsId);
                voorraadRepo.slaOp(voorraad);
            });
        }
    }

    @Override
    public void annuleerBestelling(String bestellingsId) {
        Optional<Bestelling> bestellingOptional = bestellingsRepo.geefBestelling(bestellingsId);
        if (bestellingOptional.isPresent()) {
            Bestelling bestelling = bestellingOptional.get();
            List<MedicijnProduct> medicijnen = bestelling.getMedicijnen();
            bestelling.annuleer();
            bestellingsRepo.slaOp(bestelling);

            checkNaToevoegen(medicijnen);
        }
    }
}
