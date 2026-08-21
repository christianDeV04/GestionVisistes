package com.formation.gestionvisites.ui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.kordamp.ikonli.swing.FontIcon;

import static com.formation.gestionvisites.ui.IconManager.*;

public class UIPalette {

    public static final Color VERT_PRINCIPAL = new Color(46, 125, 50);
    public static final Color VERT_FONCE = new Color(27, 94, 32);
    public static final Color VERT_MOYEN = new Color(76, 175, 80);
    public static final Color VERT_CLAIR = new Color(200, 230, 201);
    public static final Color VERT_TRES_CLAIR = new Color(232, 245, 233);
    public static final Color VERT_ACCENT = new Color(102, 187, 106);

    public static final Color ROUGE_PRINCIPAL = new Color(198, 40, 40);
    public static final Color ROUGE_CLAIR = new Color(255, 205, 210);
    public static final Color BLEU_PRINCIPAL = new Color(21, 101, 192);
    public static final Color ORANGE_PRINCIPAL = new Color(230, 81, 0);
    public static final Color BLEU_CLAIR = new Color(187, 222, 251);

    public static final Color FOND_PRINCIPAL = new Color(245, 247, 246);
    public static final Color FOND_CARTE = Color.WHITE;
    public static final Color TEXTE_PRINCIPAL = new Color(33, 33, 33);
    public static final Color TEXTE_SECONDAIRE = new Color(97, 97, 97);
    public static final Color TEXTE_INVERSE = Color.WHITE;

    public static final Color BORDE_CLAIRE = new Color(224, 224, 224);
    public static final Color BORDE_FONCE = new Color(189, 189, 189);
    public static final Color OMBRE = new Color(0, 0, 0, 15);

    public static final Color ONGLET_NORMAL_FOND = new Color(240, 244, 240);
    public static final Color ONGLET_NORMAL_TEXTE = new Color(85, 95, 85);
    public static final Color ONGLET_SELECTIONNE_FOND = VERT_PRINCIPAL;
    public static final Color ONGLET_SELECTIONNE_TEXTE = Color.WHITE;
    public static final Color ONGLET_SURVOL_FOND = VERT_CLAIR;
    public static final Color ONGLET_SURVOL_TEXTE = VERT_FONCE;

    public static final Color ONGLET_NORMAL_ICONE = VERT_PRINCIPAL;
    public static final Color ONGLET_SELECTIONNE_ICONE = Color.WHITE;
    public static final Color ONGLET_SURVOL_ICONE = VERT_FONCE;

    public static final Font POLICE_TITRE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font POLICE_SOUS_TITRE = new Font("Segoe UI", Font.BOLD, 17);
    public static final Font POLICE_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font POLICE_NORMAL = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font POLICE_CHAMP = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font POLICE_TABLEAU = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font POLICE_BOUTON = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font POLICE_ONGLET = new Font("Segoe UI", Font.BOLD, 13);

    static {
        initialiserUIManager();
    }

    // ========================================================================
    // INITIALISATION GLOBALE DE L'UI
    // ========================================================================

    /**
     * CORRECTION BUG #2/#3 : on définit TOUTES les couleurs UIManager qui
     * pilotent le contraste des onglets et des en-têtes de tableau.
     * Cette méthode est appelée depuis MainWindow APRÈS setLookAndFeel
     * (l'ordre précédent — LAF après UIManager — écrasait nos valeurs).
     */
    public static void initialiserUIManager() {
        UIManager.put("TabbedPane.font", new FontUIResource(POLICE_ONGLET));
        UIManager.put("TabbedPane.foreground", new ColorUIResource(ONGLET_NORMAL_TEXTE));
        UIManager.put("TabbedPane.background", new ColorUIResource(FOND_PRINCIPAL));
        UIManager.put("TabbedPane.selectedForeground", new ColorUIResource(ONGLET_SELECTIONNE_TEXTE));
        UIManager.put("TabbedPane.selectedBackground", new ColorUIResource(ONGLET_SELECTIONNE_FOND));
        UIManager.put("TabbedPane.tabAreaBackground", new ColorUIResource(FOND_PRINCIPAL));
        UIManager.put("TabbedPane.contentAreaColor", new ColorUIResource(FOND_PRINCIPAL));
        UIManager.put("TabbedPane.darkShadow", new ColorUIResource(BORDE_CLAIRE));
        UIManager.put("TabbedPane.lightHighlight", new ColorUIResource(Color.WHITE));
        UIManager.put("TabbedPane.focus", new ColorUIResource(VERT_MOYEN));
        UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);

        // --- En-têtes de tableau : contraste FORT garanti ---
        UIManager.put("TableHeader.font", new FontUIResource(POLICE_LABEL));
        UIManager.put("TableHeader.background", new ColorUIResource(VERT_FONCE));
        UIManager.put("TableHeader.foreground", new ColorUIResource(Color.WHITE));
        UIManager.put("TableHeader.cellBorder",
                new BorderUIResource(new EmptyBorder(8, 10, 8, 10)));

        UIManager.put("Panel.background", new ColorUIResource(FOND_PRINCIPAL));
        UIManager.put("OptionPane.messageFont", new FontUIResource(POLICE_NORMAL));
        UIManager.put("OptionPane.buttonFont", new FontUIResource(POLICE_BOUTON));
        UIManager.put("Label.font", new FontUIResource(POLICE_NORMAL));
        UIManager.put("TextField.font", new FontUIResource(POLICE_CHAMP));
        UIManager.put("ComboBox.font", new FontUIResource(POLICE_CHAMP));
        UIManager.put("Spinner.font", new FontUIResource(POLICE_CHAMP));
        UIManager.put("Button.font", new FontUIResource(POLICE_BOUTON));
        UIManager.put("Menu.font", new FontUIResource(POLICE_LABEL));
        UIManager.put("MenuItem.font", new FontUIResource(POLICE_NORMAL));
        UIManager.put("MenuBar.font", new FontUIResource(POLICE_LABEL));
    }

    // ========================================================================
    // BORDURES
    // ========================================================================

    public static Border creerBordureArrondie(Color couleur, int epaisseur, int rayon) {
        return new CompoundBorder(
                new ArrondiBorder(couleur, epaisseur, rayon),
                new EmptyBorder(2, 2, 2, 2)
        );
    }

    public static Border creerBordureTitre(String titre) {
        Border line = new ArrondiBorder(VERT_CLAIR, 1, 10);
        Border empty = new EmptyBorder(18, 18, 18, 18);
        CompoundBorder cb = new CompoundBorder(line, empty);
        TitledBorder tb = BorderFactory.createTitledBorder(cb, " " + titre + " ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                POLICE_SOUS_TITRE, VERT_FONCE);
        return tb;
    }

    // ========================================================================
    // BOUTONS avec icônes Material Design (PAS d'emojis)
    // ========================================================================

    public static JButton creerBoutonPrimaire(String texte) {
        JButton btn = baseBouton(texte, Color.WHITE, VERT_PRINCIPAL, VERT_MOYEN);
        try { btn.setIcon(extraireIconeDefaut(texte)); } catch (Exception ignored) {}
        return btn;
    }

    public static JButton creerBoutonPrimaire(String texte, Icon icone) {
        JButton btn = baseBouton(texte, Color.WHITE, VERT_PRINCIPAL, VERT_MOYEN);
        btn.setIcon(icone);
        return btn;
    }

    public static JButton creerBoutonDanger(String texte) {
        return baseBouton(texte, Color.WHITE, ROUGE_PRINCIPAL, new Color(239, 83, 80));
    }

    public static JButton creerBoutonDanger(String texte, Icon icone) {
        JButton btn = baseBouton(texte, Color.WHITE, ROUGE_PRINCIPAL, new Color(239, 83, 80));
        btn.setIcon(icone);
        return btn;
    }

    public static JButton creerBoutonInfo(String texte) {
        return baseBouton(texte, Color.WHITE, BLEU_PRINCIPAL, new Color(30, 136, 229));
    }

    public static JButton creerBoutonInfo(String texte, Icon icone) {
        JButton btn = baseBouton(texte, Color.WHITE, BLEU_PRINCIPAL, new Color(30, 136, 229));
        btn.setIcon(icone);
        return btn;
    }

    public static JButton creerBoutonSecondaire(String texte) {
        JButton btn = new JButton(texte);
        btn.setFont(POLICE_BOUTON);
        btn.setForeground(VERT_FONCE);
        btn.setBackground(VERT_TRES_CLAIR);
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setOpaque(true);
        btn.setBorder(new CompoundBorder(
                new ArrondiBorder(VERT_MOYEN, 1, 8),
                new EmptyBorder(8, 14, 8, 14)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setIconTextGap(8);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(VERT_CLAIR);
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(VERT_TRES_CLAIR);
            }
        });
        return btn;
    }

    public static JButton creerBoutonSecondaire(String texte, Icon icone) {
        JButton btn = creerBoutonSecondaire(texte);
        btn.setIcon(icone);
        return btn;
    }

    private static JButton baseBouton(String texte, Color fg, Color bg, Color hover) {
        JButton btn = new JButton(texte);
        btn.setFont(POLICE_BOUTON);
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(9, 16, 9, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setIconTextGap(8);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent evt) { btn.setBackground(bg); }
        });
        return btn;
    }

    private static Icon extraireIconeDefaut(String texte) {
        if (texte.contains("Ajout")) return ajouter();
        if (texte.contains("Modifier")) return modifier();
        if (texte.contains("Supprim")) return supprimer();
        if (texte.contains("Enregistr")) return enregistrer();
        if (texte.contains("Actualis")) return actualiser();
        if (texte.contains("Recherch")) return rechercherBtn();
        if (texte.contains("Afficher")) return afficherTous();
        if (texte.contains("Réinitial") || texte.contains("Init")) return reinitialiser();
        return null;
    }

    // ========================================================================
    // CHAMPS DE SAISIE
    // ========================================================================

    public static JTextField creerChampTexte(int colonnes) {
        JTextField field = new JTextField(colonnes);
        field.setFont(POLICE_CHAMP);
        field.setForeground(TEXTE_PRINCIPAL);
        field.setBorder(new CompoundBorder(
                new ArrondiBorder(BORDE_CLAIRE, 1, 8),
                new EmptyBorder(5, 10, 5, 10)));
        field.setBackground(Color.WHITE);
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 34));
        field.setCaretColor(VERT_PRINCIPAL);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(new CompoundBorder(
                        new ArrondiBorder(VERT_MOYEN, 2, 8),
                        new EmptyBorder(4, 9, 4, 9)));
                field.setBackground(VERT_TRES_CLAIR);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(new CompoundBorder(
                        new ArrondiBorder(BORDE_CLAIRE, 1, 8),
                        new EmptyBorder(5, 10, 5, 10)));
                field.setBackground(Color.WHITE);
            }
        });
        return field;
    }

    @SuppressWarnings("rawtypes")
    public static void styliserComboBox(JComboBox cbo) {
        cbo.setFont(POLICE_CHAMP);
        cbo.setForeground(TEXTE_PRINCIPAL);
        cbo.setBackground(Color.WHITE);
        cbo.setBorder(new CompoundBorder(
                new ArrondiBorder(BORDE_CLAIRE, 1, 8),
                new EmptyBorder(3, 8, 3, 8)));
        cbo.setPreferredSize(new Dimension(cbo.getPreferredSize().width, 34));
        cbo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Object rend = cbo.getRenderer();
        if (rend instanceof JLabel) {
            ((JLabel) rend).setBorder(new EmptyBorder(3, 6, 3, 6));
        }
    }

    // ========================================================================
    // TITRE DE SECTION
    // ========================================================================

    public static JPanel creerTitreSection(Icon icone, String titre, String sousTitre) {
        JPanel panel = new JPanel(new BorderLayout(14, 4));
        panel.setBackground(VERT_TRES_CLAIR);
        panel.setBorder(new EmptyBorder(14, 18, 14, 18));

        JLabel lblIcone = new JLabel(icone);
        lblIcone.setVerticalAlignment(SwingConstants.CENTER);
        lblIcone.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel lblTitre = new JLabel(titre);
        lblTitre.setFont(POLICE_SOUS_TITRE);
        lblTitre.setForeground(VERT_FONCE);
        lblTitre.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(lblTitre);

        if (sousTitre != null && !sousTitre.isEmpty()) {
            JLabel lblSous = new JLabel(sousTitre);
            lblSous.setFont(POLICE_NORMAL);
            lblSous.setForeground(TEXTE_SECONDAIRE);
            lblSous.setAlignmentX(Component.LEFT_ALIGNMENT);
            textPanel.add(Box.createVerticalStrut(3));
            textPanel.add(lblSous);
        }

        panel.add(lblIcone, BorderLayout.WEST);
        panel.add(textPanel, BorderLayout.CENTER);

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.setBorder(new MatteBorder(0, 0, 1, 0, VERT_CLAIR));
        wrap.add(panel, BorderLayout.CENTER);
        return wrap;
    }

    // ========================================================================
    // TABLEAU (correction ROBUSTE du contraste en-tête)
    // ========================================================================

    /**
     * CORRECTION BUG #3 : lisibilité JTableHeader.
     *
     * Cause probable initiale :
     * 1. initialiserUIManager() définissait TableHeader.background/foreground
     *    dans un bloc static, MAIS MainWindow appelait UIManager.setLookAndFeel()
     *    APRÈS, ce qui réinitialise TOUS les UIDefaults (y compris les nôtres).
     * 2. Le renderer BasicTableHeaderUI utilise parfois des couleurs dérivées
     *    (brighter/darker) au lieu des valeurs explicites.
     * 3. Les colonnes créées AVANT l'appel à styliserTableau() avaient un
     *    renderer par défaut qui n'était pas remplacé.
     *
     * Correctif appliqué :
     * - On utilise un renderer personnalisé PLUS FORT : VERT_FONCE (plus sombre
     *   que VERT_PRINCIPAL) + texte BLANC en BOLD + on le force sur TOUTES
     *   les colonnes existantes ET on remplace le defaultRenderer du header
     *   pour les colonnes ajoutées ultérieurement.
     * - On force setBackground/setForeground sur l'objet JTableHeader lui-même.
     * - On ajoute une ligne de séparation 2px VERT_FONCE en bas de l'en-tête
     *   pour bien séparer visuellement titres / données.
     */
    public static void styliserTableau(JTable tableau, JScrollPane scrollPane) {
        tableau.setFont(POLICE_TABLEAU);
        tableau.setForeground(TEXTE_PRINCIPAL);
        tableau.setBackground(Color.WHITE);
        tableau.setRowHeight(30);
        tableau.setGridColor(BORDE_CLAIRE);
        tableau.setIntercellSpacing(new Dimension(0, 0));
        tableau.setShowVerticalLines(false);
        tableau.setShowHorizontalLines(true);
        tableau.setSelectionBackground(VERT_CLAIR);
        tableau.setSelectionForeground(VERT_FONCE);
        tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableau.setShowGrid(false);
        tableau.setAutoCreateRowSorter(true);

        // --- En-tête : CONTRASTE MAXIMAL garanti ---
        JTableHeader header = tableau.getTableHeader();
        header.setFont(POLICE_LABEL);
        header.setOpaque(true);
        header.setBackground(VERT_FONCE);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        header.setReorderingAllowed(true);
        header.setResizingAllowed(true);
        header.setBorder(new MatteBorder(0, 0, 2, 0, new Color(15, 60, 18)));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value,
                        false, false, row, column);
                lbl.setOpaque(true);
                lbl.setBackground(VERT_FONCE);
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font(POLICE_LABEL.getName(), Font.BOLD, POLICE_LABEL.getSize()));
                lbl.setHorizontalAlignment(SwingConstants.LEFT);
                lbl.setBorder(new EmptyBorder(10, 12, 10, 12));
                return lbl;
            }
        };

        int nbColonnes = tableau.getColumnCount();
        for (int i = 0; i < nbColonnes; i++) {
            tableau.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        header.setDefaultRenderer(headerRenderer);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : VERT_TRES_CLAIR);
                    c.setForeground(TEXTE_PRINCIPAL);
                } else {
                    c.setBackground(VERT_CLAIR);
                    c.setForeground(VERT_FONCE);
                }
                setBorder(new EmptyBorder(4, 12, 4, 12));
                return c;
            }
        };
        tableau.setDefaultRenderer(Object.class, cellRenderer);

        scrollPane.setBorder(new CompoundBorder(
                new ArrondiBorder(BORDE_CLAIRE, 1, 10),
                new EmptyBorder(1, 1, 1, 1)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setOpaque(true);
    }

    // ========================================================================
    // TABBEDPANE PERSONNALISÉ (correction contraste onglets TEXTE + ICÔNES)
    // ========================================================================

    /**
     * CORRECTION BUG #2 : onglets Médecins/Visites illisibles quand sélectionnés.
     *
     * CAUSE PRINCIPALE IDENTIFIÉE :
     * L'UI précédente personnalisait paintText() pour changer la couleur du
     * TEXTE selon l'état (normal/survol/sélectionné), MAIS elle ne gérait PAS
     * la couleur de l'ICÔNE du tab. L'icône était fournie par
     * IconManager.ongletMedecin() = VERT_PRINCIPAL, et l'onglet sélectionné
     * a aussi un fond VERT_PRINCIPAL → icône VERT sur fond VERT = invisible.
     *
     * CORRECTIF APPLIQUÉ :
     * 1. On remplace paintIcon() de BasicTabbedPaneUI pour repeindre l'icône
     *    avec la BONNE couleur selon l'état :
     *      - Onglet NORMAL      : icône VERT_PRINCIPAL sur fond gris-vert
     *      - Onglet SURVOLÉ     : icône VERT_FONCE sur fond vert clair
     *      - Onglet SÉLECTIONNÉ : icône BLANCHE sur fond vert principal
     * 2. On garde paintText() avec les mêmes règles pour le texte.
     * 3. On utilise Ikonli FontIcon : si l'icône fournie est une FontIcon on
     *    extrait le glyph et on le redessine avec la nouvelle couleur ; sinon
     *    on laisse l'icône d'origine (sécurité).
     */
    public static void appliquerUIOnglets(JTabbedPane tp) {
        tp.setUI(new BasicTabbedPaneUI() {

            private int indexSurvol = -1;

            @Override
            protected void installDefaults() {
                super.installDefaults();
                tabInsets = new Insets(10, 16, 10, 16);
                selectedTabPadInsets = new Insets(0, 0, 0, 0);
                contentBorderInsets = new Insets(8, 0, 0, 0);
            }

            @Override
            protected void installListeners() {
                super.installListeners();
                MouseAdapter ma = new MouseAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        int idx = tabForCoordinate(tp, e.getX(), e.getY());
                        if (idx != indexSurvol) {
                            indexSurvol = idx;
                            tp.repaint();
                        }
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        indexSurvol = -1;
                        tp.repaint();
                    }
                };
                tp.addMouseMotionListener(ma);
                tp.addMouseListener(ma);
            }

            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement,
                                               int tabIndex, int x, int y, int w, int h,
                                               boolean isSelected) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                Color bg;
                if (isSelected) {
                    bg = ONGLET_SELECTIONNE_FOND;
                } else if (tabIndex == indexSurvol) {
                    bg = ONGLET_SURVOL_FOND;
                } else {
                    bg = ONGLET_NORMAL_FOND;
                }

                g2.setColor(bg);
                int r = 10;
                g2.fillRoundRect(x, y, w, h, r, r);
                g2.setColor(bg);
                g2.fillRect(x, y + h - r, w, r);
                g2.dispose();
            }

            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement,
                                          int tabIndex, int x, int y, int w, int h,
                                          boolean isSelected) {
            }

            @Override
            protected void paintFocusIndicator(Graphics g, int tabPlacement,
                                               Rectangle[] rects, int tabIndex,
                                               Rectangle iconRect, Rectangle textRect,
                                               boolean isSelected) {
            }

            // ----------------------------------------------------------
            // CORRECTION : couleur ICÔNE adaptée à l'état du tab
            // ----------------------------------------------------------
            @Override
            protected void paintIcon(Graphics g, int tabPlacement, int tabIndex,
                                     Icon icon, Rectangle iconRect, boolean isSelected) {
                if (icon == null) return;

                boolean isSurvol = (tabIndex == indexSurvol);

                Color couleurIcone;
                if (isSelected) {
                    couleurIcone = ONGLET_SELECTIONNE_ICONE;   // BLANC sur fond vert
                } else if (isSurvol) {
                    couleurIcone = ONGLET_SURVOL_ICONE;        // VERT_FONCE
                } else {
                    couleurIcone = ONGLET_NORMAL_ICONE;        // VERT_PRINCIPAL
                }

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                if (icon instanceof FontIcon) {
                    FontIcon fi = (FontIcon) icon;
                    FontIcon recolored = FontIcon.of(fi.getIkon(), fi.getIconWidth(), couleurIcone);
                    recolored.paintIcon(tabPane, g2, iconRect.x, iconRect.y);
                } else {
                    icon.paintIcon(tabPane, g2, iconRect.x, iconRect.y);
                }
                g2.dispose();
            }

            @Override
            protected void paintText(Graphics g, int tabPlacement, Font font,
                                     FontMetrics metrics, int tabIndex, String title,
                                     Rectangle textRect, boolean isSelected) {
                g.setFont(POLICE_ONGLET);

                Color fg;
                if (isSelected) {
                    fg = ONGLET_SELECTIONNE_TEXTE;
                } else if (tabIndex == indexSurvol) {
                    fg = ONGLET_SURVOL_TEXTE;
                } else {
                    fg = ONGLET_NORMAL_TEXTE;
                }

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(fg);
                g2.drawString(title, textRect.x, textRect.y + metrics.getAscent());
                g2.dispose();
            }

            @Override
            protected LayoutManager createLayoutManager() {
                return new TabbedPaneLayout() {
                    @Override
                    public void layoutContainer(Container parent) {
                        super.layoutContainer(parent);
                    }
                };
            }
        });
    }

    // ========================================================================
    // BORDURE ARRONDI personnalisée
    // ========================================================================

    private static class ArrondiBorder extends AbstractBorder {
        private final Color color;
        private final int thickness;
        private final int radius;

        ArrondiBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(this.color);
            g2.setStroke(new BasicStroke(this.thickness));
            g2.drawRoundRect(x + this.thickness / 2,
                             y + this.thickness / 2,
                             w - this.thickness,
                             h - this.thickness,
                             this.radius, this.radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.thickness, this.thickness,
                              this.thickness, this.thickness);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = this.thickness;
            return insets;
        }
    }
}
