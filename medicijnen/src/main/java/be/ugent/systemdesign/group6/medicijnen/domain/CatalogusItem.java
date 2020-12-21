package be.ugent.systemdesign.group6.medicijnen.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CatalogusItem {
    private int id;
    private String naam, beschrijving;
    private boolean voorschriftNoodzakelijk;
    private double gewensteTemperatuur, prijs;
    private int kritischeWaarde;
}
