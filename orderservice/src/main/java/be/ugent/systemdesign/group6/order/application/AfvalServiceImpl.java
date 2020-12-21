package be.ugent.systemdesign.group6.order.application;

import be.ugent.systemdesign.group6.order.application.command.CommandDispatcher;
import be.ugent.systemdesign.group6.order.application.command.HaalAfvalOpCommand;
import be.ugent.systemdesign.group6.order.domain.Afvalcontainer;
import be.ugent.systemdesign.group6.order.domain.AfvalcontainerRepository;
import be.ugent.systemdesign.group6.order.domain.GeenAfvalcontainerBeschikbaar;
import be.ugent.systemdesign.group6.order.domain.MagazijnRepository;
import be.ugent.systemdesign.group6.order.infrastructure.MedicijnNietInMagazijn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class AfvalServiceImpl implements AfvalService {

    @Autowired
    AfvalcontainerRepository afvalrepo;

    @Autowired
    CommandDispatcher commandDispatcher;

    @Autowired
    MagazijnRepository magazijnrepo;

    @Override
    public Response afvalcontainersOpgehaald() {
        afvalrepo.haalAfvalOp();
        return new Response("Afval werd opgehaald", ResponseStatus.SUCCESS);
    }

    @Override
    public Response plaatsBijAfval(Integer catalogusId, int aantal) {/*
        try {
            Thread.sleep(magazijnrepo.geefRekMedicijn(catalogusId));
            Afvalcontainer a = afvalrepo.gooiMedicijnenWeg(aantal);
            if( a.isVol()){
                commandDispatcher.stuurHaalAfvalOpCommand(new HaalAfvalOpCommand(true));
            }
        } catch (GeenAfvalcontainerBeschikbaar geenAfvalcontainerBeschikbaar ) {
            return new Response(geenAfvalcontainerBeschikbaar.getMessage(), ResponseStatus.FAIL);
        } catch (MedicijnNietInMagazijn | InterruptedException e){
            return new Response("Het medicijn wordt niet weggegooid want het is niet aanwezig in het magazijn.", ResponseStatus.SUCCESS);
        }
        return new Response("Er werden " + aantal + " medicijnen met catalogusId " + catalogusId + " weggegooid.", ResponseStatus.SUCCESS);*/
        try {
            Thread.sleep(magazijnrepo.geefRekMedicijn(catalogusId));
        } catch (MedicijnNietInMagazijn | InterruptedException e) {
            return new Response("Het medicijn wordt niet weggegooid want het is niet aanwezig in het magazijn.", ResponseStatus.SUCCESS);
        } finally {
            try{
                Afvalcontainer a = afvalrepo.gooiMedicijnenWeg(aantal);

                if (a.isVol()) {
                    commandDispatcher.stuurHaalAfvalOpCommand(new HaalAfvalOpCommand(true));
                }
                return new Response("Er werden " + aantal + " medicijnen met catalogusId " + catalogusId + " weggegooid.", ResponseStatus.SUCCESS);
            } catch(GeenAfvalcontainerBeschikbaar geenAfvalcontainerBeschikbaar ){
                return new Response(geenAfvalcontainerBeschikbaar.getMessage(), ResponseStatus.FAIL);

            }
    }




    @Override
    public Response retourBijAfval(int aantal) {
        try {
            Afvalcontainer a = afvalrepo.gooiMedicijnenWeg(aantal);
            if( a.isVol()){
                commandDispatcher.stuurHaalAfvalOpCommand(new HaalAfvalOpCommand(true));
            }
        } catch (GeenAfvalcontainerBeschikbaar geenAfvalcontainerBeschikbaar) {
            return new Response(geenAfvalcontainerBeschikbaar.getMessage(), ResponseStatus.FAIL);
        }
        return new Response("Er werden " + aantal + " medicijnen weggegooid.", ResponseStatus.SUCCESS);
    }
}
