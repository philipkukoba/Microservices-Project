package be.ugent.systemdesign.group6.order.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GeenPlaatsInMagazijn extends Exception{
    private Integer medicijnId;

    @Override
    public String getMessage(){
        return "Het mgazijn is vol. Er is geen plaats meer om medicijn met Id "+ medicijnId + " op te slaan.";
    }
}
