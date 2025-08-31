package presentacion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import interfaces.IControlador;
import excepciones.ExisteUsuarioException;
import datatypes.Zona;
import java.sql.Date;

public class AgregarLectorWindow extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private IControlador icon;
    
    private JTextField textFieldNombre;
    private JTextField textFieldEmail;
    private JTextField textFieldApellido;
    private JTextField textFieldDireccion;
    private JComboBox<String> comboBoxZona;

    public AgregarLectorWindow(IControlador icon) {
        this.icon = icon;
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Alta de un Lector");
        setBounds(100, 100, 450, 350);
        getContentPane().setLayout(null);
        
        JLabel lblNombre = new JLabel("NOMBRE");
        lblNombre.setBounds(47, 65, 70, 15);
        getContentPane().add(lblNombre);
        
        JLabel lblApellido = new JLabel("APELLIDO");
        lblApellido.setBounds(47, 95, 70, 15);
        getContentPane().add(lblApellido);
        
        JLabel lblEmail = new JLabel("EMAIL");
        lblEmail.setBounds(47, 125, 70, 15);
        getContentPane().add(lblEmail);
        
        JLabel lblDireccion = new JLabel("DIRECCION");
        lblDireccion.setBounds(47, 155, 70, 15);
        getContentPane().add(lblDireccion);
        
        JLabel lblZona = new JLabel("ZONA");
        lblZona.setBounds(47, 185, 70, 15);
        getContentPane().add(lblZona);
        
        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(135, 63, 114, 19);
        getContentPane().add(textFieldNombre);
        textFieldNombre.setColumns(10);
        
        textFieldApellido = new JTextField();
        textFieldApellido.setBounds(135, 93, 114, 19);
        getContentPane().add(textFieldApellido);
        textFieldApellido.setColumns(10);
        
        textFieldEmail = new JTextField();
        textFieldEmail.setBounds(135, 123, 114, 19);
        getContentPane().add(textFieldEmail);
        textFieldEmail.setColumns(10);
        
        textFieldDireccion = new JTextField();
        textFieldDireccion.setBounds(135, 153, 114, 19);
        getContentPane().add(textFieldDireccion);
        textFieldDireccion.setColumns(10);
        
        String[] zonas = {"", "BIBLIOTECA_CENTRAL", "SUCURSAL_ESTE", "SUCURSAL_OESTE", "BIBLIOTECA_INFANTIL", "ARCHIVO_GENERAL"};
        comboBoxZona = new JComboBox<>(zonas);
        comboBoxZona.setBounds(135, 183, 114, 19);
        getContentPane().add(comboBoxZona);
        
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarLectorAceptarActionPerformed(e);
            }
        });
        btnAceptar.setBounds(65, 250, 117, 25);
        getContentPane().add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarLectorCancelarActionPerformed(e);
            }
        });
        btnCancelar.setBounds(247, 250, 117, 25);
        getContentPane().add(btnCancelar);
    }
    
    protected void agregarLectorCancelarActionPerformed(ActionEvent arg0) {
        limpiarFormulario();
        setVisible(false);
    }
    
    protected void agregarLectorAceptarActionPerformed(ActionEvent arg0) {
        if (checkFormulario()) {
            try {
                String nombre = this.textFieldNombre.getText();
                String email = this.textFieldEmail.getText();
                String apellido = this.textFieldApellido.getText();
                String direccion = this.textFieldDireccion.getText();
                String zonaStr = (String) this.comboBoxZona.getSelectedItem();
                
                // Convertir String a Zona enum
                Zona zona = Zona.valueOf(zonaStr);
                // Crear fecha actual
                Date fecha = new Date(System.currentTimeMillis());
                // Registrar el lector
                this.icon.registrarLector(nombre, email, direccion, zona, fecha);
                
                JOptionPane.showMessageDialog(this, "El Lector se ha creado con éxito", "Agregar Lector",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (ExisteUsuarioException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Agregar Lector", JOptionPane.ERROR_MESSAGE);
            }
            limpiarFormulario();
            setVisible(false);
        }
    }
    
    private boolean checkFormulario() {
        String nombre = this.textFieldNombre.getText();
        String apellido = this.textFieldApellido.getText();
        String email = this.textFieldEmail.getText();
        String direccion = this.textFieldDireccion.getText();
        
        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Agregar Lector",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "El email debe tener un formato válido", "Agregar Lector",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void limpiarFormulario() {
        textFieldNombre.setText("");
        textFieldApellido.setText("");
        textFieldEmail.setText("");
        textFieldDireccion.setText("");
        comboBoxZona.setSelectedIndex(0);
    }
}
