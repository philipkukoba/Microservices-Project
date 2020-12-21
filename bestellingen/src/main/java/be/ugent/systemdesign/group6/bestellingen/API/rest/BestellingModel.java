package be.ugent.systemdesign.group6.bestellingen.API.rest;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class BestellingModel {
    private String klantenId;
    private String thuisAdres;
    private String apotheekAdres;
    private HashMap<Integer, Integer> medicijnen;

    public BestellingModel(){}
}
