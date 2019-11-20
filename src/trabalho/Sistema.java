package trabalho;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.text.ParseException;


public class Sistema{
    private HashMap<Long,Docente> docentesCadastrados = new HashMap<Long,Docente>();
    private HashMap<String, Veiculo> veiculosCadastrados = new HashMap<String, Veiculo>();
    private ArrayList<Publicacao> publicacoesCadastradas = new ArrayList<Publicacao>();    
    private ArrayList<Regra> regrasCadastradas = new ArrayList<Regra>();

    /* ************************* DOCENTES ************************** */

    public void carregaArquivoDocentes(BufferedReader arquivo){
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while(linha != null){
                String[] campos = linha.split(";");
                long codigo = Long.parseLong(campos[0]);
                String nome = campos[1];
                Date nascimento = new SimpleDateFormat("dd/MM/yyyy").parse(campos[2]);
                Date ingresso = new SimpleDateFormat("dd/MM/yyyy").parse(campos[3]);
                boolean coordenador = false;
                // System.out.println(linha);
                if (campos.length == 5) {
                    if (campos[4] == "X") {
                        coordenador = true;
                    }
                }
                Docente docente = new Docente(codigo, nome, nascimento, ingresso, coordenador);
                // docentesCadastrados.add(docente);
                insereDocente(docente);
                linha = arquivo.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro: " + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
    }


    public void imprimeDocentes(){
        for (Map.Entry<Long,Docente> pair : docentesCadastrados.entrySet()) {
            pair.getValue().imprimeDocente();
        }
    }

    /**
     * Insere um docente na Hash de docentes cadastrados
     */
    public void insereDocente(Docente d){
        this.docentesCadastrados.put(d.getCodigo(), d);
    }


    /* ************************* Veiculo ************************** */

    public void carregaArquivoVeiculos(BufferedReader arquivo){
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while(linha != null){
                String[] campos = linha.split(";");
                String sigla = campos[0];
                String nome = campos[1];
                char tipo = campos[2].charAt(0);
                campos[3] = campos[3].replace(',', '.');
                double impacto = Double.parseDouble(campos[3]);
                Veiculo veiculo = null;
                switch (tipo){
                case 'C':
                    veiculo = new Conferencia(sigla, nome, tipo, impacto);
                    insereVeiculo(veiculo);
                    break;
                case 'P':
                    String issn = campos[4];
                    veiculo = new Periodico(sigla, nome, tipo, impacto, issn);
                    insereVeiculo(veiculo);
                    break;
                }
                linha = arquivo.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro: " + e.getMessage());
        }
    }


    public void insereVeiculo(Veiculo v){
        this.veiculosCadastrados.put(v.getSigla(), v);
    }


    public void imprimeVeiculos(){
        for (Map.Entry<String,Veiculo> pair : veiculosCadastrados.entrySet()) {
            pair.getValue().imprimeVeiculo();
        }
    }

    public boolean isPeriodico(String veiculo){
        if (veiculosCadastrados.get(veiculo).getTipo() == 'P'){
            return true;
        }
        return false;
    }

    /* ************************* Publicações ************************** */

    public void carregaArquivoPublicacoes(BufferedReader arquivo){
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while(linha != null){
                String[] campos = linha.split(";");
                int ano = Integer.parseInt(campos[0]);
                String veiculo = campos[1];
                String titulo = campos[2];
                String autores = campos[3];
                int numero = Integer.parseInt(campos[4]);
                int volume;
                String local;
                if (isPeriodico(veiculo)){
                    volume = Integer.parseInt(campos[5]);
                }
                else{
                    local = campos[6];
                }
                int pinicial = Integer.parseInt(campos[7]);
                int pfinal = Integer.parseInt(campos[8]);
                Publicacao publicacao = new Publicacao(ano, titulo, pinicial, pfinal);
                publicacoesCadastradas.add(publicacao);
                linha = arquivo.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void imprimePublicacoes(){
        for (Publicacao p : publicacoesCadastradas){
            p.imprime();
        }
    }

    /* ************************* Regras ************************** */

    public void carregaArquivoRegras(BufferedReader arquivo){
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while(linha != null){
                String[] campos = linha.split(";");
                Date dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(campos[0]);
                Date dataFim = new SimpleDateFormat("dd/MM/yyyy").parse(campos[1]);
                String qualis = campos[2];
                String pontos = campos[3];
                campos[4] = campos[4].replace(',', '.');
                double multiplicador = Double.parseDouble(campos[4]);
                int anos = Integer.parseInt(campos[5]);
                int pontuacao = Integer.parseInt(campos[6]);
                Regra regra = new Regra(dataInicio, dataFim, multiplicador, anos, pontuacao, designaPontosPorQuali(qualis, pontos));
                regrasCadastradas.add(regra);
                linha = arquivo.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro: " + e.getMessage());
        } catch (ParseException e){
            e.printStackTrace();
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public int[] designaPontosPorQuali(String stringQualis, String stringPontos){
        String[] separaPontos = stringPontos.split(",");
        String[] separaQualis = stringQualis.split(",");
        int [] vetorPosicao = new int[separaQualis.length];
        int[] vetorPontos = new int[8];
        Qualis[] qualis = Qualis.values();
        for (int i = 0; i< separaQualis.length; i++) {
            for (Qualis q: qualis){
                if(separaQualis[i] == q.toString()){
                    vetorPosicao[i] =  q.ordinal();
                }
            }
        }
        for(int i = 0; i<8; i++){
            int j = 0;
            int pontuacao = 0;
            if(vetorPosicao[j] == i){
                pontuacao = Integer.parseInt(separaPontos[j]);
            }
            vetorPontos[i] = pontuacao;
        }
        return vetorPontos;
    }


    public void imprimeRegras(){
        for (Regra r : regrasCadastradas){
            r.imprime();
        }
    }

}