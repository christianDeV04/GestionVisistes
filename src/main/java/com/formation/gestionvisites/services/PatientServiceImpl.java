package com.formation.gestionvisites.services;

import com.formation.gestionvisites.entities.Patient;
import com.formation.gestionvisites.repositories.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient save(Patient patient) {
        if (patient.getCodepat() == null || patient.getCodepat().trim().isEmpty()) {
            patient.setCodepat(genererNouveauCode());
        }
        return patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> findById(String codepat) {
        return patientRepository.findById(codepat);
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public void deleteById(String codepat) {
        patientRepository.deleteById(codepat);
    }

    @Override
    public boolean existsById(String codepat) {
        return patientRepository.existsById(codepat);
    }

    @Override
    public List<Patient> findByCodepatOrNomContaining(String recherche) {
        return patientRepository.findByCodepatOrNomContaining(recherche);
    }

    @Override
    public String genererNouveauCode() {
        List<String> codes = patientRepository.findTopCodepatOrderByCodepatDesc();
        int maxNumero = 0;
        for (String code : codes) {
            if (code != null && code.startsWith("PAT")) {
                try {
                    int numero = Integer.parseInt(code.substring(3));
                    if (numero > maxNumero) maxNumero = numero;
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return String.format("PAT%03d", maxNumero + 1);
    }
}
