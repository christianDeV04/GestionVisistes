package com.formation.gestionvisites.services;

import com.formation.gestionvisites.entities.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    Patient save(Patient patient);

    Optional<Patient> findById(String codepat);

    List<Patient> findAll();

    void deleteById(String codepat);

    boolean existsById(String codepat);

    List<Patient> findByCodepatOrNomContaining(String recherche);

    String genererNouveauCode();
}
