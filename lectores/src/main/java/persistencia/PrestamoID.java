package persistencia;

import java.io.Serializable;

public class PrestamoID implements Serializable{
    private static final long serialVersionUID = 1L;

    private String usuario_mail;
    private String material_id;

    public PrestamoID(){
        super();
    }

    public String getUsuario(){
        return this.usuario_mail;
    }
    
    public String getMaterial(){
        return this.usuario_mail;
    }

    public void setUsuario(String usuario){
        this.usuario_mail=usuario;
    }

    public void setMaterial(String mat){
        this.material_id=mat;
    }

    //Tiene  que tener los métodos hashCode y equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario_mail == null) ? 0 : usuario_mail.hashCode());
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
        if(usuario_mail == null){
            if(other.usuario_mail != null)
                return false;
        }else if (!usuario_mail.equals(other.usuario_mail))
			return false;
		if (material_id == null) {
			if (other.material_id != null)
				return false;
		} else if (!material_id.equals(other.material_id))
			return false;
		return true;
	}

}
