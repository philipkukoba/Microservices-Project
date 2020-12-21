package be.ugent.systemdesign.group6.order.infrastructure;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AfvalcontainerDataModel {
    @Id
    private Integer containerId;
    private int maxCap;
    private int huidigeCap;

    public AfvalcontainerDataModel(Integer containerId, int maxCap) {
        this.containerId = containerId;
        this.maxCap = maxCap;
        this.huidigeCap = 0;
    }

}
