package excepciones;

public class PrestamoIncorrectoException extends Exception {
    private static final long serialVersionUID = 1L;
    public PrestamoIncorrectoException(String string){
        super(string);
    }
}
