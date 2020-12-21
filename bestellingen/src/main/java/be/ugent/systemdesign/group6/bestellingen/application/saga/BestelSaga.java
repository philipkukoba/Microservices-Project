package be.ugent.systemdesign.group6.bestellingen.application.saga;

import be.ugent.systemdesign.group6.bestellingen.application.command.*;
import be.ugent.systemdesign.group6.bestellingen.domain.Bestelling;
import be.ugent.systemdesign.group6.bestellingen.domain.BestellingenRepository;
import be.ugent.systemdesign.group6.bestellingen.domain.Medicijn;
import be.ugent.systemdesign.group6.bestellingen.domain.Status;
import be.ugent.systemdesign.group6.bestellingen.infrastructure.BestellingNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Transactional
@Service
public class BestelSaga {

    private final Random r = new Random();

    private static Logger log = LoggerFactory.getLogger(BestelSaga.class);

    @Autowired
    CommandDispatcher commandDispatcher;

    @Autowired
    BestellingenRepository repo;

    public void startSaga(Bestelling bestelling) {
        log.info("$Start SAGA voor bestelling " + bestelling.getId());
        ReserveerBestellingCommand command = new ReserveerBestellingCommand(bestelling);
        log.info("$SAGA Medicijnen worden gereserveerd");
        commandDispatcher.sendReserveerBestellingCommand(command);
    }

    public void gereserveerdBestelling(String id, List<Medicijn> medicijnen) {
        try{
            Bestelling bestelling = repo.getBestelling(id);

            boolean betalingGelukt = true;
            //betaling simuleren indien besteld door klant
            //indien status 'BESTELD_DOOR_TICKET' is, wordt de bestelling geplaatst zonder betaling
            if(bestelling.getStatus() == Status.BESTELD_DOOR_KLANT){
                log.info("$SAGA - betaling wordt gesimuleerd voor bestelling " + bestelling.getId());
                betalingGelukt = (r.nextInt(100) < 30);
            }
            if(betalingGelukt){
                log.info("$SAGA - betaling gelukt voor bestelling " + bestelling.getId());
                bestelling.setMedicijnen(medicijnen);
                bestelling.setStatus(Status.WORDT_SAMENGESTELD);
                repo.saveBestelling(bestelling);
                BevestigBestellingCommand bevestigCommand = new BevestigBestellingCommand(id);
                log.info("$SAGA - Bevestig bestelling " + bestelling.getId());
                commandDispatcher.sendBevestigBestellingCommand(bevestigCommand);
                MaakOrderCommand orderCommand = new MaakOrderCommand(
                        bestelling.getId(),
                        bestelling.getThuisAdres(),
                        bestelling.getApotheekAdres(),
                        bestelling.getMedicijnen()
                );
                log.info("$SAGA - Maak Order voor bestelling " + bestelling.getId());
                commandDispatcher.sendMaakOrderCommand(orderCommand);
                MaakFactuurCommand factuurCommand = new MaakFactuurCommand(
                        bestelling.getId(),
                        bestelling.getKlantenId(),
                        bestelling.getMedicijnen()
                );
                log.info("$SAGA - Maak factuur voor bestelling " + bestelling.getId());
                commandDispatcher.sendMaakFactuurCommand(factuurCommand);
            }
            else{
                log.info("$SAGA - betaling niet gelukt voor bestelling " + bestelling.getId());
                repo.removeBestelling(id);
                GeefVrijBestellingCommand geefVrijCommand = new GeefVrijBestellingCommand(id);
                log.info("$SAGA - Medicijnen worden vrijgegeven");
                commandDispatcher.sendGeefVrijBestellingCommand(geefVrijCommand);
            }
        } catch(BestellingNotFoundException e){
            log.info("$SAGA - Bestelling " + id + " niet gevonden");
            GeefVrijBestellingCommand geefVrijCommand = new GeefVrijBestellingCommand(id);
            log.info("$SAGA - Medicijnen worden vrijgegeven");
            commandDispatcher.sendGeefVrijBestellingCommand(geefVrijCommand);
        }
    }

    public void reserverenMislukt(String id) {
        log.info("$SAGA - Reserveren mislukt, bestelling wordt verwijderd");
        repo.removeBestelling(id);
    }
}
