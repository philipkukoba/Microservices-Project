package be.ugent.systemdesign.group6.verzendingsdienst.application.command;

//handlers maken gebruik van services, en services maken gebruik van dispatcher
public interface CommandDispatcher {
    void plaatsGeannuleerdeBestellingTerugCommand(PlaatsGeannuleerdeBestellingTerugCommand command);
    void bestellingGeannuleerdCommand(BestellingGeannuleerdCommand command);
}
