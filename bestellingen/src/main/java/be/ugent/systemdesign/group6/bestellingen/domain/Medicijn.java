package be.ugent.systemdesign.group6.bestellingen.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Medicijn {
    private int id;
    private String naam;
    private Boolean voorschrift;
    private double prijs;
    private int aantal;

    public Medicijn(int id, String naam, Boolean voorschrift, double prijs, int aantal){
        this.id = id;
        this.naam = naam;
        this.voorschrift = voorschrift;
        this.prijs = prijs;
        this.aantal = aantal;
    }

    public Medicijn(int id, int aantal){
        this.id = id;
        this.aantal = aantal;
    }

    public Medicijn(){}
}
