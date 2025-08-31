package persistencia;

import java.io.Serializable;

public class PrestamoID implements Serializable{
    private static final long serialVersionUID = 1L;

    private String lector_mail;
    private String bibliotecario_mail;
	private String material_id;
	

    public PrestamoID(){
        super();
    }

    public String getLector(){
        return this.lector_mail;
    }

	public String getBibliotecario(){
        return this.bibliotecario_mail;
    }
    
    public String getMaterial(){
        return this.material_id;
    }

    public void setLector(String lector){
        this.lector_mail=lector;
    }

	public void setBibliotecario(String bibliotecario){
        this.bibliotecario_mail=bibliotecario;
    }

    public void setMaterial(String mat){
        this.material_id=mat;
    }

    //Tiene  que tener los métodos hashCode y equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lector_mail == null) ? 0 : lector_mail.hashCode());
		result = prime * result + ((bibliotecario_mail == null) ? 0 : bibliotecario_mail.hashCode());
		result = prime * result + ((material_id == null) ? 0 : material_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;		
		if (getClass() != obj.getClass())
			return false;

		PrestamoID other = (PrestamoID) obj;
		
		//Caso lector
        if(lector_mail == null){
            if(other.lector_mail != null)
                return false;
        }else if (!lector_mail.equals(other.lector_mail))
			return false;
		
		//Caso material
		if (material_id == null) {
			if (other.material_id != null)
				return false;
		} else if (!material_id.equals(other.material_id))
			return false;
		
		//Caso bibliotecario
		 if (bibliotecario_mail == null) {
            if (other.bibliotecario_mail != null)
                return false;
        } else if (!bibliotecario_mail.equals(other.bibliotecario_mail))
            return false;

		return true;
	}

}
