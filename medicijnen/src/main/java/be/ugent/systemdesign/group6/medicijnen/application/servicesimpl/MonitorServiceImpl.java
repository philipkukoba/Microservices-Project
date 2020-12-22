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
    @Scheduled(fixedRate = 5 * 1000) // elke 5s
    @Override
    public Antwoord checkVervalData() {

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

        return new Antwoord(Status.GELUKT, "De voorraad zijn vervaldata werden gecontroleerd");
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

        return new Antwoord(Status.GELUKT, "");
    }
}
