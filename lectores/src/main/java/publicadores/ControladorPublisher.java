package publicadores;

import java.sql.Date;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

import datatypes.DtBibliotecario;
import datatypes.DtLector;
import datatypes.EstadoLector;
import datatypes.Zona;
import excepciones.ExisteUsuarioException;
import excepciones.NoExisteUsuarioException;
import excepciones.ValorIncorrectoDeEstadoException;
import excepciones.ValorIncorrectoDeZonaException;
import interfaces.Fabrica;
import interfaces.IControlador;

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
}
