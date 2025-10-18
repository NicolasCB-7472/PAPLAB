package datatypes;
import java.sql.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DtLibro", propOrder = {
    "titulo",
    "cantPaginas"
})

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

    // Getters y setter
    public String getTitulo(){
        return this.titulo;
    }
    
    public int getCantPaginas(){
        return this.cantPaginas;
    }
}
