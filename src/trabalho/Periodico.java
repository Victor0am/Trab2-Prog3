package trabalho;

import java.util.*;
//import Veiculo.java;

public class Periodico extends Veiculo{
    private String issn;
    private int volume;
    
    public Periodico(String sigla, String nome, char tipo, double fatorImpacto, String issn) {
        super(sigla, nome, tipo, fatorImpacto);
        this.issn = issn;
    }
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
}