package be.ugent.systemdesign.group6.bestellingen.API.rest;

import be.ugent.systemdesign.group6.bestellingen.application.CQRS.MedicijnReadModel;
import lombok.Getter;

@Getter
public class MedicijnViewModel {

    private int id;
    private String naam;
    private int aantal;

    public MedicijnViewModel(MedicijnReadModel m){
        this.id = m.getId();
        this.naam = m.getNaam();
        this.aantal = m.getAantal();
    }
}
