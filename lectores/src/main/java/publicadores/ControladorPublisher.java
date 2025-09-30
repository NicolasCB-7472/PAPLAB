package publicadores;

import java.sql.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import datatypes.DtLector;
import datatypes.EstadoLector;
import datatypes.Zona;
import excepciones.ExisteUsuarioException;
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
        } catch (ValorIncorrectoDeZonaException e) {
            // Zona inválida
            return null;
        }
    }
}
