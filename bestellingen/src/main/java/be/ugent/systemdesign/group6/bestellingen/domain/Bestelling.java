package be.ugent.systemdesign.group6.bestellingen.domain;

import java.util.List;

import be.ugent.systemdesign.group6.bestellingen.domain.seedwork.AggregateRoot;
import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Bestelling extends AggregateRoot {

    private String id;
    private String klantenId;
    private String thuisAdres;
    private String apotheekAdres;
    private Status status;
    private List<Medicijn> medicijnen;

    public Bestelling(String klantenId, String thuisAdres, String apotheekAdres, List<Medicijn> medicijnen){
        this.id = new ObjectId().toString();
        this.klantenId = klantenId;
        this.thuisAdres = thuisAdres;
        this.apotheekAdres = apotheekAdres;
        this.status = Status.BESTELD_DOOR_KLANT;
        this.medicijnen = medicijnen;
    }

    public void setStatus(Status status) {
        if(this.status.ordinal() < status.ordinal()){
            this.status = status;
        }
    }

    public void annuleerBestelling(){
        status = Status.GEANNULEERD;
        addDomainEvent(new BestellingGeannuleerdEvent(id));
    }
}
