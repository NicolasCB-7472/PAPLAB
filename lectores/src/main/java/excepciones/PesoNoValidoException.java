package excepciones;

public class PesoNoValidoException extends Exception{
    private static final long serialVersionUID = 1L;
    public PesoNoValidoException(String string){
        super(string);
    }
}
