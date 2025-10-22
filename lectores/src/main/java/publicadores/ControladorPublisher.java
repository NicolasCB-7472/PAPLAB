package publicadores;

import java.sql.Date;
import java.util.ArrayList;

import datatypes.DtArticulo;
import datatypes.DtBibliotecario;
import datatypes.DtLector;
import datatypes.DtLibro;
import datatypes.DtMaterial;
import datatypes.DtPrestamo;
import datatypes.EstadoLector;
import datatypes.Zona;
import excepciones.CantidadDePaginasNoValidaException;
import excepciones.DescripcionNoValidaException;
import excepciones.ExisteUsuarioException;
import excepciones.NoExisteUsuarioException;
import excepciones.PesoNoValidoException;
import excepciones.TituloNoValidoException;
import excepciones.ValorIncorrectoDeEstadoException;
import interfaces.Fabrica;
import interfaces.IControlador;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class ControladorPublisher{
    
    private Fabrica fabrica;
    private IControlador icon;
    private Endpoint endpoint;
    
    public ControladorPublisher() {
        fabrica = Fabrica.getInstancia();
        icon = fabrica.getIControlador();
    }
    
    @WebMethod(exclude = true)
    public void publicar() {
        endpoint = Endpoint.publish("http://localhost:8080/biblioteca", this);
        System.out.println("Servicio publicado en: http://localhost:8080/biblioteca");
    }
    
    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return endpoint;
    }
    
    // ========== CASO DE USO: REGISTRAR LECTORES ==========
    @WebMethod
    public String registrarLector(String nombre, String email, String direccion, String zona, int dia, int mes, int anio) {
        try {
            Zona zonaEnum = Zona.valueOf(zona);
            // Construir Date desde los 3 enteros
            @SuppressWarnings("deprecation")
            Date fecha = new Date(anio - 1900, mes - 1, dia);
            
            icon.registrarLector(nombre, email, direccion, zonaEnum, fecha);
            
            // Devolver String con la información
            return String.format("SUCCESS|%s|%s|%s|%s|%d|%d|%d|ACTIVO", 
                nombre, email, direccion, zona, dia, mes, anio);
        } catch (ExisteUsuarioException e) {
            return "ERROR|El usuario ya existe";
        }
    }
    
    // ========== CASO DE USO: REGISTRAR BIBLIOTECARIOS ==========
    @WebMethod
    public String registrarBibliotecario(String nombre, String email, String numeroEmpleado) {
        try {
            icon.registrarBibliotecario(nombre, email, numeroEmpleado);
            return String.format("SUCCESS|%s|%s|%s", nombre, email, numeroEmpleado);
        } catch (ExisteUsuarioException e) {
            return "ERROR|El usuario ya existe";
        }
    }
    
    // ========== CASO DE USO: MODIFICAR ESTADO DE LECTOR ==========
    @WebMethod
    public boolean cambiarEstadoLector(String email, String nuevoEstado) {
        try {
            EstadoLector estado = EstadoLector.valueOf(nuevoEstado);
            icon.cambiarEstadoLector(email, estado);
            return true;
        } catch (NoExisteUsuarioException e) {
            return false;
        } catch (ValorIncorrectoDeEstadoException e) {
            return false;
        } 
    }
    
    // ========== CASO DE USO: REGISTRAR NUEVA DONACIÓN DE LIBROS ==========
    @WebMethod
    public String registrarDonacionLibro(String id, String titulo, int cantPaginas) {
        try {
            icon.agregarNuevoLibro(id, titulo, cantPaginas);
            Date fecha = icon.getFechaActual();
            @SuppressWarnings("deprecation")
            String fechaStr = String.format("%d/%d/%d", 
                fecha.getDate(), fecha.getMonth() + 1, fecha.getYear() + 1900);
            return String.format("SUCCESS|%s|%s|%s|%d", id, titulo, fechaStr, cantPaginas);
        } catch (CantidadDePaginasNoValidaException e) {
            return "ERROR|Cantidad de páginas no válida";
        } catch (TituloNoValidoException e) {
            return "ERROR|Título no válido";
        }
    }
    
    // ========== CASO DE USO: REGISTRAR NUEVA DONACIÓN DE ARTÍCULO ESPECIAL ==========
    @WebMethod
    public String registrarDonacionArticulo(String id, String descripcion, float peso, String dimensiones) {
        try {
            icon.agregarNuevoArticulo(id, descripcion, peso, dimensiones);
            Date fecha = icon.getFechaActual();
            @SuppressWarnings("deprecation")
            String fechaStr = String.format("%d/%d/%d", 
                fecha.getDate(), fecha.getMonth() + 1, fecha.getYear() + 1900);
            return String.format("SUCCESS|%s|%s|%s|%.2f|%s", 
                id, descripcion, fechaStr, peso, dimensiones);
        } catch (DescripcionNoValidaException e) {
            return "ERROR|Descripción no válida";
        } catch (PesoNoValidoException e) {
            return "ERROR|Peso no válido";
        }
    }
    
    // ========== CASO DE USO: CONSULTAR TODAS LAS DONACIONES REGISTRADAS ==========
    @WebMethod
    public String consultarDonacionesRegistradas() {
        try {
            ArrayList<DtMaterial> materiales = icon.consultarDonacionesRegistradas();
            
            if (materiales == null || materiales.isEmpty()) {
                return "NO_HAY_DONACIONES";
            }
            
            StringBuilder resultado = new StringBuilder();
            
            for (DtMaterial material : materiales) {
                if (material instanceof DtLibro) {
                    DtLibro libro = (DtLibro) material;
                    resultado.append("LIBRO|")
                             .append("ID: ").append(libro.getId()).append("|")
                             .append("Título: ").append(libro.getTitulo()).append("|")
                             .append("Páginas: ").append(libro.getCantPaginas())
                             .append("\n");
                } else if (material instanceof DtArticulo) {
                    DtArticulo articulo = (DtArticulo) material;
                    resultado.append("ARTICULO|")
                             .append("ID: ").append(articulo.getId()).append("|")
                             .append("Descripción: ").append(articulo.getDescripcion()).append("|")
                             .append("Peso: ").append(articulo.getPeso()).append(" kg|")
                             .append("Dimensiones: ").append(articulo.getDimensiones())
                             .append("\n");
                }
            }
            
            return resultado.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }
    
    // ========== CASO DE USO: AUTENTICACIÓN DE USUARIOS ==========
    @WebMethod
    public boolean autenticarUsuario(String email, String password) {
        return icon.autenticarUsuario(email, password);
    }
    
    // ========== CASO DE USO: CAMBIAR ZONA DE LECTOR ==========
    @WebMethod
    public boolean cambiarZonaLector(String email, String nuevaZona) {
        try {
            Zona zona = Zona.valueOf(nuevaZona);
            icon.cambiarZonaLector(email, zona);
            return true;
        } catch (NoExisteUsuarioException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    // ========== CASO DE USO: AGREGAR PRÉSTAMO ==========
    @WebMethod
    public String agregarPrestamo(String lectorEmail, String bibliotecarioEmail, String numeroEmpleado, 
                                  int materialId, int diaSol, int mesSol, int anioSol, 
                                  int diaDev, int mesDev, int anioDev, String estado) {
        try {
            // Formatear fechas como yyyy-MM-dd y usar valueOf
            String fechaSolStr = String.format("%04d-%02d-%02d", anioSol, mesSol, diaSol);
            String fechaDevStr = String.format("%04d-%02d-%02d", anioDev, mesDev, diaDev);
            
            Date fechaSolicitud = Date.valueOf(fechaSolStr);
            Date fechaDevolucion = Date.valueOf(fechaDevStr);
            datatypes.EstadoPrestamo estadoEnum = datatypes.EstadoPrestamo.valueOf(estado);
            
            icon.agregarPrestamo(lectorEmail, bibliotecarioEmail, numeroEmpleado, 
                               materialId, fechaSolicitud, fechaDevolucion, estadoEnum);
            
            return String.format("SUCCESS|%s|%s|%d|%s", lectorEmail, bibliotecarioEmail, materialId, estado);
        } catch (Exception e) {
            return "ERROR|" + e.getMessage();
        }
    }
    
    // ========== CASO DE USO: ACTUALIZAR ESTADO DE PRÉSTAMO ==========
    @WebMethod
    public String actualizarEstadoPrestamo(String lectorEmail, String bibliotecarioEmail, 
                                          int materialId, String nuevoEstado) {
        try {
            datatypes.EstadoPrestamo estadoEnum = datatypes.EstadoPrestamo.valueOf(nuevoEstado);
            icon.actualizarEstadoPrestamo(lectorEmail, bibliotecarioEmail, materialId, estadoEnum);
            
            return String.format("SUCCESS|%s|%s|%d|%s", lectorEmail, bibliotecarioEmail, materialId, nuevoEstado);
        } catch (Exception e) {
            return "ERROR|" + e.getMessage();
        }
    }
    
    // ========== CASO DE USO: CONSULTAR PRÉSTAMOS ==========
    @WebMethod
    public String consultarPrestamos() {
        try {
            ArrayList<DtPrestamo> prestamos = icon.obtenerPrestamos();
            
            if (prestamos == null || prestamos.isEmpty()) {
                return "NO_HAY_PRESTAMOS";
            }
            
            StringBuilder resultado = new StringBuilder();
            
            for (DtPrestamo prestamo : prestamos) {
                resultado.append("PRESTAMO|")
                         .append("Lector: ").append(prestamo.getLector() != null ? prestamo.getLector() : "N/A").append("|")
                         .append("Bibliotecario: ").append(prestamo.getBibliotecario() != null ? prestamo.getBibliotecario() : "N/A").append("|")
                         .append("Material ID: ").append(prestamo.getMaterial()).append("|")
                         .append("Estado: ").append(prestamo.getEstado() != null ? prestamo.getEstado().toString() : "N/A").append("|")
                         .append("Fecha Solicitud: ").append(prestamo.getFechaSolicitud() != null ? prestamo.getFechaSolicitud().toString() : "N/A").append("|")
                         .append("Fecha Devolución: ").append(prestamo.getFechaDevolucion() != null ? prestamo.getFechaDevolucion().toString() : "N/A")
                         .append("\n");
            }
            
            return resultado.toString();
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    @WebMethod
    public String obtenerMailsLectores() {
        ArrayList<String> emails = icon.obtenerMailLectores();
        return String.join(",", emails);
    }
    
    @WebMethod
    public String obtenerMailsBibliotecarios() {
        ArrayList<String> emails = icon.obtenerMailBibliotecarios();
        return String.join(",", emails);
    }
    
    @WebMethod
    public String obtenerIdsMateriales() {
        ArrayList<Integer> ids = icon.obtenerIdMateriales();
        return ids.stream().map(String::valueOf).reduce((a, b) -> a + "," + b).orElse("");
    }
}
