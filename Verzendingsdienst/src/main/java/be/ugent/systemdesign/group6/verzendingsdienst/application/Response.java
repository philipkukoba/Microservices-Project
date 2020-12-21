package be.ugent.systemdesign.group6.verzendingsdienst.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {
    final public String message;
    final public ResponseStatus status;
}
