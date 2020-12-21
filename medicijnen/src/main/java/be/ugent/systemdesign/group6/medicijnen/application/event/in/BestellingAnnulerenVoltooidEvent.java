package be.ugent.systemdesign.group6.medicijnen.application.event.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BestellingAnnulerenVoltooidEvent {
    private String id; // van Bestelling
}
