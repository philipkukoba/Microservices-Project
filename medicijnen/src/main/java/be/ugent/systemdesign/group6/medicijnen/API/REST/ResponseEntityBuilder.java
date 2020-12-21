package be.ugent.systemdesign.group6.medicijnen.API.REST;

import be.ugent.systemdesign.group6.medicijnen.application.Antwoord;
import be.ugent.systemdesign.group6.medicijnen.application.AntwoordMetData;
import be.ugent.systemdesign.group6.medicijnen.application.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {
    public static ResponseEntity<String> maakBoodschap(Antwoord antwoord) {
        return new ResponseEntity<>(antwoord.getBoodschap(),
                antwoord.getStatus() == Status.GELUKT ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity maakBoodschapMetData(AntwoordMetData antwoord) {
        if (antwoord.getStatus() == Status.NIET_GELUKT)
            return maakBoodschap(antwoord);

        return ResponseEntity.ok(antwoord.getData());
    }
}
