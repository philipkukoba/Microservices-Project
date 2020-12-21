package be.ugent.systemdesign.group6.medicijnen.application.services;

import be.ugent.systemdesign.group6.medicijnen.application.Antwoord;

import java.time.LocalDate;
import java.util.HashMap;

public interface VoorraadService {
    // Lading:
    //        {
    //        key: naam van het medicijn,
    //        value:{
    //              key: vervaldatum,
    //              value: aantal geleverde 'doosjes' met die vervaldatum
    //              }
    //        }
    Antwoord verwerkLading(HashMap<String, HashMap<LocalDate, Integer>> lading);
}
