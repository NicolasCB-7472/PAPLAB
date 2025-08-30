package presentacion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import interfaces.IControlador;

public class ModificarLectorWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private IControlador icon;

    private JButton btnModificarEstado;
    private JButton btnCambiarBarrio;
    private JButton btnCancelar;

    public ModificarLectorWindow(IControlador icon) {
        this.icon = icon;
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Modificar Lector");
        setBounds(100, 100, 400, 250);
        getContentPane().setLayout(null);
        
        JLabel lblTitulo = new JLabel("Seleccione la opción deseada:");
        lblTitulo.setBounds(100, 30, 200, 20);
        getContentPane().add(lblTitulo);
        
        btnModificarEstado = new JButton("Modificar Estado del Lector");
        btnModificarEstado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarEstadoActionPerformed(e);
            }
        });
        btnModificarEstado.setBounds(80, 80, 240, 30);
        getContentPane().add(btnModificarEstado);
        
        btnCambiarBarrio = new JButton("Cambiar Barrio del Lector");
        btnCambiarBarrio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarBarrioActionPerformed(e);
            }
        });
        btnCambiarBarrio.setBounds(80, 130, 240, 30);
        getContentPane().add(btnCambiarBarrio);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelarActionPerformed(e);
            }
        });
        btnCancelar.setBounds(150, 180, 100, 30);
        getContentPane().add(btnCancelar);
    }
    
    protected void modificarEstadoActionPerformed(ActionEvent e) {
        ModificarEstadoLectorWindow modificarEstado = new ModificarEstadoLectorWindow(icon);
        modificarEstado.setVisible(true);
    }
    
    protected void cambiarBarrioActionPerformed(ActionEvent e) {
        CambiarZonaLectorWindow cambiarZona = new CambiarZonaLectorWindow(icon);
        cambiarZona.setVisible(true);
    }
    
    protected void cancelarActionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }
}
