package datatypes;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DtUsuario", propOrder = {
    "nombre",
    "email",
})

public class DtUsuario {
    private String nombre;
    private String email;

    public DtUsuario(String name, String mail){
        super();
        this.nombre=name;
        this.email=mail;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getEmail(){
        return this.email;
    }

    @Override
    public String toString(){
        return "|NOMBRE = " + nombre + " \n|EMAIL = " + email; 
    }
}
