package excepciones;

public class DescripcionNoValidaException extends Exception{
    private static final long serialVersionUID = 1L;
    public DescripcionNoValidaException(String string){
        super(string);
    }
}
