package trabalho;

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

    /**
     * Carrega dados de docentes no sistema.
     * @param arquivo
     * @throws IOException
     */
    public void carregaArquivoDocentes(BufferedReader arquivo) throws IOException {
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
                insereDocente(docente);
                linha = arquivo.readLine();
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
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
     * @param d
     * @throws IOException
     */
    public void insereDocente(Docente d) throws IOException {
        try{
            if (verificaDocente(d)){
                this.docentesCadastrados.put(d.getCodigo(), d);
            }
            else{
                throw new IOException("Código repetido para " +d.getNome()
                 + ": " + d.getCodigo() +".");
            }
        }catch(IOException e){
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Verifica se já existe algum docente cadastrado com aquele código
     * @param d
     * @return
     */
    public boolean verificaDocente(Docente d){
        Docente docente = docentesCadastrados.get(d.getCodigo());
        return (docente == null);
    }


    /* ************************* VEICULOS ************************** */

    /**
     * 
     * @param arquivo
     * @throws IOException
     */
    public void carregaArquivoVeiculos(BufferedReader arquivo) throws IOException {
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while(linha != null){
                String[] campos = linha.split(";");
                String sigla = campos[0].trim();
                String nome = campos[1].trim();
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
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Adiciona um veiculo à Hash de veículos cadastrados.
     * @param v
     * @throws IOException
     */
    public void insereVeiculo(Veiculo v) throws IOException {
        try{
            if (verificaVeiculo(v)){
                    this.veiculosCadastrados.put(v.getSigla(), v);
                }
            else{
                throw new IOException("Código repetido para " + v.getNome()
                + ": " + v.getSigla() +".");
            }
        }catch(IOException e){
            throw new IOException(e.getMessage());
        }
    }

    public boolean verificaVeiculo(Veiculo v){
        Veiculo veiculo = veiculosCadastrados.get(v.getSigla());
        return (veiculo == null);
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

    /* ************************* PUBLICAÇÕES ************************** */

    public void carregaArquivoPublicacoes(BufferedReader arquivo) throws IOException{
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while(linha != null){
                Publicacao publicacao = null;
                String[] campos = linha.split(";");
                int ano = Integer.parseInt(campos[0]);
                String veiculo = campos[1].trim();
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

    /**
     * Atribui uma publicação a um determinado docente.
     * @param p
     * @throws IOException
     */
    public void atribuiPublicacao(Publicacao p) throws IOException {
        try {
            Docente docente = docentesCadastrados.get(p.getAutor());
            if (docente == null){
                throw new IOException("Código de docente não definido usado na "
                + "publicação “" + p.getTitulo() + "”: " + p.getVeiculo() +".");
            }
            else{
                docentesCadastrados.get(p.getAutor()).getPublicacoes().add(p);
            }
        }catch(IOException e){
            throw new IOException(e.getMessage());
        }
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


    /* ************************* REGRAS ************************** */

    public void carregaArquivoRegras(BufferedReader arquivo){
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while(linha != null){
                String[] campos = linha.split(";");
                Date dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(campos[0]);
                Date dataFim = new SimpleDateFormat("dd/MM/yyyy").parse(campos[1]);
                String qualis[] = campos[2].split(",");
                String pontos[] = campos[3].split(",");
                for (String quali:qualis) {
                    if(!checaQuali(quali)){
                        throw new IOException("Qualis desconhecido para regras de " + campos[0] +": " + quali);
                    }
                }
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

    public ArrayList<Integer> designaPontosPorQuali(String stringQualis[], String stringPontos[]){
        ArrayList<Integer> vetorPosicao = new ArrayList<Integer>();
        ArrayList<Integer> vetorPontos = new ArrayList<Integer>();
        Qualis[] qualis = Qualis.values();
        for (int i = 0; i< stringQualis.length; i++) {
            vetorPosicao.add(valorQuali(stringQualis[i]));
        }
        for(int i = 0, pontuacao = 0, j = 0; i<8; i++){
            if(vetorPosicao.get(j) == i){
                pontuacao = Integer.parseInt(stringPontos[j]);
                j++;
            }
            vetorPontos.add(pontuacao);
        }
        return vetorPontos;
    }


    public void imprimeRegras(){
        for (Regra r : regrasCadastradas){
            r.imprime();
        }
    }
    public int valorQuali(String quali){
        switch(quali){
            case "A1":
                return 0;
            case "A2":
                return 1;
            case "B1":
                return 2;
            case "B2":
                return 3;
            case "B3":
                return 4;
            case "B4":
                return 5;
            case "B5":
                return 6;
            case "C":
                return 7;
            default:
                return 0;
        }
    }
    public boolean checaQuali(String quali){
        switch(quali){
            case "A1":
            case "A2":
            case "B1":
            case "B2":
            case "B3":
            case "B4":
            case "B5":
            case "C":
                return true;
            default:
                return false;
        }
    }

    /* ************************* QUALIS ************************** */

    public void carregaArquivoQualis(BufferedReader arquivo) throws IOException{
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while(linha != null){
                String[] campos = linha.split(";");
                int ano = Integer.parseInt(campos[0]);
                String veiculo = campos[1].trim();
                if (veiculosCadastrados.get(veiculo) == null){
                    throw new IOException("Sigla de veículo não definida usada na " 
                    + "qualificação do ano “" + ano + "”: "+ veiculo + ".");
                }
                String classificacao = campos[2];
                veiculosCadastrados.get(veiculo).qualis.put(ano, classificacao);
                linha = arquivo.readLine();
            }
        } catch (IOException e){
            throw new IOException(e.getMessage());
        }
    }


    /* ************************* SAIDAS ************************** */

    public void calculaResultados(){
        for (Map.Entry<Long, Docente> pair : docentesCadastrados.entrySet()) {
            pair.getValue().calculaPontuacao();
        }
    }
}