package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Conexion {
    private static Conexion instancia = null;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    private Conexion() {}

    public static Conexion getInstancia() {
        if(instancia == null) {
            instancia = new Conexion();
            emf = Persistence.createEntityManagerFactory("lectoreshibernate");
            em=emf.createEntityManager();
        }
        return instancia;
    }

    public EntityManager getEntityManager() {
        System.out.println("\nConexión establecida correctamente!\n");
        return Conexion.em;
    }

    public void close() {
        Conexion.em.close();
        Conexion.emf.close();
        System.out.println("\nConexión finaliza correctamente!\n");
    }
}

