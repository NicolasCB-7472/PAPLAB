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
import excepciones.NoExisteUsuarioException;
import excepciones.ValorIncorrectoDeEstadoException;
import datatypes.EstadoLector;

public class ModificarEstadoLectorWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private IControlador icon;

    private JTextField textFieldEmail;
    private JComboBox<String> comboBoxEstado;

    public ModificarEstadoLectorWindow(IControlador icon) {
        this.icon = icon;
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Modificar Estado del Lector");
        setBounds(100, 100, 400, 250);
        getContentPane().setLayout(null);
        
        JLabel lblEmail = new JLabel("EMAIL");
        lblEmail.setBounds(47, 35, 70, 15);
        getContentPane().add(lblEmail);
        
        JLabel lblEstado = new JLabel("NUEVO ESTADO");
        lblEstado.setBounds(47, 65, 100, 15);
        getContentPane().add(lblEstado);
        
        textFieldEmail = new JTextField();
        textFieldEmail.setBounds(135, 33, 200, 19);
        getContentPane().add(textFieldEmail);
        textFieldEmail.setColumns(10);
        
        String[] estados = {"", "HABILITADO", "SUSPENDIDO", "SANCIONADO"};
        comboBoxEstado = new JComboBox<>(estados);
        comboBoxEstado.setBounds(135, 63, 200, 19);
        getContentPane().add(comboBoxEstado);
        
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarEstadoAceptarActionPerformed(e);
            }
        });
        btnAceptar.setBounds(65, 150, 117, 25);
        getContentPane().add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarEstadoCancelarActionPerformed(e);
            }
        });
        btnCancelar.setBounds(247, 150, 117, 25);
        getContentPane().add(btnCancelar);
    }
    
    protected void modificarEstadoCancelarActionPerformed(ActionEvent arg0) {
        limpiarFormulario();
        setVisible(false);
    }
    
    protected void modificarEstadoAceptarActionPerformed(ActionEvent arg0) {
        if (checkFormulario()) {
            try {
                String email = this.textFieldEmail.getText();
                String estadoStr = (String) this.comboBoxEstado.getSelectedItem();
                
                // Convertir String a EstadoLector enum
                EstadoLector nuevoEstado = EstadoLector.valueOf(estadoStr);
                
                // Modificar el estado del lector
                this.icon.cambiarEstadoLector(email, nuevoEstado);
                
                JOptionPane.showMessageDialog(this, "El estado del lector se ha modificado con éxito", "Modificar Estado",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NoExisteUsuarioException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Modificar Estado", JOptionPane.ERROR_MESSAGE);
            } catch (ValorIncorrectoDeEstadoException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Modificar Estado", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al procesar los datos: " + e.getMessage(), "Modificar Estado",
                        JOptionPane.ERROR_MESSAGE);
            }
            limpiarFormulario();
            setVisible(false);
        }
    }
    
    private boolean checkFormulario() {
        String email = this.textFieldEmail.getText();
        String estado = (String) this.comboBoxEstado.getSelectedItem();
        
        if (email.isEmpty() || estado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Modificar Estado",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "El email debe tener un formato válido", "Modificar Estado",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        textFieldEmail.setText("");
        comboBoxEstado.setSelectedIndex(0);
    }
}
