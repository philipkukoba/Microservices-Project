package be.ugent.systemdesign.group6.order.infrastructure;

import be.ugent.systemdesign.group6.order.domain.Order;
import be.ugent.systemdesign.group6.order.domain.OrderRepository;
import be.ugent.systemdesign.group6.order.domain.Pakket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private static final Logger log = LoggerFactory.getLogger(OrderRepositoryImpl.class);


    @Autowired
    OrderMongoRepository repo;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Override
    public void voegOrderToe(Order o) {
        repo.save(mapOrderNaarDataModel(o));
    }

    @Override
    public List<Order> haalOnverwerkteOrdersOp() {
        ArrayList<OrderDataModel> onverwerktePakketten_dm = (ArrayList<OrderDataModel>) repo.getAllByKlaargemaaktIsFalse();
        ArrayList<Order> onverwerktePakketten = new ArrayList<>();
        onverwerktePakketten_dm.forEach(e -> onverwerktePakketten.add(mapdataModelNaarOrder(e)));
        return onverwerktePakketten;
    }

    @Override
    public void orderIsVerwerkt(Order o) {
        if( repo.existsById(o.getId())){
            OrderDataModel o_dm = mapOrderNaarDataModel(o);
            o_dm.setKlaargemaakt(true);

            repo.save(o_dm);

            o.getDomainEvents().forEach(domainEvent -> {log.info(domainEvent.toString()); eventPublisher.publishEvent(domainEvent); });
            o.clearDomainEvents();
        }
    }

    @Override
    public boolean annuleerOrder(String orderId) {
        Optional<OrderDataModel> o_dm = repo.getById(orderId);
        if ( o_dm.isPresent() && !repo.getById(orderId).get().isKlaargemaakt() ){
            repo.delete(o_dm.get());
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> getMedicijnenVanOrder(String orderId) {
        OrderDataModel o_dm = repo.getById(orderId).orElseThrow();
        ArrayList<Pakket> pakketten = (ArrayList<Pakket>) o_dm.getPakketten();
        ArrayList<Integer> meds = new ArrayList<>();
        for(Pakket p : pakketten){
            meds.addAll(meds.size(), p.getMedicijnen());
        }
        return meds;
    }

    private OrderDataModel mapOrderNaarDataModel(Order o){
        return new OrderDataModel(o.getId(),o.getPakketten(), o.isKlaargemaakt());
    }

    private Order mapdataModelNaarOrder(OrderDataModel o_dm){
        return Order.builder()
                .id(o_dm.getId())
                .klaargemaakt(o_dm.isKlaargemaakt())
                .pakketten(o_dm.getPakketten())
                .build();
    }
}
