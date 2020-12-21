package be.ugent.systemdesign.group6.verzendingsdienst.application.command;

import be.ugent.systemdesign.group6.verzendingsdienst.application.AnnuleringsDienst;
import be.ugent.systemdesign.group6.verzendingsdienst.application.RolcontainerOnderhoud;
import be.ugent.systemdesign.group6.verzendingsdienst.application.VerzendingsAdministratie;
import be.ugent.systemdesign.group6.verzendingsdienst.application.VerzendingsAdministratieImpl;
import be.ugent.systemdesign.group6.verzendingsdienst.application.bpost.BPostKoerier;
import be.ugent.systemdesign.group6.verzendingsdienst.domain.Rolcontainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandHandler {

    @Autowired
    AnnuleringsDienst annuleringsDienst;

    @Autowired
    RolcontainerOnderhoud rolcontainerOnderhoud;

    public AnnuleerBestellingResponse handleAnnuleerBestelling(AnnuleerBestellingCommand command){
        return annuleringsDienst.annuleerBestelling(command.getId());
    }

    public void haalRolcontainerOpCommand(){
        
        rolcontainerOnderhoud.haalRolcontainerOp();
    }

}
