package be.ugent.systemdesign.group6.bestellingen.application.command;

import be.ugent.systemdesign.group6.bestellingen.application.Response;
import be.ugent.systemdesign.group6.bestellingen.application.ResponseStatus;
import lombok.Getter;

@Getter
public class GeefStatusResponse extends Response {

    private String id;
    private String bestellingStatus;

    public GeefStatusResponse(ResponseStatus status, String message, String id, String bestellingStatus){
        super(status, message);
        this.id = id;
        this.bestellingStatus = bestellingStatus;
    }
}
