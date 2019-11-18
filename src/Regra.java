import java.util.*;


public class Regra{
    private Date dataInicio;
    private Date dataFim;
    private float multiplicador;
    private int anosVigencia;
    private int pontuacaoMinima;
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

    public float getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(float multiplicador) {
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

    public Regra(Date inicio, Date fim, float multiplicador, int anos, int pontuacao){
        this.dataInicio = inicio;
        this.dataInicio = fim;
        this.multiplicador = multiplicador;
        this.anosVigencia = anos;
        this.pontuacaoMinima = pontuacao;
    }

}
