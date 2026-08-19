package com.example.ComercialHuguito.Repository;

import com.example.ComercialHuguito.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser,Integer> {

    AppUser findByEmail(String email);
    List<AppUser> findByRole(String role);
    boolean existsByEmail(String email);

}
