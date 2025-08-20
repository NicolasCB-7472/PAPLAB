package datatypes;

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
        return super.toString() + "\nNUMERO EMPLEADO = " + numero_empleado;
    }
}
