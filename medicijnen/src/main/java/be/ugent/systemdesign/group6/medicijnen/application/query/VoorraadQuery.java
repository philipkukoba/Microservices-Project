package be.ugent.systemdesign.group6.medicijnen.application.query;

import java.util.List;

public interface VoorraadQuery {
    // Per CatalogusMedicijn het aantal in de voorraad weergeven.
    List<VoorraadReadModel> geefCompleteVoorraad();
}
