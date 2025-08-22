package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgregarBibliotecarioWindows implements ActionListener {
    private JFrame window;
    private JPanel mainPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;

    // Campos del formulario
    private JTextField nombreField;
    private JTextField emailField;
    private JTextField numeroEmpleadoField;
    private JComboBox<String> zonaCombo;
    

    //Botones
    private JButton agregarButton;
    private JButton cancelarButton;
    private JButton limpiarButton;

    //Labels
    private JLabel tituloLabel;
    private JLabel nombreLabel;
    private JLabel emailLabel;
    private JLabel numeroEmpleadoLabel;

public AgregarBibliotecarioWindows() {
    initialize();
    creaFormulario();
    crearBotones();
    setupLayout();
    window.setVisible(true);
}

private void initialize() {
    window = new JFrame("Agregar Nuevo Bibliotecario");
    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    window.setSize(500, 350);
    window.setLocationRelativeTo(null);
    window.setResizable(false);

    mainPanel = new JPanel(new BorderLayout(15, 15));
    formPanel = new JPanel(new GridBagLayout());
    buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    
    // Configurar márgenes
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
    formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
}

private void creaFormulario() {
    //Titulo
    tituloLabel = new JLabel("Registro de Nuevo Bibliotecario");
    tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
    tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
    tituloLabel.setForeground(new Color(50, 50, 50));

    //Campos del formulario
    nombreLabel = new JLabel("Nombre:");
    nombreField = new JTextField(12);
    
    emailLabel = new JLabel("Email:");
    emailField = new JTextField(12);
    
    numeroEmpleadoLabel = new JLabel("Número de Empleado:");
    numeroEmpleadoField = new JTextField(12);

    //Agregar componentes al panel del formulario
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.WEST;
    
    // Nombre
    gbc.gridx = 0; gbc.gridy = 0;
    formPanel.add(nombreLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(nombreField, gbc);
    
    // Email
    gbc.gridx = 0; gbc.gridy = 1;
    formPanel.add(emailLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(emailField, gbc);
    
    // Número de Empleado
    gbc.gridx = 0; gbc.gridy = 2;
    formPanel.add(numeroEmpleadoLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(numeroEmpleadoField, gbc);
}

private void crearBotones() {
    agregarButton = new JButton("Registrar Bibliotecario");
    agregarButton.setFont(new Font("Arial", Font.BOLD, 12));
    agregarButton.setBackground(new Color(34, 139, 34));
    agregarButton.setForeground(Color.WHITE);
    agregarButton.setFocusPainted(false);
    agregarButton.addActionListener(this);

    cancelarButton = new JButton("Cancelar");
    cancelarButton.setFont(new Font("Arial", Font.BOLD, 12));
    cancelarButton.setBackground(new Color(220, 20, 60));
    cancelarButton.setForeground(Color.WHITE);
    cancelarButton.setFocusPainted(false);
    cancelarButton.addActionListener(this);

    limpiarButton = new JButton("Limpiar Campos");
    limpiarButton.setFont(new Font("Arial", Font.BOLD, 12));
    limpiarButton.setBackground(new Color(70, 130, 180));
    limpiarButton.setForeground(Color.WHITE);
    limpiarButton.setFocusPainted(false);
    limpiarButton.addActionListener(this);

    //Agregar botones al panel
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
        agregarBibliotecario();
    } else if (e.getSource() == limpiarButton) {
        limpiarCampos();
    } else if (e.getSource() == cancelarButton) {
        window.dispose();
    }
}

private void limpiarCampos() {
    nombreField.setText("");
    emailField.setText("");
    numeroEmpleadoField.setText("");
    nombreField.requestFocus();
}

private void agregarBibliotecario() {
    // Obtener datos del formulario
    String nombre = nombreField.getText().trim();
    String email = emailField.getText().trim();
    String numeroEmpleado = numeroEmpleadoField.getText().trim();
    
    // Validar campos obligatorios
    if (nombre.isEmpty() || email.isEmpty() || numeroEmpleado.isEmpty()) {
        JOptionPane.showMessageDialog(window, 
            "Por favor complete los campos obligatorios:\n" +
            "• Nombre\n" +
            "• Email\n" +
            "• Número de Empleado", 
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
    
    // Validar número de empleado (debe ser numérico)
    try {
        Integer.parseInt(numeroEmpleado);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(window, 
            "El número de empleado debe ser numérico", 
            "Número de Empleado Inválido", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    try {
        // Mostrar resumen de datos
        StringBuilder resumen = new StringBuilder();
        resumen.append("Bibliotecario registrado exitosamente:\n\n");
        resumen.append("Nombre: ").append(nombre).append("\n");
        resumen.append("Email: ").append(email).append("\n");
        resumen.append("Número de Empleado: ").append(numeroEmpleado);
        
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
            "Error al registrar el bibliotecario: " + ex.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}
}
