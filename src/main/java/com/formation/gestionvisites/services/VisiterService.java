package com.formation.gestionvisites.services;

import com.formation.gestionvisites.entities.Visiter;
import com.formation.gestionvisites.entities.VisiterId;

import java.util.List;
import java.util.Optional;

public interface VisiterService {

    Visiter save(Visiter visiter);

    Optional<Visiter> findById(VisiterId id);

    List<Visiter> findAll();

    List<Visiter> findAllWithDetails();

    void deleteById(VisiterId id);

    boolean existsById(VisiterId id);

    List<Visiter> findByMedecin(String codemed);

    List<Visiter> findByPatient(String codepat);
}
