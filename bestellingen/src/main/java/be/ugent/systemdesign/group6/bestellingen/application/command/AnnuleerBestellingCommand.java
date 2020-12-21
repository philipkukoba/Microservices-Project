package be.ugent.systemdesign.group6.bestellingen.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnnuleerBestellingCommand {

    private String id;
    private String responseDestination;

    public AnnuleerBestellingCommand(String id){
        this.id = id;
    }
}
