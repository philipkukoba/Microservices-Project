package be.ugent.systemdesign.group6.medicijnen.infrastructure;

import be.ugent.systemdesign.group6.medicijnen.application.query.ProductVoorraadReadModel;
import be.ugent.systemdesign.group6.medicijnen.domain.CatalogusItem;
import be.ugent.systemdesign.group6.medicijnen.domain.MedicijnProduct;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.datamodel.CatalogusItemDataModel;
import be.ugent.systemdesign.group6.medicijnen.infrastructure.datamodel.MedicijnProductDataModel;

public class Mapper {

    public static CatalogusItem mapToDomain(CatalogusItemDataModel model) {
        return CatalogusItem.builder().beschrijving(model.getBeschrijving()).
                gewensteTemperatuur(model.getGewensteTemperatuur()).id(model.getId()).
                kritischeWaarde(model.getKritischeWaarde()).naam(model.getNaam()).prijs(model.getPrijs()).
                voorschriftNoodzakelijk(model.isVoorschriftNoodzakelijk()).build();
    }

    public static MedicijnProduct mapToDomain(MedicijnProductDataModel model) {
        return MedicijnProduct.builder().bestellingsId(model.getBestellingsId()).
                catalogusItemId(model.getCatalogusItemId()).id(model.getId()).koelCelId(model.getKoelCelId()).
                verkocht(model.isVerkocht()).vervalDatum(model.getVervalDatum()).build();
    }

    public static MedicijnProductDataModel mapToDataModel(MedicijnProduct domain) {
        return MedicijnProductDataModel.builder().bestellingsId(domain.getBestellingsId()).
                catalogusItemId(domain.getCatalogusItemId()).id(domain.getId()).koelCelId(domain.getKoelCelId()).
                verkocht(domain.isVerkocht()).vervalDatum(domain.getVervalDatum()).build();
    }

    public static CatalogusItemDataModel mapToDataModel(CatalogusItem domain) {
        return CatalogusItemDataModel.builder().beschrijving(domain.getBeschrijving()).
                gewensteTemperatuur(domain.getGewensteTemperatuur()).id(domain.getId()).
                kritischeWaarde(domain.getKritischeWaarde()).naam(domain.getNaam()).prijs(domain.getPrijs()).
                voorschriftNoodzakelijk(domain.isVoorschriftNoodzakelijk()).build();
    }

    public static ProductVoorraadReadModel mapToReadModel(int catalogusMedicijnId, int aantal) {
        return ProductVoorraadReadModel.builder().medicijnId(catalogusMedicijnId).aantal(aantal).build();
    }
}
