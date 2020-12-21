package be.ugent.systemdesign.group6.verzendingsdienst.infrastructure;

import be.ugent.systemdesign.group6.verzendingsdienst.domain.PakketStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class PakketDataModel {

    @Id
    @Getter
    private int id;

    private LocalDate verzendDatum;

    //String barCode, LocalDate geldigheidsDatum
    private String labelID;
    private LocalDate labelGeldigheidsDatum;

    private String orderID;

    private String status;

    public PakketDataModel(int id, LocalDate verzendDatum, String labelID, LocalDate labelGeldigheidsDatum, String orderID, PakketStatus status) {
        this.id = id;
        this.verzendDatum = verzendDatum;

        this.labelID = labelID;
        this.labelGeldigheidsDatum = labelGeldigheidsDatum;
        /*
        if (label != null) {
            this.labelID = label.getId();
            this.labelGeldigheidsDatum = label.getGeldigheidsDatum();
        }
        else {
            this.labelID = null;
        } */

        this.orderID = orderID;
        this.status = status.name();
    }
}
