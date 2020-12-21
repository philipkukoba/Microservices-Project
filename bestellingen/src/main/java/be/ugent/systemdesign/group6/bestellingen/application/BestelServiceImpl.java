package be.ugent.systemdesign.group6.bestellingen.application;

import be.ugent.systemdesign.group6.bestellingen.application.command.AnnuleerBestellingCommand;
import be.ugent.systemdesign.group6.bestellingen.application.command.CommandDispatcher;
import be.ugent.systemdesign.group6.bestellingen.application.saga.BestelSaga;
import be.ugent.systemdesign.group6.bestellingen.domain.Bestelling;
import be.ugent.systemdesign.group6.bestellingen.domain.BestellingenRepository;
import be.ugent.systemdesign.group6.bestellingen.domain.Medicijn;
import be.ugent.systemdesign.group6.bestellingen.domain.Status;
import be.ugent.systemdesign.group6.bestellingen.infrastructure.BestellingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Transactional
@Service
public class BestelServiceImpl implements BestelService {

    @Autowired
    BestellingenRepository repo;

    @Autowired
    BestelSaga saga;

    @Autowired
    CommandDispatcher commandDispatcher;

    @Override
    public Response plaatsBestelling(String klantenId, String thuisAdres, String apotheekAdres, HashMap<Integer, Integer> medicijnen) {
        try {
            Bestelling bestelling = maakBestelling(klantenId, thuisAdres, apotheekAdres, medicijnen);
            repo.saveBestelling(bestelling);
            saga.startSaga(bestelling);
            return new Response(ResponseStatus.GELUKT, "Bestelling plaatsen met id " + bestelling.getId() + " in behandeling");
        }
        catch(RuntimeException e){
            return new Response(ResponseStatus.NIET_GELUKT, "Fout opgetreden bij plaatsen van bestelling");
        }
    }

    @Override
    public Response annuleerBestelling(String id) {
        try {
            if(repo.getStatus(id) == Status.VERZONDEN){
                return new Response(ResponseStatus.NIET_GELUKT, "Bestelling is reeds verzonden");
            }
            AnnuleerBestellingCommand command = new AnnuleerBestellingCommand(id);
            commandDispatcher.sendAnnuleerBestellingCommand(command);
        } catch(BestellingNotFoundException e){
            return new Response(ResponseStatus.NIET_GELUKT, "Geen bestelling met id " + id + " gevonden");
        } catch(RuntimeException e){
            return new Response(ResponseStatus.NIET_GELUKT, "Bestelling annuleren niet gelukt");
        }
        return new Response(ResponseStatus.GELUKT, "Bestelling annuleren in behandeling");
    }

    @Override
    public Response plaatsBestellingTicket(String klantenId, String thuisAdres, String apotheekAdres, HashMap<Integer, Integer> medicijnen) {
        try{
            Bestelling bestelling = maakBestelling(klantenId, thuisAdres, apotheekAdres, medicijnen);
            bestelling.setStatus(Status.BESTELD_DOOR_TICKET);
            repo.saveBestelling(bestelling);
            saga.startSaga(bestelling);
            return new Response(ResponseStatus.GELUKT, "Bestelling plaatsen met id " + bestelling.getId() + " in behandeling");
        } catch(RuntimeException e){
            return new Response(ResponseStatus.NIET_GELUKT, "Fout opgetreden bij plaatsen van bestelling");
        }
    }

    private Bestelling maakBestelling(String klantenId, String thuisAdres, String apotheekAdres, HashMap<Integer, Integer> medicijnen) throws RuntimeException{
        List<Medicijn> meds = new ArrayList<>();
        medicijnen.forEach((id, aantal) -> {
            meds.add(new Medicijn(id, aantal));
        });
        return new Bestelling(klantenId, thuisAdres, apotheekAdres, meds);
    }
}
