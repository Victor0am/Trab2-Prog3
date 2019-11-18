package trabalho;

import java.util.*;


public class Veiculo{
    protected String sigla;
    protected String nome;
    protected char tipo;
    protected double fatorImpacto;
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

    public void imprimeVeiculo(){
        System.out.printf("Sigla:\t%s\n", sigla);
        System.out.printf("Nome:\t%s\n", nome);
        System.out.printf("Tipo:\t%c\n", tipo);
        System.out.printf("Impacto:\t%.3f\n", fatorImpacto);
    }

    public Veiculo(String sigla, String nome, char tipo, double fatorImpacto) {
        this.sigla = sigla;
        this.nome = nome;
        this.tipo = tipo;
        this.fatorImpacto = fatorImpacto;
    }

}