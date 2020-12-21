package be.ugent.systemdesign.group6.verzendingsdienst.application;

import be.ugent.systemdesign.group6.verzendingsdienst.application.bpost.BPostKoerier;
import be.ugent.systemdesign.group6.verzendingsdienst.application.event.EventDispatcher;
import be.ugent.systemdesign.group6.verzendingsdienst.application.event.OrderVerzondenEvent;
import be.ugent.systemdesign.group6.verzendingsdienst.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Stack;
import java.util.logging.Logger;

@Transactional
@Service
public class VerzendingsAdministratieImpl implements VerzendingsAdministratie {

    private final Logger logger = Logger.getLogger(Logger.class.getName());

    //niet gebruikte labels in memory bijhouden
    private Stack<Label> labels = new Stack<Label>();

    @Autowired
    BPostKoerier bPostKoerier;

    @Autowired
    VerzendingRepository verzendingRepo;

    @Autowired
    EventDispatcher eventDispatcher; //negeer roder onderlijning

    //alle pakketten van de rolcontainer registreren als verzonden
    //event dispatchen orderVerzonden
    @Override
    public Response RolcontainerOpgehaald(Rolcontainer rolcontainer) {
        logger.info("rolcontainer size: " + rolcontainer.getPakketten().size());
        for (Pakket p: rolcontainer.getPakketten()){
            try{
                p.verstuur();
                eventDispatcher.publishOrderVerzondenEvent(new OrderVerzondenEvent(p.getOrderID()));
                logger.info("net voor updateVerstuurdPakket");
                verzendingRepo.updateVerstuurdPakket(p.getId(), p.getStatus().name(), p.getVerzendDatum());
            }
            catch (PakketNogNietGelabeld e1) {
                Response r = new Response("Pakket kon niet verzonden worden want het pakket heeft geen label", ResponseStatus.FAIL);
                logger.info(r.message);
                return r;
            }
            catch (RuntimeException e2) {
                Response r = new Response("Pakket kon niet opgeslagen worden als verzonden", ResponseStatus.FAIL);
                logger.info(r.message);
                return r;
            }
        }
        Response r = new Response("Rolcontainer werd succesvol opgehaald", ResponseStatus.SUCCESS);
        logger.info(r.message);
        return r;
    }

    //proberen labelen (indien niet genoeg labels, nieuwe labels opvragen)
    //en opslaan in db
    @Override
    public Response orderCompleet(String orderId) {
        try {
            Pakket p = new Pakket(orderId);

            if (labels.isEmpty()) { //nieuwe labels opvragen
                this.labels = bPostKoerier.vraagLabelsAan(10);
            }

            //labelen
            Label lbl = labels.pop();
            logger.info("pakket zal gelabeld worden met label: " + lbl.getId() + " geldigheidsdatum " + lbl.getGeldigheidsDatum());
            p.zetLabel(lbl);

            //opslaan
            verzendingRepo.slaOp(p);
        }
        catch (RuntimeException ex){
            logger.info(ex.getMessage());
            Response r = new Response("Nieuw pakket kon niet aangemaakt of opgeslagen worden", ResponseStatus.FAIL);;
            logger.info(r.message);
            return r;
        }
        Response r = new Response("Nieuwe order werd succesvol geregistreerd.", ResponseStatus.SUCCESS);;
        logger.info(r.message);
        return r;
    }
}
