package logica;

import datatypes.EstadoPrestamo;
import datatypes.EstadoLector;
import datatypes.Zona;
import excepciones.ExisteUsuarioException;
import excepciones.NoExisteUsuarioException;
import excepciones.ValorIncorrectoDeEstadoException;
import excepciones.ValorIncorrectoDeZonaException;
import interfaces.IControlador;
import jakarta.persistence.EntityManager;
import persistencia.Conexion;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

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
                
                // Guardar el cambio en la base de datos
                persistencia.Conexion conexion = persistencia.Conexion.getInstancia();
                jakarta.persistence.EntityManager em = conexion.getEntityManager();
                em.getTransaction().begin();
                em.merge(L);
                em.getTransaction().commit();
            }
            else{
                throw new NoExisteUsuarioException("No existe un lector con el email dado");
            }
        }
        else{
            throw new NoExisteUsuarioException("No existe un lector con el email dado");
        }
    }

    public void agregarPrestamo(Lector lec, Bibliotecario bib, Material mat, Date fecha_sol,  Date fecha_dev, EstadoPrestamo estado){
        ManejadorUsuario MU = ManejadorUsuario.getInstancia();
        ManejadorMaterial MM = ManejadorMaterial.getInstancia();
        
        Usuario l = MU.buscarUsuario(lec.getEmail());
        Usuario b = MU.buscarUsuario(bib.getEmail());
        Material m = MM.buscarMaterial(mat);
        
        if(l != null && b != null && m != null){
            if(fecha_sol.compareTo(fecha_dev) < 0){
                //Fecha de solicitud antes que la fecha de devolucion. Entonces
                if((l instanceof Lector) && (b instanceof Bibliotecario) && (m instanceof Material)){   
                    Prestamo p = new Prestamo((Lector) l,(Bibliotecario) b,(Material) m, fecha_sol, fecha_dev, EstadoPrestamo.EN_CURSO);
                                 
                    List<Prestamo> lec_prestamos = ((Lector) l).getPrestamos();
                    lec_prestamos.add(p);
                    ((Lector) l).setPrestamos(lec_prestamos);

                    List<Prestamo> bib_prestamos = ((Bibliotecario) b).getPrestamos();
                    bib_prestamos.add(p);
                    ((Bibliotecario) b).setPrestamos(bib_prestamos);
                    
                    List<Prestamo> mat_prestamos = ((Material) m).getPrestamos();
                    mat_prestamos.add(p);
                    ((Material) m).setPrestamos(mat_prestamos);

                    Conexion conexion = Conexion.getInstancia();
		            EntityManager em = conexion.getEntityManager(); 
                    em.getTransaction().begin();
		            //Nota, l, b y m tienen que ser managed por el em. (Que es el mismo en MU y MM, por Conexion.java)
                    em.persist(p);
		            em.getTransaction().commit();
                }
            }else{
                //Excepcion fechas incorrectas
            }
        }else{
            //Excepcion no existe alguien
        }
    }



    //public void agregarNuevoLibro(String titulo , int cantPaginas)throws CantidadDePaginasNoValidaException, TituloNoValidoException{
        //if(cantPaginas <= 0){
            //throw new CantidadDePaginasNoValidaException("La cantidad de paginas debe ser mayor a 0");
        //}
        //if(titulo == ""){
            //throw new TituloNoValidoException("El titulo del libro no puede ser vacio");
       // }
       // ManejadorMaterial MM = ManejadorMaterial.getInstancia();
        //Libro nuevoLibro = new Libro(getFechaActual(),titulo, cantPaginas); // Solucionar tema id.
        //MM.agregarMaterial(nuevoLibro);
    //}







 };





