package presentacion;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import interfaces.Fabrica;
import interfaces.IControlador;

public class MainWindow implements ActionListener {

    private IControlador controlador = Fabrica.getInstancia().getIControlador();

    private JFrame window;
    private JPanel button_panel;
    private JPanel label_panel;

    // Para el bar
    private JMenuBar mainBar;
    private JMenu GestionUsuario;
    private JMenu GestionMateriales;
    private JMenu GestionPrestamos;
    private JMenu GestionHistorial;

    private JMenuItem agregar_bibliotecario;
    private JMenuItem agregar_lector;

    // Para el menu
    private JButton Usuario_button;
    private JButton Material_button;
    private JMenuItem agregar_libro;
    private JMenuItem agregar_articulo;
    private JMenuItem agregar_prestamo;
    private JMenuItem modificar_prestamo;
    private JMenuItem historial_prestamo;

    // Para top panel
    private JButton Prestamos_button;
    private JButton Historial_button;
    private JLabel title;



    public MainWindow() {
        initialize();
        run_bar();
        run_menu();
    }

    private void initialize(){
        window = new JFrame();
        window.setTitle("PAP2025");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(800,500);
        window.setLocationRelativeTo(null);
        
    }

    private void run_bar() {
        // Esta funcion se podria modulizar
        mainBar = new JMenuBar();

        GestionUsuario = new JMenu("Gestion Usuario");
        GestionMateriales = new JMenu("Gestion Material");
        GestionPrestamos = new JMenu("Gestion Prestamos");
        GestionHistorial = new JMenu("Gestion Historial");

        // Items de usuarios, materiales, prestamos, historial
        // Usuario
        agregar_bibliotecario = new JMenuItem("Agregar Bibliotecario");
        agregar_lector = new JMenuItem("Agregar Lector");

        // Materiales
        agregar_libro = new JMenuItem("Agregar Libro");
        agregar_articulo = new JMenuItem("Agregar Articulo");

        // Prestamos
        agregar_prestamo = new JMenuItem("Agregar Prestamo");
        modificar_prestamo = new JMenuItem("Modificar Prestamo");

        // Historial
        historial_prestamo = new JMenuItem("Historial de Prestamos");

        // Se agrega a la barra
        GestionUsuario.add(agregar_bibliotecario);
        GestionUsuario.add(agregar_lector);

        GestionMateriales.add(agregar_libro);
        GestionMateriales.add(agregar_articulo);

        GestionPrestamos.add(agregar_prestamo);
        GestionPrestamos.add(modificar_prestamo);

        GestionHistorial.add(historial_prestamo);

        mainBar.add(GestionUsuario);
        mainBar.add(GestionMateriales);
        mainBar.add(GestionPrestamos);
        mainBar.add(GestionHistorial);
        
        window.setJMenuBar(mainBar);

        // Event listeners para lanzar la ventana
        agregar_bibliotecario.addActionListener(this);
        agregar_lector.addActionListener(this);
        agregar_libro.addActionListener(this);
        agregar_articulo.addActionListener(this);
        agregar_prestamo.addActionListener(this);
        modificar_prestamo.addActionListener(this);
        historial_prestamo.addActionListener(this);
    }

    private void run_menu(){
        Usuario_button = new JButton("Gestion Usuario");
        Material_button = new JButton("Material_button");
        Prestamos_button = new JButton("Prestamos_button");
        Historial_button = new JButton("Historial_button");

        button_panel = new JPanel();
        label_panel = new JPanel();

        title = new JLabel("Programacion de Aplicacion - Entrega 1 - 2025");
        label_panel.add(title, BorderLayout.CENTER);

        button_panel.add(Usuario_button, BorderLayout.CENTER);        
        button_panel.add(Material_button, BorderLayout.CENTER);
        button_panel.add(Prestamos_button, BorderLayout.CENTER);
        button_panel.add(Historial_button, BorderLayout.CENTER); 
        
        window.add(label_panel, BorderLayout.NORTH);
        window.add(button_panel, BorderLayout.CENTER);
    }

    public void show() {
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JMenuItem){
            JMenuItem item = (JMenuItem) e.getSource();
            String text = item.getText();
            System.out.println(text);
            // Aqui va donde se abren las ventanas
            switch(text){
                case "Agregar Bibliotecario":
                    AgregarBibliotecarioWindows agregarBiblio = new AgregarBibliotecarioWindows(controlador);
                    agregarBiblio.show();
                    break;
                case "Agregar Lector": 
                    AgregarLectorWindow agregarLect = new AgregarLectorWindow(controlador);
                    agregarLect.show();
                    break;
                case "Agregar Libro":
                    AgregarLibroWindow agregarLibro = new AgregarLibroWindow(controlador);
                    agregarLibro.show();
                    break;
                case "Agregar Articulo":
                    AgregarArticuloWindow agregarArticulo = new AgregarArticuloWindow();
                    agregarArticulo.show();
                    break;
            }
        }
    }

}
