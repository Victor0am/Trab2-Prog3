package trabalho;

import java.io.Serializable;
import java.util.*;


public class Publicacao implements Serializable{
    private int ano;
    private String veiculo;
    private String titulo;
    private int paginaInicial;
    private int paginaFinal;
    private Long autor;
    private String quali;
    private ArrayList<Long> autores = new ArrayList<Long>();

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Long getAutor() {
        return autor;
    }

    public ArrayList<Long> getAutores() {
        return autores;
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

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
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

    public String getQuali(){
        return quali;
    }

    public void setQuali(String quali){
        this.quali = quali;
    }

    public Publicacao(int ano, String veiculo, String titulo, int paginaInicial, int paginaFinal, ArrayList<Long> autores) {
        this.ano = ano;
        this.veiculo = veiculo;
        this.titulo = titulo;
        this.paginaInicial = paginaInicial;
        this.paginaFinal = paginaFinal;
        this.autores = autores;
    }

    // public Publicacao(int ano, String veiculo, String titulo, int paginaInicial, int paginaFinal, Long autor) {
    //     this.ano = ano;
    //     this.veiculo = veiculo;
    //     this.titulo = titulo;
    //     this.paginaInicial = paginaInicial;
    //     this.paginaFinal = paginaFinal;
    //     this.autor = autor;
    // }

    public void imprime(HashMap<Long,Docente> docentesCadastrados, HashMap<String,Veiculo> veiculosCadastrados){
        System.out.printf("%d;%s;%s;%.3f;%s;", ano, veiculo, 
            veiculosCadastrados.get(veiculo).getNome(),
            veiculosCadastrados.get(veiculo).getFatorImpacto(),
            titulo);
        // System.out.printf("\tVeículo:\t\t%s\n", veiculo);
        // System.out.printf("\tTitulo:\t\t%s\n", titulo);
        for (Long l: autores){
            System.out.printf("%s", docentesCadastrados.get(l).getNome());
            if (l != autores.get(autores.size() - 1)){
                System.out.print(",");
            }
        }
        System.out.println();
    }
}