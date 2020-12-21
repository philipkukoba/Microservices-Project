package be.ugent.systemdesign.group6.medicijnen.application;

import lombok.Getter;

@Getter
public class AntwoordMetData extends Antwoord {
    private Object data;

    public AntwoordMetData(Status status, String boodschap, Object data) {
        super(status, boodschap);
        this.data = data;
    }
}
