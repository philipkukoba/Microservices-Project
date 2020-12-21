package be.ugent.systemdesign.group6.bestellingen.application.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown  =  true)
public class BestellingVerzondenEvent {

    private String id;
}
