package be.ugent.systemdesign.group6.order.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AfvalcontainerDataModelJPARepository extends JpaRepository<AfvalcontainerDataModel, Integer> {
    @Query(value = "select * from afvalcontainer_data_model where huidige_cap <= max_cap - ?1 ORDER BY huidige_cap DESC", nativeQuery = true)
    List<AfvalcontainerDataModel> getAfvalcontainerWithEnoughCap(int aantal);

    @Query(value = "select * from afvalcontainer_data_model where huidige_cap = max_cap", nativeQuery = true)
    List<AfvalcontainerDataModel> getVolleAfvalcontainers();

}
