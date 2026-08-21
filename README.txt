
GESTION DES VISITES - CENTRE MEDICAL
=====================================

PROJET 8: Gestion des visites dans un centre médical
=====================================================

CONTEXTE TECHNIQUE
------------------
- Backend : Spring Boot 4.1.0
- ORM : Spring Data JPA (Hibernate)
- Base de données : PostgreSQL (gestion_visites)
- Client : Application desktop Java Swing
- Build tool : Maven
- Java : Version 25

MODELE DE DONNEES
-----------------

MEDECIN(codemed [PK, alphanumérique], Nom, Prenom, Grade)
PATIENT(codepat [PK, alphanumérique], Nom, Prenom, Sexe, Adresse)
VISITER(codemed [PK,FK], codepat [PK,FK], date)

Contraintes:
- codemed et codepat sont des clés primaires alphanumériques (String, 2 à 20 caractères)
- VISITER est une table d'association avec clé primaire composite (codemed + codepat)
- Relation Many-to-Many entre MEDECIN et PATIENT avec attribut "date"
- Sexe : type énuméré (M/F)
- date : type LocalDate (java.time)
- Grade : type énuméré (MEDECIN_GENERALISTE, SPECIALISTE, CHEF_DE_CLINIQUE, PROFESSEUR, INTERNE, EXTERNE)
- Noms/prénoms : chaînes avec longueur bornée (50 caractères)

STRUCTURE DU PROJET
-------------------

src/main/java/com/formation/gestionvisites/
├── GestionVisitesApplication.java      # Classe principale (main)
├── entities/
│   ├── Sexe.java                         # Enum M/F
│   ├── Grade.java                      # Enum grades médecins
│   ├── Medecin.java                  # Entité MEDECIN
│   ├── Patient.java                  # Entité PATIENT
│   ├── Visiter.java                  # Entité VISITER
│   └── VisiterId.java                # Clé composite pour VISITER
├── repositories/
│   ├── MedecinRepository.java         # Repository Medecin
│   ├── PatientRepository.java       # Repository Patient
│   └── VisiterRepository.java      # Repository Visiter
├── services/
│   ├── MedecinService.java           # Interface service Medecin
│   ├── MedecinServiceImpl.java    # Implémentation Medecin
│   ├── PatientService.java       # Interface service Patient
│   ├── PatientServiceImpl.java   # Implémentation Patient
│   ├── VisiterService.java      # Interface service Visiter
│   └── VisiterServiceImpl.java  # Implémentation Visiter
└── ui/
    ├── MainWindow.java               # Fenêtre principale avec JTabbedPane
    ├── MedecinPanel.java           # Onglet Médecins
    ├── PatientPanel.java           # Onglet Patients
    └── VisiterPanel.java           # Onglet Visites

src/main/resources/
└── application.properties  # Configuration Spring + BDD

FONCTIONNALITES
----------------

Pour MEDECIN et PATIENT :
- Ajouter un enregistrement
- Modifier un enregistrement
- Supprimer un enregistrement
- Lister tous les enregistrements (JTable)
- Rechercher un patient par son code OU par son nom (recherche partielle)

Pour VISITER :
- Enregistrer une visite (associer médecin + patient + date)
- Modifier une visite (changer la date)
- Supprimer une visite
- Lister l'historique des visites avec noms lisibles (médecin et patient)

COMMENT LANCER LE PROJET
-----------------------

1. PREREQUIS
   - Installer PostgreSQL (port 5432)
   - Créer la base de données "gestion_visites" dans PostgreSQL :
     CREATE DATABASE gestion_visites;
   - Installer JDK 25 ou supérieur

2. CONFIGURATION
   - Vérifier le fichier src/main/resources/application.properties :
     * URL de connexion : jdbc:postgresql://localhost:5432/gestion_visites
     * Utilisateur : postgres
     * Mot de passe : admin
   - Adapter identifiants PostgreSQL dans application.properties si nécessaire

3. COMPILATION
   - Ouvrir un terminal dans le dossier du projet
   - Exécuter : mvn clean install

4. EXECUTION
   - Avec Maven : mvn spring-boot:run  (la configuration dans pom.xml définit -Djava.awt.headless=false)
   - OU avec JAR : java -Djava.awt.headless=false -jar target/gestion-visites-0.0.1-SNAPSHOT.jar
   - OU depuis un IDE : exécuter la classe com.formation.gestionvisites.GestionVisitesApplication
     (si erreur HeadlessException, ajouter -Djava.awt.headless=false dans les VM Options de la run configuration)

IMPORTANT - ERREUR HeadlessException :
---------------------------------------
Si vous obtenez l'erreur java.awt.HeadlessException :
→ C'est que Java est en mode "headless" (sans environnement graphique).
→ Solution 1 : Lancer avec mvn spring-boot:run (configuré dans pom.xml)
→ Solution 2 : Ajouter l'argument JVM : -Djava.awt.headless=false
→ Solution 3 : Vérifiez que vous n'exécutez pas dans un terminal sans affichage (SSH sans X11, serveur sans GUI).

LANCEMENT D'UNE APPLICATION SWING DANS SPRING BOOT
-----------------------------------------------------

Pour lancer une application Swing dans un contexte Spring Boot :

1. Désactiver le mode headless AVANT le démarrage :
   System.setProperty("java.awt.headless", "false");

2. Démarrer le contexte Spring avec SpringApplicationBuilder (et .headless(false)) :
   new SpringApplicationBuilder(Application.class).headless(false).run(args);

3. Récupérer les beans Spring nécessaires (services) via context.getBean()

4. Lancer l'interface Swing dans l'Event Dispatch Thread (EDT) :
   SwingUtilities.invokeLater(() -> {
       // Création et affichage de la fenêtre Swing
   });

Pourquoi SwingUtilities.invokeLater() ?
- Toutes les modifications d'UI Swing doivent être exécutées dans l'EDT
- Spring Boot s'exécute dans le thread principal
- invokeLater() garantit que l'UI est créée dans le bon thread

Pourquoi désactiver le mode headless ?
- Par défaut Spring Boot et certains environnements activent java.awt.headless=true
- Ce mode empêche toute création de fenêtres/frames AWT/Swing
- Il faut explicitement le désactiver avant toute interaction avec AWT

EXEMPLES DE DONNEES (à saisir dans l'interface)
---------------------------------------------------

Médecins :
- Code : MED001, Nom : DUPONT, Prénom : Jean, Grade : MEDECIN_GENERALISTE
- Code : MED002, Nom : MARTIN, Prénom : Sophie, Grade : SPECIALISTE

Patients :
- Code : PAT001, Nom : DURAND, Prénom : Paul, Sexe : M, Adresse : 12 rue des Fleurs
- Code : PAT002, Nom : MOREAU, Prénom : Marie, Sexe : F, Adresse : 8 avenue des Champs
