package datatypes;
import java.sql.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DtMaterial", propOrder = {
    "id",
    "fechaIngreso",
})


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

    // Getters
    public String getId(){
        return this.id;
    }

    public Date getFechaIngreso(){
        return this.fechaIngreso;
    }
}
