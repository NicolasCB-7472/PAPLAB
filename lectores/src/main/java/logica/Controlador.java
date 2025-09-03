package logica;



import java.sql.Date;
import java.util.ArrayList;

import datatypes.DtMaterial;
import datatypes.EstadoLector;
import datatypes.EstadoPrestamo;
import datatypes.Zona;
import excepciones.CantidadDePaginasNoValidaException;
import excepciones.DescripcionNoValidaException;
import excepciones.ExisteUsuarioException;
import excepciones.NoExisteUsuarioException;
import excepciones.PesoNoValidoException;
import excepciones.TituloNoValidoException;
import excepciones.ValorIncorrectoDeEstadoException;
import excepciones.ValorIncorrectoDeZonaException;
import interfaces.IControlador;
import jakarta.persistence.EntityManager;
import persistencia.Conexion;

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

    public void agregarPrestamo(String lec_mail, String bib_mail, String numEmpleado, Integer mat_id, Date fecha_sol,  Date fecha_dev, EstadoPrestamo estado){
        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        ManejadorMaterial MM = ManejadorMaterial.getInstancia();
        
        Usuario l = MU.buscarUsuario(lec_mail);
        Usuario b = MU.buscarUsuario(bib_mail);
        Material m = MM.buscarMaterial_PorID(mat_id);
        
        if(l != null && b != null && m != null){
            if(fecha_sol.compareTo(fecha_dev) < 0){
                //Fecha de solicitud antes que la fecha de devolucion y bibliotecario es empleado. Entonces
                if((l instanceof Lector) && (b instanceof Bibliotecario) && (m instanceof Material) 
                    && ((Bibliotecario) b).getNumeroEmpleado().equals(numEmpleado)){   
                        Prestamo p = new Prestamo((Lector) l,(Bibliotecario) b,(Material) m, fecha_sol, fecha_dev, EstadoPrestamo.EN_CURSO);
                                    
                        ((Lector) l).agregarPrestamo(p);

                        ((Bibliotecario) b).agregarPrestamo(p);
                        
                        ((Material) m).agregarPrestamo(p);

                        Conexion conexion = Conexion.getInstancia();
                        EntityManager em = conexion.getEntityManager(); 
                        em.getTransaction().begin();
                        //Nota, l, b y m tienen que ser managed por el em. (Que es el mismo en MU y MM, por Conexion.java)
                        em.persist(p);
                        em.getTransaction().commit();
                }else{
                    //Excepcion casteo incorrecto, o empleado no valido
                    //throw new EmpleadoyCasteoNoValidoException("Empleado no valido, o casteo incorrecto");
                }
            }else{
                //Excepcion fechas incorrectas
                //throw new FechasIncorrectasException("La fecha de solicitud es antes que la fecha de devolucion");
            }
        }else{
            //Excepcion no existe alguien
            //throw new PrestamoIncorrectoException("Lector, biblitoecario, o material no se encuentran");
        }
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


};






