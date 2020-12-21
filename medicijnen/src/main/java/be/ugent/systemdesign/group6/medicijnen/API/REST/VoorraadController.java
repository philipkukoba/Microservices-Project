package be.ugent.systemdesign.group6.medicijnen.API.REST;

import be.ugent.systemdesign.group6.medicijnen.application.Antwoord;
import be.ugent.systemdesign.group6.medicijnen.application.AntwoordMetData;
import be.ugent.systemdesign.group6.medicijnen.application.Status;
import be.ugent.systemdesign.group6.medicijnen.application.query.ProductVoorraadQuery;
import be.ugent.systemdesign.group6.medicijnen.application.query.ProductVoorraadReadModel;
import be.ugent.systemdesign.group6.medicijnen.application.services.VoorraadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/api/voorraad")
@CrossOrigin(origins="*")
public class VoorraadController {

    @Autowired
    private VoorraadService voorraadService;

    @Autowired
    private ProductVoorraadQuery query;

    @GetMapping("/overzicht")
    public ResponseEntity geefOverzicht() {
        List<ProductVoorraadReadModel> completeVoorraad = query.geefCompleteVoorraad();
        // Dit lukt altijd
        AntwoordMetData antwoord = new AntwoordMetData(Status.GELUKT, "", completeVoorraad);
        return ResponseEntityBuilder.maakBoodschapMetData(antwoord);
    }

    @PostMapping("/lading")
    public ResponseEntity<String> verwerkLading(@RequestBody HashMap<String, HashMap<LocalDate, Integer>> lading) {
        Antwoord antwoord = voorraadService.verwerkLading(lading);
        return ResponseEntityBuilder.maakBoodschap(antwoord);
    }
}
