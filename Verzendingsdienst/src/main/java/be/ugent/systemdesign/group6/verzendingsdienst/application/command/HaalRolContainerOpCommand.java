package be.ugent.systemdesign.group6.verzendingsdienst.application.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HaalRolContainerOpCommand {
    private String id;
}
