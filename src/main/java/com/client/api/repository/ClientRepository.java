package com.client.api.repository;

import com.client.api.entitiy.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByMobileNumber(String mobileNumber);
    Optional<Client> findByIdNumber(String idNumber);

    @Query("SELECT c FROM Client c " +
            "WHERE LOWER(c.firstName) like LOWER(concat('%', :searchKeyword, '%')) " +
            "OR LOWER(c.idNumber) like LOWER(concat('%', :searchKeyword, '%')) " +
            "OR LOWER(c.mobileNumber) like LOWER(concat('%', :searchKeyword, '%')) "
    )
    List<Client> searchClient(@Param("searchKeyword") String searchKeyword);
}
