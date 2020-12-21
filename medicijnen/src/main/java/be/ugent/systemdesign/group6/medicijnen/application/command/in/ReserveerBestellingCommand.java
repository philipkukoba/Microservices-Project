package be.ugent.systemdesign.group6.medicijnen.application.command.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReserveerBestellingCommand extends CommandMetAntwoord {
    // {key: id van medicijn, value: aantal}
    private HashMap<Integer, Integer> medicijnen;
    private String id; // van bestelling
}
