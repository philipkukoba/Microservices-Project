package be.ugent.systemdesign.group6.verzendingsdienst.application.event;

public interface EventDispatcher {
    void publishOrderVerzondenEvent(OrderVerzondenEvent event);
}
