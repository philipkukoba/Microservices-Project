package be.ugent.systemdesign.group6.order.application.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HaalAfvalOpCommand {
    private boolean afhalen;

}
