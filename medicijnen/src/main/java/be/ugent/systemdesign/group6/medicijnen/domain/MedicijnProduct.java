package be.ugent.systemdesign.group6.medicijnen.domain;

import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MedicijnProduct {
    private int id;
    private int catalogusItemId;
    private LocalDate vervalDatum;
    private Integer koelCelId;
    private String bestellingsId;
    private boolean verkocht;
    /*
    Combinatie van bestellingsId en verkocht, definieert een aantal toestanden
    bestellingsId | verkocht |
    null          | false    | in de voorraad
    ingevuld      | false    | gereserveerd
    null          | true     | deze combinatie bestaat niet
    ingevuld      | true     | verkocht
     */

    public void plaatsInKoelcel(Integer koelCelId) {
        this.koelCelId = koelCelId;
    }

    public void reserveer(String bestellingsId) {
        this.bestellingsId = bestellingsId;
    }

    public boolean isBeschikbaar() {
        return this.bestellingsId != null;
    }

    public void bevestig() {
        if (this.bestellingsId != null && !this.verkocht) {
            this.verkocht = true;
            // eenmaal verkocht kan die niet meer in een koelcel zitten
            this.koelCelId = null;
        }
    }

    public void annuleer() {
        this.verkocht = false;
        this.bestellingsId = null;
    }

    public void geefVrij() {
        if (this.bestellingsId != null && !this.verkocht) {
            this.bestellingsId = null;
        }
    }

    public boolean isVervalDatumOverschreden() {
        // https://stackoverflow.com/questions/27005861/calculate-days-between-two-dates-in-java-8
        long dagenVoorVervalDatum = Duration.between(vervalDatum.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
        // product moet minstens 14 dagen goed blijven vooraleer
        return dagenVoorVervalDatum >= 14;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicijnProduct that = (MedicijnProduct) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
