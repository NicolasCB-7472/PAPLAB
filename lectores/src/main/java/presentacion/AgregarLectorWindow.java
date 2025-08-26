package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgregarLectorWindow implements ActionListener {
    
    private JFrame window;
    private JPanel mainPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;
    
    // Campos del formulario
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField emailField;
    private JTextField direccionField;
    private JComboBox<String> zonaCombo;
    
    // Botones
    private JButton agregarButton;
    private JButton cancelarButton;
    private JButton limpiarButton;
    
    // Labels
    private JLabel tituloLabel;
    private JLabel nombreLabel;
    private JLabel apellidoLabel;
    private JLabel emailLabel;
    private JLabel direccionLabel;
    private JLabel zonaLabel;
    
    public AgregarLectorWindow() {
        initialize();
        creaFormulario();
        crearBotones();
        setupLayout();
    }
    
    private void initialize() {
        window = new JFrame("Agregar Nuevo Lector");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(500, 400);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        
        mainPanel = new JPanel(new BorderLayout(15, 15));
        formPanel = new JPanel(new GridLayout(5, 2, 10, 8));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        // Configurar márgenes
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }
    
    private void creaFormulario() {
        // Título
        tituloLabel = new JLabel("Registro de Nuevo Lector");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setForeground(new Color(50, 50, 50));
        
        // Campos del formulario
        nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField(20);
        
        apellidoLabel = new JLabel("Apellido:");
        apellidoField = new JTextField(20);
        
        emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        
        direccionLabel = new JLabel("Dirección:");
        direccionField = new JTextField(20);
        
        zonaLabel = new JLabel("Zona:");
        String[] zonas = {"", "BIBLIOTECA_CENTRAL", "SUCURSAL_ESTE", "SUCURSAL_OESTE", "BIBLIOTECA_INFANTIL", "ARCHIVO_GENERAL"};
        zonaCombo = new JComboBox<>(zonas);
        
        // Agregar componentes al panel del formulario insertas lo que creaste arriba
        formPanel.add(nombreLabel);
        formPanel.add(nombreField);
        formPanel.add(apellidoLabel);
        formPanel.add(apellidoField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(direccionLabel);
        formPanel.add(direccionField);
        formPanel.add(zonaLabel);
        formPanel.add(zonaCombo);
    }
    
    private void crearBotones() {
        agregarButton = new JButton("Registrar Lector");
        cancelarButton = new JButton("Cancelar");
        limpiarButton = new JButton("Limpiar Campos");
        
        // Configurar colores y estilos
        agregarButton.setBackground(new Color(34, 139, 34));
        agregarButton.setForeground(Color.WHITE);
        agregarButton.setFocusPainted(false);
        agregarButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        cancelarButton.setBackground(new Color(220, 20, 60));
        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.setFocusPainted(false);
        cancelarButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        limpiarButton.setBackground(new Color(70, 130, 180));
        limpiarButton.setForeground(Color.WHITE);
        limpiarButton.setFocusPainted(false);
        limpiarButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Agregar listeners
        agregarButton.addActionListener(this);
        cancelarButton.addActionListener(this);
        limpiarButton.addActionListener(this);
        
        // Agregar botones al panel
        buttonPanel.add(agregarButton);
        buttonPanel.add(limpiarButton);
        buttonPanel.add(cancelarButton);
    }
    
    private void setupLayout() {
        mainPanel.add(tituloLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        window.add(mainPanel);
    }
    
    public void show() {
        window.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == agregarButton) {
            agregarLector();
        } else if (e.getSource() == cancelarButton) {
            window.dispose();
        } else if (e.getSource() == limpiarButton) {
            limpiarCampos();
        }
    }
    
    private void limpiarCampos() {
        nombreField.setText("");
        apellidoField.setText("");
        emailField.setText("");
        direccionField.setText("");
        zonaCombo.setSelectedIndex(0);
        nombreField.requestFocus();
    }
    
    private void agregarLector() {
        // Obtener datos del formulario
        String nombre = nombreField.getText().trim();
        String apellido = apellidoField.getText().trim();
        String email = emailField.getText().trim();
        String direccion = direccionField.getText().trim();
        String zona = (String) zonaCombo.getSelectedItem();
        
        // Validar campos obligatorios
        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(window, 
                "Por favor complete los campos obligatorios:\n" +
                "• Nombre\n" +
                "• Apellido\n" +
                "• Email\n" +
                "• Dirección", 
                "Campos Requeridos", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar formato de email
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(window, 
                "Por favor ingrese un email válido", 
                "Email Inválido", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Mostrar resumen de datos
            StringBuilder resumen = new StringBuilder();
            resumen.append("Lector registrado exitosamente:\n\n");
            resumen.append("Nombre: ").append(nombre).append(" ").append(apellido).append("\n");
            resumen.append("Email: ").append(email).append("\n");
            resumen.append("Dirección: ").append(direccion);
            
            if (zona != null && !"Seleccionar...".equals(zona)) {
                resumen.append(", Zona: ").append(zona);
            }
            
            JOptionPane.showMessageDialog(window, 
                resumen.toString(), 
                "Registro Exitoso", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar campos
            limpiarCampos();
            
            // Cerrar ventana
            window.dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(window, 
                "Error al registrar el lector: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
