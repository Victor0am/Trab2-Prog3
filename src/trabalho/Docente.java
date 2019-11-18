package trabalho;

import java.util.*;
import java.text.SimpleDateFormat;

public class Docente{
    private long codigo;
    private String nome;
    private Date dataNascimento;
    private  Date dataIngresso;
    private boolean coordenador;

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

    public Docente(long codigo, String nome, Date nascimento, Date ingresso, boolean coordenador){
        this.codigo = codigo;
        this.nome = nome;
        this.dataIngresso = ingresso;
        this.dataNascimento = nascimento;
        this.coordenador = coordenador;
    }

    public void imprimeDocente(){
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        String data;
        System.out.printf("Codigo:\t%d\n", getCodigo());
        System.out.printf("Nome:\t%s\n", getNome());
        data = DATE_FORMAT.format(getDataNascimento());
        System.out.println("Data de Nascimento:\t" + data);
        data = DATE_FORMAT.format(getDataIngresso());
        System.out.println("Data de Ingresso:\t" + data);
        System.out.println("Coordenador: " + 
                            ((isCoordenador() == true)? "Sim" : "Não" ));
    }

}