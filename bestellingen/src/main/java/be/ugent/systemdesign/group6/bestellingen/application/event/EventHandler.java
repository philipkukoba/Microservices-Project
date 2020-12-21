package be.ugent.systemdesign.group6.bestellingen.application.event;

import be.ugent.systemdesign.group6.bestellingen.application.BeheerService;
import be.ugent.systemdesign.group6.bestellingen.application.Response;
import be.ugent.systemdesign.group6.bestellingen.domain.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventHandler {

    private static final Logger log = LoggerFactory.getLogger(EventHandler.class);

    @Autowired
    BeheerService beheerService;

    public void handleBestellingCompleet(BestellingCompleetEvent e){
        Response response = beheerService.updateStatus(e.getId(), Status.KLAAR_VOOR_VERZENDING);
        log.info("-response status[{}] message[{}]", response.getStatus(), response.getMessage());
    }

    public void handleBestellingVerzonden(BestellingVerzondenEvent e){
        Response response = beheerService.updateStatus(e.getId(), Status.VERZONDEN);
        log.info("-response status[{}] message[{}]", response.getStatus(), response.getMessage());
    }
}
