package be.ugent.systemdesign.group6.order.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaatsDataModel {
    @Id
    private int rek;
    private Integer idMedicijn;

    public PlaatsDataModel(int rek){
        this.rek=rek;
        this.idMedicijn=null;
    }
}
