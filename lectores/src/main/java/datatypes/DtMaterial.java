package datatypes;
import logica.Material;

public abstract class DtMaterial  {
    private String id;
    private Date fechaIngreso;
    

    // Constructores
    public DtMaterial(){
        super();
    }
    
    public DtMaterial(String id , Date fechaIng){
        super();
        this.id = id;
        this.fechaIngreso = fechaIng;
    }
    public DtMaterial(Material M){// Esto quiero preguntarle al Yona
        super();
        this.id = M.getId();
        this.fechaIngreso = M.getFechaIngreso();
    }
    // Getters
    public String getId(){
        return this.id;
    }

    public Date getFechaIngreso(){
        return this.fechaIngreso;
    }
}
