package be.ugent.systemdesign.group6.medicijnen.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Bestelling {
    private List<MedicijnProduct> medicijnen;
    private String bestellingsId;

    public void bevestig() {
        medicijnen.forEach(MedicijnProduct::bevestig);
    }

    public void annuleer() {
        medicijnen.forEach(MedicijnProduct::annuleer);
    }

    public void geefVrij() {
        medicijnen.forEach(MedicijnProduct::geefVrij);
    }
}
