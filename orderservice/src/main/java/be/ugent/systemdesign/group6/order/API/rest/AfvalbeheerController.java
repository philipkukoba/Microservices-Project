package be.ugent.systemdesign.group6.order.API.rest;

import be.ugent.systemdesign.group6.order.application.AfvalService;
import be.ugent.systemdesign.group6.order.application.Response;
import be.ugent.systemdesign.group6.order.application.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(path="api/order/afval")
public class AfvalbeheerController {
    @Autowired
    AfvalService afvalService;

    @PutMapping("/haalAfvalOp")
    public ResponseEntity<String> afvalOphalen() {
        Response response = afvalService.afvalcontainersOpgehaald();
        return createResponseEntity(response.status, "Afval opgehaald", HttpStatus.OK, response.message,HttpStatus.CONFLICT);
    }

    private ResponseEntity<String> createResponseEntity(ResponseStatus status, String happyMessage, HttpStatus happyStatus, String sadMessage, HttpStatus sadStatus){
        if(status == ResponseStatus.FAIL)
            return new ResponseEntity<>(sadMessage, sadStatus);
        return new ResponseEntity<>(happyMessage,happyStatus);
    }
}
