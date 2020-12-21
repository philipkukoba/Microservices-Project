package be.ugent.systemdesign.group6.order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Pakket {
    private String adres;
    private List<Integer> medicijnen;
}
