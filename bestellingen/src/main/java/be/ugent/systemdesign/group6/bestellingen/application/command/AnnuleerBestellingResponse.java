package be.ugent.systemdesign.group6.bestellingen.application.command;

import be.ugent.systemdesign.group6.bestellingen.application.Response;
import be.ugent.systemdesign.group6.bestellingen.application.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnnuleerBestellingResponse extends Response {

    private String id;

    public AnnuleerBestellingResponse(ResponseStatus status, String message, String id){
        super(status, message);
        this.id = id;
    }
}
