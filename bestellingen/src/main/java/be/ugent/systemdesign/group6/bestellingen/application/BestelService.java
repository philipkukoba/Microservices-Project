package be.ugent.systemdesign.group6.bestellingen.application;

import java.util.HashMap;

public interface BestelService {
    Response plaatsBestelling(String klantenId, String thuisAdres, String apotheekAdres, HashMap<Integer, Integer> medicijnen);
    Response annuleerBestelling(String id);
    Response plaatsBestellingTicket(String klantenId, String thuisAdres, String apotheekAdres, HashMap<Integer, Integer> medicijnen);
}
