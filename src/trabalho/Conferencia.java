package trabalho;

//import Veiculo.java;
import java.util.*;


public class Conferencia extends Veiculo{
    private String local;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Conferencia(String sigla, String nome, char tipo, float fatorImpacto, int numero, String local) {
        super(sigla, nome, tipo, fatorImpacto, numero);
        this.local = local;
    }
}