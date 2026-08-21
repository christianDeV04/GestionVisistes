package com.formation.gestionvisites.repositories;

import com.formation.gestionvisites.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, String> {

    List<Medecin> findByNomContainingIgnoreCase(String nom);

    @Query("SELECT m.codemed FROM Medecin m ORDER BY m.codemed DESC")
    List<String> findTopCodemedOrderByCodemedDesc();
}
