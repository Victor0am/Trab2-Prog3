package trabalho;

import java.util.*;
//import Veiculo.java;

public class Periodico extends Veiculo{
    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    private String issn;
    private int volume;

    public Periodico(String sigla, String nome, char tipo, float fatorImpacto, int numero, String issn, int volume) {
        super(sigla, nome, tipo, fatorImpacto, numero);
        this.issn = issn;
        this.volume = volume;
    }
}