package be.ugent.systemdesign.group6.medicijnen.application.command.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeefVrijBestellingCommand {
    private String id; // van bestelling
}
