package com.formation.gestionvisites.services;

import com.formation.gestionvisites.entities.Medecin;
import com.formation.gestionvisites.repositories.MedecinRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedecinServiceImpl implements MedecinService {

    private final MedecinRepository medecinRepository;

    public MedecinServiceImpl(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    @Override
    public Medecin save(Medecin medecin) {
        if (medecin.getCodemed() == null || medecin.getCodemed().trim().isEmpty()) {
            medecin.setCodemed(genererNouveauCode());
        }
        return medecinRepository.save(medecin);
    }

    @Override
    public Optional<Medecin> findById(String codemed) {
        return medecinRepository.findById(codemed);
    }

    @Override
    public List<Medecin> findAll() {
        return medecinRepository.findAll();
    }

    @Override
    public void deleteById(String codemed) {
        medecinRepository.deleteById(codemed);
    }

    @Override
    public boolean existsById(String codemed) {
        return medecinRepository.existsById(codemed);
    }

    @Override
    public List<Medecin> findByNomContaining(String nom) {
        return medecinRepository.findByNomContainingIgnoreCase(nom);
    }

    @Override
    public String genererNouveauCode() {
        List<String> codes = medecinRepository.findTopCodemedOrderByCodemedDesc();
        int maxNumero = 0;
        for (String code : codes) {
            if (code != null && code.startsWith("MED")) {
                try {
                    int numero = Integer.parseInt(code.substring(3));
                    if (numero > maxNumero) maxNumero = numero;
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return String.format("MED%03d", maxNumero + 1);
    }
}
