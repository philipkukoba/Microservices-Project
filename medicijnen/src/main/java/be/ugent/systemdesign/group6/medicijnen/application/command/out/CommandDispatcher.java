package be.ugent.systemdesign.group6.medicijnen.application.command.out;

import org.springframework.stereotype.Service;

@Service
public interface CommandDispatcher {
    void stuurPlaatsBijAfval(PlaatsBijAfvalCommand plaatsBijAfvalCommand);
}
