import java.util.*;


public class Veiculo{
    protected String sigla;
    protected String nome;
    protected char tipo;
    protected float fatorImpacto;
    protected int numero;

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public float getFatorImpacto() {
        return fatorImpacto;
    }

    public void setFatorImpacto(float fatorImpacto) {
        this.fatorImpacto = fatorImpacto;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }


}