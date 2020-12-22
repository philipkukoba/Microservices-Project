package be.ugent.systemdesign.group6.verzendingsdienst.API.rest;

import be.ugent.systemdesign.group6.verzendingsdienst.application.RolcontainerOnderhoud;
import be.ugent.systemdesign.group6.verzendingsdienst.application.bpost.BPostKoerier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/bpost")
@CrossOrigin(origins="*")
public class BpostController {

    @Autowired
    RolcontainerOnderhoud rolcontainerOnderhoud;

    @PostMapping
    public ResponseEntity<String> rolcontainersOphalen(){
        rolcontainerOnderhoud.haalRolcontainerOp();
        return new ResponseEntity<String>("De rolcontainers zijn opgehaald", HttpStatus.OK);
    }

}
