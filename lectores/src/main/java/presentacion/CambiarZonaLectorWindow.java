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
import excepciones.ValorIncorrectoDeZonaException;
import datatypes.Zona;

public class CambiarZonaLectorWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private IControlador icon;

    private JTextField textFieldEmail;
    private JComboBox<String> comboBoxZona;

    public CambiarZonaLectorWindow(IControlador icon) {
        this.icon = icon;
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Cambiar Zona del Lector");
        setBounds(100, 100, 400, 250);
        getContentPane().setLayout(null);
        
        JLabel lblEmail = new JLabel("EMAIL");
        lblEmail.setBounds(47, 35, 70, 15);
        getContentPane().add(lblEmail);
        
        JLabel lblZona = new JLabel("NUEVA ZONA");
        lblZona.setBounds(47, 65, 100, 15);
        getContentPane().add(lblZona);
        
        textFieldEmail = new JTextField();
        textFieldEmail.setBounds(135, 33, 200, 19);
        getContentPane().add(textFieldEmail);
        textFieldEmail.setColumns(10);
        
        String[] zonas = {"", "BIBLIOTECA_CENTRAL", "SUCURSAL_ESTE", "SUCURSAL_OESTE", "BIBLIOTECA_INFANTIL", "ARCHIVO_GENERAL"};
        comboBoxZona = new JComboBox<>(zonas);
        comboBoxZona.setBounds(135, 63, 200, 19);
        getContentPane().add(comboBoxZona);
        
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarZonaAceptarActionPerformed(e);
            }
        });
        btnAceptar.setBounds(65, 150, 117, 25);
        getContentPane().add(btnAceptar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarZonaCancelarActionPerformed(e);
            }
        });
        btnCancelar.setBounds(247, 150, 117, 25);
        getContentPane().add(btnCancelar);
    }
    
    protected void cambiarZonaCancelarActionPerformed(ActionEvent arg0) {
        limpiarFormulario();
        setVisible(false);
    }
    
    protected void cambiarZonaAceptarActionPerformed(ActionEvent arg0) {
        if (checkFormulario()) {
            try {
                String email = this.textFieldEmail.getText();
                String zonaStr = (String) this.comboBoxZona.getSelectedItem();
                
                // Convertir String a Zona enum
                Zona nuevaZona = Zona.valueOf(zonaStr);
                
                // Cambiar la zona del lector
                this.icon.cambiarZonaLector(email, nuevaZona);
                
                JOptionPane.showMessageDialog(this, "La zona del lector se ha cambiado con éxito", "Cambiar Zona",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NoExisteUsuarioException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Cambiar Zona", JOptionPane.ERROR_MESSAGE);
            } catch (ValorIncorrectoDeZonaException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Cambiar Zona", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al procesar los datos: " + e.getMessage(), "Cambiar Zona",
                        JOptionPane.ERROR_MESSAGE);
            }
            limpiarFormulario();
            setVisible(false);
        }
    }
    
    private boolean checkFormulario() {
        String email = this.textFieldEmail.getText();
        String zona = (String) this.comboBoxZona.getSelectedItem();
        
        if (email.isEmpty() || zona.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Cambiar Zona",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "El email debe tener un formato válido", "Cambiar Zona",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        textFieldEmail.setText("");
        comboBoxZona.setSelectedIndex(0);
    }
}
