package be.ugent.systemdesign.group6.medicijnen.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Antwoord {
    private Status status;
    private String boodschap;
}
