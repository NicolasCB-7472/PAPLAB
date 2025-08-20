package logica;

import datatypes.EstadoLector;
import datatypes.Zona;

public interface IControlador{
    //1)
    public void registrarLector(String nombre, String email, String direccion, Zona zona);

    public void registrarBibliotecario(String nombre, String email, String nroEmpleado);

    public void cambiarEstadoLector(String email , EstadoLector nuevoEstado);

    public void cambiarZonaLector(String email , Zona nuevaZona);

    ////
}