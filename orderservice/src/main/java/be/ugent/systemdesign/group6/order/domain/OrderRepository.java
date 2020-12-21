package be.ugent.systemdesign.group6.order.domain;

import java.util.List;

public interface OrderRepository {
    public void voegOrderToe(Order o);
    public List<Order> haalOnverwerkteOrdersOp();
    public void orderIsVerwerkt(Order o);
    public boolean annuleerOrder(String orderId);
    public List<Integer> getMedicijnenVanOrder(String orderId);
}
