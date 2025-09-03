package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import interfaces.IControlador;
import logica.ManejadorUsuario;
import logica.ManejadorMaterial;
import logica.Usuario;
import logica.Lector;
import logica.Bibliotecario;
import logica.Material;
import datatypes.EstadoPrestamo;
import excepciones.*;

public class AgregarPrestamoWindow extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private IControlador controlador;
    private ManejadorUsuario manejadorUsuario;
    private ManejadorMaterial manejadorMaterial;
    
    // Componentes de la interfaz
    private JComboBox<String> comboBoxLector;
    private JComboBox<String> comboBoxBibliotecario;
    private JTextField textFieldNumEmpleado;
    private JComboBox<Integer> comboBoxMaterial;
    private JSpinner spinnerFechaSolicitud;
    private JSpinner spinnerFechaDevolucion;
    private JComboBox<EstadoPrestamo> comboBoxEstado;
    private JButton buttonAceptar;
    private JButton buttonCancelar;
    private JButton buttonRecargar;
    
    public AgregarPrestamoWindow(IControlador controlador) {
        this.controlador = controlador;
        this.manejadorUsuario = ManejadorUsuario.getInstancia();
        this.manejadorMaterial = ManejadorMaterial.getInstancia();
        
        initializeComponents();
        loadData();
        setupEventListeners();
        
        setTitle("Agregar Préstamo");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Panel principal con GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título
        JLabel titleLabel = new JLabel("Nuevo Préstamo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);
        
        // Lector
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Lector:"), gbc);
        
        comboBoxLector = new JComboBox<>();
        gbc.gridx = 1;
        mainPanel.add(comboBoxLector, gbc);
        
        // Bibliotecario
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Bibliotecario:"), gbc);
        
        comboBoxBibliotecario = new JComboBox<>();
        gbc.gridx = 1;
        mainPanel.add(comboBoxBibliotecario, gbc);
        
        // Número de Empleado
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Número Empleado:"), gbc);
        
        textFieldNumEmpleado = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(textFieldNumEmpleado, gbc);
        
        // Material
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Material:"), gbc);
        
        comboBoxMaterial = new JComboBox<>();
        gbc.gridx = 1;
        mainPanel.add(comboBoxMaterial, gbc);
        
        // Fecha Solicitud
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("Fecha Solicitud:"), gbc);
        
        spinnerFechaSolicitud = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerFechaSolicitud, "dd/MM/yyyy");
        spinnerFechaSolicitud.setEditor(dateEditor);
        gbc.gridx = 1;
        mainPanel.add(spinnerFechaSolicitud, gbc);
        
        // Fecha Devolución
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(new JLabel("Fecha Devolución:"), gbc);
        
        spinnerFechaDevolucion = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditorDev = new JSpinner.DateEditor(spinnerFechaDevolucion, "dd/MM/yyyy");
        spinnerFechaDevolucion.setEditor(dateEditorDev);
        gbc.gridx = 1;
        mainPanel.add(spinnerFechaDevolucion, gbc);
        
        // Estado
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(new JLabel("Estado:"), gbc);
        
        comboBoxEstado = new JComboBox<>(EstadoPrestamo.values());
        gbc.gridx = 1;
        mainPanel.add(comboBoxEstado, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonAceptar = new JButton("Aceptar");
        buttonCancelar = new JButton("Cancelar");
        buttonRecargar = new JButton("🔄 Recargar");
        buttonPanel.add(buttonAceptar);
        buttonPanel.add(buttonCancelar);
        buttonPanel.add(buttonRecargar);
        
        // Agregar paneles al frame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadData() {
        try {
            // Limpiar combos antes de cargar
            comboBoxLector.removeAllItems();
            comboBoxBibliotecario.removeAllItems();
            comboBoxMaterial.removeAllItems();
            
            System.out.println("🔄 Cargando datos...");
            
            // Cargar lectores
            ArrayList<String> usuarios = manejadorUsuario.obtenerUsuarios();
            System.out.println("📧 Total usuarios encontrados: " + usuarios.size());
            
            int lectoresCount = 0;
            int bibliotecariosCount = 0;
            
            for (String email : usuarios) {
                Usuario usuario = manejadorUsuario.buscarUsuario(email);
                if (usuario instanceof Lector) {
                    comboBoxLector.addItem(email);
                    lectoresCount++;
                    System.out.println("👤 Lector agregado: " + email);
                } else if (usuario instanceof Bibliotecario) {
                    comboBoxBibliotecario.addItem(email);
                    bibliotecariosCount++;
                    System.out.println("👨‍💼 Bibliotecario agregado: " + email);
                }
            }
            
            System.out.println("📊 Resumen: " + lectoresCount + " lectores, " + bibliotecariosCount + " bibliotecarios");
            
            // Cargar materiales
            ArrayList<Integer> materiales = manejadorMaterial.obtenerMateriales();
            System.out.println("📚 Materiales encontrados: " + materiales.size());
            for (Integer id : materiales) {
                comboBoxMaterial.addItem(id);
                System.out.println("📖 Material agregado: ID " + id);
            }
            
            // Establecer fechas por defecto
            Calendar cal = Calendar.getInstance();
            spinnerFechaSolicitud.setValue(cal.getTime());
            
            cal.add(Calendar.DAY_OF_MONTH, 15); // 15 días para devolución
            spinnerFechaDevolucion.setValue(cal.getTime());
            
            // Establecer estado por defecto
            comboBoxEstado.setSelectedItem(EstadoPrestamo.PENDIENTE);
            
            // Verificar que se cargaron datos
            if (comboBoxLector.getItemCount() == 0) {
                System.out.println("⚠️ ADVERTENCIA: No se cargaron lectores");
            }
            if (comboBoxBibliotecario.getItemCount() == 0) {
                System.out.println("⚠️ ADVERTENCIA: No se cargaron bibliotecarios");
            }
            if (comboBoxMaterial.getItemCount() == 0) {
                System.out.println("⚠️ ADVERTENCIA: No se cargaron materiales");
            }
            
            System.out.println("✅ Carga de datos completada");
            
        } catch (Exception e) {
            System.err.println("❌ Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setupEventListeners() {
        buttonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPrestamoAceptarActionPerformed(e);
            }
        });
        
        buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPrestamoCancelarActionPerformed(e);
            }
        });
        
        buttonRecargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("🔄 Recargando datos manualmente...");
                loadData();
                JOptionPane.showMessageDialog(AgregarPrestamoWindow.this, 
                    "Datos recargados. Lectores: " + comboBoxLector.getItemCount() + 
                    ", Bibliotecarios: " + comboBoxBibliotecario.getItemCount() + 
                    ", Materiales: " + comboBoxMaterial.getItemCount(), 
                    "Datos Recargados", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    private void agregarPrestamoAceptarActionPerformed(ActionEvent evt) {
        if (!checkFormulario()) {
            return;
        }
        
        try {
            String lectorEmail = (String) comboBoxLector.getSelectedItem();
            String bibliotecarioEmail = (String) comboBoxBibliotecario.getSelectedItem();
            String numEmpleado = textFieldNumEmpleado.getText().trim();
            Integer materialId = (Integer) comboBoxMaterial.getSelectedItem();
            Date fechaSolicitud = new Date(((java.util.Date) spinnerFechaSolicitud.getValue()).getTime());
            Date fechaDevolucion = new Date(((java.util.Date) spinnerFechaDevolucion.getValue()).getTime());
            EstadoPrestamo estado = (EstadoPrestamo) comboBoxEstado.getSelectedItem();
            
            // Llamar al método del controlador
            controlador.agregarPrestamo(lectorEmail, bibliotecarioEmail, numEmpleado, 
                                      materialId, fechaSolicitud, fechaDevolucion, estado);
            
            JOptionPane.showMessageDialog(this, 
                "Préstamo agregado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            limpiarFormulario();
            
        } catch (EmpleadoyCasteoNoValidoException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error de Empleado", 
                JOptionPane.ERROR_MESSAGE);
        } catch (FechasIncorrectasException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error de Fechas", 
                JOptionPane.ERROR_MESSAGE);
        } catch (PrestamoIncorrectoException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error de Préstamo", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregarPrestamoCancelarActionPerformed(ActionEvent evt) {
        dispose();
    }
    
    private boolean checkFormulario() {
        if (comboBoxLector.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un lector", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (comboBoxBibliotecario.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un bibliotecario", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (textFieldNumEmpleado.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el número de empleado", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (comboBoxMaterial.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un material", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        java.util.Date fechaSol = (java.util.Date) spinnerFechaSolicitud.getValue();
        java.util.Date fechaDev = (java.util.Date) spinnerFechaDevolucion.getValue();
        
        if (fechaSol.after(fechaDev)) {
            JOptionPane.showMessageDialog(this, "La fecha de solicitud no puede ser posterior a la fecha de devolución", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        comboBoxLector.setSelectedIndex(0);
        comboBoxBibliotecario.setSelectedIndex(0);
        textFieldNumEmpleado.setText("");
        comboBoxMaterial.setSelectedIndex(0);
        
        Calendar cal = Calendar.getInstance();
        spinnerFechaSolicitud.setValue(cal.getTime());
        
        cal.add(Calendar.DAY_OF_MONTH, 15);
        spinnerFechaDevolucion.setValue(cal.getTime());
        
        comboBoxEstado.setSelectedItem(EstadoPrestamo.PENDIENTE);
    }
}
