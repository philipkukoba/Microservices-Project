package be.ugent.systemdesign.group6.order.domain;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Afvalcontainer {
    private Integer containerId;
    private int maxCap;
    private int huidigeCap;

    public Afvalcontainer(Integer containerId, int maxCap) {
        this.containerId = containerId;
        this.maxCap = maxCap;
        this.huidigeCap = 0;
    }

    public boolean isVol(){
        return maxCap==huidigeCap;
    }

    public void gooiWeg(int aantal){
        this.huidigeCap+= aantal;
    }

    public void maakLeeg(){
        this.huidigeCap = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Afvalcontainer)) return false;
        Afvalcontainer that = (Afvalcontainer) o;
        return getContainerId().equals(that.getContainerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContainerId());
    }
}
