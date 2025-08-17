package datatypes;

import logica.Libro;

public class DtLibro extends DtMaterial{
    private String titulo;    
    private int cantPaginas; // Lo hice int en vez de string porque tiene mas sentido.

    public DtLibro(){
        super();
    }

    public DtLibro(String id , Date fechaIng , String titulo , int cantPaginas){
        super(id,fechaIng);
        this.titulo = titulo;
        this.cantPaginas = cantPaginas;
    }
    public DtLibro(Libro L){ // Esto quiero preguntarle al Yona
        super(L.getId(),L.getFechaIngreso());
        this.titulo = L.getTitulo();
        this.cantPaginas = L.getCantPaginas();
    }
    // Getters y setter
    public String getTitulo(){
        return this.titulo;
    }
    
    public int getCantPaginas(){
        return this.cantPaginas;
    }
}
