package be.ugent.systemdesign.group6.order.application.event;

import be.ugent.systemdesign.group6.order.domain.OrderCompleetEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class OrderEventListener {

    @Autowired
    EventDispatcher eventDispatcher;

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    @Async
    @TransactionalEventListener(phase= TransactionPhase.AFTER_COMMIT)
    public void handleOrderCompleetEventAsync(OrderCompleetEvent event){
        log.info(event.getId());
        eventDispatcher.publishOrderCompleetEvent(event);
    }
}
