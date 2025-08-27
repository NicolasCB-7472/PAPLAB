package logica;
import datatypes.EstadoLector;
import datatypes.Zona;
import java.sql.Date;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("L")
public class Lector extends Usuario{
    private String direccion;
    private Date FechaRegistro;
    private EstadoLector estado = EstadoLector.ACTIVO;

    private Zona zona;

    //Constructores
    public Lector(){
        super();
    }
    public Lector(String nombre, String email, String direccion, Zona zona, Date fecha){
        super(nombre , email);
        this.direccion = direccion;
        this.zona = zona;
        this.FechaRegistro=fecha;
    }
    //Setters and Getters
    public String getDireccion(){
        return this.direccion;
    }

    public Date getFechaRegistro(){
        return this.FechaRegistro;
    }

    public EstadoLector getEstadoLector(){
        return this.estado;
    }

    public Zona getZona(){
        return this.zona;
    }

    public void setDireccion(String address){
        this.direccion=address;
    }

    public void setFechaRegistro(Date registro){
        this.FechaRegistro=registro;
    }

    public void setEstado(EstadoLector nuevo_estado){
        this.estado=nuevo_estado;
    }

    public void setZona(Zona nueva_zona){
        this.zona=nueva_zona;
    }    
}
