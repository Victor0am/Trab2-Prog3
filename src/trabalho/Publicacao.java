package trabalho;

import java.util.*;


public class Publicacao{
    private int ano;
    private String titulo;
    private int paginaInicial;
    private int paginaFinal;
    private Long autor;

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Long getAutor() {
        return autor;
    }

    public void setAutor(Long autor) {
        this.autor = autor;
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

    public Publicacao(int ano, String titulo, int paginaInicial, int paginaFinal, Long autor) {
        this.ano = ano;
        this.titulo = titulo;
        this.paginaInicial = paginaInicial;
        this.paginaFinal = paginaFinal;
        this.autor = autor;
    }

    public void imprime(){
        System.out.printf("\tTitulo:\t\t%s\n", titulo);
        System.out.printf("\tAno:\t\t%d\n", ano);
        System.out.printf("\tPágina inicial:\t%d\n", paginaInicial);
        System.out.printf("\tPágina final:\t%d\n", paginaFinal);
    }
}