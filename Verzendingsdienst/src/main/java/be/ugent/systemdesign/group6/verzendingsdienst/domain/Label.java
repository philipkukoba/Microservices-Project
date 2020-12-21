package be.ugent.systemdesign.group6.verzendingsdienst.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Label {

    private final String barCode;
    private final LocalDate geldigheidsDatum;

    public Label(String barCode, LocalDate geldigheidsDatum) {
        this.barCode = barCode;
        this.geldigheidsDatum = geldigheidsDatum;
    }

    public String getId(){
        return barCode;
    }

}
