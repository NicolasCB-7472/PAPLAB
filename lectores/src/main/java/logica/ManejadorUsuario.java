package logica;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import persistencia.Conexion;

public class ManejadorUsuario {
        private static ManejadorUsuario instancia = null;

        private ManejadorUsuario() {}

        public static ManejadorUsuario getInstancia() {
            if(instancia == null){
                instancia = new ManejadorUsuario();
            }
            return instancia;
        }

        public void agregarUsuario(Usuario user){
            Conexion conexion = Conexion.getInstancia();
            EntityManager em = conexion.getEntityManager();
            em.getTransaction().begin();
            
            em.persist(user);
            
            em.getTransaction().commit();
        }

        public Usuario buscarUsuario(String email){
            Conexion conexion = Conexion.getInstancia();
            EntityManager em = conexion.getEntityManager();

            Usuario user = em.find(Usuario.class, email);
            return user;
        }

        public ArrayList<String> obtenerUsuarios(){
            Conexion conexion = Conexion.getInstancia();
            EntityManager em = conexion.getEntityManager();

            Query query = em.createQuery("select u from Usuario u");
            //Conversion de objeto no segura
            //List<Usuario> listUsuario = (List<Usuario>) query.getResultList();
            
            //Conversion de objeto segura
            List<?> rawList = query.getResultList();
            List<Usuario> listUsuario = new ArrayList<>();
            
            for(Object obj : rawList){
                if(obj instanceof Usuario){
                    listUsuario.add((Usuario) obj);
                }
            }

            ArrayList<String> ret_list = new ArrayList<>();
            for(Usuario u: listUsuario) {
                ret_list.add(new String(u.getEmail()));
            }
            return ret_list;
        }
        
        public boolean existeUsuario(String email){
            Conexion conexion = Conexion.getInstancia();
            EntityManager em = conexion.getEntityManager();

            Usuario UsuarioABuscar= em.find(Usuario.class, email);
            if(UsuarioABuscar != null){
                return true;
            }
            else{
                return false;
            }

        }
        
        public Usuario darUsuario(String email){
            Conexion conexion = Conexion.getInstancia();
            EntityManager em = conexion.getEntityManager();
            Usuario UsuarioABuscar= em.find(Usuario.class, email);
            if(UsuarioABuscar != null){
                return UsuarioABuscar;
            }
            else{
                return null;
            }
        }

        public void confirmarCambiosUsuario(Usuario U){
            // Guardar el cambio en la base de datos
            persistencia.Conexion conexion = persistencia.Conexion.getInstancia();
            jakarta.persistence.EntityManager em = conexion.getEntityManager();
            em.getTransaction().begin();
            em.merge(U);
            em.getTransaction().commit();
        }
        
        public ArrayList<String> obtenerLectores(){
            Conexion conexion = Conexion.getInstancia();
            EntityManager em = conexion.getEntityManager();

            Query query = em.createQuery("select u from Lector u");

            List<?> rawList = query.getResultList();
            List<Lector> listUsuario = new ArrayList<>();
            
            for(Object obj : rawList){
                if(obj instanceof Lector){
                    listUsuario.add((Lector) obj);
                }
            }
            ArrayList<String> ret_list = new ArrayList<>();
            for(Lector u: listUsuario) {
                ret_list.add(new String(u.getEmail()));
            }
            return ret_list;
        }

}

