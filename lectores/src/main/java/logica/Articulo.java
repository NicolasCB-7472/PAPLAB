package logica;

import java.sql.Date;


import datatypes.DtArticulo;
import datatypes.DtMaterial;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Articulo extends Material{
    private float pesoKg;
    private String descripcion;
    private String  dimensiones;

    //Constructores
    public Articulo(){
        super();
    }

    public Articulo(String id, Date fechaIng , float peso , String descripcion , String dimensiones){
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

    public void setDescripcion(String des){
        this.descripcion = des;
    }
    
    public void setDimensiones(String dim){
        this.dimensiones = dim;
    }

    // Operaciones

    @Override
	public DtMaterial getDtMaterial() {
		return new DtArticulo(this.getId(), this.getFechaIngreso(), this.getPeso(), this.getDescripcion() ,this.getDimensiones());
	}


    
}
