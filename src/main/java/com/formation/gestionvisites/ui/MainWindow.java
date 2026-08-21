package com.formation.gestionvisites.ui;

import com.formation.gestionvisites.services.MedecinService;
import com.formation.gestionvisites.services.PatientService;
import com.formation.gestionvisites.services.VisiterService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.formation.gestionvisites.ui.IconManager.*;
import static com.formation.gestionvisites.ui.UIPalette.*;

public class MainWindow extends JFrame {

    private final MedecinService medecinService;
    private final PatientService patientService;
    private final VisiterService visiterService;

    public MainWindow(MedecinService medecinService, PatientService patientService, VisiterService visiterService) {
        this.medecinService = medecinService;
        this.patientService = patientService;
        this.visiterService = visiterService;
        initialiserLAFEnPremier();
        initUI();
    }

    /**
     * CORRECTION BUG #2 / #3 : ORDRE D'INITIALISATION CRITIQUE.
     *
     * Ancien ordre (bugué) :
     *   1. Chargement classe UIPalette → bloc static → initialiserUIManager()
     *      (donc UIManager reçoit TableHeader.foreground=BLANC, etc.)
     *   2. MainWindow.initUI() appelle UIManager.setLookAndFeel()
     *      → CELA RÉINITIALISE TOUS LES UIDefaults → nos valeurs sont perdues !
     *
     * Nouvel ordre (correct) :
     *   1. On force d'abord le LookAndFeel (avant même d'accéder à UIPalette).
     *   2. PUIS on rappelle explicitement UIPalette.initialiserUIManager() pour
     *      écraser les defaults du LAF avec NOS valeurs (contraste fort, etc.).
     *
     * Résultat : les TableHeader.foreground/background et TabbedPane.* restent
     * ceux que l'on veut vraiment, pas ceux du LAF système/METAL par défaut.
     */
    private void initialiserLAFEnPremier() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
        }
        UIManager.put("TabbedPane.selected", new ColorUIResource(VERT_PRINCIPAL));
        UIPalette.initialiserUIManager();
    }

    private void initUI() {
        setTitle("Gestion des Visites - Centre Médical");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1180, 780));
        setMinimumSize(new Dimension(950, 620));
        setLocationRelativeTo(null);
        getContentPane().setBackground(FOND_PRINCIPAL);

        // ======================== BARRE DE MENU ========================
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(VERT_FONCE);
        menuBar.setForeground(TEXTE_INVERSE);
        menuBar.setBorderPainted(false);
        menuBar.setOpaque(true);
        menuBar.setBorder(new EmptyBorder(4, 8, 4, 8));

        JMenu menuFichier = creerMenu("Fichier");
        JMenuItem menuQuitter = creerMenuItem("Quitter l'application", quitter());
        menuQuitter.addActionListener(e -> System.exit(0));
        menuFichier.add(menuQuitter);
        menuBar.add(menuFichier);

        JMenu menuAide = creerMenu("Aide");
        JMenuItem menuAPropos = creerMenuItem("À propos", apropos());
        menuAPropos.addActionListener(e -> afficherAPropos());
        menuAide.add(menuAPropos);
        menuBar.add(menuAide);

        setJMenuBar(menuBar);

        // ======================== EN-TETE PRINCIPAL ========================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(VERT_PRINCIPAL);
        headerPanel.setBorder(new EmptyBorder(20, 28, 20, 28));

        JPanel titlePanel = new JPanel(new BorderLayout(14, 0));
        titlePanel.setOpaque(false);

        JLabel lblIconeApp = new JLabel(titreApplication());
        lblIconeApp.setVerticalAlignment(SwingConstants.CENTER);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel titreAppli = new JLabel("Gestion des Visites Médicales");
        titreAppli.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titreAppli.setForeground(TEXTE_INVERSE);
        titreAppli.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sousTitreAppli = new JLabel("Centre Médical");
        sousTitreAppli.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sousTitreAppli.setForeground(VERT_CLAIR);
        sousTitreAppli.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(titreAppli);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(sousTitreAppli);

        titlePanel.add(lblIconeApp, BorderLayout.WEST);
        titlePanel.add(textPanel, BorderLayout.CENTER);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        datePanel.setOpaque(false);

        JLabel lblDateIcone = new JLabel(date());
        lblDateIcone.setVerticalAlignment(SwingConstants.CENTER);

        JLabel dateLabel = new JLabel(LocalDate.now().format(
                DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.FRENCH)));
        dateLabel.setFont(POLICE_NORMAL);
        dateLabel.setForeground(VERT_CLAIR);
        dateLabel.setVerticalAlignment(SwingConstants.CENTER);

        datePanel.add(lblDateIcone);
        datePanel.add(dateLabel);
        headerPanel.add(datePanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // ======================== TABBED PANE ========================
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(POLICE_ONGLET);
        tabbedPane.setBackground(FOND_PRINCIPAL);
        tabbedPane.setOpaque(true);
        tabbedPane.setBorder(new EmptyBorder(18, 20, 0, 20));

        appliquerUIOnglets(tabbedPane);

        MedecinPanel medecinPanel = new MedecinPanel(medecinService);
        tabbedPane.addTab("Médecins", ongletMedecin(), medecinPanel,
                "Gestion des médecins du centre");

        PatientPanel patientPanel = new PatientPanel(patientService);
        tabbedPane.addTab("Patients", ongletPatient(), patientPanel,
                "Gestion des patients et dossiers médicaux");

        VisiterPanel visiterPanel = new VisiterPanel(visiterService, medecinService, patientService);
        tabbedPane.addTab("Visites", ongletVisite(), visiterPanel,
                "Historique et enregistrement des consultations");

        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                Component c = tabbedPane.getComponentAt(i);
                if (c != null) {
                    c.setBackground(i == selectedIndex ? FOND_PRINCIPAL : FOND_CARTE);
                }
            }
            if (selectedIndex == 2) {
                visiterPanel.rafraichir();
            }
        });

        add(tabbedPane, BorderLayout.CENTER);

        // ======================== BARRE DE STATUT ========================
        JPanel statusPanel = new JPanel(new BorderLayout(16, 0));
        statusPanel.setBackground(VERT_TRES_CLAIR);
        statusPanel.setBorder(new EmptyBorder(8, 24, 8, 24));

        JPanel statusLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        statusLeft.setOpaque(false);
        // JLabel statutIcone = new JLabel(statutOk());
        // JLabel statusLabel = new JLabel("");
        // statusLabel.setFont(POLICE_NORMAL);
        // statusLabel.setForeground(VERT_FONCE);
        // statusLeft.add(statutIcone);
        // statusLeft.add(statusLabel);

        // JLabel versionLabel = new JLabel("");
        // versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        // versionLabel.setForeground(TEXTE_SECONDAIRE);
        // versionLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        statusPanel.add(statusLeft, BorderLayout.WEST);
        // statusPanel.add(versionLabel, BorderLayout.EAST);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private JMenu creerMenu(String titre) {
        JMenu menu = new JMenu(titre);
        menu.setFont(POLICE_LABEL);
        menu.setForeground(TEXTE_INVERSE);
        menu.setOpaque(true);
        menu.setBackground(VERT_FONCE);
        menu.setBorder(new EmptyBorder(6, 10, 6, 10));
        menu.getAccessibleContext().setAccessibleDescription(titre);
        return menu;
    }

    private JMenuItem creerMenuItem(String titre, Icon icone) {
        JMenuItem item = new JMenuItem(titre, icone);
        item.setFont(POLICE_NORMAL);
        item.setForeground(TEXTE_PRINCIPAL);
        item.setBackground(Color.WHITE);
        item.setBorder(new EmptyBorder(8, 18, 8, 18));
        item.setIconTextGap(10);
        return item;
    }

    private void afficherAPropos() {
        JPanel panel = new JPanel(new BorderLayout(16, 12));
        panel.setBorder(new EmptyBorder(12, 8, 0, 8));
        panel.setBackground(Color.WHITE);

        JLabel lblIcone = new JLabel(titreMedecin());
        lblIcone.setVerticalAlignment(SwingConstants.CENTER);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel titre = new JLabel("Gestion des Visites");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titre.setForeground(VERT_FONCE);
        titre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sous = new JLabel("Centre Médical — Application de gestion");
        sous.setFont(POLICE_LABEL);
        sous.setForeground(TEXTE_SECONDAIRE);
        sous.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sep = new JLabel(" ");
        sep.setBorder(new EmptyBorder(4, 0, 4, 0));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea text = new JTextArea();
        text.setOpaque(false);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setFont(POLICE_NORMAL);
        text.setForeground(TEXTE_PRINCIPAL);
        text.setText("Application desktop développée avec :\n"
                + "• Java Swing (interface graphique)\n"
                + "• Spring Boot 4 + Spring Data JPA (backend)\n"
                + "• PostgreSQL (base de données)\n"
                + "• Ikonli Material Design 2 (icônes vectorielles)");
        text.setAlignmentX(Component.LEFT_ALIGNMENT);

        info.add(titre);
        info.add(sous);
        info.add(sep);
        info.add(text);

        JLabel cop = new JLabel("© 2025 — Tous droits réservés");
        cop.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        cop.setForeground(TEXTE_SECONDAIRE);
        cop.setBorder(new EmptyBorder(10, 0, 0, 0));
        cop.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.add(cop);

        panel.add(lblIcone, BorderLayout.WEST);
        panel.add(info, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "À propos",
                JOptionPane.PLAIN_MESSAGE);
    }
}
