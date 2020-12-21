package be.ugent.systemdesign.group6.bestellingen.infrastructure;

import be.ugent.systemdesign.group6.bestellingen.application.CQRS.MedicijnReadModel;
import be.ugent.systemdesign.group6.bestellingen.application.CQRS.StatistiekenQuery;
import be.ugent.systemdesign.group6.bestellingen.domain.Medicijn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class StatistiekenQueryImpl implements StatistiekenQuery {

    @Autowired
    BestellingenDataModelRepository repo;

    @Override
    public HashMap<Integer, MedicijnReadModel> genereerStatistieken() {
        HashMap<Integer, MedicijnReadModel> meds = new HashMap<>();
        List<BestellingDataModel> bestellingen = repo.findAll();
        for(BestellingDataModel b : bestellingen){
            for(Medicijn m : b.getMedicijnen()){
                if(meds.containsKey(m.getId())){
                    meds.get(m.getId()).telBijAantal(m.getAantal());
                }
                else{
                    meds.put(m.getId(), new MedicijnReadModel(m.getId(), m.getNaam(), m.getAantal()));
                }
            }
        }
        return meds;
    }
}
