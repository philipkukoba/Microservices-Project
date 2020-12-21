package be.ugent.systemdesign.group6.order.domain;

import be.ugent.systemdesign.group6.order.infrastructure.MedicijnNietInMagazijn;

public interface MagazijnRepository {
    public int geefRekMedicijn(Integer medicijnId) throws MedicijnNietInMagazijn;
    public int geefRekVrij(Integer medicijnId) throws MedicijnNietInMagazijn;
    public int slaNieuwMedicijnOp(Integer medicijnId) throws GeenPlaatsInMagazijn;
}
