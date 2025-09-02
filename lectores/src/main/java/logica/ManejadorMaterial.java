package logica;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import datatypes.DtMaterial;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import persistencia.Conexion;

public class ManejadorMaterial {
    private static ManejadorMaterial instancia = null;

    private ManejadorMaterial(){}

    public static ManejadorMaterial getInstancia(){
        if(instancia == null){
            instancia = new ManejadorMaterial();
        }
        return instancia;
    }

    public void agregarMaterial(Material material_nuevo){
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();

        em.getTransaction().begin();

        em.persist(material_nuevo);

        em.getTransaction().commit();
    }

    public Material buscarMaterial(Material id){
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();

        Material mats = em.find(Material.class, id);

        return mats;
    }

    public Material buscarMaterial_PorID(Integer id){
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();

        Material mats = em.find(Material.class, id);

        return mats;
    }

    public ArrayList<Integer> obtenerMateriales(){
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();

        Query query = em.createQuery("select m from Material m");
        
        List<?> rawList = query.getResultList();
        List<Material> listMaterial = new ArrayList<>();
        
        for(Object obj : rawList){
            if(obj instanceof Material){
                listMaterial.add((Material) obj);
            }
        }
        
        ArrayList<Integer> ret_list = new ArrayList<>();
        for(Material m: listMaterial){
            // Agregar new, lanza un problema de resolucion de tipo a Integer.valueOf()
            ret_list.add(Integer.valueOf(m.getId()));
        }

        return ret_list;
    }

    public ArrayList<DtMaterial> obtenerDataMateriales(){
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();

        Query query = em.createQuery("select m from Material m");
        
        List<?> rawList = query.getResultList();
        List<Material> listMaterial = new ArrayList<>();
        
        for(Object obj : rawList){
            if(obj instanceof Material){
                listMaterial.add((Material) obj);
            }
        }
        
        ArrayList<DtMaterial> ret_list = new ArrayList<>();
        for(Material m: listMaterial){
            ret_list.add(m.getDtMaterial());
        }

        return ret_list;
    }

    public ArrayList<DtMaterial> obtenerDataMaterialesEntreFechas(Date menor, Date mayor){
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();

        Query query = em.createQuery("select m from Material m where m.fechaIngreso >= :menor and m.fecha <= :mayor"); //Compara directamente en la query.
        query.setParameter("menor", menor);//Antes de obtener la query setea que son :menor y :mayor
        query.setParameter("mayor", mayor);
        List<?> rawList = query.getResultList();
        List<Material> listMaterial = new ArrayList<>();
        

        for(Object obj : rawList){
            if(obj instanceof Material){
                Material mat = (Material) obj;
                    listMaterial.add(mat);
            }
        }
        
        ArrayList<DtMaterial> ret_list = new ArrayList<>();
        for(Material m: listMaterial){
            ret_list.add(m.getDtMaterial());
        }

        return ret_list;
    }
}
