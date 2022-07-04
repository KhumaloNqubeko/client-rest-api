package com.client.api.repository;

import com.client.api.entitiy.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByMobileNumber(String phoneNumber);
}
