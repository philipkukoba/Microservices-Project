package be.ugent.systemdesign.group6.medicijnen.application.services;

import be.ugent.systemdesign.group6.medicijnen.application.command.out.ReserveerBestellingAntwoord;

import java.util.HashMap;

public interface BestelService {
    ReserveerBestellingAntwoord reserveer(String bestellingsId, HashMap<Integer, Integer> medicijnenIds);

    void geefVrij(String bestellingsId);

    void bevestig(String bestellingsId);

    void annuleerBestelling(String bestellingsId);
}
