package be.ugent.systemdesign.group6.bestellingen.infrastructure;

import be.ugent.systemdesign.group6.bestellingen.domain.Bestelling;
import be.ugent.systemdesign.group6.bestellingen.domain.BestellingenRepository;
import be.ugent.systemdesign.group6.bestellingen.domain.Status;
import be.ugent.systemdesign.group6.bestellingen.domain.seedwork.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

@Repository
public class BestellingenRepositoryImpl implements BestellingenRepository {

    @Autowired
    BestellingenDataModelRepository repo;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Override
    public Bestelling getBestelling(String id) throws BestellingNotFoundException{
        BestellingDataModel b = repo.findById(id).orElseThrow(BestellingNotFoundException::new);
        return mapToBestelling(b);
    }

    @Override
    public void saveBestelling(Bestelling b) {
        BestellingDataModel bestelling = mapToBestellingDataModel(b);
        repo.save(bestelling);
        b.getDomainEvents().forEach(e -> eventPublisher.publishEvent(e));
        b.clearDomainEvents();
    }

    @Override
    public Status getStatus(String id) throws BestellingNotFoundException {
        StatusProjection st = repo.readStatusById(id).orElseThrow(BestellingNotFoundException::new);
        return Status.valueOf(st.getStatus());
    }

    @Override
    public void removeBestelling(String id){
        repo.deleteById(id);
    }

    private BestellingDataModel mapToBestellingDataModel(Bestelling b){
        return new BestellingDataModel(b.getId(), b.getKlantenId(), b.getStatus(), b.getThuisAdres(), b.getApotheekAdres(), b.getMedicijnen());
    }

    private Bestelling mapToBestelling(BestellingDataModel b){
        return Bestelling.builder()
                .id(b.getId())
                .klantenId(b.getKlantenId())
                .thuisAdres(b.getThuisAdres())
                .apotheekAdres(b.getApotheekAdres())
                .status(Status.valueOf(b.getStatus()))
                .medicijnen(b.getMedicijnen())
                .build();
    }
}
