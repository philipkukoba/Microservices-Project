package be.ugent.systemdesign.group6.order.application.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class PlaatsRetourBijAfvalCommand {
    int id;
    int aantal;
}
