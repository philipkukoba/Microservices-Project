package be.ugent.systemdesign.group6.medicijnen.application.servicesimpl;

import be.ugent.systemdesign.group6.medicijnen.application.Antwoord;
import be.ugent.systemdesign.group6.medicijnen.application.AntwoordMetData;
import be.ugent.systemdesign.group6.medicijnen.application.Status;
import be.ugent.systemdesign.group6.medicijnen.application.command.out.CommandDispatcher;
import be.ugent.systemdesign.group6.medicijnen.application.command.out.PlaatsBijAfvalCommand;
import be.ugent.systemdesign.group6.medicijnen.application.repositories.CatalogusItemRepository;
import be.ugent.systemdesign.group6.medicijnen.application.repositories.VoorraadRepository;
import be.ugent.systemdesign.group6.medicijnen.application.services.CatalogusService;
import be.ugent.systemdesign.group6.medicijnen.domain.CatalogusItem;
import be.ugent.systemdesign.group6.medicijnen.domain.Voorraad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CatalogusServiceImpl implements CatalogusService {

    @Autowired
    private VoorraadRepository voorraadRepository;

    @Autowired
    private CatalogusItemRepository catalogusItemRepository;

    @Autowired
    private CommandDispatcher dispatcher;

    @Override
    @Transactional
    public Antwoord verwijderMedicijnUitCatalogus(int catalogusId) {
        Optional<Voorraad> voorraadOptional = voorraadRepository.geef(catalogusId);
        if (voorraadOptional.isEmpty())
            // bevindt zich niet in de catalogus of er is al geen voorraad meer
            return new Antwoord(Status.GELUKT, "");
        Voorraad voorraad = voorraadOptional.get();

        // verwijderen van resterende voorraad
        int aantal = (int) voorraad.getNietVerkochteMedicijnen().stream().
                filter(medicijnProduct -> !medicijnProduct.isBeschikbaar()).count();
        if (aantal > 0) {
            PlaatsBijAfvalCommand afvalCommand = new PlaatsBijAfvalCommand(catalogusId, aantal);
            dispatcher.stuurPlaatsBijAfval(afvalCommand);
        }

        voorraad.verwijderUitCatalogus();
        voorraadRepository.slaOp(voorraad);
        voorraadRepository.verwijderUitCatalogus(catalogusId);

        return new Antwoord(Status.GELUKT, "");
    }

    /* Hier geen @Transactional want anders kan je de exceptie niet opvangen.
        Het maakt ook niet uit hier aangezien er maar 1 operatie op de databank wordt uitgevoerd. */
    @Override
    public AntwoordMetData voegMedicijnToeInCatalogus(CatalogusItem c) {
        Optional<CatalogusItem> catalogusItemOptional = catalogusItemRepository.zoekOpNaam(c.getNaam());
        if (catalogusItemOptional.isPresent()) {
            return new AntwoordMetData(Status.NIET_GELUKT, "Item in catalogus met naam " + c.getNaam() + " bestaat al.", null);
        }
        try {
            Voorraad voorraad = Voorraad.builder().catalogusItem(c).medicijnen(new ArrayList<>()).build();
            voorraad = voorraadRepository.slaOp(voorraad);
            return new AntwoordMetData(Status.GELUKT, "", voorraad.getCatalogusItem());
        } catch (Exception e) {
            return new AntwoordMetData(Status.NIET_GELUKT, e.getMessage(), null);
        }
    }

}
