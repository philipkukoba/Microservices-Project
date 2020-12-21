package be.ugent.systemdesign.group6.order.domain;

import be.ugent.systemdesign.group6.order.domain.seedwork.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Order extends AggregateRoot {
    private String id;
    private List<Pakket> pakketten;
    private boolean klaargemaakt;

    public Order(String id, List<Pakket> pakketten){
        this.id=id;
        this.pakketten=pakketten;
        klaargemaakt = false;
    }

    public void orderCompleetEventAanmaken(){
        //Voor elk pakket in het order moet er een event uitgestuurd worden
        for(Pakket p:pakketten){
            if(p != null && p.getMedicijnen() != null && p.getMedicijnen().size() != 0){
                addDomainEvent(new OrderCompleetEvent(id));
            }
        }
    }
}
