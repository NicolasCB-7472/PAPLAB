package logica;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import datatypes.DtMaterial;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Material {
    @Id
    private String id;
    private Date fechaIngreso;
    
    @OneToMany(mappedBy="material_id",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Prestamo> Prestamo = new ArrayList<>();

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
    }

    public List<Prestamo> getPrestamos(){
        return this.Prestamo;
    }

    public void setPrestamos(List<Prestamo> prestamo_list){
        this.Prestamo=prestamo_list;
    } 

    public void agregarPrestamo(Prestamo p){
        this.Prestamo.add(p);
    }


    //Operaciones
    public abstract DtMaterial getDtMaterial();

    
}
