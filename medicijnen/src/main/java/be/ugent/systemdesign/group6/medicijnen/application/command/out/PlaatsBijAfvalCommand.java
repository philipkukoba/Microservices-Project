package be.ugent.systemdesign.group6.medicijnen.application.command.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaatsBijAfvalCommand {
    private int id; // van CatalogusItem
    private int aantal;
}
