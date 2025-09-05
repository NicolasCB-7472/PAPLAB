package datatypes;

import java.sql.Date;

public class DtLector extends DtUsuario {
    private String direccion;
    private Date FechaRegistro;
    private EstadoLector estado;
    private Zona zona;

    public DtLector(String nombre, String email, String address, Date Registro, 
                    EstadoLector estado_lector, Zona zona_lector){
        super(nombre, email);
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
