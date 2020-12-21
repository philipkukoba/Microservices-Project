package be.ugent.systemdesign.group6.order.application.command;

import be.ugent.systemdesign.group6.order.application.Response;
import be.ugent.systemdesign.group6.order.application.ResponseStatus;
import lombok.Getter;

@Getter
public class AnnuleerBestellingResponse extends Response {
    private String id;
    public AnnuleerBestellingResponse(String message, ResponseStatus status, String id ) {
        super(message, status);
        this.id = id;
    }
}
