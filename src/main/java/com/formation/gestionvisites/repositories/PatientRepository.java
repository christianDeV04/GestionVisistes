package com.formation.gestionvisites.repositories;

import com.formation.gestionvisites.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {

    @Query("SELECT p FROM Patient p WHERE LOWER(p.codepat) LIKE LOWER(CONCAT('%', :recherche, '%')) " +
           "OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :recherche, '%'))")
    List<Patient> findByCodepatOrNomContaining(@Param("recherche") String recherche);

    List<Patient> findByNomContainingIgnoreCase(String nom);

    Patient findByCodepat(String codepat);

    @Query("SELECT p.codepat FROM Patient p ORDER BY p.codepat DESC")
    List<String> findTopCodepatOrderByCodepatDesc();
}
