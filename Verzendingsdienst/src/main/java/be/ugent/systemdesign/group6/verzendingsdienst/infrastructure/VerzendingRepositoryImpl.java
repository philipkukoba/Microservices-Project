package be.ugent.systemdesign.group6.verzendingsdienst.infrastructure;

import be.ugent.systemdesign.group6.verzendingsdienst.domain.Label;
import be.ugent.systemdesign.group6.verzendingsdienst.domain.PakketStatus;
import be.ugent.systemdesign.group6.verzendingsdienst.domain.VerzendingRepository;
import be.ugent.systemdesign.group6.verzendingsdienst.domain.Pakket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Repository
public class VerzendingRepositoryImpl implements VerzendingRepository {

    private final Logger logger = Logger.getLogger(Logger.class.getName());

    @Autowired
    PakketDataModelJpaRepository pakketDMRepo;

    @Override
    public Pakket geefPakket(int verzendingID) {
        PakketDataModel pakketDataModel = pakketDMRepo.findById(verzendingID).orElseThrow(PakketNotFoundException::new);
        return mapNaarPakket(pakketDataModel);
    }


    @Override
    public List<Pakket> geefAlleNietVerzondenPakketten() {
        return pakketDMRepo.findAllByStatus(PakketStatus.KLAAR_VOOR_VERZENDING.name())
                .stream()
                .map(elt -> mapNaarPakket(elt))
                .collect(Collectors.toList());
    }


    @Override
    public List<Pakket> geefAllePakkettenVaneenOrder(String orderId) {
        logger.info("orderid binnengekregen: " + orderId);
        logger.info("will return all orders, size " + pakketDMRepo.findAllByOrderID(orderId).size());
        return pakketDMRepo.findAllByOrderID(orderId)
                .stream()
                .map(elt -> mapNaarPakket(elt))
                .collect(Collectors.toList());
    }


    @Override
    public void slaOp(Pakket p) {
        logger.info("proberen mappen met label " + p.getLabel().getId() + " " + p.getLabel().getGeldigheidsDatum());
        PakketDataModel pakketDataModel = mapNaarPakketDataModel(p);
        logger.info("succesvol gemapt");
        logger.info("labelid" + pakketDataModel.getLabelID());
        logger.info("labelgeldigheidsdatum" + pakketDataModel.getLabelGeldigheidsDatum());
        pakketDMRepo.save(pakketDataModel);
    }

    @Override
    public void updatePakket(int id, String status) {
        pakketDMRepo.updatePakket(id, status);
    }

    @Override
    public void updateVerstuurdPakket(int id, String status, LocalDate verzenddatum) {
        logger.info("in updateverstuurdpakket");
        pakketDMRepo.updateVerstuurdPakket(id, status, verzenddatum);
    }


    private Pakket mapNaarPakket(PakketDataModel pakketDM) {
        Label lbl = new Label(pakketDM.getLabelID(), pakketDM.getLabelGeldigheidsDatum());
        return Pakket.builder()
                    .id(pakketDM.getId())
                    .verzendDatum(pakketDM.getVerzendDatum())
                    .label(lbl)
                    .status(PakketStatus.valueOf(pakketDM.getStatus()))
                    .orderID(pakketDM.getOrderID())
                    .build();
    }

    private PakketDataModel mapNaarPakketDataModel(Pakket p){
        return new PakketDataModel(
                p.getId(),
                p.getVerzendDatum(),
                p.getLabel().getId(),
                p.getLabel().getGeldigheidsDatum(),
                p.getOrderID(),
                p.getStatus());
    }
}
