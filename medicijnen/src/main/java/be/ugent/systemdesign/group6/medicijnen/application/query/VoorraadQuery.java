package be.ugent.systemdesign.group6.medicijnen.application.query;

import java.util.List;

public interface ProductVoorraadQuery {
    // Per CatalogusMedicijn het aantal in de voorraad weergeven.
    List<ProductVoorraadReadModel> geefCompleteVoorraad();
}
