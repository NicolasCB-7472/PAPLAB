package logica;

import java.util.ArrayList;
import java.util.List;

import datatypes.DtPrestamo;
import datatypes.EstadoPrestamo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import persistencia.Conexion;
import persistencia.PrestamoID;

public class ManejadorPrestamo {
    private static ManejadorPrestamo instancia = null;

    private ManejadorPrestamo() {}

    public static ManejadorPrestamo getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorPrestamo();
        }
        return instancia;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();
        em.getTransaction().begin();
        em.persist(prestamo);
        em.getTransaction().commit();
    }

    public void actualizarPrestamo(Prestamo prestamo) {
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();
        em.getTransaction().begin();
        em.merge(prestamo);
        em.getTransaction().commit();
    }

    public Prestamo buscarPrestamo(String lectorEmail, String bibliotecarioEmail, Integer materialId) {
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();

        // Crear el ID compuesto
        PrestamoID prestamoId = new PrestamoID();
        prestamoId.setLector(lectorEmail);
        prestamoId.setBibliotecario(bibliotecarioEmail);
        prestamoId.setMaterial(materialId.toString());

        Prestamo prestamo = em.find(Prestamo.class, prestamoId);
        return prestamo;
    }

    public ArrayList<Prestamo> obtenerTodosLosPrestamos() {
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();

        Query query = em.createQuery("select p from Prestamo p");
        
        List<?> rawList = query.getResultList();
        List<Prestamo> listPrestamos = new ArrayList<>();
        
        for (Object obj : rawList) {
            if (obj instanceof Prestamo) {
                listPrestamos.add((Prestamo) obj);
            }
        }
        
        return new ArrayList<>(listPrestamos);
    }

    public ArrayList<Prestamo> obtenerPrestamosPorEstado(EstadoPrestamo estado) {
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();

        Query query = em.createQuery("select p from Prestamo p where p.estado = :estado");
        query.setParameter("estado", estado);
        
        List<?> rawList = query.getResultList();
        List<Prestamo> listPrestamos = new ArrayList<>();
        
        for (Object obj : rawList) {
            if (obj instanceof Prestamo) {
                listPrestamos.add((Prestamo) obj);
            }
        }
        
        return new ArrayList<>(listPrestamos);
    }

    public ArrayList<Prestamo> obtenerPrestamosPorLector(String lectorEmail) {
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();

        Query query = em.createQuery("select p from Prestamo p where p.lector_mail.email = :lectorEmail");
        query.setParameter("lectorEmail", lectorEmail);
        
        List<?> rawList = query.getResultList();
        List<Prestamo> listPrestamos = new ArrayList<>();
        
        for (Object obj : rawList) {
            if (obj instanceof Prestamo) {
                listPrestamos.add((Prestamo) obj);
            }
        }
        
        return new ArrayList<>(listPrestamos);
    }

    public ArrayList<Prestamo> obtenerPrestamosPorBibliotecario(String bibliotecarioEmail) {
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();

        Query query = em.createQuery("select p from Prestamo p where p.bibliotecario_mail.email = :bibliotecarioEmail");
        query.setParameter("bibliotecarioEmail", bibliotecarioEmail);
        
        List<?> rawList = query.getResultList();
        List<Prestamo> listPrestamos = new ArrayList<>();
        
        for (Object obj : rawList) {
            if (obj instanceof Prestamo) {
                listPrestamos.add((Prestamo) obj);
            }
        }
        
        return new ArrayList<>(listPrestamos);
    }

    public ArrayList<DtPrestamo> obtenerDataPrestamos() {
        ArrayList<Prestamo> prestamos = obtenerTodosLosPrestamos();
        ArrayList<DtPrestamo> dtPrestamos = new ArrayList<>();
        
        for (Prestamo prestamo : prestamos) {
            DtPrestamo dtPrestamo = new DtPrestamo(
                prestamo.getLector().getEmail(),
                prestamo.getBibliotecario().getEmail(),
                prestamo.getMaterial().getId().toString(),
                new java.sql.Date(prestamo.getFechaSolicitud().getTime()),
                new java.sql.Date(prestamo.getFechaDevolucion().getTime()),
                prestamo.getEstado()
            );
            dtPrestamos.add(dtPrestamo);
        }
        
        return dtPrestamos;
    }

    public boolean existePrestamo(String lectorEmail, String bibliotecarioEmail, Integer materialId) {
        Prestamo prestamo = buscarPrestamo(lectorEmail, bibliotecarioEmail, materialId);
        return prestamo != null;
    }
}
