package trabalho;

import java.util.*;


public class Publicacao{
    private int ano;
    private String titulo;
    private int paginaInicial;
    private int paginaFinal;

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPaginaInicial() {
        return paginaInicial;
    }

    public void setPaginaInicial(int paginaInicial) {
        this.paginaInicial = paginaInicial;
    }

    public int getPaginaFinal() {
        return paginaFinal;
    }

    public void setPaginaFinal(int paginaFinal) {
        this.paginaFinal = paginaFinal;
    }

    public Publicacao(int ano, String titulo, int paginaInicial, int paginaFinal) {
        this.ano = ano;
        this.titulo = titulo;
        this.paginaInicial = paginaInicial;
        this.paginaFinal = paginaFinal;
    }

    public void imprime(){
        System.out.printf("Ano:\t\t%d\n", ano);
        System.out.printf("Titulo:\t\t%s\n", titulo);
        System.out.printf("Página inicial:\t%d\n", paginaInicial);
        System.out.printf("Página final:\t%d\n", paginaFinal);
    }
}