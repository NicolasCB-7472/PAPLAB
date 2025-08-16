package logica;
import java.sql.Date;
public abstract class Material {
    private String id;
    private Date fechaIngreso;
    

    // Constructores
    public Material(){
        super();
    }
    
    public Material(String id , Date fechaIng){
        super();
        this.id = id;
        this.fechaIngreso = fechaIng;
    }
    // Getters y setters
    public String getId(){
        return this.id;
    }

    public Date getFechaIngreso(){
        return this.fechaIngreso;
    }
    
    public void setId(String id){
        this.id = id;
    }

    public void setFechaIngreso(Date fechaIng){
        this.fechaIngreso = fechaIng;
    }//Deberia tener forma de modificar estos datos?

    //Operaciones
    
}
