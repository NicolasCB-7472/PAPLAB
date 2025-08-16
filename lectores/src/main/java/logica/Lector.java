package logica;

public class Lector extends Usuario{
    private String direccion;
    //private Date FechaRegistro;
    //private EstadoLector estado;
    //private Zona zona;

    //Constructores
    public Lector(){
        super();
    }

    //Setters and Getters
    public String getDireccion(){
        return this.direccion;
    }

    public void setDireccion(String address){
        this.direccion=address;
    }
    
}
