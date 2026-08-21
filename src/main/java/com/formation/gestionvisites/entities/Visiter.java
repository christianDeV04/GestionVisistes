package com.formation.gestionvisites.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "visiter")
public class Visiter {

    @EmbeddedId
    private VisiterId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("codemed")
    @JoinColumn(name = "codemed", nullable = false)
    private Medecin medecin;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("codepat")
    @JoinColumn(name = "codepat", nullable = false)
    private Patient patient;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public Visiter() {
        this.id = new VisiterId();
    }

    public Visiter(Medecin medecin, Patient patient, LocalDate date) {
        this.medecin = medecin;
        this.patient = patient;
        this.id = new VisiterId(medecin.getCodemed(), patient.getCodepat());
        this.date = date;
    }

    public VisiterId getId() {
        return id;
    }

    public void setId(VisiterId id) {
        this.id = id;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
        if (medecin != null && patient != null) {
            this.id = new VisiterId(medecin.getCodemed(), patient.getCodepat());
        }
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        if (medecin != null && patient != null) {
            this.id = new VisiterId(medecin.getCodemed(), patient.getCodepat());
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visiter visiter = (Visiter) o;
        return Objects.equals(id, visiter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Visiter{" +
                "id=" + id +
                ", medecin=" + (medecin != null ? medecin.getNom() + " " + medecin.getPrenom() : "null") +
                ", patient=" + (patient != null ? patient.getNom() + " " + patient.getPrenom() : "null") +
                ", date=" + date +
                '}';
    }
}
