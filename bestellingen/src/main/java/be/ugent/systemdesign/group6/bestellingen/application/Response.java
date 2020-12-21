package be.ugent.systemdesign.group6.bestellingen.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response {

    private ResponseStatus status;
    private String message;
}
