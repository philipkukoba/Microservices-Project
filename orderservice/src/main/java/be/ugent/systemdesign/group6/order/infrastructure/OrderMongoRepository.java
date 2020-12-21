package be.ugent.systemdesign.group6.order.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderMongoRepository extends MongoRepository<OrderDataModel, String> {
    List<OrderDataModel> getAllByKlaargemaaktIsFalse();
    Optional<OrderDataModel> getById(String id);
}
