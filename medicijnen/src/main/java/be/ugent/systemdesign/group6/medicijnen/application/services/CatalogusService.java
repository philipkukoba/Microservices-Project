package be.ugent.systemdesign.group6.medicijnen.application.services;

import be.ugent.systemdesign.group6.medicijnen.application.Antwoord;
import be.ugent.systemdesign.group6.medicijnen.application.AntwoordMetData;
import be.ugent.systemdesign.group6.medicijnen.domain.CatalogusItem;

public interface CatalogusService {
    Antwoord verwijderMedicijnUitCatalogus(int id);

    AntwoordMetData voegMedicijnToeInCatalogus(CatalogusItem c);
}
