package datatypes;
import java.sql.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DtPrestamo", propOrder = {
    "lector_mail",
    "bibliotecario_mail",
    "material_id",
    "fechaSolicitud",
    "fechaDevolucion",
    "estado"
})


public class DtPrestamo {
    private String lector_mail;
    private String bibliotecario_mail;
    private String material_id;
    private Date fechaSolicitud;
    private Date fechaDevolucion;
    private EstadoPrestamo estado;
    
    public DtPrestamo(){
        super();

    }

    public DtPrestamo(String lector_mail, String bibliotecario_mail, String material_id, Date fechaSol, Date fechaDev, EstadoPrestamo estado){
        this.lector_mail = lector_mail;
        this.bibliotecario_mail = bibliotecario_mail;
        this.material_id = material_id;
        this.fechaSolicitud = fechaSol;
        this.fechaDevolucion = fechaDev;
        this.estado = estado;
    }

    public Date getFechaSolicitud() {
        return this.fechaSolicitud;
    }

    public Date getFechaDevolucion() {
        return this.fechaDevolucion;
    }

    public EstadoPrestamo getEstado() {
        return this.estado;
    }

    public String getLector(){
        return this.lector_mail;
    }

    public String getBibliotecario(){
        return this.bibliotecario_mail;
    }

    public String getMaterial(){
        return this.material_id;
    }
}
