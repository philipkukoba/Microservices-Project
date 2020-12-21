package be.ugent.systemdesign.group6.bestellingen.application.command;

public interface CommandDispatcher {
    void sendAnnuleerBestellingCommand(AnnuleerBestellingCommand command);
    void sendReserveerBestellingCommand(ReserveerBestellingCommand command);
    void sendGeefVrijBestellingCommand(GeefVrijBestellingCommand command);
    void sendBevestigBestellingCommand(BevestigBestellingCommand command);
    void sendMaakOrderCommand(MaakOrderCommand command);
    void sendMaakFactuurCommand(MaakFactuurCommand command);
}
