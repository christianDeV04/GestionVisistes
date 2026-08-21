package com.formation.gestionvisites.ui;

import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;
import org.kordamp.ikonli.materialdesign2.MaterialDesignE;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;
import org.kordamp.ikonli.materialdesign2.MaterialDesignH;
import org.kordamp.ikonli.materialdesign2.MaterialDesignI;
import org.kordamp.ikonli.materialdesign2.MaterialDesignM;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignR;
import org.kordamp.ikonli.materialdesign2.MaterialDesignS;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

import static com.formation.gestionvisites.ui.UIPalette.*;

public class IconManager {

    public static final int TAILLE_PETITE = 16;
    public static final int TAILLE_MOYENNE = 20;
    public static final int TAILLE_GRANDE = 24;
    public static final int TAILLE_TITRE = 32;

    // ========================================================================
    // Icônes du thème vert (utilisées sur fond clair)
    // ========================================================================

    public static Icon medecin() {
        return FontIcon.of(MaterialDesignS.STETHOSCOPE, TAILLE_GRANDE, VERT_PRINCIPAL);
    }

    public static Icon patient() {
        return FontIcon.of(MaterialDesignA.ACCOUNT, TAILLE_GRANDE, VERT_PRINCIPAL);
    }

    public static Icon visiter() {
        return FontIcon.of(MaterialDesignC.CALENDAR_CHECK_OUTLINE, TAILLE_GRANDE, VERT_PRINCIPAL);
    }

    public static Icon ajouter() {
        return FontIcon.of(MaterialDesignP.PLUS_CIRCLE_OUTLINE, TAILLE_PETITE, Color.WHITE);
    }

    public static Icon modifier() {
        return FontIcon.of(MaterialDesignP.PENCIL_OUTLINE, TAILLE_PETITE, Color.WHITE);
    }

    public static Icon supprimer() {
        return FontIcon.of(MaterialDesignD.DELETE_OUTLINE, TAILLE_PETITE, Color.WHITE);
    }

    public static Icon supprimerRouge() {
        return FontIcon.of(MaterialDesignD.DELETE_OUTLINE, TAILLE_PETITE, Color.WHITE);
    }

    public static Icon reinitialiser() {
        return FontIcon.of(MaterialDesignR.REFRESH, TAILLE_PETITE, VERT_FONCE);
    }

    public static Icon rechercher() {
        return FontIcon.of(MaterialDesignM.MAGNIFY, TAILLE_PETITE, VERT_FONCE);
    }

    public static Icon rechercherBtn() {
        return FontIcon.of(MaterialDesignM.MAGNIFY, TAILLE_PETITE, Color.WHITE);
    }

    public static Icon afficherTous() {
        return FontIcon.of(MaterialDesignF.FORMAT_LIST_BULLETED, TAILLE_PETITE, VERT_FONCE);
    }

    public static Icon actualiser() {
        return FontIcon.of(MaterialDesignR.REFRESH_CIRCLE, TAILLE_PETITE, Color.WHITE);
    }

    public static Icon enregistrer() {
        return FontIcon.of(MaterialDesignC.CONTENT_SAVE_OUTLINE, TAILLE_PETITE, Color.WHITE);
    }

    public static Icon quitter() {
        return FontIcon.of(MaterialDesignE.EXIT_TO_APP, TAILLE_PETITE, Color.WHITE);
    }

    public static Icon apropos() {
        return FontIcon.of(MaterialDesignI.INFORMATION_OUTLINE, TAILLE_PETITE, Color.WHITE);
    }

    public static Icon statutOk() {
        return FontIcon.of(MaterialDesignC.CHECK_CIRCLE, TAILLE_PETITE, VERT_PRINCIPAL);
    }

    // ========================================================================
    // Icônes pour titres de section (grandes, sur thème vert clair)
    // ========================================================================

    public static Icon titreMedecin() {
        return FontIcon.of(MaterialDesignS.STETHOSCOPE, TAILLE_TITRE, VERT_PRINCIPAL);
    }

    public static Icon titrePatient() {
        return FontIcon.of(MaterialDesignA.ACCOUNT_HEART, TAILLE_TITRE, VERT_PRINCIPAL);
    }

    public static Icon titreVisite() {
        return FontIcon.of(MaterialDesignC.CALENDAR_MONTH, TAILLE_TITRE, VERT_PRINCIPAL);
    }

    public static Icon titreApplication() {
        return FontIcon.of(MaterialDesignH.HOSPITAL_MARKER, 36, Color.WHITE);
    }

    public static Icon calendrier() {
        return FontIcon.of(MaterialDesignC.CALENDAR_TODAY, TAILLE_PETITE, VERT_PRINCIPAL);
    }

    public static Icon dossierMedical() {
        return FontIcon.of(MaterialDesignF.FILE_DOCUMENT_OUTLINE, TAILLE_PETITE, VERT_PRINCIPAL);
    }

    public static Icon rechercheChamp() {
        return FontIcon.of(MaterialDesignM.MAGNIFY, TAILLE_MOYENNE, VERT_PRINCIPAL);
    }

    public static Icon date() {
        return FontIcon.of(MaterialDesignC.CALENDAR_RANGE, TAILLE_PETITE, VERT_PRINCIPAL);
    }

    // ========================================================================
    // Icônes d'ONGLETS — version NORMALE (sur fond gris-vert pâle)
    // Couleur : VERT_PRINCIPAL pour un bon contraste sur ONGLET_NORMAL_FOND
    // ========================================================================

    public static Icon ongletMedecin() {
        return FontIcon.of(MaterialDesignS.STETHOSCOPE, TAILLE_MOYENNE, VERT_PRINCIPAL);
    }

    public static Icon ongletPatient() {
        return FontIcon.of(MaterialDesignA.ACCOUNT_MULTIPLE_OUTLINE, TAILLE_MOYENNE, VERT_PRINCIPAL);
    }

    public static Icon ongletVisite() {
        return FontIcon.of(MaterialDesignC.CALENDAR_CHECK_OUTLINE, TAILLE_MOYENNE, VERT_PRINCIPAL);
    }

    // ========================================================================
    // Icônes d'ONGLETS — version SÉLECTIONNÉE (sur fond VERT_PRINCIPAL)
    // Couleur : BLANC — CORRECTION du bug de contraste : icône blanche sur vert
    // ========================================================================

    public static Icon ongletMedecinActif() {
        return FontIcon.of(MaterialDesignS.STETHOSCOPE, TAILLE_MOYENNE, Color.WHITE);
    }

    public static Icon ongletPatientActif() {
        return FontIcon.of(MaterialDesignA.ACCOUNT_MULTIPLE_OUTLINE, TAILLE_MOYENNE, Color.WHITE);
    }

    public static Icon ongletVisiteActif() {
        return FontIcon.of(MaterialDesignC.CALENDAR_CHECK_OUTLINE, TAILLE_MOYENNE, Color.WHITE);
    }

    // ========================================================================
    // Icônes d'ONGLETS — version SURVOL (sur fond VERT_CLAIR)
    // Couleur : VERT_FONCE — bon contraste sur fond vert clair
    // ========================================================================

    public static Icon ongletMedecinSurvol() {
        return FontIcon.of(MaterialDesignS.STETHOSCOPE, TAILLE_MOYENNE, VERT_FONCE);
    }

    public static Icon ongletPatientSurvol() {
        return FontIcon.of(MaterialDesignA.ACCOUNT_MULTIPLE_OUTLINE, TAILLE_MOYENNE, VERT_FONCE);
    }

    public static Icon ongletVisiteSurvol() {
        return FontIcon.of(MaterialDesignC.CALENDAR_CHECK_OUTLINE, TAILLE_MOYENNE, VERT_FONCE);
    }

    // ========================================================================
    // NOUVELLES Icônes : remplacement des EMOJIs
    // (Remplace ⚠ ✓ ❌ 📋 💾 🗑️ ↩️ 🔄 ✅ par de vraies icônes Material Design)
    // ========================================================================

    // --- Icône AVERTISSEMENT (⚠) - utilisée dans les erreurs de saisie ---
    public static Icon avertissement() {
        return FontIcon.of(MaterialDesignC.CHECK_CIRCLE, TAILLE_MOYENNE, ROUGE_PRINCIPAL);
    }

    public static Icon avertissementPanneau() {
        return FontIcon.of(MaterialDesignI.INFORMATION_OUTLINE, 28, ORANGE_PRINCIPAL);
    }

    // --- Icône SUCCÈS / VALIDATION (✓ / ✅) ---
    public static Icon succes() {
        return FontIcon.of(MaterialDesignC.CHECK_CIRCLE, TAILLE_MOYENNE, VERT_PRINCIPAL);
    }

    public static Icon succesPanneau() {
        return FontIcon.of(MaterialDesignC.CHECK_CIRCLE, 28, VERT_PRINCIPAL);
    }

    // --- Icône ERREUR CRITIQUE (❌) ---
    public static Icon erreurCritique() {
        return FontIcon.of(MaterialDesignC.CHECK_CIRCLE, TAILLE_MOYENNE, ROUGE_PRINCIPAL);
    }

    public static Icon erreurCritiquePanneau() {
        return FontIcon.of(MaterialDesignC.CHECK_CIRCLE, 28, ROUGE_PRINCIPAL);
    }

    // --- Icône CLIPBOARD / TABLEAU DE BORD (📋) - titre section visites ---
    public static Icon titreClipboard() {
        return FontIcon.of(MaterialDesignF.FILE_DOCUMENT_OUTLINE, TAILLE_TITRE, VERT_PRINCIPAL);
    }

    // --- Icône ENREGISTRER / SAUVEGARDER (💾) ---
    public static Icon enregistrerBouton() {
        return FontIcon.of(MaterialDesignC.CONTENT_SAVE_OUTLINE, TAILLE_PETITE, Color.WHITE);
    }

    // --- Icône CORBEILLE / SUPPRIMER (🗑️) ---
    public static Icon corbeille() {
        return FontIcon.of(MaterialDesignD.DELETE_OUTLINE, TAILLE_PETITE, Color.WHITE);
    }

    // --- Icône RETOUR / RÉINITIALISER (↩️) ---
    public static Icon retourArriere() {
        return FontIcon.of(MaterialDesignR.REFRESH, TAILLE_PETITE, VERT_FONCE);
    }

    // --- Icône ACTUALISER / RAFRAÎCHIR (🔄) ---
    public static Icon actualiserBouton() {
        return FontIcon.of(MaterialDesignR.REFRESH, TAILLE_PETITE, Color.WHITE);
    }

    // --- Icônes INFO ---
    public static Icon infoPanneau() {
        return FontIcon.of(MaterialDesignI.INFORMATION_OUTLINE, 28, BLEU_PRINCIPAL);
    }

    // ========================================================================
    // MÉTHODE UTILITAIRE : crée une copie d'icône avec une couleur différente
    // (Pour adapter dynamiquement les couleurs selon l'état UI)
    // ========================================================================

    public static Icon avecCouleur(Icon source, Color nouvelleCouleur) {
        if (!(source instanceof FontIcon)) return source;
        FontIcon fi = (FontIcon) source;
        return FontIcon.of(fi.getIkon(), fi.getIconWidth(), nouvelleCouleur);
    }

    public static int getTaille(Icon icone) {
        return icone != null ? icone.getIconWidth() : TAILLE_MOYENNE;
    }
}
