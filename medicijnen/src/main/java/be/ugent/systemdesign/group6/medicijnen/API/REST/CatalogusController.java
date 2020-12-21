package be.ugent.systemdesign.group6.medicijnen.API.REST;

import be.ugent.systemdesign.group6.medicijnen.API.REST.restmodels.CatalogusItemRestModel;
import be.ugent.systemdesign.group6.medicijnen.application.Antwoord;
import be.ugent.systemdesign.group6.medicijnen.application.AntwoordMetData;
import be.ugent.systemdesign.group6.medicijnen.application.services.CatalogusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/catalogus")
@CrossOrigin(origins="*")
public class CatalogusController {

    @Autowired
    CatalogusService service;

    @PostMapping
    public ResponseEntity voegMedicijnToeInCatalogus(@RequestBody CatalogusItemRestModel catalogusItemRestModel) {
        AntwoordMetData antwoord = service.voegMedicijnToeInCatalogus(catalogusItemRestModel.toDomainModel());
        return ResponseEntityBuilder.maakBoodschapMetData(antwoord);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> verwijderMedicijnUitCatalogus(@PathVariable("id") Integer id) {
        Antwoord antwoord = service.verwijderMedicijnUitCatalogus(id);
        return ResponseEntityBuilder.maakBoodschap(antwoord);
    }
}
