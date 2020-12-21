package be.ugent.systemdesign.group6.order.application;

import be.ugent.systemdesign.group6.order.domain.GeenPlaatsInMagazijn;

public interface MagazijnService {
    Response geefRekVanMedicijn(Integer medicijnId) ;
    Response verwijderMedicijnUitMagazijn(Integer medicijnId);
    Response voegMedicijnToeAanMagazijn(Integer medicijnId) throws GeenPlaatsInMagazijn;
}
