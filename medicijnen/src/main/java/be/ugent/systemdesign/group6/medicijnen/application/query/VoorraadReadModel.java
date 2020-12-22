package be.ugent.systemdesign.group6.medicijnen.application.query;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductVoorraadReadModel {
    // ReadModel om voor een CatalogusItem, het aantal items in de voorraad weer te geven
    private int medicijnId, aantal;
}
