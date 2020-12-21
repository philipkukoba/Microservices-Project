package be.ugent.systemdesign.group6.bestellingen.application.command;

import be.ugent.systemdesign.group6.bestellingen.application.Response;
import be.ugent.systemdesign.group6.bestellingen.application.ResponseStatus;
import be.ugent.systemdesign.group6.bestellingen.domain.Medicijn;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReserveerBestellingResponse extends Response {

    private String id;
    private List<Medicijn> medicijnen;

    public ReserveerBestellingResponse(String id, ResponseStatus status, String message, List<Medicijn> medicijnen){
        super(status, message);
        this.id = id;
        this.medicijnen = medicijnen;
    }
}
