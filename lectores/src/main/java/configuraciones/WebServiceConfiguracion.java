package configuraciones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class WebServiceConfiguracion {
    private String path = "C:/Users/bruno/.Biblioteca/.properties.txt";
    private HashMap<String, String> configs;
    
    public WebServiceConfiguracion() throws Exception {
        configs = new HashMap<>();
        System.out.println("Cargando configuración desde: " + path);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String properties;
            while((properties = reader.readLine()) != null){
                // Ignorar líneas vacías y comentarios
                if(!properties.trim().isEmpty() && !properties.trim().startsWith("#")) {
                    String[] div = properties.split("=", 2); // Limitar a 2 partes por si el valor contiene '='
                    if(div.length == 2) {
                        configs.put(div[0].trim(), div[1].trim());
                    }
                }
            }
        } catch(Exception e) {
            System.err.println("Error al cargar configuración: " + e.getMessage());
            //throw new ErrorEnFileException();
        }
    }
    
    public String getConfigOf(String nombre) {
        return configs.get(nombre); 
    }
}
