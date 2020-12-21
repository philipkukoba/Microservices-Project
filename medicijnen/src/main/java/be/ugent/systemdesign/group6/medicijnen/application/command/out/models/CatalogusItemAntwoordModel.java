package be.ugent.systemdesign.group6.medicijnen.application.command.out.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CatalogusItemAntwoordModel {
    /* CatalgousItemAntwoordModel zal enkel de data naar buiten sturen die externe microservice
    nodig hebben/ mogen weten.
    */
    private int aantal;
    private String naam;
    private boolean voorschrift;
    private double prijs;
    private int id;
}
