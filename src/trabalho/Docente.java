package trabalho;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.SimpleDateFormat;

public class Docente{
    private long codigo;
    private String nome;
    private LocalDate dataNascimento;
    private LocalDate dataIngresso;
    private boolean coordenador;
    private ArrayList<Publicacao> publicacoes = new ArrayList<Publicacao>();
    private double pontuacao;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataIngresso() {
        return dataIngresso;
    }

    public void setData_ingresso(LocalDate dataIngresso) {
        this.dataIngresso = dataIngresso;
    }

    public boolean isCoordenador() {
        return coordenador;
    }

    public void setCoordenador(boolean coordenador) {
        this.coordenador = coordenador;
    }

    public ArrayList<Publicacao> getPublicacoes(){
        return publicacoes;
    }

    public double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Docente(long codigo, String nome, LocalDate nascimento, LocalDate ingresso, boolean coordenador){
        this.codigo = codigo;
        this.nome = nome;
        this.dataIngresso = ingresso;
        this.dataNascimento = nascimento;
        this.coordenador = coordenador;
    }

    public void imprime(){
        System.out.println("=============================");
        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data;
        System.out.printf("Codigo:\t%d\n", codigo);
        System.out.printf("Nome:\t%s\n", nome);
        data = DATE_FORMAT.format(dataNascimento);
        System.out.println("Data de Nascimento:\t" + data);
        data = DATE_FORMAT.format(dataIngresso);
        System.out.println("Data de Ingresso:\t" + data);
        System.out.println("Coordenador: " + 
        ((coordenador == true)? "Sim" : "Não" ));
        System.out.println("Publicações:");
        if (publicacoes.isEmpty()){
            System.out.println("\tDocente não possui publicações registradas");
        }
        else{
            for (Publicacao p : publicacoes){
                System.out.println("\t-----------------------------");
                p.imprime();
            }
        }
        System.out.println("Pontuação:\t");
        System.out.println(pontuacao);
    }


}