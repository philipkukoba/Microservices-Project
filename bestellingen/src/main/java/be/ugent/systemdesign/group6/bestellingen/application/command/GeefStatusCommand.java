package be.ugent.systemdesign.group6.bestellingen.application.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeefStatusCommand {
    private String id;
    private String responseDestination;
}
