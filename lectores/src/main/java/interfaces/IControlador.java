package interfaces;

import java.sql.Date;
import java.util.ArrayList;

import datatypes.DtMaterial;
import datatypes.DtPrestamo;
import datatypes.EstadoLector;
import datatypes.EstadoPrestamo;
import datatypes.Zona;
import excepciones.CantidadDePaginasNoValidaException;
import excepciones.DescripcionNoValidaException;
import excepciones.EmpleadoyCasteoNoValidoException;
import excepciones.ExisteUsuarioException;
import excepciones.FechasIncorrectasException;
import excepciones.NoExisteUsuarioException;
import excepciones.PesoNoValidoException;
import excepciones.PrestamoIncorrectoException;
import excepciones.TituloNoValidoException;
import excepciones.ValorIncorrectoDeEstadoException;
import excepciones.ValorIncorrectoDeZonaException;

public interface IControlador{
    /// Usuarios
    //1)
    public void registrarLector(String nombre, String email, String direccion, Zona zona, Date fecha)throws ExisteUsuarioException;

    public void registrarBibliotecario(String nombre, String email, String nroEmpleado)throws ExisteUsuarioException;

    public void cambiarEstadoLector(String email , EstadoLector nuevoEstado) throws NoExisteUsuarioException , ValorIncorrectoDeEstadoException;

    public void cambiarZonaLector(String email , Zona nuevaZona)throws NoExisteUsuarioException, ValorIncorrectoDeZonaException;

    //// Materiales
    //2)
    public void agregarNuevoLibro(String id, String titulo, int cantPaginas)throws CantidadDePaginasNoValidaException, TituloNoValidoException;

    public void agregarNuevoArticulo(String id, String descripcion, float peso, String dimensiones)throws DescripcionNoValidaException, PesoNoValidoException;

    public ArrayList<DtMaterial> consultarDonacionesRegistradas();

    public ArrayList<DtMaterial> consultarDonacionesRegistradasConFecha(Date fechaMenor, Date fechaMayor);

    /// Prestamos
    //3)
    public void agregarPrestamo(String lec_mail, String bib_mail, String numEmpleado, Integer mat_id, Date fecha_sol,  Date fecha_dev, EstadoPrestamo estado)
        throws EmpleadoyCasteoNoValidoException, FechasIncorrectasException, PrestamoIncorrectoException;

    /// Gestión de Préstamos
    public void actualizarEstadoPrestamo(String lectorEmail, String bibliotecarioEmail, Integer materialId, EstadoPrestamo nuevoEstado) 
        throws NoExisteUsuarioException, PrestamoIncorrectoException;
    
    public ArrayList<DtPrestamo> obtenerPrestamos();
    
    public DtPrestamo obtenerPrestamo(String lectorEmail, String bibliotecarioEmail, Integer materialId) 
        throws PrestamoIncorrectoException;

    /// Extras
    public ArrayList<String> obtenerMailLectores();
    
    public ArrayList<String> obtenerMailBibliotecarios();

    public ArrayList<Integer> obtenerIdMateriales();
    
    public String obtenerNumeroEmpleadoBibliotecario(String emailBibliotecario) throws NoExisteUsuarioException;
}