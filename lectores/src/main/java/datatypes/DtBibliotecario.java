package datatypes;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DtBibliotecario", propOrder = {
    "numero_empleado"
})
public class DtBibliotecario extends DtUsuario{
    private String numero_empleado;

    public DtBibliotecario(String nombre, String email, String codigo){
        super(nombre, email);
        this.numero_empleado=codigo;
    }

    public String getNumeroEmpleado(){
        return this.numero_empleado;
    }

    @Override public String toString(){
        return super.toString() + " \n|NUMERO EMPLEADO = " + numero_empleado + "|";
    }
}
