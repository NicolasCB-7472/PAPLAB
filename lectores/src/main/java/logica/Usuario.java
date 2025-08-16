package logica;

public abstract class Usuario {
    
    private String nombre;
    private String email;

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

