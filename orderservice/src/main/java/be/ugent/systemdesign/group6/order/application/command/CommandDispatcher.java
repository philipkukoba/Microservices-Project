package be.ugent.systemdesign.group6.order.application.command;

public interface CommandDispatcher {

    void stuurHaalAfvalOpCommand(HaalAfvalOpCommand command);
}
