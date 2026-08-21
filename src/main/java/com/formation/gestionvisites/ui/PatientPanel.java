package com.formation.gestionvisites.ui;

import com.formation.gestionvisites.entities.Patient;
import com.formation.gestionvisites.entities.Sexe;
import com.formation.gestionvisites.services.PatientService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Optional;

import static com.formation.gestionvisites.ui.IconManager.*;
import static com.formation.gestionvisites.ui.UIPalette.*;

public class PatientPanel extends JPanel {

    private final PatientService patientService;
    private JTextField txtCodePat;
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JComboBox<Sexe> cboSexe;
    private JTextField txtAdresse;
    private JTextField txtRecherche;
    private JTable table;
    private DefaultTableModel tableModel;
    private String codeSelectionne = null;

    public PatientPanel(PatientService patientService) {
        this.patientService = patientService;
        initComponents();
        chargerDonnees();
    }

    private void initComponents() {
        setLayout(new BorderLayout(16, 16));
        setBackground(FOND_PRINCIPAL);
        setBorder(new EmptyBorder(16, 16, 20, 16));

        JPanel contentPanel = new JPanel(new BorderLayout(16, 16));
        contentPanel.setOpaque(false);

        // ======================== CARTE FORMULAIRE ========================
        JPanel formCard = new JPanel(new BorderLayout(0, 0));
        formCard.setBackground(FOND_CARTE);
        formCard.setBorder(new CompoundBorder(
                new LightBorder(BORDE_CLAIRE, 1, 12),
                new EmptyBorder(0, 0, 18, 0)));

        formCard.add(creerTitreSection(titrePatient(),
                        "Gestion des Patients",
                        "Référencement des patients du centre médical"),
                BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(20, 24, 10, 24));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        formPanel.add(creerLabel("Code Patient"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtCodePat = creerChampTexte(18);
        txtCodePat.setEditable(false);
        txtCodePat.setBackground(VERT_TRES_CLAIR);
        txtCodePat.setForeground(VERT_FONCE);
        txtCodePat.setText(patientService.genererNouveauCode());
        formPanel.add(avecIcone(txtCodePat, patient()), gbc);

        gbc.gridx = 2; gbc.weightx = 0.0;
        formPanel.add(creerLabel("Sexe"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        cboSexe = new JComboBox<>(Sexe.values());
        styliserComboBox(cboSexe);
        formPanel.add(cboSexe, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        formPanel.add(creerLabel("Nom"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNom = creerChampTexte(18);
        formPanel.add(txtNom, gbc);

        gbc.gridx = 2; gbc.weightx = 0.0;
        formPanel.add(creerLabel("Prénom"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        txtPrenom = creerChampTexte(18);
        formPanel.add(txtPrenom, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        formPanel.add(creerLabel("Adresse"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        txtAdresse = creerChampTexte(50);
        formPanel.add(avecIcone(txtAdresse, dossierMedical()), gbc);

        formCard.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(new EmptyBorder(8, 8, 0, 8));

        JButton btnAjouter     = creerBoutonPrimaire("Ajouter", IconManager.ajouter());
        JButton btnModifier    = creerBoutonInfo("Modifier", IconManager.modifier());
        JButton btnSupprimer   = creerBoutonDanger("Supprimer", IconManager.supprimer());
        JButton btnReinit      = creerBoutonSecondaire("Réinitialiser", IconManager.reinitialiser());

        btnAjouter.addActionListener(e -> ajouter());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());
        btnReinit.addActionListener(e -> reinitialiser());

        btnPanel.add(btnAjouter);
        btnPanel.add(btnModifier);
        btnPanel.add(btnSupprimer);
        btnPanel.add(btnReinit);
        formCard.add(btnPanel, BorderLayout.SOUTH);

        contentPanel.add(formCard, BorderLayout.NORTH);

        // ======================== CARTE RESULTATS ========================
        JPanel resultsCard = new JPanel(new BorderLayout(0, 14));
        resultsCard.setOpaque(false);

        JPanel recherchePanel = new JPanel(new BorderLayout(12, 0));
        recherchePanel.setBackground(FOND_CARTE);
        recherchePanel.setBorder(new CompoundBorder(
                new LightBorder(VERT_CLAIR, 2, 12),
                new EmptyBorder(14, 18, 14, 18)));

        JPanel rechercheLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rechercheLeft.setOpaque(false);
        rechercheLeft.add(new JLabel(rechercheChamp()));
        rechercheLeft.add(creerLabel("Rechercher (code OU nom) :"));
        txtRecherche = creerChampTexte(24);
        txtRecherche.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { lancerRechercheDynamique(); }
            @Override public void removeUpdate(DocumentEvent e) { lancerRechercheDynamique(); }
            @Override public void changedUpdate(DocumentEvent e) { lancerRechercheDynamique(); }
        });
        rechercheLeft.add(txtRecherche);
        JButton btnRechercher = creerBoutonPrimaire("Rechercher", rechercherBtn());
        btnRechercher.addActionListener(e -> rechercher());
        rechercheLeft.add(btnRechercher);

        recherchePanel.add(rechercheLeft, BorderLayout.WEST);
        resultsCard.add(recherchePanel, BorderLayout.NORTH);

        String[] colonnes = {"Code Patient", "Nom", "Prénom", "Sexe", "Adresse"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> selectionnerLigne());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                new LightBorder(BORDE_CLAIRE, 1, 12),
                "  Liste des patients enregistrés  ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                POLICE_LABEL, VERT_FONCE));

        styliserTableau(table, scrollPane);
        resultsCard.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(resultsCard, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel avecIcone(JComponent c, Icon icone) {
        JPanel p = new JPanel(new BorderLayout(6, 0));
        p.setOpaque(false);
        p.add(new JLabel(icone), BorderLayout.WEST);
        p.add(c, BorderLayout.CENTER);
        return p;
    }

    private JLabel creerLabel(String texte) {
        JLabel lbl = new JLabel(texte);
        lbl.setFont(POLICE_LABEL);
        lbl.setForeground(TEXTE_PRINCIPAL);
        return lbl;
    }

    private void chargerDonnees() {
        tableModel.setRowCount(0);
        List<Patient> patients = patientService.findAll();
        for (Patient p : patients) {
            Object[] row = {p.getCodepat(), p.getNom(), p.getPrenom(), p.getSexe(), p.getAdresse()};
            tableModel.addRow(row);
        }
    }

    private void lancerRechercheDynamique() {
        String recherche = txtRecherche.getText().trim();
        if (recherche.isEmpty()) {
            chargerDonnees();
        } else {
            tableModel.setRowCount(0);
            List<Patient> patients = patientService.findByCodepatOrNomContaining(recherche);
            for (Patient p : patients) {
                Object[] row = {p.getCodepat(), p.getNom(), p.getPrenom(), p.getSexe(), p.getAdresse()};
                tableModel.addRow(row);
            }
        }
    }

    private void rechercher() {
        lancerRechercheDynamique();
    }

    private void selectionnerLigne() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            codeSelectionne = (String) tableModel.getValueAt(row, 0);
            txtCodePat.setText(codeSelectionne);
            txtNom.setText((String) tableModel.getValueAt(row, 1));
            txtPrenom.setText((String) tableModel.getValueAt(row, 2));
            cboSexe.setSelectedItem(tableModel.getValueAt(row, 3));
            txtAdresse.setText((String) tableModel.getValueAt(row, 4));
        }
    }

    private void reinitialiser() {
        txtCodePat.setText(patientService.genererNouveauCode());
        txtNom.setText("");
        txtPrenom.setText("");
        if (cboSexe.getItemCount() > 0) cboSexe.setSelectedIndex(0);
        txtAdresse.setText("");
        txtRecherche.setText("");
        codeSelectionne = null;
        table.clearSelection();
        chargerDonnees();
    }

    private boolean validerChamps() {
        if (txtNom.getText().trim().isEmpty()) {
            afficherErreur("Le nom est obligatoire !");
            txtNom.requestFocus();
            return false;
        }
        if (txtPrenom.getText().trim().isEmpty()) {
            afficherErreur("Le prénom est obligatoire !");
            txtPrenom.requestFocus();
            return false;
        }
        if (cboSexe.getSelectedItem() == null) {
            afficherErreur("Le sexe est obligatoire !");
            return false;
        }
        return true;
    }

    private void afficherErreur(String msg) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBorder(new EmptyBorder(6, 2, 6, 2));
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel(erreurCritique()), BorderLayout.WEST);
        JLabel lbl = new JLabel("<html><div style='color:#b71c1c; font-size:13px;'>" + msg + "</div></html>");
        lbl.setFont(POLICE_NORMAL);
        panel.add(lbl, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, panel, "Erreur de saisie", JOptionPane.PLAIN_MESSAGE);
    }

    private void afficherSucces(String msg) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBorder(new EmptyBorder(6, 2, 6, 2));
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel(succes()), BorderLayout.WEST);
        JLabel lbl = new JLabel("<html><div style='color:#1b5e20; font-size:13px;'>" + msg + "</div></html>");
        lbl.setFont(POLICE_NORMAL);
        panel.add(lbl, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, panel, "Succès", JOptionPane.PLAIN_MESSAGE);
    }

    private void ajouter() {
        if (!validerChamps()) return;
        Patient patient = new Patient();
        patient.setCodepat(txtCodePat.getText().trim());
        patient.setNom(txtNom.getText().trim());
        patient.setPrenom(txtPrenom.getText().trim());
        patient.setSexe((Sexe) cboSexe.getSelectedItem());
        patient.setAdresse(txtAdresse.getText().trim());
        patientService.save(patient);
        afficherSucces("Patient ajouté avec succès !");
        reinitialiser();
    }

    private void modifier() {
        if (codeSelectionne == null) {
            afficherErreur("Veuillez sélectionner un patient dans la liste !");
            return;
        }
        if (!validerChamps()) return;
        Optional<Patient> optPatient = patientService.findById(codeSelectionne);
        if (optPatient.isPresent()) {
            Patient patient = optPatient.get();
            patient.setNom(txtNom.getText().trim());
            patient.setPrenom(txtPrenom.getText().trim());
            patient.setSexe((Sexe) cboSexe.getSelectedItem());
            patient.setAdresse(txtAdresse.getText().trim());
            patientService.save(patient);
            afficherSucces("Patient modifié avec succès !");
            reinitialiser();
        }
    }

    private void supprimer() {
        if (codeSelectionne == null) {
            afficherErreur("Veuillez sélectionner un patient dans la liste !");
            return;
        }

        JPanel confirmPanel = new JPanel(new BorderLayout(14, 8));
        confirmPanel.setBorder(new EmptyBorder(8, 4, 4, 4));
        confirmPanel.setBackground(Color.WHITE);

        JPanel titreRow = new JPanel(new BorderLayout(10, 0));
        titreRow.setOpaque(false);
        titreRow.add(new JLabel(avertissementPanneau()), BorderLayout.WEST);
        JLabel titreSuppr = new JLabel("<html><div style='color:#2e7d32; font-size:16px; font-weight:bold;'>"
                + "Suppression du patient</div></html>");
        titreRow.add(titreSuppr, BorderLayout.CENTER);

        JPanel messages = new JPanel();
        messages.setOpaque(false);
        messages.setLayout(new BoxLayout(messages, BoxLayout.Y_AXIS));
        JLabel lbl1 = new JLabel("<html><div style='color:#424242; font-size:13px; margin-top:8px;'>"
                + "Êtes-vous sûr de vouloir supprimer ce patient ?</div></html>");
        lbl1.setFont(POLICE_NORMAL);
        lbl1.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lbl2 = new JLabel("<html><div style='color:#c62828; font-size:11px; margin-top:6px;'>"
                + "Cette action est irréversible et peut affecter les visites associées.</div></html>");
        lbl2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl2.setAlignmentX(Component.LEFT_ALIGNMENT);
        messages.add(lbl1);
        messages.add(Box.createVerticalStrut(4));
        messages.add(lbl2);

        confirmPanel.add(titreRow, BorderLayout.NORTH);
        confirmPanel.add(messages, BorderLayout.CENTER);

        int confirmation = JOptionPane.showConfirmDialog(this, confirmPanel,
                "Confirmer la suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                patientService.deleteById(codeSelectionne);
                afficherSucces("Patient supprimé avec succès !");
                reinitialiser();
            } catch (Exception ex) {
                JPanel errPanel = new JPanel(new BorderLayout(12, 0));
                errPanel.setBorder(new EmptyBorder(6, 2, 6, 2));
                errPanel.setBackground(Color.WHITE);
                errPanel.add(new JLabel(erreurCritiquePanneau()), BorderLayout.WEST);
                JLabel errLbl = new JLabel("<html><div style='color:#b71c1c; font-size:13px;'>"
                        + "Erreur lors de la suppression : ce patient est peut-être référencé dans des visites."
                        + "</div></html>");
                errLbl.setFont(POLICE_NORMAL);
                errPanel.add(errLbl, BorderLayout.CENTER);
                JOptionPane.showMessageDialog(this, errPanel, "Erreur", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    private static class LightBorder extends AbstractBorder {
        private final Color color;
        private final int thickness;
        private final int radius;
        LightBorder(Color c, int t, int r) { color=c; thickness=t; radius=r; }
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(this.color);
            g2.setStroke(new BasicStroke(this.thickness));
            g2.drawRoundRect(x+this.thickness/2, y+this.thickness/2,
                    w-this.thickness, h-this.thickness, this.radius, this.radius);
            g2.dispose();
        }
        @Override public Insets getBorderInsets(Component c) {
            return new Insets(this.thickness, this.thickness, this.thickness, this.thickness);
        }
    }
}
