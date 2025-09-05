package presentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import datatypes.DtPrestamo;
import datatypes.EstadoPrestamo;
import excepciones.NoExisteUsuarioException;
import excepciones.PrestamoIncorrectoException;
import interfaces.IControlador;

public class GestionarPrestamoWindow extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private IControlador controlador;
    
    // Componentes de la interfaz
    private JTable tablePrestamos;
    private DefaultTableModel tableModel;
    private JComboBox<EstadoPrestamo> comboBoxNuevoEstado;
    private JButton buttonActualizar;
    private JButton buttonRecargar;
    private JButton buttonCerrar;
    
    public GestionarPrestamoWindow(IControlador controlador) {
        this.controlador = controlador;
        
        initializeComponents();
        loadData();
        setupEventListeners();
        
        setTitle("Gestionar Estado de Prestamos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Titulo
        JLabel titleLabel = new JLabel("Gestion de Estado de Prestamos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Tabla de prestamos
        String[] columnNames = {"Lector", "Bibliotecario", "Material ID", "Fecha Solicitud", "Fecha Devolucion", "Estado Actual"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla de solo lectura
            }
        };
        
        tablePrestamos = new JTable(tableModel);
        tablePrestamos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablePrestamos);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de controles
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Etiqueta y combo para nuevo estado
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        controlPanel.add(new JLabel("Nuevo Estado:"), gbc);
        
        comboBoxNuevoEstado = new JComboBox<>(EstadoPrestamo.values());
        gbc.gridx = 1;
        controlPanel.add(comboBoxNuevoEstado, gbc);
        
        // Boton actualizar
        buttonActualizar = new JButton("Actualizar Estado");
        gbc.gridx = 2;
        controlPanel.add(buttonActualizar, gbc);
        
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonRecargar = new JButton("Recargar");
        buttonCerrar = new JButton("Cerrar");
        buttonPanel.add(buttonRecargar);
        buttonPanel.add(buttonCerrar);
        
        // Agregar paneles al frame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadData() {
        try {
            // Limpiar tabla
            tableModel.setRowCount(0);
            
            // Obtener prestamos del controlador
            ArrayList<DtPrestamo> prestamos = controlador.obtenerPrestamos();
            
            // Llenar tabla
            for (DtPrestamo prestamo : prestamos) {
                Object[] row = {
                    prestamo.getLector(),
                    prestamo.getBibliotecario(),
                    prestamo.getMaterial(),
                    prestamo.getFechaSolicitud().toString(),
                    prestamo.getFechaDevolucion().toString(),
                    prestamo.getEstado().toString()
                };
                tableModel.addRow(row);
            }
            
            // Establecer estado por defecto
            comboBoxNuevoEstado.setSelectedItem(EstadoPrestamo.EN_CURSO);
            
        } catch (Exception e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setupEventListeners() {
        buttonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEstadoActionPerformed(e);
            }
        });
        
        buttonRecargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
                JOptionPane.showMessageDialog(GestionarPrestamoWindow.this, 
                    "Datos recargados exitosamente", 
                    "Informacion", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        buttonCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void actualizarEstadoActionPerformed(ActionEvent evt) {
        int selectedRow = tablePrestamos.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un prestamo de la tabla", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Obtener datos del prestamo seleccionado
            String lectorEmail = (String) tableModel.getValueAt(selectedRow, 0);
            String bibliotecarioEmail = (String) tableModel.getValueAt(selectedRow, 1);
            String materialIdStr = (String) tableModel.getValueAt(selectedRow, 2);
            EstadoPrestamo nuevoEstado = (EstadoPrestamo) comboBoxNuevoEstado.getSelectedItem();
            EstadoPrestamo estadoActual = EstadoPrestamo.valueOf((String) tableModel.getValueAt(selectedRow, 5));
            
            // Validar que los datos no esten vacios
            if (lectorEmail == null || lectorEmail.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: Email del lector no valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (bibliotecarioEmail == null || bibliotecarioEmail.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: Email del bibliotecario no valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (materialIdStr == null || materialIdStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: ID del material no valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Integer materialId = Integer.parseInt(materialIdStr);
            
            // Validar que el nuevo estado sea diferente al actual
            if (nuevoEstado == estadoActual) {
                JOptionPane.showMessageDialog(this, 
                    "El nuevo estado debe ser diferente al estado actual", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Confirmar la operación
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Esta seguro de que desea cambiar el estado del prestamo de " + 
                estadoActual + " a " + nuevoEstado + "?", 
                "Confirmar Cambio", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Llamar al método del controlador
                controlador.actualizarEstadoPrestamo(lectorEmail, bibliotecarioEmail, materialId, nuevoEstado);
                
                JOptionPane.showMessageDialog(this, 
                    "Estado del préstamo actualizado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Recargar datos
                loadData();
            }
            
        } catch (NoExisteUsuarioException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error de Usuario", 
                JOptionPane.ERROR_MESSAGE);
        } catch (PrestamoIncorrectoException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error de Préstamo", 
                JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: ID de material inválido", 
                "Error de Formato", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
