package com.formation.gestionvisites.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VisiterId implements Serializable {

    private String codemed;
    private String codepat;

    public VisiterId() {
    }

    public VisiterId(String codemed, String codepat) {
        this.codemed = codemed;
        this.codepat = codepat;
    }

    public String getCodemed() {
        return codemed;
    }

    public void setCodemed(String codemed) {
        this.codemed = codemed;
    }

    public String getCodepat() {
        return codepat;
    }

    public void setCodepat(String codepat) {
        this.codepat = codepat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisiterId visiterId = (VisiterId) o;
        return Objects.equals(codemed, visiterId.codemed) &&
                Objects.equals(codepat, visiterId.codepat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codemed, codepat);
    }
}
