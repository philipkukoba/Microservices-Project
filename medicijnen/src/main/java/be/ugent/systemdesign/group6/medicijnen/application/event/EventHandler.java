package be.ugent.systemdesign.group6.medicijnen.application.event;

import be.ugent.systemdesign.group6.medicijnen.application.event.in.AfwijkendeTemperatuurEvent;
import be.ugent.systemdesign.group6.medicijnen.application.event.in.BestellingAnnulerenVoltooidEvent;
import be.ugent.systemdesign.group6.medicijnen.application.services.BestelService;
import be.ugent.systemdesign.group6.medicijnen.application.services.MonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class EventHandler {

    private static final Logger log = LoggerFactory.getLogger(EventHandler.class);

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private BestelService bestelService;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void verwerkAfwijkendeTemperatuur(AfwijkendeTemperatuurEvent e) {
        log.info("IN -> event: afwijkende temperatuur koelcel {} met {}", e.getKoelCelId(), e.getTemperatuur());
        monitorService.verwijderTeHogeTemperatuur(e.getKoelCelId(), e.getTemperatuur());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void verwerkBestellingAnnulerenVoltooid(BestellingAnnulerenVoltooidEvent e) {
        log.info("IN -> event: Bestelling met id: {} werd geannuleerd", e.getId());
        bestelService.annuleerBestelling(e.getId());
    }

}
