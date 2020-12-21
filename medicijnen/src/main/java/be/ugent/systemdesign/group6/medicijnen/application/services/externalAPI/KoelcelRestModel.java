package be.ugent.systemdesign.group6.medicijnen.application.services.externalAPI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class KoelcelRestModel {
    // REST model om antwoord van koelcel endpoint op te vangen
    private int id; // id van koelcel
    private double min, max; // producten met een gewenste temperatuur tussen min en max mogen in deze cel
}
