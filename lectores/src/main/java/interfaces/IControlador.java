package interfaces;

import datatypes.EstadoLector;
import datatypes.Zona;
import excepciones.ExisteUsuarioException;
import excepciones.NoExisteUsuarioException;
import excepciones.ValorIncorrectoDeEstadoException;
import excepciones.ValorIncorrectoDeZonaException;

public interface IControlador{
    //1)
    public void registrarLector(String nombre, String email, String direccion, Zona zona)throws ExisteUsuarioException;

    public void registrarBibliotecario(String nombre, String email, String nroEmpleado)throws ExisteUsuarioException;

    public void cambiarEstadoLector(String email , EstadoLector nuevoEstado) throws NoExisteUsuarioException , ValorIncorrectoDeEstadoException;

    public void cambiarZonaLector(String email , Zona nuevaZona)throws ValorIncorrectoDeZonaException;

    ////
}