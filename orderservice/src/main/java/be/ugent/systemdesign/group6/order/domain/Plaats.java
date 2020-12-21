package be.ugent.systemdesign.group6.order.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Plaats {
    private int rek;
    private Integer idMedicijn;

    public Plaats(int rek){
        this.rek=rek;
        this.idMedicijn=null;
    }

    public void verwijderMedicijn(){
        this.idMedicijn=null;
    }
}
