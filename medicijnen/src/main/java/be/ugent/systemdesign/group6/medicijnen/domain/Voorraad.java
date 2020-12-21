package be.ugent.systemdesign.group6.medicijnen.domain;

import be.ugent.systemdesign.group6.medicijnen.domain.seedwork.AggregateRoot;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Voorraad extends AggregateRoot {
    private static final double TOEGELATEN_TEMPERATUUR_AFWIJKING = 5.0;

    private List<MedicijnProduct> medicijnen;
    private CatalogusItem catalogusItem;

    public List<MedicijnProduct> getNietVerkochteMedicijnen() {
        return this.medicijnen.stream().filter(medicijnProduct -> !medicijnProduct.isVerkocht()).
                collect(Collectors.toList());
    }

    public void voegToe(List<MedicijnProduct> nieuweMedicijnen) {
        medicijnen.addAll(nieuweMedicijnen);
    }

    public void verwijder(List<MedicijnProduct> medicijnenTeVerwijderen) {
        boolean bovenKritischeWaarde = catalogusItem != null && getNietVerkochteMedicijnen().size() >= catalogusItem.getKritischeWaarde();
        medicijnen.removeAll(medicijnenTeVerwijderen);
        boolean onderKritischeWaarde = catalogusItem != null && getNietVerkochteMedicijnen().size() < catalogusItem.getKritischeWaarde();

        if (bovenKritischeWaarde && onderKritischeWaarde) {
            // het verwijderen zorg ervoor dat de voorraad onder de kritische waarde daalt
            // steeds 'kritischeWaarde' aantal producten bij bestellen, hier voor de eenvoud deze waarde,
            // kan uitgebreid worden met een apart attribuut
            voegDomainEventToe(new MedicijnKritischeWaardeDomainEvent(catalogusItem.getId(), catalogusItem.getKritischeWaarde()));
        }
    }

    public boolean reserveer(String bestellingsId, int aantal) {
        // nagaan of er nog genoeg producten vrij zijn om te reserveren
        int aantalVrijeProducten = (int) getNietVerkochteMedicijnen().stream().
                filter(productMedicijn -> productMedicijn.getBestellingsId() == null).count();

        if (aantalVrijeProducten < aantal) {
            return false;
        }

        // indien nog genoeg producten vrij zijn, het gewenste aantal reserveren
        int nogTeReserveren = aantal;
        int i = 0;

        while (nogTeReserveren > 0 && i < medicijnen.size()) {
            if (!medicijnen.get(i).isGereserveerd()) {
                medicijnen.get(i).reserveer(bestellingsId);
                nogTeReserveren--;
            }
            i++;
        }

        return true;
    }

    public void bevestig(String bestellingsId) {
        if (medicijnen.size() == 0) {
            return;
        }
        boolean bovenKritischeWaarde = catalogusItem != null && getNietVerkochteMedicijnen().size() >= catalogusItem.getKritischeWaarde();

        medicijnen.forEach(medicijnProduct -> {
            if (!medicijnProduct.isVerkocht() && medicijnProduct.isGereserveerd() &&
                    medicijnProduct.getBestellingsId() != null &&
                    medicijnProduct.getBestellingsId().equals(bestellingsId)) {
                medicijnProduct.bevestig();
            }
        });

        boolean onderKritischeWaarde = catalogusItem != null && getNietVerkochteMedicijnen().size() < catalogusItem.getKritischeWaarde();

        if (bovenKritischeWaarde && onderKritischeWaarde) {
            // deze bevestiging zorg ervoor dat de voorraad onder de kritische waarde daalt
            // steeds 'kritischeWaarde' aantal producten bij bestellen, hier voor de eenvoud deze waarde,
            // kan uitgebreid worden met een apart attribuut
            voegDomainEventToe(new MedicijnKritischeWaardeDomainEvent(catalogusItem.getId(), catalogusItem.getKritischeWaarde()));
        }
    }

    public boolean isHetNogGoed(double temp) {
        // product is slecht
        return !(catalogusItem.getGewensteTemperatuur() + TOEGELATEN_TEMPERATUUR_AFWIJKING < temp);
    }

    public void verwijderUitCatalogus() {
        List<MedicijnProduct> teVerwijderen = getNietVerkochteMedicijnen().stream().
                filter(medicijnProduct -> !medicijnProduct.isGereserveerd()).collect(Collectors.toList());
        medicijnen.removeAll(teVerwijderen);
        voegDomainEventToe(new MedicijnCatalogusVerwijderdEvent(catalogusItem.getId()));
    }
}
