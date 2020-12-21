package be.ugent.systemdesign.group6.order.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaatsDataModelJPARepository extends JpaRepository<PlaatsDataModel, Integer> {

    public Optional<PlaatsDataModel> getOneByIdMedicijn(Integer idMedicijn);
    public List<PlaatsDataModel> findAllByIdMedicijnIsNull();

    @Modifying
    @Query( value = "update plaats_data_model set id_medicijn=null where rek=?1", nativeQuery = true)
    public void maakRekLeeg(Integer rek);

}
