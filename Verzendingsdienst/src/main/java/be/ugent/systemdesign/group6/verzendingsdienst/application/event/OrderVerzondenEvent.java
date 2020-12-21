package be.ugent.systemdesign.group6.verzendingsdienst.application.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderVerzondenEvent {
    private String id;
}
