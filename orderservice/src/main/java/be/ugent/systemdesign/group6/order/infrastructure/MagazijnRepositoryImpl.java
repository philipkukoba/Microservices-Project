package be.ugent.systemdesign.group6.order.infrastructure;

import be.ugent.systemdesign.group6.order.domain.GeenPlaatsInMagazijn;
import be.ugent.systemdesign.group6.order.domain.MagazijnRepository;
import be.ugent.systemdesign.group6.order.domain.Plaats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class MagazijnRepositoryImpl implements MagazijnRepository {

    @Autowired
    PlaatsDataModelJPARepository repo;

    @Override
    public int geefRekMedicijn(Integer medicijnId) throws MedicijnNietInMagazijn {
        PlaatsDataModel p_dm = repo.getOneByIdMedicijn(medicijnId).orElseThrow(MedicijnNietInMagazijn::new);
        return p_dm.getRek();
    }

    @Override
    public int geefRekVrij(Integer medicijnId) throws MedicijnNietInMagazijn {

        PlaatsDataModel p_dm = repo.getOneByIdMedicijn(medicijnId).orElseThrow(MedicijnNietInMagazijn::new);
        Plaats p = mapDataModelNaarPlaats(p_dm);
        p.verwijderMedicijn();
       // repo.save(mapPlaatsNaarDataModel(p));
        repo.maakRekLeeg(p.getRek());
        return p.getRek();
    }

    @Override
    public int slaNieuwMedicijnOp(Integer medicijnId) throws GeenPlaatsInMagazijn {
        ArrayList<PlaatsDataModel> legeRekken = (ArrayList<PlaatsDataModel>) repo.findAllByIdMedicijnIsNull();
        if(legeRekken.isEmpty()){
            throw new GeenPlaatsInMagazijn(medicijnId);
        }
        PlaatsDataModel rek = legeRekken.get(0);
        rek.setIdMedicijn(medicijnId);
        repo.save(rek);

        return rek.getRek();
    }

    private Plaats mapDataModelNaarPlaats(PlaatsDataModel p_dm){
        return Plaats.builder()
                .idMedicijn(p_dm.getIdMedicijn())
                .rek(p_dm.getRek())
                .build();
    }

    private PlaatsDataModel mapPlaatsNaarDataModel(Plaats p){
        return new PlaatsDataModel(p.getRek(), p.getIdMedicijn());
    }
}
