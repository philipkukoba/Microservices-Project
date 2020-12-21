package be.ugent.systemdesign.group6.medicijnen.API.REST.restmodels;

import be.ugent.systemdesign.group6.medicijnen.domain.CatalogusItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CatalogusItemRestModel {
    /* CatalogusItemRestModel is een gereduceerde versie van het CatalogusItem, waarbij de id werd weggelaten
        omdat deze door deze microservice wordt gegenereerd.
     */
    private String naam, beschrijving;
    private boolean voorschriftNoodzakelijk;
    private double gewensteTemperatuur, prijs;
    private int kritischeWaarde;

    public CatalogusItem toDomainModel() {
        return CatalogusItem.builder().id(0).beschrijving(beschrijving).naam(naam).
                voorschriftNoodzakelijk(voorschriftNoodzakelijk).gewensteTemperatuur(gewensteTemperatuur).
                kritischeWaarde(kritischeWaarde).prijs(prijs).build();
    }
}
