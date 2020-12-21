package be.ugent.systemdesign.group6.medicijnen.application.event;

import be.ugent.systemdesign.group6.medicijnen.domain.MedicijnCatalogusVerwijderdEvent;
import be.ugent.systemdesign.group6.medicijnen.domain.MedicijnKritischeWaardeDomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class ProductVoorraadEventListener {

    private static final Logger log = LoggerFactory.getLogger(ProductVoorraadEventListener.class);

    @Autowired
    private EventDispatcher eventDispatcher;


    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @EventListener
    public void handleMedicijnKritischeWaardeDomainEvent(MedicijnKritischeWaardeDomainEvent e) {
        log.info("OUT-> event: kritische waarde {}, bij te bestellen {}", e.getCatalogusId(), e.getAantalBijTeBestellen());
        eventDispatcher.publishMedicijnKritischeWaardeDomainEvent(e);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @EventListener
    public void handleMedicijnCatalogusVerwijderdEvent(MedicijnCatalogusVerwijderdEvent e) {
        log.info("OUT-> event: medicijn met id: {} uit de catalogus verwijderd", e.getId());
        eventDispatcher.publishMedicijnCatalogusVerwijderdEvent(e);
    }
}
