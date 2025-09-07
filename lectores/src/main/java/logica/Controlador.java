package logica;



import java.sql.Date;
import java.util.ArrayList;

import datatypes.DtMaterial;
import datatypes.DtPrestamo;
import datatypes.EstadoLector;
import datatypes.EstadoPrestamo;
import datatypes.Zona;
import datatypes.DtLector;
import datatypes.DtBibliotecario;
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
    public void  registrarLector(String nombre, String email, String direccion, Zona zona, Date fecha)throws ExisteUsuarioException{

        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        boolean existe = MU.existeUsuario(email);
        if(existe){
            throw new ExisteUsuarioException("Ya existe un usuario con el email dado");
        }
        else{
            Lector L = new Lector(nombre, email, direccion, zona, fecha);
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
                    MU.confirmarCambiosUsuario(L);
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
                MU.confirmarCambiosUsuario(L);
            }
            else{
                throw new NoExisteUsuarioException("No existe un lector con el email dado");
            }
        }
        else{
            throw new NoExisteUsuarioException("No existe un lector con el email dado");
        }
    }

    public void agregarPrestamo(String lec_mail, String bib_mail, String numEmpleado, Integer mat_id, Date fecha_sol,  Date fecha_dev, EstadoPrestamo estado)
        throws EmpleadoyCasteoNoValidoException, FechasIncorrectasException, PrestamoIncorrectoException {
        
        // Validar fechas
        if (fecha_sol.compareTo(fecha_dev) >= 0) {
            throw new FechasIncorrectasException("La fecha de solicitud debe ser anterior a la fecha de devolución");
        }
        
        // Obtener entidades
        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        ManejadorMaterial MM = ManejadorMaterial.getInstancia();
        
        Usuario l = MU.buscarUsuario(lec_mail);
        Usuario b = MU.buscarUsuario(bib_mail);
        Material m = MM.buscarMaterial_PorID(mat_id);
        
        // Validar que existen las entidades
        if (l == null || b == null || m == null) {
            throw new PrestamoIncorrectoException("Lector, bibliotecario o material no se encuentran");
        }
        
        // Validar tipos y empleado
        if (!(l instanceof Lector)) {
            throw new EmpleadoyCasteoNoValidoException("El email no corresponde a un lector");
        }
        
        if (!(b instanceof Bibliotecario)) {
            throw new EmpleadoyCasteoNoValidoException("El email no corresponde a un bibliotecario");
        }
        
        Bibliotecario bibliotecario = (Bibliotecario) b;
        if (!bibliotecario.getNumeroEmpleado().equals(numEmpleado)) {
            throw new EmpleadoyCasteoNoValidoException("El número de empleado no coincide con el bibliotecario");
        }
        
        // Crear préstamo
        Lector lector = (Lector) l;
        Prestamo prestamo = new Prestamo(lector, bibliotecario, m, fecha_sol, fecha_dev, EstadoPrestamo.EN_CURSO);
        
        // Agregar préstamo a las entidades relacionadas
        lector.agregarPrestamo(prestamo);
        bibliotecario.agregarPrestamo(prestamo);
        m.agregarPrestamo(prestamo);
        
        // Persistir en la base de datos usando el manejador
        ManejadorPrestamo MP = ManejadorPrestamo.getInstancia();
        MP.agregarPrestamo(prestamo);
    }


    public void agregarNuevoLibro(String id,String titulo , int cantPaginas)throws CantidadDePaginasNoValidaException, TituloNoValidoException{
        if(cantPaginas <= 0){
            throw new CantidadDePaginasNoValidaException("La cantidad de paginas debe ser mayor a 0");
        }
        if(titulo == ""){
            throw new TituloNoValidoException("El titulo del libro no puede ser vacio");
        }
        ManejadorMaterial MM = ManejadorMaterial.getInstancia();
        Libro nuevoLibro = new Libro(id,getFechaActual(),titulo, cantPaginas); // Solucionar tema id.
        MM.agregarMaterial(nuevoLibro);
    }

    public void agregarNuevoArticulo(String id,String descripcion, float peso , String dimensiones)throws DescripcionNoValidaException, PesoNoValidoException{
        if(descripcion == ""){
            throw new DescripcionNoValidaException("La descripcion del articulo no puede ser vacia");
        }
        if(peso <= 0){
            throw new PesoNoValidoException("El peso debe ser mayor que 0"); 
        }
        ManejadorMaterial MM = ManejadorMaterial.getInstancia();
        Articulo nuevoArticulo = new Articulo(id,getFechaActual(),peso,descripcion,dimensiones);
        MM.agregarMaterial(nuevoArticulo);
    }

    public ArrayList<DtMaterial> consultarDonacionesRegistradas(){
        ManejadorMaterial MM = ManejadorMaterial.getInstancia();
        return MM.obtenerDataMateriales();
    }

    public ArrayList<DtMaterial> consultarDonacionesRegistradasConFecha(Date fechaMenor, Date fechaMayor){
        ManejadorMaterial MM = ManejadorMaterial.getInstancia();
        return MM.obtenerDataMaterialesEntreFechas(fechaMenor , fechaMayor);
    }

    public Date getFechaActual(){
        return new java.sql.Date(System.currentTimeMillis());
    }

    public ArrayList<String> obtenerMailLectores(){
        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        return MU.obtenerLectores();
    }

    public ArrayList<String> obtenerMailBibliotecarios(){
        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        return MU.obtenerBibliotecarios();
    }

    public ArrayList<Integer> obtenerIdMateriales(){
        ManejadorMaterial MM = ManejadorMaterial.getInstancia();
        return MM.obtenerMateriales();
    }
    
    public String obtenerNumeroEmpleadoBibliotecario(String emailBibliotecario) throws NoExisteUsuarioException {
        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        
        if (!MU.existeUsuario(emailBibliotecario)) {
            throw new NoExisteUsuarioException("No existe un bibliotecario con el email: " + emailBibliotecario);
        }
        
        Usuario usuario = MU.darUsuario(emailBibliotecario);
        if (usuario instanceof Bibliotecario) {
            Bibliotecario bibliotecario = (Bibliotecario) usuario;
            return bibliotecario.getNumeroEmpleado();
        } else {
            throw new NoExisteUsuarioException("El email no corresponde a un bibliotecario");
        }
    }

    // Métodos para gestión de préstamos
    public void actualizarEstadoPrestamo(String lectorEmail, String bibliotecarioEmail, Integer materialId, EstadoPrestamo nuevoEstado) 
        throws NoExisteUsuarioException, PrestamoIncorrectoException {
        
        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        ManejadorMaterial MM = ManejadorMaterial.getInstancia();
        ManejadorPrestamo MP = ManejadorPrestamo.getInstancia();
        
        // Verificar que el lector existe
        if (!MU.existeUsuario(lectorEmail)) {
            throw new NoExisteUsuarioException("No existe un lector con el email: " + lectorEmail);
        }
        
        // Verificar que el bibliotecario existe
        if (!MU.existeUsuario(bibliotecarioEmail)) {
            throw new NoExisteUsuarioException("No existe un bibliotecario con el email: " + bibliotecarioEmail);
        }
        
        // Verificar que el material existe
        Material material = MM.buscarMaterial_PorID(materialId);
        if (material == null) {
            throw new PrestamoIncorrectoException("No existe un material con el ID: " + materialId);
        }
        
        // Buscar el préstamo usando el manejador
        Prestamo prestamo = MP.buscarPrestamo(lectorEmail, bibliotecarioEmail, materialId);
        if (prestamo == null) {
            throw new PrestamoIncorrectoException("No existe un préstamo con los datos proporcionados");
        }
        
        // Actualizar el estado del préstamo
        prestamo.setEstado(nuevoEstado);
        
        // Persistir los cambios usando el manejador
        MP.actualizarPrestamo(prestamo);
    }
    
    public ArrayList<DtPrestamo> obtenerPrestamos() {
        ManejadorPrestamo MP = ManejadorPrestamo.getInstancia();
        return MP.obtenerDataPrestamos();
    }
    
    public DtPrestamo obtenerPrestamo(String lectorEmail, String bibliotecarioEmail, Integer materialId) 
        throws PrestamoIncorrectoException {
        
        ManejadorPrestamo MP = ManejadorPrestamo.getInstancia();
        Prestamo prestamo = MP.buscarPrestamo(lectorEmail, bibliotecarioEmail, materialId);
        
        if (prestamo == null) {
            throw new PrestamoIncorrectoException("No existe un préstamo con los datos proporcionados");
        }
        
        return new DtPrestamo(
            prestamo.getLector().getEmail(),
            prestamo.getBibliotecario().getEmail(),
            prestamo.getMaterial().getId().toString(),
            new java.sql.Date(prestamo.getFechaSolicitud().getTime()),
            new java.sql.Date(prestamo.getFechaDevolucion().getTime()),
            prestamo.getEstado()
        );
    }

    public ArrayList<DtLector> obtenerDataLectores(){
        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        return MU.obtenerDataLectores();
    }

    public ArrayList<DtBibliotecario> obtenerDataBibliotecario(){
        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        return MU.obtenerDataBibliotecarios();
    }
};






