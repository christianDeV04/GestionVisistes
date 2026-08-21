package com.formation.gestionvisites.services;

import com.formation.gestionvisites.entities.Visiter;
import com.formation.gestionvisites.entities.VisiterId;
import com.formation.gestionvisites.repositories.VisiterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VisiterServiceImpl implements VisiterService {

    private final VisiterRepository visiterRepository;

    public VisiterServiceImpl(VisiterRepository visiterRepository) {
        this.visiterRepository = visiterRepository;
    }

    @Override
    public Visiter save(Visiter visiter) {
        return visiterRepository.save(visiter);
    }

    @Override
    public Optional<Visiter> findById(VisiterId id) {
        return visiterRepository.findById(id);
    }

    @Override
    public List<Visiter> findAll() {
        return visiterRepository.findAll();
    }

    @Override
    public List<Visiter> findAllWithDetails() {
        return visiterRepository.findAllWithDetails();
    }

    @Override
    public void deleteById(VisiterId id) {
        visiterRepository.deleteById(id);
    }

    @Override
    public boolean existsById(VisiterId id) {
        return visiterRepository.existsById(id);
    }

    @Override
    public List<Visiter> findByMedecin(String codemed) {
        return visiterRepository.findByMedecin_Codemed(codemed);
    }

    @Override
    public List<Visiter> findByPatient(String codepat) {
        return visiterRepository.findByPatient_Codepat(codepat);
    }
}
