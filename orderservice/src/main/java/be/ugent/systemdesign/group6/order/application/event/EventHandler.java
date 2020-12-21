package be.ugent.systemdesign.group6.order.application.event;

import be.ugent.systemdesign.group6.order.application.MagazijnService;
import be.ugent.systemdesign.group6.order.application.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventHandler {

    private static final Logger log = LoggerFactory.getLogger(EventHandler.class);

    @Autowired
    MagazijnService magazijnService;

    public void handleMedicijnCatalogusVerwijderd(MedicijnCatalogusVerwijderdEvent e){
        log.info("Medicijn met id {} is verwijderd uit de catalogus en de plaats in het magazijn moet nu worden vrijgegeven.", e.getId());
        Response r = magazijnService.verwijderMedicijnUitMagazijn(e.getId());
        log.info(r.message);
    }
}
