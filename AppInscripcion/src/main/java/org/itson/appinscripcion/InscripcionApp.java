package org.itson.appinscripcion;

import javax.swing.*;

// Importamos todas las piezas de nuestro caso de uso
import Entidades.CatalogoCursos;
import MVC.ModeloInscripcion;
import MVC.ControladorInscripcion;
import MVC.ModeloInscripcion;
import MVC.PantallaInscripcion;

public class InscripcionApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // 1. (Mocks)
            CatalogoCursos catalogo = new CatalogoCursos();
            
            // 2. MVC
            ModeloInscripcion modelo = new ModeloInscripcion(catalogo);
            ControladorInscripcion controlador = new ControladorInscripcion(modelo);
            PantallaInscripcion pantalla = new PantallaInscripcion(controlador, modelo);

            // 3. Configurar la Ventana Principal
            JFrame frame = new JFrame("Inscripción de Clases - MVC Clásico");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(pantalla);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
