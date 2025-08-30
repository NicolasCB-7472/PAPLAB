package logica;
import java.util.Date;
import datatypes.EstadoPrestamo;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


import persistencia.PrestamoID;

@Entity
@IdClass(PrestamoID.class)
public class Prestamo{
    @Id
    @ManyToOne
    @JoinColumn(
        insertable=false,
        updatable=false
    )
    private Usuario usuario_mail;

    @Id
    @ManyToOne
    @JoinColumn(
        insertable=false,
        updatable=false
    )
    private Material material_id;

    private Date fechaSolicitud;
    private Date fechaDevolucion;
    private EstadoPrestamo estado;

    //constructores

    public Prestamo(){
        super(); //opcional ya que en si se hereda el constructor de la clase objetos

    }

      public Prestamo(Usuario user, Material mat, Date fechaSolicitud, Date fechaDevolucion, EstadoPrestamo estado) {
        super();
        this.usuario_mail=user;
        this.material_id=mat;
        this.fechaSolicitud = fechaSolicitud; // el bebe
        this.fechaDevolucion = fechaDevolucion;
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

    public Usuario getUsuario(){
        return this.usuario_mail;
    }

    public Material getMaterial(){
        return this.material_id;
    }


    //setters
    
    public void setFechaSolucitud(Date SolicitudDeLaFecha){
        this.fechaSolicitud= SolicitudDeLaFecha;
    }


    public void setFechaDevolucion(Date DevolucionDeLaFecha){
        this.fechaDevolucion= DevolucionDeLaFecha;
    }

    public void setEstado(EstadoPrestamo estadoActual){
        this.estado= estadoActual;
    }

     public void setUsuario(Usuario user){
        this.usuario_mail=user;
    }

    public void setMaterial(Material mat){
        this.material_id=mat;
    }

}

