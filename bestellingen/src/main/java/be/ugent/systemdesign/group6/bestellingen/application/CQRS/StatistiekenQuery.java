package be.ugent.systemdesign.group6.bestellingen.application.CQRS;

import java.util.HashMap;

public interface StatistiekenQuery {

    HashMap<Integer, MedicijnReadModel> genereerStatistieken();
}
