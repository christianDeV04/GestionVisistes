package com.formation.gestionvisites.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "medecin")
public class Medecin {

    @Id
    @Column(name = "codemed", length = 20, nullable = false)
    @NotBlank(message = "Le code médecin est obligatoire")
    @Size(min = 2, max = 20, message = "Le code médecin doit contenir entre 2 et 20 caractères")
    private String codemed;

    @Column(name = "nom", length = 50, nullable = false)
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50, message = "Le nom ne peut pas dépasser 50 caractères")
    private String nom;

    @Column(name = "prenom", length = 50, nullable = false)
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50, message = "Le prénom ne peut pas dépasser 50 caractères")
    private String prenom;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", length = 30, nullable = false)
    private Grade grade;

    public Medecin() {
    }

    public Medecin(String codemed, String nom, String prenom, Grade grade) {
        this.codemed = codemed;
        this.nom = nom;
        this.prenom = prenom;
        this.grade = grade;
    }

    public String getCodemed() {
        return codemed;
    }

    public void setCodemed(String codemed) {
        this.codemed = codemed;
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

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medecin medecin = (Medecin) o;
        return Objects.equals(codemed, medecin.codemed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codemed);
    }

    @Override
    public String toString() {
        return "Medecin{" +
                "codemed='" + codemed + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", grade=" + grade +
                '}';
    }
}
