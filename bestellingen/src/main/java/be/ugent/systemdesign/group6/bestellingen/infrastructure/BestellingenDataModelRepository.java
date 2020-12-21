package be.ugent.systemdesign.group6.bestellingen.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface BestellingenDataModelRepository extends MongoRepository<BestellingDataModel, String> {

    @Query(value="{'_id': ?0}", fields="{'status': 1}")
    Optional<StatusProjection> readStatusById(String id);
}
