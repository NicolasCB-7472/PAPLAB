package logica;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import datatypes.DtBibliotecario;
import datatypes.DtUsuario;

@Entity
@DiscriminatorValue("B")
public class Bibliotecario extends Usuario {
    private String numeroEmpleado;

    // Constructores
    public Bibliotecario(){
        super();
    }

    public Bibliotecario(String codigo){
        super();
        this.numeroEmpleado=codigo;
    }

    public Bibliotecario(String username, String mail, String codigo){
        super(username, mail);
        this.numeroEmpleado=codigo;
    }

    // Setters and getters
    public String getNumeroEmpleado(){
        return this.numeroEmpleado;
    }

    public void setNumeroEmpleado(String codigo){
        this.numeroEmpleado=codigo;
    }

    @Override 
    public DtUsuario getDtUsuario(){
        return new DtBibliotecario(this.getNombre(), this.getEmail(), this.getNumeroEmpleado());
    }

}
