package com.example.MontanaShop.Repository;

import com.example.MontanaShop.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    public Client findByUsername(String nazwa_klienta);
    boolean existsByUsername(String nazwa_klienta);
    boolean existsByEmail(String email);
    List<Client> findAllByIdNot(long id);

}
