package be.ugent.systemdesign.group6.medicijnen.infrastructure.repositories;

import be.ugent.systemdesign.group6.medicijnen.infrastructure.datamodel.MedicijnProductDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicijnProductDataModelRepository extends JpaRepository<MedicijnProductDataModel, Integer> {
    List<MedicijnProductDataModel> findAllByCatalogusItemId(int catalogusItemId);

    List<MedicijnProductDataModel> findAllByBestellingsId(String bestellingsId);

    //void deleteAllByCatalogusItemIdAndBestellingsIdNull(int catalogusItemId);

    void deleteAllByCatalogusItemIdAndBestellingsIdNull(int catalogusItemId);


    List<MedicijnProductDataModel> findAllByBestellingsIdNullAndVerkochtFalse();

    void deleteAllByIdIn(List<Integer> ids);
}
