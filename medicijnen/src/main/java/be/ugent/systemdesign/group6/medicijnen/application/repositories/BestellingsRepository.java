package be.ugent.systemdesign.group6.medicijnen.application.repositories;

import be.ugent.systemdesign.group6.medicijnen.domain.Bestelling;

import java.util.Optional;

public interface BestellingsRepository {
    Optional<Bestelling> geefBestelling(String bestellingsId);

    Bestelling slaOp(Bestelling bestelling);
}
