package be.ugent.systemdesign.group6.verzendingsdienst.domain;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class Rolcontainer {

    private final Logger logger = Logger.getLogger(Logger.class.getName());

    private UUID id;

    private ArrayList<Pakket> pakketten;

    private static final int MAX_CAPACITEIT = 20;

    public Rolcontainer(){
        this.id = UUID.randomUUID();
        this.pakketten = new ArrayList<Pakket>(MAX_CAPACITEIT);
    }

    public boolean isVol(){
        return pakketten.size() == MAX_CAPACITEIT;
    }

    public void voegPakketToe(Pakket pakket){
        if (pakketten.size() == MAX_CAPACITEIT){
            logger.info("Reached max capacity of rolcontainer, cannot add pakket to rolcontainer");
        }
        else {
         pakketten.add(pakket);
         logger.info("pakket succesvol toegevoegd aan rolcontainer");
        }
    }

    public void verwijderPakket(Pakket pakket){
        pakketten.remove(pakket);
    }

    public UUID getId() {
        return id;
    }

    public ArrayList<Pakket> getPakketten() {
        return pakketten;
    }
}
