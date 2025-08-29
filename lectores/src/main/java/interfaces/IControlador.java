package interfaces;

import java.util.ArrayList;

import datatypes.EstadoLector;
import datatypes.Zona;
import excepciones.CantidadDePaginasNoValidaException;
import excepciones.DescripcionNoValidaException;
import excepciones.ExisteUsuarioException;
import excepciones.NoExisteUsuarioException;
import excepciones.PesoNoValidoException;
import excepciones.TituloNoValidoException;
import excepciones.ValorIncorrectoDeEstadoException;
import excepciones.ValorIncorrectoDeZonaException;
import java.sql.Date;

public interface IControlador{
    //1)
    public void registrarLector(String nombre, String email, String direccion, Zona zona, Date fecha)throws ExisteUsuarioException;

    public void registrarBibliotecario(String nombre, String email, String nroEmpleado)throws ExisteUsuarioException;

    public void cambiarEstadoLector(String email , EstadoLector nuevoEstado) throws NoExisteUsuarioException , ValorIncorrectoDeEstadoException;

    public void cambiarZonaLector(String email , Zona nuevaZona)throws NoExisteUsuarioException, ValorIncorrectoDeZonaException;

    ////
    //2)
    public void agregarNuevoLibro(String titulo , int cantPaginas)throws CantidadDePaginasNoValidaException, TituloNoValidoException;

    public void agregarNuevoArticulo(String descripcion, float peso , String dimensiones )throws DescripcionNoValidaException, PesoNoValidoException;

    public ArrayList<DtMaterial> consultarDonacionesRegistradas();

    public ArrayList<DtMaterial> consultarDonacionesRegistradasConFecha(Date fechaMenor, Date fechaMayor);
}