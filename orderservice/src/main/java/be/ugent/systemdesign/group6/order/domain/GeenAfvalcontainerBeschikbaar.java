package be.ugent.systemdesign.group6.order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeenAfvalcontainerBeschikbaar extends Exception{
    private int hoeveelheid;

    @Override
    public String getMessage() {
        return super.getMessage() + "Er is geen afvalcontainer beschikbaar om " + hoeveelheid + " medicijnen weg te gooien.";
    }
}
