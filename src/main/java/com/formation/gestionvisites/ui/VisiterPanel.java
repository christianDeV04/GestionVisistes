package com.formation.gestionvisites.ui;

import com.formation.gestionvisites.entities.Medecin;
import com.formation.gestionvisites.entities.Patient;
import com.formation.gestionvisites.entities.Visiter;
import com.formation.gestionvisites.entities.VisiterId;
import com.formation.gestionvisites.services.MedecinService;
import com.formation.gestionvisites.services.PatientService;
import com.formation.gestionvisites.services.VisiterService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.formation.gestionvisites.ui.IconManager.*;
import static com.formation.gestionvisites.ui.UIPalette.*;

public class VisiterPanel extends JPanel {

    private final VisiterService visiterService;
    private final MedecinService medecinService;
    private final PatientService patientService;

    private JComboBox<String> cboMedecin;
    private JComboBox<String> cboPatient;
    private JSpinner spinnerDate;
    private JTextField txtRecherche;
    private JTable table;
    private DefaultTableModel tableModel;
    private VisiterId idSelectionne = null;
    private List<Medecin> listeMedecins;
    private List<Patient> listePatients;
    private List<Visiter> cacheVisites = new ArrayList<>();

    public VisiterPanel(VisiterService visiterService, MedecinService medecinService, PatientService patientService) {
        this.visiterService = visiterService;
        this.medecinService = medecinService;
        this.patientService = patientService;
        initComponents();
        actualiserListesDeroulantes();
        chargerDonnees();
    }

    public void rafraichir() {
        actualiserListesDeroulantes();
        chargerDonnees();
        if (txtRecherche != null) txtRecherche.setText("");
    }

    private void initComponents() {
        setLayout(new BorderLayout(16, 16));
        setBackground(FOND_PRINCIPAL);
        setBorder(new EmptyBorder(16, 16, 16, 16));

        JPanel contentPanel = new JPanel(new BorderLayout(16, 16));
        contentPanel.setOpaque(false);

        JPanel formCard = new JPanel(new BorderLayout(0, 0));
        formCard.setBackground(FOND_CARTE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_CLAIRE, 1, true),
                new EmptyBorder(0, 0, 16, 0)));

        // --- REMPLACEMENT EMOJI 📋 par icône Material CLIPBOARD_TEXT_OUTLINE (via titreClipboard()) ---
        formCard.add(creerTitreSection(titreClipboard(), "Gestion des Visites",
                        "Enregistrez, modifiez ou supprimez les consultations entre médecins et patients"),
                BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(16, 20, 8, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        formPanel.add(creerLabel("Médecin *"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cboMedecin = new JComboBox<>();
        styliserComboBoxString(cboMedecin);
        formPanel.add(cboMedecin, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        formPanel.add(creerLabel("Patient *"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        cboPatient = new JComboBox<>();
        styliserComboBoxString(cboPatient);
        formPanel.add(cboPatient, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(creerLabel("Date de visite *"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spinnerDate = new JSpinner(dateModel);
        spinnerDate.setFont(POLICE_CHAMP);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerDate, "dd/MM/yyyy");
        editor.getTextField().setFont(POLICE_CHAMP);
        editor.getTextField().setHorizontalAlignment(JTextField.LEFT);
        spinnerDate.setEditor(editor);
        spinnerDate.setBorder(creerBordureArrondie(BORDE_CLAIRE, 1, 6));
        spinnerDate.setPreferredSize(new Dimension(spinnerDate.getPreferredSize().width, 32));
        formPanel.add(spinnerDate, gbc);

        formCard.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(new EmptyBorder(4, 8, 0, 8));

        JButton btnAjouter = creerBoutonPrimaire("Enregistrer", enregistrerBouton());
        JButton btnSupprimer = creerBoutonDanger("Supprimer", corbeille());
        JButton btnReinitialiser = creerBoutonSecondaire("Réinitialiser", retourArriere());

        btnAjouter.addActionListener(e -> enregistrer());
        btnSupprimer.addActionListener(e -> supprimer());
        btnReinitialiser.addActionListener(e -> reinitialiser());

        btnPanel.add(btnAjouter);
        btnPanel.add(btnSupprimer);
        btnPanel.add(btnReinitialiser);
        formCard.add(btnPanel, BorderLayout.SOUTH);

        contentPanel.add(formCard, BorderLayout.NORTH);

        JPanel resultsCard = new JPanel(new BorderLayout(0, 14));
        resultsCard.setOpaque(false);

        JPanel recherchePanel = new JPanel(new BorderLayout(12, 0));
        recherchePanel.setBackground(FOND_CARTE);
        recherchePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(VERT_CLAIR, 2, true),
                new EmptyBorder(14, 18, 14, 18)));

        JPanel rechercheLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rechercheLeft.setOpaque(false);
        rechercheLeft.add(new JLabel(rechercheChamp()));
        rechercheLeft.add(creerLabel("Rechercher (nom médecin OU patient) :"));
        txtRecherche = creerChampTexte(30);
        txtRecherche.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { lancerRechercheDynamique(); }
            @Override public void removeUpdate(DocumentEvent e) { lancerRechercheDynamique(); }
            @Override public void changedUpdate(DocumentEvent e) { lancerRechercheDynamique(); }
        });
        rechercheLeft.add(txtRecherche);

        recherchePanel.add(rechercheLeft, BorderLayout.WEST);
        resultsCard.add(recherchePanel, BorderLayout.NORTH);

        String[] colonnes = {"Code Médecin", "Nom Médecin", "Prénom Médecin",
                             "Code Patient", "Nom Patient", "Prénom Patient", "Date"};
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
                BorderFactory.createLineBorder(BORDE_CLAIRE, 1, true),
                " Historique des consultations médicales ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                POLICE_LABEL, VERT_FONCE));

        styliserTableau(table, scrollPane);
        resultsCard.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(resultsCard, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JLabel creerLabel(String texte) {
        JLabel lbl = new JLabel(texte);
        lbl.setFont(POLICE_LABEL);
        lbl.setForeground(TEXTE_PRINCIPAL);
        return lbl;
    }

    private void styliserComboBoxString(JComboBox<String> cbo) {
        cbo.setFont(POLICE_CHAMP);
        cbo.setForeground(TEXTE_PRINCIPAL);
        cbo.setBackground(Color.WHITE);
        cbo.setBorder(creerBordureArrondie(BORDE_CLAIRE, 1, 6));
        cbo.setPreferredSize(new Dimension(cbo.getPreferredSize().width, 32));
    }

    private void actualiserListesDeroulantes() {
        listeMedecins = medecinService.findAll();
        listePatients = patientService.findAll();

        String medSelectionne = (String) cboMedecin.getSelectedItem();
        String patSelectionne = (String) cboPatient.getSelectedItem();

        cboMedecin.removeAllItems();
        for (Medecin m : listeMedecins) {
            cboMedecin.addItem(m.getCodemed() + " - " + m.getNom() + " " + m.getPrenom());
        }

        cboPatient.removeAllItems();
        for (Patient p : listePatients) {
            cboPatient.addItem(p.getCodepat() + " - " + p.getNom() + " " + p.getPrenom());
        }

        if (medSelectionne != null) {
            for (int i = 0; i < cboMedecin.getItemCount(); i++) {
                if (cboMedecin.getItemAt(i).equals(medSelectionne)) {
                    cboMedecin.setSelectedIndex(i);
                    break;
                }
            }
        }
        if (patSelectionne != null) {
            for (int i = 0; i < cboPatient.getItemCount(); i++) {
                if (cboPatient.getItemAt(i).equals(patSelectionne)) {
                    cboPatient.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void chargerDonnees() {
        cacheVisites = visiterService.findAllWithDetails();
        remplirTableau(cacheVisites);
    }

    private void remplirTableau(List<Visiter> visites) {
        tableModel.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Visiter v : visites) {
            Object[] row = {
                    v.getMedecin().getCodemed(),
                    v.getMedecin().getNom(),
                    v.getMedecin().getPrenom(),
                    v.getPatient().getCodepat(),
                    v.getPatient().getNom(),
                    v.getPatient().getPrenom(),
                    v.getDate().format(fmt)
            };
            tableModel.addRow(row);
        }
    }

    private void lancerRechercheDynamique() {
        if (txtRecherche == null) return;
        String recherche = txtRecherche.getText().trim().toLowerCase();
        if (recherche.isEmpty()) {
            remplirTableau(cacheVisites);
        } else {
            List<Visiter> filtrees = new ArrayList<>();
            for (Visiter v : cacheVisites) {
                boolean matchMedNom = v.getMedecin().getNom().toLowerCase().contains(recherche);
                boolean matchMedPrenom = v.getMedecin().getPrenom().toLowerCase().contains(recherche);
                boolean matchPatNom = v.getPatient().getNom().toLowerCase().contains(recherche);
                boolean matchPatPrenom = v.getPatient().getPrenom().toLowerCase().contains(recherche);
                boolean matchCodeMed = v.getMedecin().getCodemed().toLowerCase().contains(recherche);
                boolean matchCodePat = v.getPatient().getCodepat().toLowerCase().contains(recherche);
                boolean matchDate = v.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(recherche);
                if (matchMedNom || matchMedPrenom || matchPatNom || matchPatPrenom
                        || matchCodeMed || matchCodePat || matchDate) {
                    filtrees.add(v);
                }
            }
            remplirTableau(filtrees);
        }
    }

    private void selectionnerLigne() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String codeMed = (String) tableModel.getValueAt(row, 0);
            String codePat = (String) tableModel.getValueAt(row, 3);
            idSelectionne = new VisiterId(codeMed, codePat);

            String medItem = null;
            for (int i = 0; i < cboMedecin.getItemCount(); i++) {
                String item = cboMedecin.getItemAt(i);
                if (item.startsWith(codeMed + " -")) {
                    medItem = item;
                    break;
                }
            }
            if (medItem != null) cboMedecin.setSelectedItem(medItem);

            String patItem = null;
            for (int i = 0; i < cboPatient.getItemCount(); i++) {
                String item = cboPatient.getItemAt(i);
                if (item.startsWith(codePat + " -")) {
                    patItem = item;
                    break;
                }
            }
            if (patItem != null) cboPatient.setSelectedItem(patItem);

            Object dateObj = tableModel.getValueAt(row, 6);
            if (dateObj instanceof String) {
                try {
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate ld = LocalDate.parse((String) dateObj, fmt);
                    spinnerDate.setValue(Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void reinitialiser() {
        actualiserListesDeroulantes();
        if (cboMedecin.getItemCount() > 0) cboMedecin.setSelectedIndex(0);
        if (cboPatient.getItemCount() > 0) cboPatient.setSelectedIndex(0);
        spinnerDate.setValue(new Date());
        idSelectionne = null;
        table.clearSelection();
        if (txtRecherche != null) txtRecherche.setText("");
        chargerDonnees();
    }

    private String extraireCode(String selection) {
        if (selection == null) return null;
        int idx = selection.indexOf(" -");
        return idx >= 0 ? selection.substring(0, idx) : selection;
    }

    private boolean validerChamps() {
        if (cboMedecin.getSelectedItem() == null || cboMedecin.getItemCount() == 0) {
            afficherErreur("Aucun médecin disponible ! Veuillez d'abord en ajouter.");
            return false;
        }
        if (cboPatient.getSelectedItem() == null || cboPatient.getItemCount() == 0) {
            afficherErreur("Aucun patient disponible ! Veuillez d'abord en ajouter.");
            return false;
        }
        if (spinnerDate.getValue() == null) {
            afficherErreur("La date est obligatoire !");
            return false;
        }
        return true;
    }

    // --- REMPLACEMENT EMOJIS dans boîtes de dialogue ---
    private void afficherErreur(String msg) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBorder(new EmptyBorder(6, 2, 6, 2));
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel(avertissement()), BorderLayout.WEST);
        JLabel lbl = new JLabel("<html><div style='color:#b71c1c; font-size:13px;'>" + msg + "</div></html>");
        lbl.setFont(POLICE_NORMAL);
        panel.add(lbl, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, panel, "Erreur", JOptionPane.PLAIN_MESSAGE);
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

    private void enregistrer() {
        if (!validerChamps()) return;

        String codeMed = extraireCode((String) cboMedecin.getSelectedItem());
        String codePat = extraireCode((String) cboPatient.getSelectedItem());

        Optional<Medecin> optMed = medecinService.findById(codeMed);
        Optional<Patient> optPat = patientService.findById(codePat);

        if (optMed.isEmpty() || optPat.isEmpty()) {
            afficherErreur("Médecin ou patient introuvable !");
            return;
        }

        Date date = (Date) spinnerDate.getValue();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Visiter visiter = new Visiter(optMed.get(), optPat.get(), localDate);

        visiterService.save(visiter);
        afficherSucces("Visite enregistrée avec succès !");
        reinitialiser();
    }

    private void supprimer() {
        if (idSelectionne == null) {
            afficherErreur("Veuillez sélectionner une visite dans la liste !");
            return;
        }

        // --- REMPLACEMENT EMOJIS 🗑️ ⚠ par icônes Material Design ---
        JPanel confirmPanel = new JPanel(new BorderLayout(14, 8));
        confirmPanel.setBorder(new EmptyBorder(8, 4, 4, 4));
        confirmPanel.setBackground(Color.WHITE);

        JPanel titreRow = new JPanel(new BorderLayout(10, 0));
        titreRow.setOpaque(false);
        titreRow.add(new JLabel(avertissementPanneau()), BorderLayout.WEST);
        JLabel titreSuppr = new JLabel("<html><div style='color:#2e7d32; font-size:16px; font-weight:bold;'>"
                + "Suppression de la visite</div></html>");
        titreRow.add(titreSuppr, BorderLayout.CENTER);

        JPanel messages = new JPanel();
        messages.setOpaque(false);
        messages.setLayout(new BoxLayout(messages, BoxLayout.Y_AXIS));
        JLabel lbl1 = new JLabel("<html><div style='color:#424242; font-size:13px;'>"
                + "Êtes-vous sûr de vouloir supprimer cette visite ?</div></html>");
        lbl1.setFont(POLICE_NORMAL);
        lbl1.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lbl2 = new JLabel("<html><div style='color:#c62828; font-size:11px; margin-top:6px;'>"
                + "Cette action est irréversible.</div></html>");
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
            visiterService.deleteById(idSelectionne);
            afficherSucces("Visite supprimée avec succès !");
            reinitialiser();
        }
    }
}
