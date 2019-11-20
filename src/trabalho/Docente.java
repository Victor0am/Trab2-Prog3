package trabalho;

import java.util.*;
import java.text.SimpleDateFormat;

public class Docente{
    private long codigo;
    private String nome;
    private Date dataNascimento;
    private Date dataIngresso;
    private boolean coordenador;
    private ArrayList<Publicacao> publicacoes = new ArrayList<Publicacao>();

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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Date getDataIngresso() {
        return dataIngresso;
    }

    public void setData_ingresso(Date dataIngresso) {
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

    public Docente(long codigo, String nome, Date nascimento, Date ingresso, boolean coordenador){
        this.codigo = codigo;
        this.nome = nome;
        this.dataIngresso = ingresso;
        this.dataNascimento = nascimento;
        this.coordenador = coordenador;
    }

    public void imprimeDocente(){
        System.out.println("=============================");
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
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
    }

}