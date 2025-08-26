package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgregarArticuloWindow implements ActionListener {
    
    private JFrame window;
    private JPanel mainPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;
    
    // Campos del formulario
    private JTextField nombreField;
    private JTextField descripcionField;
    private JTextField pesoField;
    private JTextField dimensionesField;
    
    // Botones
    private JButton agregarButton;
    private JButton cancelarButton;
    private JButton limpiarButton;
    
    // Labels
    private JLabel tituloLabel;
    private JLabel nombreLabel;
    private JLabel descripcionLabel;
    private JLabel pesoLabel;
    private JLabel dimensionesLabel;
    
    public AgregarArticuloWindow() {
        initialize();
        creaFormulario();
        crearBotones();
        setupLayout();
    }
    
    private void initialize() {
        window = new JFrame("Agregar Nuevo Artículo");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(500, 400);
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
        // Título
        tituloLabel = new JLabel("Registro de Nuevo Artículo");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setForeground(new Color(50, 50, 50));
        
        // Campos del formulario
        nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField(20);
        
        descripcionLabel = new JLabel("Descripción:");
        descripcionField = new JTextField(20);
        
        pesoLabel = new JLabel("Peso (kg):");
        pesoField = new JTextField(20);
        
        dimensionesLabel = new JLabel("Dimensiones:");
        dimensionesField = new JTextField(20);
        
        // Agregar componentes al panel del formulario usando GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(nombreLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nombreField, gbc);
        
        // Descripción
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(descripcionLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(descripcionField, gbc);
        
        // Peso
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(pesoLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(pesoField, gbc);
        
        // Dimensiones
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(dimensionesLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(dimensionesField, gbc);
    }
    
    private void crearBotones() {
        agregarButton = new JButton("Registrar Artículo");
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
            agregarArticulo();
        } else if (e.getSource() == cancelarButton) {
            window.dispose();
        } else if (e.getSource() == limpiarButton) {
            limpiarCampos();
        }
    }
    
    private void limpiarCampos() {
        nombreField.setText("");
        descripcionField.setText("");
        pesoField.setText("");
        dimensionesField.setText("");
        nombreField.requestFocus();
    }
    
    private void agregarArticulo() {
        // Obtener datos del formulario
        String nombre = nombreField.getText().trim();
        String descripcion = descripcionField.getText().trim();
        String pesoStr = pesoField.getText().trim();
        String dimensiones = dimensionesField.getText().trim();
        
        // Validar campos obligatorios
        if (nombre.isEmpty() || descripcion.isEmpty() || pesoStr.isEmpty() || dimensiones.isEmpty()) {
            JOptionPane.showMessageDialog(window, 
                "Por favor complete los campos obligatorios:\n" +
                "• Nombre\n" +
                "• Descripción\n" +
                "• Peso\n" +
                "• Dimensiones", 
                "Campos Requeridos", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar que el peso sea numérico
        try {
            float peso = Float.parseFloat(pesoStr);
            
            if (peso <= 0) {
                JOptionPane.showMessageDialog(window, 
                    "El peso debe ser mayor a 0", 
                    "Peso Inválido", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(window, 
                "El peso debe ser un número válido", 
                "Peso Inválido", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Mostrar resumen de datos
            StringBuilder resumen = new StringBuilder();
            resumen.append("Artículo registrado exitosamente:\n\n");
            resumen.append("Nombre: ").append(nombre).append("\n");
            resumen.append("Descripción: ").append(descripcion).append("\n");
            resumen.append("Peso: ").append(pesoStr).append(" kg\n");
            resumen.append("Dimensiones: ").append(dimensiones);
            
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
                "Error al registrar el artículo: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
