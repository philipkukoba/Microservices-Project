package be.ugent.systemdesign.group6.bestellingen.application.command;

import be.ugent.systemdesign.group6.bestellingen.domain.Bestelling;
import be.ugent.systemdesign.group6.bestellingen.domain.Medicijn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
public class ReserveerBestellingCommand {

    private String id;
    private HashMap<Integer, Integer> medicijnen;
    private String antwoordKanaal;

    public ReserveerBestellingCommand(Bestelling bestelling){
        this.id = bestelling.getId();
        this.medicijnen = new HashMap<>();
        for(Medicijn med : bestelling.getMedicijnen()){
            medicijnen.put(med.getId(), med.getAantal());
        }
    }
}
