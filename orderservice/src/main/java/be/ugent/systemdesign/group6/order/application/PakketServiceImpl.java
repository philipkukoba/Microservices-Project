package be.ugent.systemdesign.group6.order.application;

import be.ugent.systemdesign.group6.order.application.command.AnnuleerBestellingResponse;
import be.ugent.systemdesign.group6.order.application.command.MaakOrderCommand;
import be.ugent.systemdesign.group6.order.domain.GeenPlaatsInMagazijn;
import be.ugent.systemdesign.group6.order.domain.Order;
import be.ugent.systemdesign.group6.order.domain.OrderRepository;
import be.ugent.systemdesign.group6.order.domain.Pakket;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@Transactional
public class PakketServiceImpl implements PakketService{

    private static final Logger log = LoggerFactory.getLogger(PakketServiceImpl.class);

    @Autowired
    MagazijnService magazijnService;

    @Autowired
    OrderRepository repo;

    private ArrayList<Orderpicker> orderpickers = new ArrayList<>(
            Arrays.asList(new Orderpicker(0),
                    new Orderpicker(1),
                    new Orderpicker(2),
                    new Orderpicker(3))
    );

    private Orderpicker wachtOpVrijeOrderpicker() {
        int i = 0;

        while (true){
            Orderpicker o = orderpickers.get(i%orderpickers.size());
            if(!o.isBezig()){
                return o;
            }
            i++;
        }
    }

    @Override
    public void plaatsGeannuleerdeBestellingTerug(String orderId) {
        Orderpicker vrijeOrderpicker = wachtOpVrijeOrderpicker();
        //vrijeOrderpicker.run(1, "Geannuleerde bestelling terug plaatsen");
        repo.annuleerOrder(orderId);
    }

    @Override
    public Response stelOrderOp(MaakOrderCommand maakOrderCommand) {
        ArrayList<Integer> medicijnenVoorsvchrift = new ArrayList<>();
        ArrayList<Integer> medicijnenZonderVoorsvchrift = new ArrayList<>();
        int werkTijd = 1;
        try{
            for (Medicijn m : maakOrderCommand.getMedicijnen()) {
                Response response = magazijnService.geefRekVanMedicijn(m.getId());
                if( response.status == ResponseStatus.FAIL){
                    magazijnService.voegMedicijnToeAanMagazijn(m.getId());

                } else {
                    MagazijnRekResponse rekResponse = (MagazijnRekResponse) response;
                    werkTijd += rekResponse.rek;
                }
                if (m.isVoorschrift()) { medicijnenVoorsvchrift.add(m.getId());
                } else {
                    medicijnenZonderVoorsvchrift.add(m.getId());
                }
            }

            Orderpicker vrijeOrderpicker = wachtOpVrijeOrderpicker();
            vrijeOrderpicker.bezig = true;

            Pakket pakket_voorschrift = new Pakket(maakOrderCommand.getApotheekAdres(), medicijnenVoorsvchrift);
            Pakket pakketten_z_voorschrift = new Pakket(maakOrderCommand.getThuisAdres(), medicijnenZonderVoorsvchrift);
            Order o = new Order(maakOrderCommand.getId(), new ArrayList<>(Arrays.asList(pakket_voorschrift, pakketten_z_voorschrift)));
            repo.voegOrderToe(o);

            Thread.sleep(werkTijd * 10);
            vrijeOrderpicker.bezig = false;

            o.orderCompleetEventAanmaken();

            repo.orderIsVerwerkt(o);

            return new Response("Pakket is samengesteld in een werktijd van " + werkTijd + " en is nu klaar voor verzending.", ResponseStatus.SUCCESS);
      }catch (GeenPlaatsInMagazijn | InterruptedException e ){
          return new Response("Magazijn is te klein.", ResponseStatus.FAIL);
      }
    }

    @Override
    public AnnuleerBestellingResponse probeerBestellingTeAnnuleren(String orderId) {
        if(repo.annuleerOrder(orderId)){
            return new AnnuleerBestellingResponse("Pakket was nog niet samengesteld kan nog succesvol geannuleerd worden.", ResponseStatus.SUCCESS, orderId);
        }
        return new AnnuleerBestellingResponse("Pakket is al bij de verzendingsdienst en kan niet meer geannuleerd worden.", ResponseStatus.FAIL, null);
    }


    @Getter
    @Setter
    private class Orderpicker {
        private boolean bezig;
        private long id;
        public Orderpicker(int id){
            bezig = false;
            this.id = id;
        }
    }
}
