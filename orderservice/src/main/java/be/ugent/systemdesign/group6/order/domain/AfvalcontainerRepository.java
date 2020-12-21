package be.ugent.systemdesign.group6.order.domain;


public interface AfvalcontainerRepository {
     /**
      * Voegt een nieuwe afvalcontainer toe aan de databank.
      * @param afvalContainer de nieuwe afvalcontainer
      */
     void voegAfvalContainerToe(Afvalcontainer afvalContainer);

     /**
      * Gooit één verpakking medicijnen weg. Deze methode zal de medicijnen in de volste afvalcontainer gooien.
      * @return de afvalcontainer waarin het afval gegooid moet worden.
      * @throws GeenAfvalcontainerBeschikbaar Wanneer er geen container is waarin er nog plaats is zal deze exceptie opgegooid worden.
      */
     Afvalcontainer gooiMedicijnWeg() throws GeenAfvalcontainerBeschikbaar;

     /**
      * Gooit een aantal verpakking medicijnen weg. Deze methode zal de medicijnen in de volste afvalcontainer gooien.
      * @return de afvalcontainer waarin het afval gegooid moet worden.
      * @param aantal Het aantal verpakkingen dat weggegooid moet worden.
      * @throws GeenAfvalcontainerBeschikbaar Wanneer er geen container is waarin er nog genoeg plaats is zal deze exceptie opgegooid worden.
      */
     Afvalcontainer gooiMedicijnenWeg(int aantal) throws GeenAfvalcontainerBeschikbaar;

     boolean isVol(Integer id);

     /**
      * Zorgt ervoor dat alle volle containers leeggemaakt worden (door de afval ophaaldienst)
      */
     void haalAfvalOp();
}
