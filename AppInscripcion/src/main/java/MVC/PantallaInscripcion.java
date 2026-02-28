package MVC;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Importaciones cruciales para la arquitectura
import Contratos.IModeloInscripcion;
import Contratos.IVista;
import MVC.ControladorInscripcion;
import Entidades.Curso;

public class PantallaInscripcion extends JPanel implements IVista {
    // MVC Dependencias
    private ControladorInscripcion controlador;
    private IModeloInscripcion modeloLectura;

    // Componentes de Presentación Swing
    private JList<Curso> lstDisponibles;
    private JList<Curso> lstInscritos;
    private DefaultListModel<Curso> modDisponibles;
    private DefaultListModel<Curso> modInscritos;
    private JButton btnAgregar;
    private JButton btnFinalizar;
    private JLabel lblTotal;
    
    // Layout y Paneles Principales
    private CardLayout cardLayout;
    private JPanel panelSeleccion;
    private JPanel panelFichaPago;

    public PantallaInscripcion(ControladorInscripcion ctrl, IModeloInscripcion mod) {
        this.controlador = ctrl;
        this.modeloLectura = mod;
        
        // La vista se suscribe al modelo al nacer
        this.modeloLectura.suscribir(this);

        inicializarComponentes();
        configurarListeners();
        
        // Simular el primer "update" para cargar la vista inicial
        this.update(this.modeloLectura);
    }

    private void inicializarComponentes() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        //VISTA 1: SELECCIÓN DE CURSOS
        panelSeleccion = new JPanel(new BorderLayout(20, 20));
        panelSeleccion.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelSeleccion.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Selección de Cursos");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        panelSeleccion.add(lblTitulo, BorderLayout.NORTH);

        JPanel pnlIzquierdo = new JPanel(new BorderLayout(10, 10));
        pnlIzquierdo.setBackground(Color.WHITE);
        pnlIzquierdo.setBorder(BorderFactory.createTitledBorder("Cursos Disponibles"));
        
        modDisponibles = new DefaultListModel<>();
        lstDisponibles = new JList<>(modDisponibles);
        lstDisponibles.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lstDisponibles.setFixedCellHeight(40);
        
        btnAgregar = new JButton("Agregar Curso Seleccionado ->");
        btnAgregar.setBackground(new Color(41, 98, 255));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setFont(new Font("SansSerif", Font.BOLD, 14));

        pnlIzquierdo.add(new JScrollPane(lstDisponibles), BorderLayout.CENTER);
        pnlIzquierdo.add(btnAgregar, BorderLayout.SOUTH);

        JPanel pnlDerecho = new JPanel(new BorderLayout(10, 10));
        pnlDerecho.setBackground(new Color(248, 249, 250));
        pnlDerecho.setBorder(BorderFactory.createTitledBorder("Cursos Inscritos"));
        pnlDerecho.setPreferredSize(new Dimension(350, 0));

        modInscritos = new DefaultListModel<>();
        lstInscritos = new JList<>(modInscritos);
        lstInscritos.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lstInscritos.setFixedCellHeight(40);

        JPanel pnlTotal = new JPanel(new GridLayout(2, 1, 10, 10));
        pnlTotal.setBackground(new Color(248, 249, 250));
        pnlTotal.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        lblTotal = new JLabel("Monto a pagar: $0.00");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setForeground(new Color(41, 98, 255));

        btnFinalizar = new JButton("Finalizar Inscripción");
        btnFinalizar.setBackground(new Color(41, 98, 255));
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setFont(new Font("SansSerif", Font.BOLD, 14));

        pnlTotal.add(lblTotal);
        pnlTotal.add(btnFinalizar);

        pnlDerecho.add(new JScrollPane(lstInscritos), BorderLayout.CENTER);
        pnlDerecho.add(pnlTotal, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlIzquierdo, pnlDerecho);
        splitPane.setResizeWeight(0.6);
        splitPane.setBorder(null);
        panelSeleccion.add(splitPane, BorderLayout.CENTER);

        // --- VISTA 2: FICHA DE PAGO ---
        panelFichaPago = new JPanel(new BorderLayout());
        panelFichaPago.setBackground(Color.WHITE);

        add(panelSeleccion, "SELECCION");
        add(panelFichaPago, "FICHA");
    }

    private void configurarListeners() {
        btnAgregar.addActionListener(e -> {
            Curso seleccionado = lstDisponibles.getSelectedValue();
            if (seleccionado != null) {
                controlador.onAgregarCurso(seleccionado.getIdCurso());
            }
        });

        btnFinalizar.addActionListener(e -> {
            controlador.onFinalizarInscripcion();
        });
    }

    @Override
    public void update(IModeloInscripcion modelo) {
        modDisponibles.clear();
        modInscritos.clear();

        for (Curso c : modelo.getCursosDisponibles()) {
            modDisponibles.addElement(c);
        }

        for (Curso c : modelo.getCursosInscritos()) {
            modInscritos.addElement(c);
        }

        lblTotal.setText(String.format("Monto a pagar: $%.2f", modelo.getTotal()));

        if (modelo.isFinalizada()) {
            mostrarFichaPago();
        }
    }

    private void mostrarFichaPago() {
        panelFichaPago.removeAll();
        panelFichaPago.setBorder(new EmptyBorder(40, 40, 40, 40));

        JPanel recibo = new JPanel(new BorderLayout(20, 20));
        recibo.setBackground(Color.WHITE);
        recibo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

        JLabel lblHeader = new JLabel("Ficha de Pago Confirmada");
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblHeader.setForeground(new Color(46, 125, 50));
        lblHeader.setBorder(new EmptyBorder(20, 20, 20, 20));
        recibo.add(lblHeader, BorderLayout.NORTH);

        DefaultListModel<String> modRecibo = new DefaultListModel<>();
        for (Curso c : modeloLectura.getCursosInscritos()) {
            modRecibo.addElement(String.format("   %s   -   %s   ........   $%.2f", c.getIdCurso(), c.getNombre(), c.getCosto()));
        }
        JList<String> lstRecibo = new JList<>(modRecibo);
        lstRecibo.setFont(new Font("Monospaced", Font.PLAIN, 16));
        lstRecibo.setEnabled(false);
        recibo.add(new JScrollPane(lstRecibo), BorderLayout.CENTER);

        JLabel lblTotalFinal = new JLabel(String.format("TOTAL GENERAL: $%.2f   ", modeloLectura.getTotal()));
        lblTotalFinal.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTotalFinal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalFinal.setBorder(new EmptyBorder(20, 20, 20, 20));
        recibo.add(lblTotalFinal, BorderLayout.SOUTH);

        panelFichaPago.add(recibo, BorderLayout.CENTER);
        
        JButton btnSalir = new JButton("Finalizar y Salir");
        btnSalir.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSalir.addActionListener(e -> System.exit(0));
        JPanel pnlBotones = new JPanel();
        pnlBotones.setBackground(Color.WHITE);
        pnlBotones.add(btnSalir);
        panelFichaPago.add(pnlBotones, BorderLayout.SOUTH);

        cardLayout.show(this, "FICHA");
        revalidate();
        repaint();
    }
}
