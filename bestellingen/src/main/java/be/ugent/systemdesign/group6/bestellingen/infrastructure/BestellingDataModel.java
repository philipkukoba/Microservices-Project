package be.ugent.systemdesign.group6.bestellingen.infrastructure;

import be.ugent.systemdesign.group6.bestellingen.domain.Medicijn;
import be.ugent.systemdesign.group6.bestellingen.domain.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
public class BestellingDataModel {

    @Id
    private String id;
    private String klantenId;
    private String thuisAdres;
    private String apotheekAdres;
    private String status;
    private List<Medicijn> medicijnen;

    public BestellingDataModel(String id, String klantenId, Status status, String thuisAdres, String apotheekAdres, List<Medicijn> medicijnen){
        this.id = id;
        this.klantenId = klantenId;
        this.status = status.name();
        this.thuisAdres = thuisAdres;
        this.apotheekAdres = apotheekAdres;
        this.medicijnen = medicijnen;
    }
}
