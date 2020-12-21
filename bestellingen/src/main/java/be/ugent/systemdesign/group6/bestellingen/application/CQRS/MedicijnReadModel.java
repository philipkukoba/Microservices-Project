package be.ugent.systemdesign.group6.bestellingen.application.CQRS;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MedicijnReadModel {

    private int id;
    private String naam;
    private int aantal;

    public void telBijAantal(int aantal){
        this.aantal += aantal;
    }
}
