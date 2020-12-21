package be.ugent.systemdesign.group6.verzendingsdienst.infrastructure;

import be.ugent.systemdesign.group6.verzendingsdienst.domain.PakketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PakketDataModelJpaRepository extends JpaRepository<PakketDataModel, Integer> {

    Optional<PakketDataModel> findById(int id);
    List<PakketDataModel> findAllByOrderID(String orderID);

    @Modifying
    @Query(value = "update pakket_data_model set status=?2 where id=?1", nativeQuery = true)
    void updatePakket(int id, String status);

    @Modifying
    @Query(value = "update pakket_data_model set status=?2, verzend_datum=?3 where id=?1", nativeQuery = true)
    void updateVerstuurdPakket(int id, String status, LocalDate verzend_datum);
}
