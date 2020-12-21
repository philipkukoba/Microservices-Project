package be.ugent.systemdesign.group6.order.application;

import be.ugent.systemdesign.group6.order.domain.GeenPlaatsInMagazijn;
import be.ugent.systemdesign.group6.order.domain.MagazijnRepository;
import be.ugent.systemdesign.group6.order.infrastructure.MedicijnNietInMagazijn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MagazijnServiceImpl implements MagazijnService {

    @Autowired
    MagazijnRepository magazijnRepo;

    @Override
    public Response geefRekVanMedicijn(Integer medicijnId) {
        try {
            int rek = magazijnRepo.geefRekMedicijn(medicijnId);
            return new MagazijnRekResponse("medicijn gevonden in magazijn", ResponseStatus.GELUKT, rek);
        } catch (MedicijnNietInMagazijn medicijnNietInMagazijn) {
            return new Response("medicijn niet in magazijn", ResponseStatus.NIET_GELUKT);
        }
    }

    @Override
    public Response verwijderMedicijnUitMagazijn(Integer medicijnId) {
        try{
            int rek = magazijnRepo.geefRekVrij(medicijnId);
            return new Response("Rek " + rek + " is vrijgemaakt.", ResponseStatus.GELUKT);
        } catch (MedicijnNietInMagazijn medicijnNietInMagazijn) {
            return new Response("Het medicijn met id " + medicijnId + " lag niet meer in het magazijn.", ResponseStatus.NIET_GELUKT);
        }
    }

    @Override
    public Response voegMedicijnToeAanMagazijn(Integer medicijnId)  {
        try{
            int rek = magazijnRepo.slaNieuwMedicijnOp(medicijnId);
            return new Response("Nieuw medicijn zal gestockeerd worden in rek "+ rek, ResponseStatus.GELUKT);
        } catch (GeenPlaatsInMagazijn geenPlaatsInMagazijn) {
            return new Response(geenPlaatsInMagazijn.getMessage(), ResponseStatus.NIET_GELUKT);
        }
    }
}
