package logica;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Usuario {
    private String nombre;
    @Id
    private String email;

    @OneToMany(mappedBy="usuario_mail",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Prestamo> Prestamo = new ArrayList<>();

    // Constructores
    public Usuario(){
        super();
    }

    public Usuario(String name, String mail){
        super();
        this.nombre=name;
        this.email=mail;
    }

    // Setters and getters
    public String getNombre(){
        return this.nombre;
    }

    public String getEmail(){
        return this.email;
    }

    public void setNombre(String username){
        this.nombre=username;
    }

    public void setEmail(String mail){
        this.email=mail;
    }

    // Operaciones
    
}

