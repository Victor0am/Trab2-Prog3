package trabalho;

import java.util.*;
import java.text.SimpleDateFormat;


public class Regra{
    private Date dataInicio;
    private Date dataFim;
    private double multiplicador;
    private int anosVigencia;
    private int pontuacaoMinima;
    private ArrayList<Integer> pontos ;
//    private vector<Integer> pontos;

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
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

    public void imprime(){
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        String data;
        data = DATE_FORMAT.format(this.dataInicio);
        System.out.println("Data de inicio:\t" + data);
        data = DATE_FORMAT.format(this.dataFim);
        System.out.println("Data de fim:\t" + data);
        System.out.printf("Multiplicador:\t%.3f\n", this.multiplicador);
        System.out.printf("Anos de vigência:\t%d\n", this.anosVigencia);
        System.out.printf("Pontuação Mínima:\t%d", this.pontuacaoMinima);
    }

    public Regra(Date inicio, Date fim, double multiplicador, int anos, int pontuacao, ArrayList<Integer> pontos){
        this.dataInicio = inicio;
        this.dataFim = fim;
        this.multiplicador = multiplicador;
        this.anosVigencia = anos;
        this.pontuacaoMinima = pontuacao;
        this.pontos = pontos;
    }

}
