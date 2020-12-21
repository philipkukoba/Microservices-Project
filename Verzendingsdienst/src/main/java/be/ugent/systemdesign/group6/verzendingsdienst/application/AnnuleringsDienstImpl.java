package be.ugent.systemdesign.group6.verzendingsdienst.application;

import be.ugent.systemdesign.group6.verzendingsdienst.application.command.AnnuleerBestellingResponse;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.BestellingGeannuleerdCommand;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.CommandDispatcher;
import be.ugent.systemdesign.group6.verzendingsdienst.application.command.PlaatsGeannuleerdeBestellingTerugCommand;
import be.ugent.systemdesign.group6.verzendingsdienst.domain.Pakket;
import be.ugent.systemdesign.group6.verzendingsdienst.domain.PakketStatus;
import be.ugent.systemdesign.group6.verzendingsdienst.domain.VerzendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Transactional
@Service
public class AnnuleringsDienstImpl implements AnnuleringsDienst {

    private final Logger logger = Logger.getLogger(Logger.class.getName());

    @Autowired
    CommandDispatcher commandDispatcher;

    @Autowired
    VerzendingRepository verzendingRepo;

    //incoming command

    @Override
    public AnnuleerBestellingResponse annuleerBestelling(String bestellingId) {
        try {
            List<Pakket> geannuleerdePakketten = verzendingRepo.geefAllePakkettenVaneenOrder(bestellingId);
            for (Pakket p : geannuleerdePakketten) {
                if (p.getStatus() == PakketStatus.KLAAR_VOOR_VERZENDING) {
                    //opdracht aan order service om bestelling ongedaan te maken
                    Response r = plaatsGeannulleerdeBestellingTerug(bestellingId);
                }
                p.annuleer();
                verzendingRepo.updatePakket(p.getId(), p.getStatus().name());
            }
        }
        catch (Exception ex) {
            logger.info(ex.getMessage());
            AnnuleerBestellingResponse r = new AnnuleerBestellingResponse("Fout bij het annuleren van de bestelling", ResponseStatus.NIET_GELUKT, bestellingId);
            logger.info(r.message);
            return r;
        }
        AnnuleerBestellingResponse r = new AnnuleerBestellingResponse("Bestelling werd succesvol geannuleerd.", ResponseStatus.GELUKT,  bestellingId);
        logger.info(r.message);
        return r;
    }

    //outgoing commands

    @Override
    public Response plaatsGeannulleerdeBestellingTerug(String bestellingId) {
        try {
            PlaatsGeannuleerdeBestellingTerugCommand command = new PlaatsGeannuleerdeBestellingTerugCommand(bestellingId);
            commandDispatcher.plaatsGeannuleerdeBestellingTerugCommand(command);
        }
        catch (Exception ex){
            Response r = new Response("Error bij het uitsturen van de command plaatsGeannuleerdeBestellingTerug", ResponseStatus.NIET_GELUKT);
            logger.info(r.message);
            return r;
        }

        Response r = new Response("Succesvol command plaatsGeannuleerdeBestellingTerug uitgestuurd", ResponseStatus.GELUKT);
        logger.info(r.message);
        return r;
    }

    @Override
    public Response bestellingGeannuleerd(String bestellingId) {
        try {
            BestellingGeannuleerdCommand command = new BestellingGeannuleerdCommand(bestellingId);
            commandDispatcher.bestellingGeannuleerdCommand(command);
        }
        catch (Exception ex){
            Response r = new Response("Error bij het uitsturen van de command plaatsGeannuleerdeBestellingTerug", ResponseStatus.NIET_GELUKT);
            logger.info(r.message);
            return r;
        }
        Response r = new Response("Succesvol command plaatsGeannuleerdeBestellingTerug uitgestuurd", ResponseStatus.GELUKT);
        logger.info(r.message);
        return r;
    }

}
