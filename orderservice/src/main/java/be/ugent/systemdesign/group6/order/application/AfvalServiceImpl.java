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
        return new Response("Afval werd opgehaald", ResponseStatus.GELUKT);
    }

    @Override
    public Response plaatsBijAfval(Integer catalogusId, int aantal) {

        try {
            Thread.sleep(magazijnrepo.geefRekMedicijn(catalogusId));
        } catch (MedicijnNietInMagazijn | InterruptedException e) {

        } finally {
            try {
                Afvalcontainer a = afvalrepo.gooiMedicijnenWeg(aantal);

                if (a.isVol()) {
                    commandDispatcher.stuurHaalAfvalOpCommand(new HaalAfvalOpCommand(true));
                }
                return new Response("Er werden " + aantal + " medicijnen met catalogusId " + catalogusId + " weggegooid.", ResponseStatus.GELUKT);
            } catch (GeenAfvalcontainerBeschikbaar geenAfvalcontainerBeschikbaar) {
                return new Response(geenAfvalcontainerBeschikbaar.getMessage(), ResponseStatus.NIET_GELUKT);

            }
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
            return new Response(geenAfvalcontainerBeschikbaar.getMessage(), ResponseStatus.NIET_GELUKT);
        }
        return new Response("Er werden " + aantal + " medicijnen weggegooid.", ResponseStatus.GELUKT);
    }
}
