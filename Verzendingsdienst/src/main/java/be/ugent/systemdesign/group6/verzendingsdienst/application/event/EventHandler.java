package be.ugent.systemdesign.group6.verzendingsdienst.application.event;

import be.ugent.systemdesign.group6.verzendingsdienst.application.Response;
import be.ugent.systemdesign.group6.verzendingsdienst.application.VerzendingsAdministratie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EventHandler {

    private final Logger logger = Logger.getLogger(Logger.class.getName());

    @Autowired
    VerzendingsAdministratie verzendingsAdministratie;

    public void handleOrderCompleet(OrderCompleetEvent event){
        String orderId = event.getId();
        Response response = verzendingsAdministratie.orderCompleet(orderId);
        logger.info(response.message);
    }

}
