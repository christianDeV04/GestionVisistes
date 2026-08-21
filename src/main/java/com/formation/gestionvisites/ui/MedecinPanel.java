package com.formation.gestionvisites.ui;

import com.formation.gestionvisites.entities.Grade;
import com.formation.gestionvisites.entities.Medecin;
import com.formation.gestionvisites.services.MedecinService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Optional;

import static com.formation.gestionvisites.ui.UIPalette.*;

public class MedecinPanel extends JPanel {

    private final MedecinService medecinService;
    private JTextField txtCodeMed;
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JComboBox<Grade> cboGrade;
    private JTextField txtRecherche;
    private JTable table;
    private DefaultTableModel tableModel;
    private String codeSelectionne = null;

    public MedecinPanel(MedecinService medecinService) {
        this.medecinService = medecinService;
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
                new ArrondiBorderLight(BORDE_CLAIRE, 1, 12),
                new EmptyBorder(0, 0, 18, 0)));

        formCard.add(creerTitreSection(IconManager.titreMedecin(),
                        "Gestion des Médecins",
                        "Ajoutez, modifiez ou supprimez les médecins du centre médical"),
                BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(20, 24, 10, 24));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        formPanel.add(creerLabel("Code Médecin"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtCodeMed = creerChampTexte(18);
        txtCodeMed.setEditable(false);
        txtCodeMed.setBackground(VERT_TRES_CLAIR);
        txtCodeMed.setForeground(VERT_FONCE);
        txtCodeMed.setText(medecinService.genererNouveauCode());
        JPanel wrap1 = avecIcone(txtCodeMed, IconManager.dossierMedical());
        formPanel.add(wrap1, gbc);

        gbc.gridx = 2; gbc.weightx = 0.0;
        formPanel.add(creerLabel("Grade"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        cboGrade = new JComboBox<>(Grade.values());
        styliserComboBox(cboGrade);
        formPanel.add(cboGrade, gbc);

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

        formCard.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(new EmptyBorder(8, 8, 0, 8));

        JButton btnAjouter     = creerBoutonPrimaire("Ajouter", IconManager.ajouter());
        JButton btnModifier    = creerBoutonInfo("Modifier", IconManager.modifier());
        JButton btnSupprimer   = creerBoutonDanger("Supprimer", IconManager.supprimerRouge());
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
                new ArrondiBorderLight(BORDE_CLAIRE, 1, 12),
                new EmptyBorder(14, 18, 14, 18)));

        JPanel rechercheLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rechercheLeft.setOpaque(false);
        rechercheLeft.add(new JLabel(IconManager.rechercheChamp()));
        rechercheLeft.add(creerLabel("Rechercher par nom :"));
        txtRecherche = creerChampTexte(22);
        txtRecherche.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { lancerRechercheDynamique(); }
            @Override public void removeUpdate(DocumentEvent e) { lancerRechercheDynamique(); }
            @Override public void changedUpdate(DocumentEvent e) { lancerRechercheDynamique(); }
        });
        rechercheLeft.add(txtRecherche);
        JButton btnRechercher = creerBoutonPrimaire("Rechercher", IconManager.rechercherBtn());
        btnRechercher.addActionListener(e -> rechercher());
        rechercheLeft.add(btnRechercher);

        recherchePanel.add(rechercheLeft, BorderLayout.WEST);
        resultsCard.add(recherchePanel, BorderLayout.NORTH);

        String[] colonnes = {"Code Médecin", "Nom", "Prénom", "Grade"};
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
                new ArrondiBorderLight(BORDE_CLAIRE, 1, 12),
                "  Liste des médecins enregistrés  ",
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
        JLabel lbl = new JLabel(texte + (texte.contains("Code") || texte.contains("Nom") || texte.contains("Prénom") || texte.contains("Grade") ? "" : ""));
        lbl.setFont(POLICE_LABEL);
        lbl.setForeground(TEXTE_PRINCIPAL);
        return lbl;
    }

    private void chargerDonnees() {
        tableModel.setRowCount(0);
        List<Medecin> medecins = medecinService.findAll();
        for (Medecin m : medecins) {
            Object[] row = {m.getCodemed(), m.getNom(), m.getPrenom(), m.getGrade()};
            tableModel.addRow(row);
        }
    }

    private void lancerRechercheDynamique() {
        String nom = txtRecherche.getText().trim();
        if (nom.isEmpty()) {
            chargerDonnees();
        } else {
            tableModel.setRowCount(0);
            List<Medecin> medecins = medecinService.findByNomContaining(nom);
            for (Medecin m : medecins) {
                Object[] row = {m.getCodemed(), m.getNom(), m.getPrenom(), m.getGrade()};
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
            txtCodeMed.setText(codeSelectionne);
            txtNom.setText((String) tableModel.getValueAt(row, 1));
            txtPrenom.setText((String) tableModel.getValueAt(row, 2));
            cboGrade.setSelectedItem(tableModel.getValueAt(row, 3));
        }
    }

    private void reinitialiser() {
        txtCodeMed.setText(medecinService.genererNouveauCode());
        txtNom.setText("");
        txtPrenom.setText("");
        if (cboGrade.getItemCount() > 0) cboGrade.setSelectedIndex(0);
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
        if (cboGrade.getSelectedItem() == null) {
            afficherErreur("Le grade est obligatoire !");
            return false;
        }
        return true;
    }

    private void afficherErreur(String msg) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBorder(new EmptyBorder(6, 2, 6, 2));
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel(IconManager.erreurCritique()), BorderLayout.WEST);
        JLabel lbl = new JLabel("<html><div style='color:#b71c1c; font-size:13px;'>" + msg + "</div></html>");
        lbl.setFont(POLICE_NORMAL);
        panel.add(lbl, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, panel, "Erreur de saisie", JOptionPane.PLAIN_MESSAGE);
    }

    private void afficherSucces(String msg) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBorder(new EmptyBorder(6, 2, 6, 2));
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel(IconManager.succes()), BorderLayout.WEST);
        JLabel lbl = new JLabel("<html><div style='color:#1b5e20; font-size:13px;'>" + msg + "</div></html>");
        lbl.setFont(POLICE_NORMAL);
        panel.add(lbl, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, panel, "Succès", JOptionPane.PLAIN_MESSAGE);
    }

    private void ajouter() {
        if (!validerChamps()) return;
        Medecin medecin = new Medecin();
        medecin.setCodemed(txtCodeMed.getText().trim());
        medecin.setNom(txtNom.getText().trim());
        medecin.setPrenom(txtPrenom.getText().trim());
        medecin.setGrade((Grade) cboGrade.getSelectedItem());
        medecinService.save(medecin);
        afficherSucces("Médecin ajouté avec succès !");
        reinitialiser();
    }

    private void modifier() {
        if (codeSelectionne == null) {
            afficherErreur("Veuillez sélectionner un médecin dans la liste !");
            return;
        }
        if (!validerChamps()) return;
        Optional<Medecin> optMedecin = medecinService.findById(codeSelectionne);
        if (optMedecin.isPresent()) {
            Medecin medecin = optMedecin.get();
            medecin.setNom(txtNom.getText().trim());
            medecin.setPrenom(txtPrenom.getText().trim());
            medecin.setGrade((Grade) cboGrade.getSelectedItem());
            medecinService.save(medecin);
            afficherSucces("Médecin modifié avec succès !");
            reinitialiser();
        }
    }

    private void supprimer() {
        if (codeSelectionne == null) {
            afficherErreur("Veuillez sélectionner un médecin dans la liste !");
            return;
        }

        JPanel confirmPanel = new JPanel(new BorderLayout(14, 8));
        confirmPanel.setBorder(new EmptyBorder(8, 4, 4, 4));
        confirmPanel.setBackground(Color.WHITE);

        JPanel titreRow = new JPanel(new BorderLayout(10, 0));
        titreRow.setOpaque(false);
        titreRow.add(new JLabel(IconManager.avertissementPanneau()), BorderLayout.WEST);
        JLabel titreSuppr = new JLabel("<html><div style='color:#2e7d32; font-size:16px; font-weight:bold;'>"
                + "Suppression du médecin</div></html>");
        titreRow.add(titreSuppr, BorderLayout.CENTER);

        JPanel messages = new JPanel();
        messages.setOpaque(false);
        messages.setLayout(new BoxLayout(messages, BoxLayout.Y_AXIS));
        JLabel lbl1 = new JLabel("<html><div style='color:#424242; font-size:13px; margin-top:8px;'>"
                + "Êtes-vous sûr de vouloir supprimer ce médecin ?</div></html>");
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
                medecinService.deleteById(codeSelectionne);
                afficherSucces("Médecin supprimé avec succès !");
                reinitialiser();
            } catch (Exception ex) {
                JPanel errPanel = new JPanel(new BorderLayout(12, 0));
                errPanel.setBorder(new EmptyBorder(6, 2, 6, 2));
                errPanel.setBackground(Color.WHITE);
                errPanel.add(new JLabel(IconManager.erreurCritiquePanneau()), BorderLayout.WEST);
                JLabel errLbl = new JLabel("<html><div style='color:#b71c1c; font-size:13px;'>"
                        + "Erreur lors de la suppression : ce médecin est peut-être référencé dans des visites."
                        + "</div></html>");
                errLbl.setFont(POLICE_NORMAL);
                errPanel.add(errLbl, BorderLayout.CENTER);
                JOptionPane.showMessageDialog(this, errPanel, "Erreur", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    // Bordure arrondie simplifiée (utilisée dans ce panel)
    private static class ArrondiBorderLight extends AbstractBorder {
        private final Color color;
        private final int thickness;
        private final int radius;
        ArrondiBorderLight(Color c, int t, int r) { color=c; thickness=t; radius=r; }
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
