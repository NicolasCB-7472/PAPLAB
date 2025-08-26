package datatypes;
import logica.Articulo;

public class DtArticulo extends DtMaterial{
    private float pesoKg;
    private String descripcion;
    private String  dimensiones;

    //Constructores
    public DtArticulo(){
        super();
    }

    public DtArticulo(String id, Date fechaIng , float peso , String descripcion , String dimensiones){
        super(id,fechaIng);
        this.pesoKg = peso;
        this.descripcion = descripcion;
        this.dimensiones = dimensiones;
    }

    // Getters y setters

    public float getPeso(){
        return this.pesoKg;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public String getDimensiones(){
        return this.dimensiones;
    }

}
