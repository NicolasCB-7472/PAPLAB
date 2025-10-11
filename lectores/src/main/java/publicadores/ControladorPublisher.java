package publicadores;

import java.sql.Date;
import java.util.ArrayList;

import datatypes.DtArticulo;
import datatypes.DtBibliotecario;
import datatypes.DtLector;
import datatypes.DtLibro;
import datatypes.DtMaterial;
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
    public DtLector registrarLector(String nombre, String email, String direccion, String zona, Date fecha) {
        try {
            Zona zonaEnum = Zona.valueOf(zona);
            icon.registrarLector(nombre, email, direccion, zonaEnum, fecha);
            
            // Devolver DtLector con los datos registrados
            return new DtLector(nombre, email, direccion, fecha, EstadoLector.ACTIVO, zonaEnum);
        } catch (ExisteUsuarioException e) {
            // Usuario ya existe
            return null;
        }
    }
    
    // ========== CASO DE USO: REGISTRAR BIBLIOTECARIOS ==========
    @WebMethod
    public DtBibliotecario registrarBibliotecario(String nombre, String email, String numeroEmpleado) {
        try {
            icon.registrarBibliotecario(nombre, email, numeroEmpleado);
            
            // Devolver DtBibliotecario con los datos registrados
            return new DtBibliotecario(nombre, email, numeroEmpleado);
        } catch (ExisteUsuarioException e) {
            // Usuario ya existe
            return null;
        }
    }
    
    // ========== CASO DE USO: MODIFICAR ESTADO DE LECTOR ==========
    @WebMethod
    public boolean cambiarEstadoLector(String email, String nuevoEstado) {
        try {
            EstadoLector estado = EstadoLector.valueOf(nuevoEstado);
            icon.cambiarEstadoLector(email, estado);
            
            // Devolver confirmación de éxito
            return true;
        } catch (NoExisteUsuarioException e) {
            // Lector no existe
            return false;
        } catch (ValorIncorrectoDeEstadoException e) {
            // Estado inválido
            return false;
        } 
        
    }
    
    // ========== CASO DE USO: REGISTRAR NUEVA DONACIÓN DE LIBROS ==========
    @WebMethod
    public DtLibro registrarDonacionLibro(String id, String titulo, int cantPaginas) {
        try {
            icon.agregarNuevoLibro(id, titulo, cantPaginas);
            
            // Devolver DtLibro con los datos registrados
            return new DtLibro(id, icon.getFechaActual(), titulo, cantPaginas);
        } catch (CantidadDePaginasNoValidaException e) {
            // Cantidad de páginas inválida
            return null;
        } catch (TituloNoValidoException e) {
            // Título inválido
            return null;
        }
    }
    
    // ========== CASO DE USO: REGISTRAR NUEVA DONACIÓN DE ARTÍCULO ESPECIAL ==========
    @WebMethod
    public DtArticulo registrarDonacionArticulo(String id, String descripcion, float peso, String dimensiones) {
        try {
            icon.agregarNuevoArticulo(id, descripcion, peso, dimensiones);
            
            // Devolver DtArticulo con los datos registrados
            return new DtArticulo(id, icon.getFechaActual(), peso, descripcion, dimensiones);
        } catch (DescripcionNoValidaException e) {
            // Descripción inválida
            return null;
        } catch (PesoNoValidoException e) {
            // Peso inválido
            return null;
        }
    }
    
    // ========== CASO DE USO: CONSULTAR TODAS LAS DONACIONES REGISTRADAS ==========
    @WebMethod
    public DtMaterial[] consultarDonacionesRegistradas() {
        ArrayList<DtMaterial> materiales = icon.consultarDonacionesRegistradas();
        DtMaterial[] arrMateriales = new DtMaterial[materiales.size()];
        for(int i = 0; i < materiales.size(); i++){
            arrMateriales[i] = materiales.get(i);
        }
        return arrMateriales;
    }


    




    
}
