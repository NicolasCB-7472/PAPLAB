package datatypes;

import java.sql.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DtLector", propOrder = {
    "direccion",
    "FechaRegistro",
    "estado",
    "zona"
})

public class DtLector extends DtUsuario {
    private String direccion;
    private Date FechaRegistro;
    private EstadoLector estado;
    private Zona zona;

    public DtLector(String nombre, String email, String address, Date Registro, 
                    EstadoLector estado_lector, Zona zona_lector){
        super(nombre, email);
        this.direccion=address;
        this.FechaRegistro=Registro;
        this.estado=estado_lector;
        this.zona=zona_lector;
    }

    public String getDireccion(){
        return this.direccion;
    }

    public Date getFechaRegistro(){
        return this.FechaRegistro;
    }

    public EstadoLector getEstado(){
        return this.estado;
    }

    public Zona getZona(){
        return this.zona;
    }

    public String toString() {
        return super.toString() + " \n|DIRECCION = " + direccion + " |FECHA REGISTRO = " + FechaRegistro +
                                  " \n|ESTADO = " + estado + " \n|ZONA= " + zona + "|";
    }

}
