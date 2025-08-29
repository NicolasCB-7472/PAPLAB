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

public class AgregarBibliotecarioWindows extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private IControlador icon;
    
    private JTextField textFieldNombre;
    private JTextField textFieldEmail;
    private JTextField textFieldNumeroEmpleado;

public AgregarBibliotecarioWindows(IControlador icon) {
    this.icon = icon;
    setResizable(true);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setTitle("Alta de un Bibliotecario");
    setBounds(100, 100, 450, 300);
    getContentPane().setLayout(null);
    
    JLabel lblNombre = new JLabel("NOMBRE");
    lblNombre.setBounds(47, 65, 70, 15);
    getContentPane().add(lblNombre);
    
    JLabel lblEmail = new JLabel("EMAIL");
    lblEmail.setBounds(47, 95, 70, 15);
    getContentPane().add(lblEmail);
    
    JLabel lblNumeroEmpleado = new JLabel("NRO. EMPLEADO");
    lblNumeroEmpleado.setBounds(47, 125, 100, 15);
    getContentPane().add(lblNumeroEmpleado);
    
    textFieldNombre = new JTextField();
    textFieldNombre.setBounds(135, 63, 114, 19);
    getContentPane().add(textFieldNombre);
    textFieldNombre.setColumns(10);
    
    textFieldEmail = new JTextField();
    textFieldEmail.setBounds(135, 93, 114, 19);
    getContentPane().add(textFieldEmail);
    textFieldEmail.setColumns(10);
    
    textFieldNumeroEmpleado = new JTextField();
    textFieldNumeroEmpleado.setBounds(135, 123, 114, 19);
    getContentPane().add(textFieldNumeroEmpleado);
    textFieldNumeroEmpleado.setColumns(10);
    
    JButton btnAceptar = new JButton("Aceptar");
    btnAceptar.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            agregarBibliotecarioAceptarActionPerformed(e);
        }
    });
    btnAceptar.setBounds(65, 200, 117, 25);
    getContentPane().add(btnAceptar);
    
    JButton btnCancelar = new JButton("Cancelar");
    btnCancelar.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            agregarBibliotecarioCancelarActionPerformed(e);
        }
    });
    btnCancelar.setBounds(247, 200, 117, 25);
    getContentPane().add(btnCancelar);
}

protected void agregarBibliotecarioCancelarActionPerformed(ActionEvent arg0) {
    limpiarFormulario();
    setVisible(false);
}

protected void agregarBibliotecarioAceptarActionPerformed(ActionEvent arg0) {
    if (checkFormulario()) {
        try {
            String nombre = this.textFieldNombre.getText();
            String email = this.textFieldEmail.getText();
            String numeroEmpleado = this.textFieldNumeroEmpleado.getText();
            
            // Registrar el bibliotecario
            this.icon.registrarBibliotecario(nombre, email, numeroEmpleado);
            
            JOptionPane.showMessageDialog(this, "El Bibliotecario se ha creado con éxito", "Agregar Bibliotecario",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (ExisteUsuarioException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Agregar Bibliotecario", JOptionPane.ERROR_MESSAGE);
        }
        limpiarFormulario();
        setVisible(false);
    }
}

private boolean checkFormulario() {
    String nombre = this.textFieldNombre.getText();
    String email = this.textFieldEmail.getText();
    String numeroEmpleado = this.textFieldNumeroEmpleado.getText();
    
    if (nombre.isEmpty() || email.isEmpty() || numeroEmpleado.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Agregar Bibliotecario",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }
    if (!email.contains("@") || !email.contains(".")) {
        JOptionPane.showMessageDialog(this, "El email debe tener un formato válido", "Agregar Bibliotecario",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }
    // Validar que el número de empleado sea numérico
    try {
        Integer.parseInt(numeroEmpleado);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El número de empleado debe ser numérico", "Agregar Bibliotecario",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }
    return true;
}

private void limpiarFormulario() {
    textFieldNombre.setText("");
    textFieldEmail.setText("");
    textFieldNumeroEmpleado.setText("");
}
}
