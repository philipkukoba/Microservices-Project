package be.ugent.systemdesign.group6.bestellingen.application.command;

import be.ugent.systemdesign.group6.bestellingen.application.BeheerService;
import be.ugent.systemdesign.group6.bestellingen.application.Response;
import be.ugent.systemdesign.group6.bestellingen.application.ResponseStatus;
import be.ugent.systemdesign.group6.bestellingen.application.saga.BestelSaga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandHandler {

    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    @Autowired
    BeheerService beheerService;

    @Autowired
    BestelSaga bestelSaga;

    public GeefStatusResponse geefStatus(GeefStatusCommand command){
        GeefStatusResponse response = beheerService.geefStatus(command.getId());
        log.info("-response status[{}] message[{}]", response.getStatus(), response.getMessage());
        return response;
    }

    public void bestellingGeannuleerd(AnnuleerBestellingResponse response){
        Response res = beheerService.bevestigAnnulatie(response.getId());
        log.info("-response status[{}] message[{}]", res.getStatus(), res.getMessage());
    }

    public void bestellingGereserveerd(ReserveerBestellingResponse response){
        if(response.getStatus() == ResponseStatus.GELUKT){
            bestelSaga.gereserveerdBestelling(response.getId(), response.getMedicijnen());
        }
        else{
            bestelSaga.reserverenMislukt(response.getId());
        }
    }
}
