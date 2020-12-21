package be.ugent.systemdesign.group6.medicijnen.application.services;

import be.ugent.systemdesign.group6.medicijnen.application.Antwoord;
import be.ugent.systemdesign.group6.medicijnen.application.command.out.ReserveerBestellingAntwoord;

import java.util.HashMap;

public interface BestelService {
    ReserveerBestellingAntwoord reserveer(String bestellingsId, HashMap<Integer, Integer> medicijnenIds);

    Antwoord geefVrij(String bestellingsId);

    Antwoord bevestig(String bestellingsId);

    Antwoord annuleerBestelling(String bestellingsId);
}
