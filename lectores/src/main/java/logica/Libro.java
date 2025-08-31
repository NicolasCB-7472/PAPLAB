package logica;

import java.sql.Date;
import datatypes.DtLibro;
import datatypes.DtMaterial;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("L")
public class Libro extends Material{
    private String titulo;    
    private int cantPaginas; // Lo hice int en vez de string porque tiene mas sentido.

    public Libro(){
        super();
    }

    public Libro(String id , Date fechaIng , String titulo , int cantPaginas){
        super(id,fechaIng);
        this.titulo = titulo;
        this.cantPaginas = cantPaginas;
    }

    // Getters y setter
    public String getTitulo(){
        return this.titulo;
    }
    
    public int getCantPaginas(){
        return this.cantPaginas;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setCantPaginas(int cant){
        this.cantPaginas = cant;
    }

    // Operaciones
    
}
