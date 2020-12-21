package be.ugent.systemdesign.group6.medicijnen.API.bus;

import be.ugent.systemdesign.group6.medicijnen.application.command.out.PlaatsBijAfvalCommand;
import be.ugent.systemdesign.group6.medicijnen.application.event.EventDispatcher;
import be.ugent.systemdesign.group6.medicijnen.domain.MedicijnCatalogusVerwijderdEvent;
import be.ugent.systemdesign.group6.medicijnen.domain.MedicijnKritischeWaardeDomainEvent;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageOutputGateway extends EventDispatcher {

    @Gateway(requestChannel = Channels.MEDICIJN_KRITISCHE_WAARDE)
    void publishMedicijnKritischeWaardeDomainEvent(MedicijnKritischeWaardeDomainEvent e);

    @Gateway(requestChannel = Channels.MEDICIJN_CATALOGUS_VERWIJDERD)
    void publishMedicijnCatalogusVerwijderdEvent(MedicijnCatalogusVerwijderdEvent e);

    @Gateway(requestChannel = Channels.PLAATS_BIJ_AFVAL)
    void stuurPlaatsBijAfvalCommand(PlaatsBijAfvalCommand c);
}

