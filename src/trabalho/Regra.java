package trabalho;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.Serializable;


public class Regra implements Serializable{
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double multiplicador;
    private int anosVigencia;
    private int pontuacaoMinima;
    private ArrayList<Integer> pontos;

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public double getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(double multiplicador) {
        this.multiplicador = multiplicador;
    }

    public int getAnosVigencia() {
        return anosVigencia;
    }

    public void setAnosVigencia(int anosVigencia) {
        this.anosVigencia = anosVigencia;
    }

    public int getPontuacaoMinima() {
        return pontuacaoMinima;
    }

    public void setPontuacaoMinima(int pontuacaoMinima) {
        this.pontuacaoMinima = pontuacaoMinima;
    }

    public ArrayList<Integer> getPontos() {
        return pontos;
    }

    public void imprime(){
        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data;
        data = DATE_FORMAT.format(this.dataInicio);
        System.out.println("Data de inicio:\t" + data);
        data = DATE_FORMAT.format(this.dataFim);
        System.out.println("Data de fim:\t" + data);
        System.out.printf("Multiplicador:\t%.3f\n", this.multiplicador);
        System.out.printf("Anos de vigência:\t%d\n", this.anosVigencia);
        System.out.printf("Pontuação Mínima:\t%d", this.pontuacaoMinima);
        System.out.println("Pontuação dos Qualis:\t");
        for(Integer i: pontos){
            System.out.println(i + " ");
        }
    }

    public Regra(LocalDate inicio, LocalDate fim, double multiplicador, int anos, int pontuacao, ArrayList<Integer> pontos){
        this.dataInicio = inicio;
        this.dataFim = fim;
        this.multiplicador = multiplicador;
        this.anosVigencia = anos;
        this.pontuacaoMinima = pontuacao;
        this.pontos = pontos;
    }

}
