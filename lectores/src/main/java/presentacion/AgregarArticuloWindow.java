package presentacion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import interfaces.IControlador;
import excepciones.ExisteUsuarioException;
import java.sql.Date;

public class AgregarArticuloWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private IControlador icon;

    private JTextField textFieldId;
    private JTextField textFieldPeso;
    private JTextField textFieldDescripcion;
    private JTextField textFieldDimensiones;
    private JTextField textFieldFechaIngreso;

    public AgregarArticuloWindow(IControlador icon) {
        this.icon = icon;
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Alta de un Artículo");
        setBounds(100, 100, 450, 350);
        getContentPane().setLayout(null);
        
        JLabel lblId = new JLabel("ID");
        lblId.setBounds(47, 35, 70, 15);
        getContentPane().add(lblId);
        
        JLabel lblPeso = new JLabel("PESO (KG)");
        lblPeso.setBounds(47, 65, 100, 15);
        getContentPane().add(lblPeso);
        
        JLabel lblDescripcion = new JLabel("DESCRIPCION");
        lblDescripcion.setBounds(47, 95, 100, 15);
        getContentPane().add(lblDescripcion);
        
        JLabel lblDimensiones = new JLabel("DIMENSIONES");
        lblDimensiones.setBounds(47, 125, 100, 15);
        getContentPane().add(lblDimensiones);
        
        JLabel lblFechaIngreso = new JLabel("FECHA INGRESO");
        lblFechaIngreso.setBounds(47, 155, 100, 15);
        getContentPane().add(lblFechaIngreso);
        
        textFieldId = new JTextField();
        textFieldId.setBounds(135, 33, 114, 19);
        getContentPane().add(textFieldId);
        textFieldId.setColumns(10);
        
        textFieldPeso = new JTextField();
        textFieldPeso.setBounds(135, 63, 114, 19);
        getContentPane().add(textFieldPeso);
        textFieldPeso.setColumns(10);
        
        textFieldDescripcion = new JTextField();
        textFieldDescripcion.setBounds(135, 93, 114, 19);
        getContentPane().add(textFieldDescripcion);
        textFieldDescripcion.setColumns(10);
        
        textFieldDimensiones = new JTextField();
        textFieldDimensiones.setBounds(135, 123, 114, 19);
        getContentPane().add(textFieldDimensiones);
        textFieldDimensiones.setColumns(10);
        
        textFieldFechaIngreso = new JTextField();
        textFieldFechaIngreso.setBounds(135, 153, 114, 19);
        getContentPane().add(textFieldFechaIngreso);
        textFieldFechaIngreso.setColumns(10);
        
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarArticuloAceptarActionPerformed(e);
            }
        });
        btnAceptar.setBounds(65, 250, 117, 25);
        getContentPane().add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarArticuloCancelarActionPerformed(e);
            }
        });
        btnCancelar.setBounds(247, 250, 117, 25);
        getContentPane().add(btnCancelar);
    }
    
    protected void agregarArticuloCancelarActionPerformed(ActionEvent arg0) {
        limpiarFormulario();
        setVisible(false);
    }
    
    protected void agregarArticuloAceptarActionPerformed(ActionEvent arg0) {
        if (checkFormulario()) {
            try {
                String id = this.textFieldId.getText();
                String pesoStr = this.textFieldPeso.getText();
                String descripcion = this.textFieldDescripcion.getText();
                String dimensiones = this.textFieldDimensiones.getText();
                String fechaIngresoStr = this.textFieldFechaIngreso.getText();
                
                // Convertir peso a float
                float peso = Float.parseFloat(pesoStr);
                
                // Convertir fechaIngreso a Date (formato: yyyy-MM-dd)
                Date fechaIngreso = Date.valueOf(fechaIngresoStr);
                
                // Registrar el artículo
                //this.icon.agregarNuevoArticulo(id, fechaIngreso, peso, descripcion, dimensiones);
                
                JOptionPane.showMessageDialog(this, "El Artículo se ha creado con éxito", "Agregar Artículo",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al procesar los datos: " + e.getMessage(), "Agregar Artículo",
                        JOptionPane.ERROR_MESSAGE);
            }
            limpiarFormulario();
            setVisible(false);
        }
    }
    
    private boolean checkFormulario() {
        String id = this.textFieldId.getText();
        String peso = this.textFieldPeso.getText();
        String descripcion = this.textFieldDescripcion.getText();
        String dimensiones = this.textFieldDimensiones.getText();
        String fechaIngreso = this.textFieldFechaIngreso.getText();
        
        if (id.isEmpty() || peso.isEmpty() || descripcion.isEmpty() || dimensiones.isEmpty() || fechaIngreso.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Agregar Artículo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar que peso sea un número positivo
        try {
            float pesoFloat = Float.parseFloat(peso);
            if (pesoFloat <= 0) {
                JOptionPane.showMessageDialog(this, "El peso debe ser mayor a 0", "Agregar Artículo",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El peso debe ser un número válido", "Agregar Artículo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar formato de fecha (yyyy-MM-dd)
        try {
            Date.valueOf(fechaIngreso);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener formato yyyy-MM-dd", "Agregar Artículo",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        textFieldId.setText("");
        textFieldPeso.setText("");
        textFieldDescripcion.setText("");
        textFieldDimensiones.setText("");
        textFieldFechaIngreso.setText("");
    }
}
