package be.ugent.systemdesign.group6.order.infrastructure;

import be.ugent.systemdesign.group6.order.domain.Afvalcontainer;
import be.ugent.systemdesign.group6.order.domain.AfvalcontainerRepository;
import be.ugent.systemdesign.group6.order.domain.GeenAfvalcontainerBeschikbaar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class AfvalcontainerRepositoryImpl implements AfvalcontainerRepository {

    @Autowired
    AfvalcontainerDataModelJPARepository repo;

    @Override
    public void voegAfvalContainerToe(Afvalcontainer afvalContainer) {
        repo.save(mapToAfvalcontainerDataModel(afvalContainer));
    }

    @Override
    public Afvalcontainer gooiMedicijnWeg() throws GeenAfvalcontainerBeschikbaar{
        return gooiMedicijnenWeg(1);
    }

    @Override
    public Afvalcontainer gooiMedicijnenWeg(int aantal) throws GeenAfvalcontainerBeschikbaar{
        ArrayList<AfvalcontainerDataModel> beschikbareContainers = (ArrayList<AfvalcontainerDataModel>) repo.getAfvalcontainerWithEnoughCap(aantal);
        if( beschikbareContainers.isEmpty()){
            throw new GeenAfvalcontainerBeschikbaar(aantal);
        }
        Afvalcontainer a = mapToAfvalcontainer(beschikbareContainers.get(0));
        a.gooiWeg(aantal);
        repo.save(mapToAfvalcontainerDataModel(a));
        return a;
    }

    @Override
    public boolean isVol(Integer id) {
        Optional<AfvalcontainerDataModel> a_dm = repo.findById(id);
        if( a_dm.isEmpty()){
            throw new NoSuchElementException();
        }
        return mapToAfvalcontainer(a_dm.get()).isVol();
    }

    @Override
    public void haalAfvalOp() {
        ArrayList<AfvalcontainerDataModel> volle_a_dm = (ArrayList<AfvalcontainerDataModel>) repo.getVolleAfvalcontainers();
        for (AfvalcontainerDataModel a_dm: volle_a_dm) {
            Afvalcontainer a = mapToAfvalcontainer(a_dm);
            a.maakLeeg();
            repo.save(mapToAfvalcontainerDataModel(a));
        }
    }

    private AfvalcontainerDataModel mapToAfvalcontainerDataModel(Afvalcontainer a){
        return new AfvalcontainerDataModel(a.getContainerId(), a.getMaxCap(), a.getHuidigeCap());
    }

    private Afvalcontainer mapToAfvalcontainer(AfvalcontainerDataModel a){
        return Afvalcontainer.builder()
                .containerId(a.getContainerId())
                .huidigeCap(a.getHuidigeCap())
                .maxCap(a.getMaxCap())
                .build();
    }
}
