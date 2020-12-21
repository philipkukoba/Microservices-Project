package be.ugent.systemdesign.group6.order.application.command;

import be.ugent.systemdesign.group6.order.application.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CommandHandler {

    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    @Autowired
    AfvalService afvalService;

    @Autowired
    PakketService pakketService;

    public void plaatsBijAfval(PlaatsBijAfvalCommand command){
        Response r = afvalService.plaatsBijAfval(command.id, command.aantal);
        log.info("status {}, message {}", r.status, r.message);
    }

    public void plaatsRetourBijAfval(PlaatsRetourBijAfvalCommand command){
        Response r = afvalService.retourBijAfval( command.aantal);
        log.info("status {}, message {}", r.status, r.message);
    }

    public void maakOrder(MaakOrderCommand command){
        Response r = pakketService.stelOrderOp(command);
        log.info("status {}, message {}", r.status, r.message);
    }

    public AnnuleerBestellingResponse annuleerBestelling(AnnuleerBestellingCommand command){
        AnnuleerBestellingResponse r = pakketService.probeerBestellingTeAnnuleren(command.getId());
        log.info("status {}, message {}", r.status, r.message);
        return r;
    }

}
