package be.ugent.systemdesign.group6.verzendingsdienst.application;

import be.ugent.systemdesign.group6.verzendingsdienst.domain.Label;
import be.ugent.systemdesign.group6.verzendingsdienst.domain.Pakket;
import be.ugent.systemdesign.group6.verzendingsdienst.domain.Rolcontainer;
import be.ugent.systemdesign.group6.verzendingsdienst.domain.VerzendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RolcontainerOnderhoudImpl implements RolcontainerOnderhoud {


    @Autowired
    private VerzendingsAdministratie verzendingsAdministratie;

    @Autowired
    private VerzendingRepository verzendingRepository;

    @Override
    public void haalRolcontainerOp() {

        //testing
        /*
        Rolcontainer r = new Rolcontainer();
        Pakket p = new Pakket("a2");
        p.setId(0);
        p.zetLabel(new Label("a", LocalDate.now()));
        r.voegPakketToe(p); */

        Rolcontainer r = new Rolcontainer();
        for (Pakket p: verzendingRepository.geefAlleNietVerzondenPakketten()){
            if (!r.isVol()){
                r.voegPakketToe(p);
            }
        }

        verzendingsAdministratie.RolcontainerOpgehaald(r);
    }

}
