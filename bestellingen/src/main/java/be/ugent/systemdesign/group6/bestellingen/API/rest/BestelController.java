package be.ugent.systemdesign.group6.bestellingen.API.rest;

import be.ugent.systemdesign.group6.bestellingen.application.BestelService;
import be.ugent.systemdesign.group6.bestellingen.application.Response;
import be.ugent.systemdesign.group6.bestellingen.application.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="api/bestellingen")
@CrossOrigin(origins="*")
public class BestelController {

    @Autowired
    private BestelService bestelService;

    @PutMapping("/annuleer/{id}")
    public ResponseEntity<String> annuleerBestelling(@PathVariable("id") String id){
        Response response = bestelService.annuleerBestelling(id);
        return createResponseEntity(response, HttpStatus.OK, HttpStatus.CONFLICT);
    }

    @PostMapping
    public ResponseEntity<String> plaatsBestelling(@RequestBody BestellingModel b){
        Response response = bestelService.plaatsBestelling(b.getKlantenId(), b.getThuisAdres(), b.getApotheekAdres(), b.getMedicijnen());
        return createResponseEntity(response, HttpStatus.OK, HttpStatus.CONFLICT);
    }

    @PostMapping("/ticket")
    public ResponseEntity<String> plaatsBestellingTicket(@RequestBody BestellingModel b){
        Response response = bestelService.plaatsBestellingTicket(b.getKlantenId(), b.getThuisAdres(), b.getApotheekAdres(), b.getMedicijnen());
        return createResponseEntity(response, HttpStatus.OK, HttpStatus.CONFLICT);
    }

    private ResponseEntity<String> createResponseEntity(Response response, HttpStatus successStatus, HttpStatus failStatus){
        if(response.getStatus() == ResponseStatus.NIET_GELUKT)
            return new ResponseEntity<>(response.getMessage(), failStatus);
        return new ResponseEntity<>(response.getMessage(), successStatus);
    }

}
