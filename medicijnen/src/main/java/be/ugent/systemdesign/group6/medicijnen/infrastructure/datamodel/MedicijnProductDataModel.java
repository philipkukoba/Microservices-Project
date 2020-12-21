package be.ugent.systemdesign.group6.medicijnen.infrastructure.datamodel;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicijnProductDataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int catalogusItemId;
    private LocalDate vervalDatum;
    private Integer koelCelId;
    private String bestellingsId;
    private boolean verkocht;
}
