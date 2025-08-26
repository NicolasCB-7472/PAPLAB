package logica;

import datatypes.EstadoLector;
import datatypes.Zona;
import excepciones.ExisteUsuarioException;
import excepciones.NoExisteUsuarioException;
import excepciones.ValorIncorrectoDeEstadoException;
import excepciones.ValorIncorrectoDeZonaException;
import interfaces.IControlador;

public class Controlador implements IControlador{
    private static Controlador instancia = null;

    private Controlador(){};

    public static synchronized Controlador get(){
        if(instancia == null){
            instancia = new Controlador();
        }
        return instancia;
    }
    //Funciones de las historias de usuario
    public void  registrarLector(String nombre, String email, String direccion, Zona zona)throws ExisteUsuarioException{

        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        boolean existe = MU.existeUsuario(email);
        if(existe){
            throw new ExisteUsuarioException("Ya existe un usuario con el email dado");
        }
        else{
            Lector L = new Lector(nombre, email, direccion, zona);
            MU.agregarUsuario(L);
        }
    }

    public void registrarBibliotecario(String nombre, String email, String nroEmpleado)throws ExisteUsuarioException{//Definir si nroEmpleado es autoincremental.
        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        boolean existe = MU.existeUsuario(email); //Puede haber un usuario que sea ambos, lector y bibliotecario?
        if(existe){
            throw new ExisteUsuarioException("Ya existe un usuario con el email dado");
        }
        else{
            Bibliotecario B = new Bibliotecario(nombre, email, nroEmpleado);
            MU.agregarUsuario(B);
        }
    }

    public void cambiarEstadoLector(String email , EstadoLector nuevoEstado) throws NoExisteUsuarioException , ValorIncorrectoDeEstadoException{
        boolean estadoValido = false;
        for(EstadoLector E : EstadoLector.values()){
            if(E == nuevoEstado){
                estadoValido = true;
            }
        }
        if(!estadoValido){
            throw new ValorIncorrectoDeEstadoException("Valor de estado incorrecto");
        }
            ManejadorUsuario MU = ManejadorUsuario.getInstancia();
            boolean existe = MU.existeUsuario(email);
            if(existe){
                Usuario U = MU.darUsuario(email);
                if(U instanceof Lector){
                    Lector L = (Lector) U;
                    L.setEstado(nuevoEstado);
                }
                else{
                    throw new NoExisteUsuarioException("No existe un lector con el email dado");
                }
            }
            else{
                throw new NoExisteUsuarioException("No existe un lector con el email dado");
            }
    }

    public void cambiarZonaLector(String email , Zona nuevaZona)throws NoExisteUsuarioException, ValorIncorrectoDeZonaException{
        boolean zonaValida = false;
        for(Zona E : Zona.values()){
            if(E == nuevaZona){
                zonaValida = true;
            }
        }
        if(!zonaValida){
            throw new ValorIncorrectoDeZonaException("Valor de estado incorrecto");
        }
        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        boolean existe = MU.existeUsuario(email); //Puede haber un usuario que sea ambos, lector y bibliotecario?
        if(existe){
            Usuario U = MU.darUsuario(email);
            if(U instanceof Lector){
                Lector L = (Lector) U;
                L.setZona(nuevaZona);
            }
            else{
                throw new NoExisteUsuarioException("No existe un lector con el email dado");
            }
        }
        else{
            throw new NoExisteUsuarioException("No existe un lector con el email dado");
        }
    }
}
