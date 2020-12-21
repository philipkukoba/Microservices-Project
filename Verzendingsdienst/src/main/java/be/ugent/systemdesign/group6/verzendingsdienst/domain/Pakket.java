package be.ugent.systemdesign.group6.verzendingsdienst.domain;

import java.time.LocalDate;
import java.util.UUID;
import java.util.logging.Logger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pakket {

    private final Logger logger = Logger.getLogger(Logger.class.getName());

    private static int uniqueId = 0;
    private Integer id;

    //private LocalDate bestelDatum;
    private LocalDate verzendDatum;

    private PakketStatus status;

    private Label label;

    private String orderID;

    public Pakket(/* LocalDate bestelDatum, */ String orderID) {
        this.id = this.uniqueId++;
        this.orderID = orderID;
        this.label = null;
        this.status = PakketStatus.NIET_GELABELD;
    }

    public void zetLabel(Label lbl){
        this.label = lbl;
        this.status = PakketStatus.GELABELD;
        logger.info("pakket gelabeld met label " + lbl.getId() + " geldigheidsdatum " + lbl.getGeldigheidsDatum());
    }

    public void verstuur() {
        if (this.status != PakketStatus.GELABELD) {
            throw new PakketNogNietGelabeld();
        }
        this.verzendDatum = LocalDate.now();
        this.status = PakketStatus.VERZONDEN;
    }

    public void annuleer() {
        this.status = PakketStatus.GEANNULEERD;
        logger.info("pakker status werd gezet op GEANNULEERD");
    }

}
