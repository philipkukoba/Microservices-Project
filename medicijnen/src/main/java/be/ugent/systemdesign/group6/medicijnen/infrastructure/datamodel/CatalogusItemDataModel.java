package be.ugent.systemdesign.group6.medicijnen.infrastructure.datamodel;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogusItemDataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String naam;
    private String beschrijving;
    private boolean voorschriftNoodzakelijk;
    private double gewensteTemperatuur, prijs;
    private int kritischeWaarde;
}
