package be.ugent.systemdesign.group6.bestellingen.application.event;

import be.ugent.systemdesign.group6.bestellingen.domain.BestellingGeannuleerdEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class BestellingEventListener {

    private static final Logger log = LoggerFactory.getLogger(BestellingEventListener.class);

    @Autowired
    EventDispatcher eventDispatcher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEventSync(BestellingGeannuleerdEvent e){
        log.info("> Bestelling sync " + e.getId() + " geannuleerd op tijdstip " + e.getCreatedTime());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEventAsync(BestellingGeannuleerdEvent e){
        log.info("> Bestelling async " + e.getId() + " geannuleerd op tijdstip " + e.getCreatedTime());
        eventDispatcher.publishBestellingEvent(e);
    }
}
