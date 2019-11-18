package trabalho;

import java.util.*;


public class Sistema{
    private HashMap<Long,Docente> docentesCadastrados = new HashMap<Long,Docente>();
    
    
    


    /* ************************* DOCENTES ************************** */

    public void imprimeDocentes(){
        for (Map.Entry<Long,Docente> pair : docentesCadastrados.entrySet()) {
            pair.getValue().imprimeDocente();
        }
    }

    /**
     * Insere um docente na Hash de docentes cadastrados
     */
    public void insereDocente(Docente d){
        this.docentesCadastrados.put(d.getCodigo(), d);
    }
}