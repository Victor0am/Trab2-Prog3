package trabalho;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.Serializable;

public class Docente implements Serializable{
    private long codigo;
    private String nome;
    private LocalDate dataNascimento;
    private LocalDate dataIngresso;
    private boolean coordenador;
    private ArrayList<Publicacao> publicacoes = new ArrayList<Publicacao>();
    private double pontuacao;
    private ArrayList<Boolean> qualisObtidos = new ArrayList<Boolean>();

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

    public ArrayList<Boolean> getQualisObtidos() {
        return qualisObtidos;
    }

    public Docente(long codigo, String nome, LocalDate nascimento, LocalDate ingresso, boolean coordenador){
        this.codigo = codigo;
        this.nome = nome;
        this.dataIngresso = ingresso;
        this.dataNascimento = nascimento;
        this.coordenador = coordenador;
        for(int i = 0; i< 8; i++){
            qualisObtidos.add(false);
        }
    }
}