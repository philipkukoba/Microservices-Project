package be.ugent.systemdesign.group6.order.application.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnnuleerBestellingCommand {
    private String id;
    private String responseDestination;
}
