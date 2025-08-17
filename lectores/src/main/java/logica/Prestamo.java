package logica;
import java.util.Date;


public class Prestamo{
    private Date fechaSolicitud;
    private Date fechaDevolucion;
    private EstadoPrestamo estado;

    public(){
        super(); //opcional ya que en si se hereda el constructor de la clase objetos 

    }

    //constructores

    public Prestamo(){
        super(); //opcional ya que en si se hereda el constructor de la clase objetos

    }

      public Prestamo(Date fechaSolicitud, Date fechaDevolucion, EstadoPrestamo estado) {
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



}

