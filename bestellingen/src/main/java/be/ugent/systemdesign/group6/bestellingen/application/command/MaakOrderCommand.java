package be.ugent.systemdesign.group6.bestellingen.application.command;

import be.ugent.systemdesign.group6.bestellingen.domain.Medicijn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MaakOrderCommand {

    private String id;
    private String thuisAdres;
    private String apotheekAdres;
    private List<Medicijn> medicijnen;
}
