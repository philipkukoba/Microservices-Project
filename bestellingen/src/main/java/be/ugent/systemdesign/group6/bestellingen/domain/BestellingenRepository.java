package be.ugent.systemdesign.group6.bestellingen.domain;

public interface BestellingenRepository {
    Bestelling getBestelling(String id);
    void saveBestelling(Bestelling bestelling);
    Status getStatus(String id);
    void removeBestelling(String id);
}
