package logica;

import java.util.ArrayList;
import java.util.List;

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

        }

        public void buscarUsuario(){
            //Acordar identificacion de user
        }

        public boolean existeUsuario(String email){
            return false;
        }
        
        public Usuario darUsuario(String email){
            return new Lector();
        }
        /*
        public ArrayList<> obtenerUsuarios(){
            // Obtener conexion y query
        
            ArrayList<> ret_list = new ArrayList<>();
        
            return ret_list;
        }

        */

}
