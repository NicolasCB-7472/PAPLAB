package excepciones;

public class TituloNoValidoException extends Exception{
    private static final long serialVersionUID = 1L;
    public TituloNoValidoException(String string){
        super(string);
    }
}
