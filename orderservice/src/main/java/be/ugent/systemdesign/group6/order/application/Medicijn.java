package be.ugent.systemdesign.group6.order.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Medicijn {
    private int id;
    private String naam;
    private boolean voorschrift;
    private double prijs;
    private int aantal;
}
