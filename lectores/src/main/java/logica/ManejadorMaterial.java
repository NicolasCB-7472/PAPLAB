package logica;

import java.util.ArrayList;
import java.util.List;

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

    }

    public void buscarMaterial(Material id){

        // return Material (objeto material encontrado)
    }

    public ArrayList<Integer> obtenerMateriales(){
        //Obtiene conexion
        
        ArrayList<Integer> ret_list = new ArrayList<>();

        return ret_list;
    }


}
