package be.ugent.systemdesign.group6.order.application.command;

import be.ugent.systemdesign.group6.order.application.Medicijn;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaakOrderCommand {
    private String id;
    private String thuisAdres;
    private String apotheekAdres;
    private List<Medicijn> medicijnen;
}
