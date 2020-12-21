package be.ugent.systemdesign.group6.medicijnen.application.command.in;

import be.ugent.systemdesign.group6.medicijnen.application.command.out.ReserveerBestellingAntwoord;
import be.ugent.systemdesign.group6.medicijnen.application.services.BestelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    @Autowired
    BestelService bestelService;

    public ReserveerBestellingAntwoord reserveerBestelling(ReserveerBestellingCommand c) {
        log.info("IN -> commando: reserveer bestelling {}", c.getId());
        return bestelService.reserveer(c.getId(), c.getMedicijnen());
    }

    public void geefVrijBestelling(GeefVrijBestellingCommand c) {
        log.info("IN -> commando: geef vrij bestelling {}", c.getId());
        bestelService.geefVrij(c.getId());
    }

    public void bevestigBestelling(BevestigBestellingCommand c) {
        log.info("IN -> commando: bevestig bestelling {}", c.getId());
        bestelService.bevestig(c.getId());
    }
}
