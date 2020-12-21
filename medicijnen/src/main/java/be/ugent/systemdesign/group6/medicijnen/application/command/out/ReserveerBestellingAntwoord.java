package be.ugent.systemdesign.group6.medicijnen.application.command.out;

import be.ugent.systemdesign.group6.medicijnen.application.Antwoord;
import be.ugent.systemdesign.group6.medicijnen.application.Status;
import be.ugent.systemdesign.group6.medicijnen.application.command.out.models.CatalogusItemAntwoordModel;
import be.ugent.systemdesign.group6.medicijnen.domain.CatalogusItem;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReserveerBestellingAntwoord extends Antwoord {
    private String id; // van bestelling
    private List<CatalogusItemAntwoordModel> medicijnen; // medicijnen (gereduceerd model) die bij deze bestelling horen

    // input voor medicijnen: HashMap met {key: CatalogusItem, value: aantal}
    // conversie naar een lijst van CatalogusItemAntwoordModel die telkens data van 1 entry bevat
    public ReserveerBestellingAntwoord(String id, Status status, HashMap<CatalogusItem, Integer> medicijnen, String boodschap) {
        super(status, boodschap);

        this.medicijnen = medicijnen.entrySet().stream().map(catalogusItemIntegerEntry -> {
            CatalogusItem item = catalogusItemIntegerEntry.getKey();
            return CatalogusItemAntwoordModel.builder().aantal(catalogusItemIntegerEntry.getValue()).naam(item.getNaam()).
                    prijs(item.getPrijs()).voorschrift(item.isVoorschriftNoodzakelijk()).
                    id(catalogusItemIntegerEntry.getKey().getId()).build();
        }).collect(Collectors.toList());

        this.id = id;
    }
}
