package be.ugent.systemdesign.group6.verzendingsdienst.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface VerzendingRepository {

    Pakket geefPakket(int verzendingID);
    List<Pakket> geefAlleNietVerzondenPakketten();
    List<Pakket> geefAllePakkettenVaneenOrder(String orderId);
    void slaOp(Pakket p);

    void updatePakket(int id, String status);
    void updateVerstuurdPakket(int id, String status, LocalDate verzenddatum);
}
