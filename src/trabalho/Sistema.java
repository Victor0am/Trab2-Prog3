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
                default:
                    throw new IllegalArgumentException("Tipo de veículo desconhecido " +
                    "para veículo " + sigla + ": " + tipo +".");
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
            System.out.println();
        }
    }

    public boolean isPeriodico(String veiculo){
            // System.out.println(veiculo);
            veiculosCadastrados.get(veiculo);
            if (veiculosCadastrados.get(veiculo) == null){
                return false;
            }
            if (veiculosCadastrados.get(veiculo).getTipo() == 'P'){
                return true;
            }
            return false;
    }

    /* ************************* Publicações ************************** */

    public void carregaArquivoPublicacoes(BufferedReader arquivo) throws IOException{
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while(linha != null){
                Publicacao publicacao = null;
                String[] campos = linha.split(";");
                int ano = Integer.parseInt(campos[0]);
                String veiculo = campos[1];
                String titulo = campos[2];
                // Long autores = Long.parseLong(campos[3]);
                String autores = campos[3];
                String[] autor = autores.split(",");
                long[] autorLong = new long [autor.length];
                int numero = Integer.parseInt(campos[4]);
                int volume;
                String local;
                int pinicial = Integer.parseInt(campos[7]);
                if (isPeriodico(veiculo)){
                    volume = Integer.parseInt(campos[5]);
                }
                else{
                    local = campos[6];
                }
                int pfinal = Integer.parseInt(campos[8]);
                for (int i = 0; i < autor.length; i++){
                    autor[i] = autor[i].replaceAll(" ", "");
                    autorLong[i] = Long.parseLong(autor[i]);
                    publicacao = new Publicacao(ano, veiculo, titulo, pinicial, pfinal, autorLong[i]);
                    atribuiPublicacao(publicacao);
                    registraPublicacao(publicacao);
                }
                publicacoesCadastradas.add(publicacao);
                linha = arquivo.readLine();
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void imprimePublicacoes(){
        for (Publicacao p : publicacoesCadastradas){
            p.imprime();
        }
    }

    public void atribuiPublicacao(Publicacao p){
        docentesCadastrados.get(p.getAutor()).getPublicacoes().add(p);
    }

    public void registraPublicacao(Publicacao p) throws IOException{
        Veiculo veiculos = null;
        veiculos = veiculosCadastrados.get(p.getVeiculo());
        try{
            if (veiculos != null){
                veiculos.getPublicacoes().add(p);
            }
            else{
                throw new IOException("Sigla de veículo não definida usada na" +
                " publicação “" + p.getTitulo() + "”: " + p.getVeiculo() + ".");
            }
        }catch(IOException e){
            throw new IOException(e.getMessage());
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

    /* ************************* Qualis ************************** */

    public void carregaArquivoQualis(BufferedReader arquivo) throws IOException{
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while(linha != null){
                String[] campos = linha.split(";");
                int ano = Integer.parseInt(campos[0]);
                String veiculo = campos[1];
                if (veiculosCadastrados.get(veiculo) == null){
                    throw new IOException("Sigla de veículo não definida usada na" 
                    + "qualificação do ano “" + ano + "”: "+ veiculo + ".");
                }
                String classificacao = campos[2];
                linha = arquivo.readLine();
            }
        } catch (IOException e){
            throw new IOException(e.getMessage());
        }
    }
}