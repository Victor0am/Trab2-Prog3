package trabalho;


public class Conferencia extends Veiculo{
    private String local;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Conferencia(String sigla, String nome, char tipo, double fatorImpacto) {
        super(sigla, nome, tipo, fatorImpacto);
    }
}