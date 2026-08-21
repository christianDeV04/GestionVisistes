package com.formation.gestionvisites.services;

import com.formation.gestionvisites.entities.Medecin;

import java.util.List;
import java.util.Optional;

public interface MedecinService {

    Medecin save(Medecin medecin);

    Optional<Medecin> findById(String codemed);

    List<Medecin> findAll();

    void deleteById(String codemed);

    boolean existsById(String codemed);

    List<Medecin> findByNomContaining(String nom);

    String genererNouveauCode();
}
