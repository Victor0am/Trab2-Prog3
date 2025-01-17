package trabalho;

import java.io.Serializable;
import java.util.*;


public class Veiculo implements Serializable{
    protected String sigla;
    protected String nome;
    protected char tipo;
    protected double fatorImpacto;
    protected int numero;
    protected ArrayList<Publicacao> publicacoes = new ArrayList<Publicacao>();
    protected HashMap<Integer, String> qualis = new HashMap<Integer, String>();

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

    public double getFatorImpacto() {
        return fatorImpacto;
    }

    public void setFatorImpacto(double fatorImpacto) {
        this.fatorImpacto = fatorImpacto;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public ArrayList<Publicacao> getPublicacoes(){
        return publicacoes;
    }

    public HashMap<Integer, String> getQualis(){
        return qualis;
    }

    public void imprimeVeiculo(){
        System.out.printf("<%s>\t%s\n", sigla, nome);
        System.out.printf("Tipo:\t%c\n", tipo);
        System.out.printf("Impacto:\t%.3f\n", fatorImpacto);
        for (Map.Entry<Integer, String> pair : qualis.entrySet()) {
            System.out.printf("Ano:\t%d\nQuali:\t%s\n",pair.getKey(),pair.getValue());
        }
        // for (Publicacao p: publicacoes){
        //     p.imprime();
        // }
    }

    public Veiculo(String sigla, String nome, char tipo, double fatorImpacto) {
        this.sigla = sigla;
        this.nome = nome;
        this.tipo = tipo;
        this.fatorImpacto = fatorImpacto;
    }

}