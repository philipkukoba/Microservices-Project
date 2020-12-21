package be.ugent.systemdesign.group6.medicijnen.application.servicesimpl;

import be.ugent.systemdesign.group6.medicijnen.application.Antwoord;
import be.ugent.systemdesign.group6.medicijnen.application.Status;
import be.ugent.systemdesign.group6.medicijnen.application.repositories.CatalogusItemRepository;
import be.ugent.systemdesign.group6.medicijnen.application.repositories.VoorraadRepository;
import be.ugent.systemdesign.group6.medicijnen.application.services.VoorraadService;
import be.ugent.systemdesign.group6.medicijnen.application.services.externalAPI.KoelcelRestModel;
import be.ugent.systemdesign.group6.medicijnen.domain.CatalogusItem;
import be.ugent.systemdesign.group6.medicijnen.domain.MedicijnProduct;
import be.ugent.systemdesign.group6.medicijnen.domain.Voorraad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class VooraadServiceImpl implements VoorraadService {

    private static final Logger log = LoggerFactory.getLogger(VooraadServiceImpl.class);

    @Autowired
    private VoorraadRepository voorraadRepo;

    @Autowired
    private CatalogusItemRepository catalogusRepo;

    @Autowired
    private RestTemplate rest;

    @Value("${external.api.koelcel.uri}")
    private String koelcellenAPIuri;

    @Override
    public Antwoord verwerkLading(HashMap<String, HashMap<LocalDate, Integer>> lading) {
        List<Antwoord> antwoordList = new ArrayList<>();

        // namen van alle medicijnen
        Set<String> naamMedicijnen = lading.keySet();
        // naam van catalogus item op id
        HashMap<CatalogusItem, Integer> catalogusIdMapping = new HashMap<>();

        naamMedicijnen.forEach(s -> {
            Optional<CatalogusItem> medicijn = catalogusRepo.zoekOpNaam(s);
            medicijn.ifPresentOrElse(catalogusItem ->
                            catalogusIdMapping.put(catalogusItem, catalogusItem.getId()),

                    () -> antwoordList.add(new Antwoord(Status.NIET_GELUKT,
                            String.format("Het geleverde medicijn %s zit nog niet in de catalogus", s))));
        });

        ///////////////REST CALL naar koelcelmonitorservice////////////////
        ///////////////////////////////////////////////////////////////////

        // Deze service is AFHANKELIJK van de koelcelmonitor service en moet zich dus aanpassen waardoor er dus ook
        // een model moet voor aangemaakt worden.
        KoelcelRestModel[] koelcellen = null;

        // https://stackoverflow.com/questions/47052421/resttemplate-connectexception-unreachable
        try {
            koelcellen = rest.getForObject("http://" + koelcellenAPIuri + "/api/" + "koelcellen", KoelcelRestModel[].class);
        } catch (ResourceAccessException e) {
            antwoordList.add(new Antwoord(Status.NIET_GELUKT, e.getMessage()));
        }

        ///////////////////////////////////////////////////////////////////

        if (koelcellen == null) {
            log.warn("De koelcelmonitorservice is niet bereikbaar.");
        }
        List<KoelcelRestModel> cellen = koelcellen == null ? new ArrayList<>() : Arrays.asList(koelcellen);

        // alle soorten producten overlopen, die geleverd zijn
        catalogusIdMapping.forEach((catalogusItem, id) -> {
            int catalogusId = id;

            // geleverde producten met per datum het aantal doosjes
            HashMap<LocalDate, Integer> map = lading.getOrDefault(catalogusItem.getNaam(), null);

            List<MedicijnProduct> medicijnen = new ArrayList<>();

            // temperatuur ophalen
            double temp = catalogusItem.getGewensteTemperatuur();

            // in een koelcel plaatsen
            map.forEach((vervalDatum, nr) -> {
                for (int i = 0; i < nr; i++) {
                    List<KoelcelRestModel> geschikteCellen = cellen.stream().
                            filter(restKoelCel -> restKoelCel.getMin() <= temp && restKoelCel.getMax() > temp).
                            collect(Collectors.toList());

                    if (geschikteCellen.size() >= 1) {
                        // https://www.geeksforgeeks.org/shuffle-or-randomize-a-list-in-java/
                        // indien meerdere cellen beschikbaar, eerst een random shuffle zodat niet altijd dezelfde gekozen wordt
                        Collections.shuffle(geschikteCellen);

                        int koelCelId = geschikteCellen.get(0).getId();
                        MedicijnProduct medicijn = MedicijnProduct.builder().catalogusItemId(catalogusId).vervalDatum(vervalDatum).build();
                        medicijn.plaatsInKoelcel(koelCelId);
                        medicijnen.add(medicijn);
                    } else {
                        // geen geschikte cel, dus we veronderstellen dat het medicijn niet in een koelcel moet staan
                        MedicijnProduct medicijn = MedicijnProduct.builder().catalogusItemId(catalogusId).
                                vervalDatum(vervalDatum).build();
                        medicijnen.add(medicijn);
                    }
                }
            });

            Optional<Voorraad> voorraadOptional = voorraadRepo.geef(catalogusId);

            if (voorraadOptional.isEmpty()) {/*
                if (catalogusItemOptional.isPresent()) {
                    // product die in de catalogus zit maar niet meer in de voorraad
                    Voorraad voorraad = Voorraad.builder().catalogusItem(catalogusItemOptional.get()).
                            medicijnen(medicijnen).build();

                    voorraadRepo.slaOp(voorraad);
                }*/
                // else --> onbekend product geleverd
                // al reeds een antwoord voor aangemaakt
            } else {
                // opslaan
                Voorraad voorraad = voorraadOptional.get();
                voorraad.voegToe(medicijnen);
                voorraadRepo.slaOp(voorraad);
            }
        });

        if (antwoordList.size() == 0) {
            return new Antwoord(Status.GELUKT, "");
        }
        List<String> boodschappen = antwoordList.stream().map(Antwoord::getBoodschap).collect(Collectors.toList());
        String boodschap = boodschappen.stream().reduce("", (s, s2) -> s + "\n" + s2);

        return new Antwoord(Status.NIET_GELUKT, boodschap);
    }
}
