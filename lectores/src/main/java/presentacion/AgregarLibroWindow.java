package presentacion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import interfaces.IControlador;

public class AgregarLibroWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private IControlador icon;

    private JTextField textFieldId;
    private JTextField textFieldTitulo;
    private JTextField textFieldCantPaginas;


    public AgregarLibroWindow(IControlador icon) {
        this.icon = icon;
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Alta de un Libro");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(null);
        
        JLabel lblId = new JLabel("ID");
        lblId.setBounds(47, 35, 70, 15);
        getContentPane().add(lblId);
        
        JLabel lblTitulo = new JLabel("TITULO");
        lblTitulo.setBounds(47, 65, 70, 15);
        getContentPane().add(lblTitulo);
        
        JLabel lblCantPaginas = new JLabel("CANT. PAGINAS");
        lblCantPaginas.setBounds(47, 95, 100, 15);
        getContentPane().add(lblCantPaginas);
        

        
        textFieldId = new JTextField();
        textFieldId.setBounds(135, 33, 114, 19);
        getContentPane().add(textFieldId);
        textFieldId.setColumns(10);
        
        textFieldTitulo = new JTextField();
        textFieldTitulo.setBounds(135, 63, 114, 19);
        getContentPane().add(textFieldTitulo);
        textFieldTitulo.setColumns(10);
        
        textFieldCantPaginas = new JTextField();
        textFieldCantPaginas.setBounds(135, 93, 114, 19);
        getContentPane().add(textFieldCantPaginas);
        textFieldCantPaginas.setColumns(10);
        

        
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarLibroAceptarActionPerformed(e);
            }
        });
        btnAceptar.setBounds(65, 200, 117, 25);
        getContentPane().add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarLibroCancelarActionPerformed(e);
            }
        });
        btnCancelar.setBounds(247, 200, 117, 25);
        getContentPane().add(btnCancelar);
    }
    
    protected void agregarLibroCancelarActionPerformed(ActionEvent arg0) {
        limpiarFormulario();
        setVisible(false);
    }
    
    protected void agregarLibroAceptarActionPerformed(ActionEvent arg0) {
        if (checkFormulario()) {
            try {
                String id = this.textFieldId.getText();
                String titulo = this.textFieldTitulo.getText();
                String cantPaginasStr = this.textFieldCantPaginas.getText();
                
                // Convertir cantPaginas a int
                int cantPaginas = Integer.parseInt(cantPaginasStr);
                
                // Registrar el libro
                this.icon.agregarNuevoLibro(id, titulo, cantPaginas);
                
                JOptionPane.showMessageDialog(this, "El Libro se ha creado con éxito", "Agregar Libro",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al procesar los datos: " + e.getMessage(), "Agregar Libro",
                        JOptionPane.ERROR_MESSAGE);
            }
            limpiarFormulario();
            setVisible(false);
        }
    }
    
    private boolean checkFormulario() {
        String id = this.textFieldId.getText();
        String titulo = this.textFieldTitulo.getText();
        String cantPaginas = this.textFieldCantPaginas.getText();
        
        if (id.isEmpty() || titulo.isEmpty() || cantPaginas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Agregar Libro",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar que cantPaginas sea un número
        try {
            Integer.parseInt(cantPaginas);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad de páginas debe ser un número válido", "Agregar Libro",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        

        
        return true;
    }
    
    private void limpiarFormulario() {
        textFieldId.setText("");
        textFieldTitulo.setText("");
        textFieldCantPaginas.setText("");
    }
}       