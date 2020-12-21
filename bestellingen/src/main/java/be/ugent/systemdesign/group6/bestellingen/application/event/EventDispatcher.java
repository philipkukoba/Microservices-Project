package be.ugent.systemdesign.group6.bestellingen.application.event;

import be.ugent.systemdesign.group6.bestellingen.domain.BestellingGeannuleerdEvent;

public interface EventDispatcher {

    void publishBestellingEvent(BestellingGeannuleerdEvent e);
}
