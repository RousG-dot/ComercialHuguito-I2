package com.example.ComercialHuguito.Repository;

import com.example.ComercialHuguito.Models.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientesRepository extends JpaRepository<Clientes,Integer> {

    Clientes findByEmail(String email);
    List<Clientes> findByRole(String role);
    boolean existsByEmail(String email);
}
