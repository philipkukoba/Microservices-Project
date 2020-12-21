package be.ugent.systemdesign.group6.verzendingsdienst.application.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCompleetEvent {

    private String id;

}
