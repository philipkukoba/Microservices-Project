package be.ugent.systemdesign.group6.medicijnen.application.command.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class CommandMetAntwoord {
    private String antwoordKanaal;
}
