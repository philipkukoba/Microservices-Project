package be.ugent.systemdesign.group6.bestellingen.application;

import be.ugent.systemdesign.group6.bestellingen.application.command.GeefStatusResponse;
import be.ugent.systemdesign.group6.bestellingen.domain.Status;

public interface BeheerService {
    Response updateStatus(String id, Status status);
    GeefStatusResponse geefStatus(String id);
    Response bevestigAnnulatie(String id);
}
