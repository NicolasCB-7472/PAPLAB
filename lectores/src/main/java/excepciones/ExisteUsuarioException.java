package excepciones;

public class ExisteUsuarioException extends Exception {
    private static final long serialVersionUID = 1L;
    public ExisteUsuarioException(String string){
        super(string);
    }
}
