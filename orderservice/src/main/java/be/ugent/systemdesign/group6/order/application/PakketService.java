package be.ugent.systemdesign.group6.order.application;

import be.ugent.systemdesign.group6.order.application.command.AnnuleerBestellingResponse;
import be.ugent.systemdesign.group6.order.application.command.MaakOrderCommand;

public interface PakketService {
    public void plaatsGeannuleerdeBestellingTerug(String orderId);
    public Response stelOrderOp(MaakOrderCommand maakOrderCommand);
    public AnnuleerBestellingResponse probeerBestellingTeAnnuleren(String orderId);
}
