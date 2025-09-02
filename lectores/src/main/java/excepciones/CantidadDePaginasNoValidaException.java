package excepciones;

public class CantidadDePaginasNoValidaException extends Exception {
    private static final long serialVersionUID = 1L;
    public CantidadDePaginasNoValidaException(String string){
        super(string);
    }
    
}
