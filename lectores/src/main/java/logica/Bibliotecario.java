package logica;

import java.util.ArrayList;
import java.util.List;

import datatypes.DtBibliotecario;
import datatypes.DtUsuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("B")
public class Bibliotecario extends Usuario {
    private String numeroEmpleado;

    @OneToMany(mappedBy="bibliotecario_mail",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Prestamo> Prestamo = new ArrayList<>();

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
    public Bibliotecario(String username, String mail, String codigo, String password){
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

    // Set/Get Prestamo (Sin implementacion de borrar prestamo)
    public List<Prestamo> getPrestamos(){
        return this.Prestamo;
    }

    public void setPrestamos(List<Prestamo> prestamo_list){
        this.Prestamo=prestamo_list;
    }

    public void agregarPrestamo(Prestamo p){
        this.Prestamo.add(p);
    }

    @Override 
    public DtUsuario getDtUsuario(){
        return new DtBibliotecario(this.getNombre(), this.getEmail(), this.getNumeroEmpleado());
    }

}
