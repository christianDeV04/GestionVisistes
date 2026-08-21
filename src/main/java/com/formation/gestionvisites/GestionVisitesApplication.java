package com.formation.gestionvisites;

import com.formation.gestionvisites.services.MedecinService;
import com.formation.gestionvisites.services.PatientService;
import com.formation.gestionvisites.services.VisiterService;
import com.formation.gestionvisites.ui.MainWindow;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class GestionVisitesApplication {

	public static void main(String[] args) {

		System.setProperty("java.awt.headless", "false");

		ConfigurableApplicationContext context = new SpringApplicationBuilder(GestionVisitesApplication.class)
				.headless(false)
				.run(args);

		MedecinService medecinService = context.getBean(MedecinService.class);
		PatientService patientService = context.getBean(PatientService.class);
		VisiterService visiterService = context.getBean(VisiterService.class);

		SwingUtilities.invokeLater(() -> {
			try {
				MainWindow mainWindow = new MainWindow(medecinService, patientService, visiterService);
				mainWindow.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"Erreur lors du lancement de l'application: " + e.getMessage(),
						"Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
