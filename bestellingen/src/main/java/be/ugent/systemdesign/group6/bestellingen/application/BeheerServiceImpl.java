package be.ugent.systemdesign.group6.bestellingen.application;

import be.ugent.systemdesign.group6.bestellingen.application.command.GeefStatusResponse;
import be.ugent.systemdesign.group6.bestellingen.domain.Bestelling;
import be.ugent.systemdesign.group6.bestellingen.domain.BestellingenRepository;
import be.ugent.systemdesign.group6.bestellingen.domain.Status;
import be.ugent.systemdesign.group6.bestellingen.infrastructure.BestellingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class BeheerServiceImpl implements BeheerService {

    @Autowired
    BestellingenRepository repo;

    @Override
    public Response updateStatus(String id, Status status) {
        try{
            Bestelling bestelling = repo.getBestelling(id);
            bestelling.setStatus(status);
            repo.saveBestelling(bestelling);
        } catch(BestellingNotFoundException e){
            return new Response(ResponseStatus.NIET_GELUKT, "Geen bestelling met id " + id + " gevonden");
        } catch(RuntimeException e){
            return new Response(ResponseStatus.NIET_GELUKT, "Fout opgetreden bij aanpassen status");
        }
        return new Response(ResponseStatus.NIET_GELUKT, "Status van bestelling met id " + id + " geüpdatet naar " + status.name());
    }

    @Override
    public GeefStatusResponse geefStatus(String id) {
        try {
            Status status = repo.getStatus(id);
            return new GeefStatusResponse(ResponseStatus.GELUKT, "", id, status.name());
        } catch(BestellingNotFoundException e){
            return new GeefStatusResponse(ResponseStatus.NIET_GELUKT, "Geen bestelling met id " + id + " gevonden", id, "");
        } catch(RuntimeException e){
            return new GeefStatusResponse(ResponseStatus.NIET_GELUKT, "Fout opgetreden bij opvragen van status", "id", "");
        }
    }

    @Override
    public Response bevestigAnnulatie(String id) {
        try{
            Bestelling bestelling = repo.getBestelling(id);
            bestelling.annuleerBestelling();
            repo.saveBestelling(bestelling);
        } catch(BestellingNotFoundException e){
            return new Response(ResponseStatus.NIET_GELUKT, "Geen bestelling met id " + id + " gevonden");
        } catch(RuntimeException e){
            return new Response(ResponseStatus.NIET_GELUKT, "Fout opgetreden bij annuleren bestelling");
        }
        return new Response(ResponseStatus.NIET_GELUKT, "Bestelling " + id + " geannuleerd");
    }
}
