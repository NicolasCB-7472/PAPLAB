package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgregarLibroWindow implements ActionListener {
    
    private JFrame window;
    private JPanel mainPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;
    
    // Campos del formulario
    private JTextField tituloField;
    private JTextField cantPaginasField;
    
    // Botones
    private JButton agregarButton;
    private JButton cancelarButton;
    private JButton limpiarButton;
    
    // Labels
    private JLabel tituloLabel;
    private JLabel tituloCampoLabel;
    private JLabel cantPaginasLabel;
    
    public AgregarLibroWindow() {
        initialize();
        creaFormulario();
        crearBotones();
        setupLayout();
    }
    
    private void initialize() {
        window = new JFrame("Agregar Nuevo Libro");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(500, 300);
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
        tituloLabel = new JLabel("Registro de Nuevo Libro");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setForeground(new Color(50, 50, 50));
        
        // Campos del formulario
        tituloCampoLabel = new JLabel("Título:");
        tituloField = new JTextField(20);
        
        cantPaginasLabel = new JLabel("Cantidad de Páginas:");
        cantPaginasField = new JTextField(20);
        
        // Agregar componentes al panel del formulario usando GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(tituloCampoLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(tituloField, gbc);
        
        // Cantidad de Páginas
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(cantPaginasLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(cantPaginasField, gbc);
    }
    
    private void crearBotones() {
        agregarButton = new JButton("Registrar Libro");
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
            agregarLibro();
        } else if (e.getSource() == cancelarButton) {
            window.dispose();
        } else if (e.getSource() == limpiarButton) {
            limpiarCampos();
        }
    }
    
    private void limpiarCampos() {
        tituloField.setText("");
        cantPaginasField.setText("");
        tituloField.requestFocus();
    }
    
    private void agregarLibro() {
        // Obtener datos del formulario
        String titulo = tituloField.getText().trim();
        String cantPaginasStr = cantPaginasField.getText().trim();
        
        // Validar campos obligatorios
        if (titulo.isEmpty() || cantPaginasStr.isEmpty()) {
            JOptionPane.showMessageDialog(window, 
                "Por favor complete los campos obligatorios:\n" +
                "• Título\n" +
                "• Cantidad de Páginas", 
                "Campos Requeridos", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar que la cantidad de páginas sea numérica
        try {
            int cantPaginas = Integer.parseInt(cantPaginasStr);
            
            if (cantPaginas <= 0) {
                JOptionPane.showMessageDialog(window, 
                    "La cantidad de páginas debe ser mayor a 0", 
                    "Cantidad Inválida", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(window, 
                "La cantidad de páginas debe ser un número válido", 
                "Cantidad Inválida", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Mostrar resumen de datos
            StringBuilder resumen = new StringBuilder();
            resumen.append("Libro registrado exitosamente:\n\n");
            resumen.append("Título: ").append(titulo).append("\n");
            resumen.append("Cantidad de Páginas: ").append(cantPaginasStr);
            
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
                "Error al registrar el libro: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
