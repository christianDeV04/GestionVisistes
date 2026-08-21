package com.formation.gestionvisites.repositories;

import com.formation.gestionvisites.entities.Visiter;
import com.formation.gestionvisites.entities.VisiterId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisiterRepository extends JpaRepository<Visiter, VisiterId> {

    @Query("SELECT v FROM Visiter v JOIN FETCH v.medecin JOIN FETCH v.patient ORDER BY v.date DESC")
    List<Visiter> findAllWithDetails();

    List<Visiter> findByMedecin_Codemed(String codemed);

    List<Visiter> findByPatient_Codepat(String codepat);
}
