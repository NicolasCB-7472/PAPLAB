package excepciones;

public class EmpleadoyCasteoNoValidoException extends Exception {
    private static final long serialVersionUID = 1L;
    public EmpleadoyCasteoNoValidoException(String string){
        super(string);
    }
}
