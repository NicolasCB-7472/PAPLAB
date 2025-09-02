package presentacion;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import datatypes.DtArticulo;
import datatypes.DtLibro;
import datatypes.DtMaterial;
import interfaces.IControlador;
public class DonacionesRegistradasWindow {
    
    private IControlador controlador;
    private JFrame window;
    private JTextArea textArea;
    
    public DonacionesRegistradasWindow(IControlador controlador) {
        this.controlador = controlador;
        initialize();
        cargarMateriales();
    }
    
    private void initialize() {
        window = new JFrame();
        window.setTitle("Donaciones Registradas");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(600, 400);
        window.setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Título
        JLabel titleLabel = new JLabel("Materiales Registrados en el Sistema", JLabel.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(16.0f));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Área de texto con scroll
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(textArea.getFont().deriveFont(12.0f));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        window.add(mainPanel);
    }
    
    private void cargarMateriales() {
        try {
            ArrayList<DtMaterial> materiales = controlador.consultarDonacionesRegistradas();
            
            if (materiales.isEmpty()) {
                textArea.setText("No hay materiales registrados en el sistema.");
                return;
            }
            
            StringBuilder contenido = new StringBuilder();
            contenido.append("LISTADO DE MATERIALES REGISTRADOS\n");
            contenido.append("================================\n\n");
            
            for (DtMaterial material : materiales) {
                contenido.append("ID: ").append(material.getId()).append("\n");
                contenido.append("Fecha de ingreso: ").append(material.getFechaIngreso()).append("\n");
                if(material instanceof DtLibro){
                    DtLibro libro  = (DtLibro) material;
                    contenido.append("Titulo: ").append(libro.getTitulo()).append("\n");
                    contenido.append("Cantidad de paginas: ").append(libro.getCantPaginas()).append("\n");
                }
                else if(material instanceof DtArticulo){
                    DtArticulo art = (DtArticulo) material;
                    contenido.append("Descripcion: ").append(art.getDescripcion()).append("\n");
                    contenido.append("Dimensiones: ").append(art.getDimensiones()).append("\n");
                    contenido.append("Peso: ").append(art.getPeso()).append("\n");
                }
                contenido.append("----------------------------------------\n");
            }
            
            textArea.setText(contenido.toString());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(window, 
                "Error al consultar materiales: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            window.dispose();
        }
    }
    
    public void show() {
        window.setVisible(true);
    }
    
    public void setVisible(boolean visible) {
        window.setVisible(visible);
    }
}
