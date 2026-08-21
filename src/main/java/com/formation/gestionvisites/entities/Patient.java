package com.formation.gestionvisites.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @Column(name = "codepat", length = 20, nullable = false)
    @NotBlank(message = "Le code patient est obligatoire")
    @Size(min = 2, max = 20, message = "Le code patient doit contenir entre 2 et 20 caractères")
    private String codepat;

    @Column(name = "nom", length = 50, nullable = false)
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50, message = "Le nom ne peut pas dépasser 50 caractères")
    private String nom;

    @Column(name = "prenom", length = 50, nullable = false)
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50, message = "Le prénom ne peut pas dépasser 50 caractères")
    private String prenom;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe", length = 1, nullable = false)
    private Sexe sexe;

    @Column(name = "adresse", length = 150)
    @Size(max = 150, message = "L'adresse ne peut pas dépasser 150 caractères")
    private String adresse;

    public Patient() {
    }

    public Patient(String codepat, String nom, String prenom, Sexe sexe, String adresse) {
        this.codepat = codepat;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.adresse = adresse;
    }

    public String getCodepat() {
        return codepat;
    }

    public void setCodepat(String codepat) {
        this.codepat = codepat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(codepat, patient.codepat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codepat);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "codepat='" + codepat + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", sexe=" + sexe +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
