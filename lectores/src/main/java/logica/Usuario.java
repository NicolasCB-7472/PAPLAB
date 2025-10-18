package logica;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Id;


import datatypes.DtUsuario;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Usuario {
    private String nombre;
    @Id
    private String email;
    private String passwordHash; // Contraseña hasheada

    // Constructores
    public Usuario(){
        super();
    }

    public Usuario(String name, String mail){
        super();
        this.nombre=name;
        this.email=mail;
        this.passwordHash = null; // Se establecerá después
    }
    
    public Usuario(String name, String mail, String password){
        super();
        this.nombre=name;
        this.email=mail;
        this.passwordHash = hashPassword(password);
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
    
    public String getPasswordHash(){
        return this.passwordHash;
    }
    
    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }
    
    public void setPassword(String password){
        this.passwordHash = hashPassword(password);
    }

    // Operaciones
    public abstract DtUsuario getDtUsuario();
    
    // Método para hashear contraseñas
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al hashear contraseña", e);
        }
    }
    
    // Método para verificar contraseña
    public boolean verificarPassword(String password) {
        // Si no tiene contraseña establecida, no puede autenticarse
        if (this.passwordHash == null) {
            return false;
        }
        String hashedInput = hashPassword(password);
        return hashedInput.equals(this.passwordHash);
    }
    
}

