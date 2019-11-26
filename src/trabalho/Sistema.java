package trabalho;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class Sistema implements Serializable{
    private HashMap<Long,Docente> docentesCadastrados = new HashMap<Long,Docente>();
    private HashMap<String, Veiculo> veiculosCadastrados = new HashMap<String, Veiculo>();
    private ArrayList<Publicacao> publicacoesCadastradas = new ArrayList<Publicacao>();    
    private ArrayList<Regra> regrasCadastradas = new ArrayList<Regra>();
    private int anoRecredenciamento;

    public Sistema(int ano){
        this.anoRecredenciamento = ano;
    }

    public int getAnoRecredenciamento() {
        return anoRecredenciamento;
    }

    /* ************************* DOCENTES ************************** */

    /**
     * Carrega dados de docentes dados no arquivo de entrada no sistema.
     * @param arquivo Arquivo que contém dados sobre docentes.
     * @throws IOException
     */
    public void carregaArquivoDocentes(BufferedReader arquivo) throws IOException {
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while (linha != null) {
                String[] campos = linha.split(";");
                long codigo = Long.parseLong(campos[0]);
                String nome = campos[1];
                String data [] = campos[2].split("/");
                int dia;
                int mes;
                int ano;
                LocalDate nascimento =  LocalDate.of(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0]));
                data  = campos[3].split("/");
                LocalDate ingresso =   LocalDate.of(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0]));
                boolean coordenador = false;
                // System.out.println(linha);
                if (campos.length == 5) {
                    if (campos[4].equals("X")) {
                        coordenador = true;
                    }
                }
                Docente docente = new Docente(codigo, nome, nascimento, ingresso, coordenador);
                insereDocente(docente);
                linha = arquivo.readLine();
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }

    }


    public void imprimeDocentes(){
        for (Map.Entry<Long,Docente> pair : docentesCadastrados.entrySet()) {
            pair.getValue().imprime();
        }
    }

    /**
     * Insere um docente na Hash de docentes cadastrados.
     * @param d é uma instancia da Classe Docente.
     * @throws IOException O mesmo código foi usado para dois 
     * docentes ou veículos diferentes.
     */
    public void insereDocente(Docente d) throws IOException {
        try{
            if (!verificaDocente(d)){
                this.docentesCadastrados.put(d.getCodigo(), d);
            }
            else{
                throw new IOException("Código repetido para docente" + 
                ": " + d.getCodigo() + ".");
            }
        }catch(IOException e){
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Verifica se já existe algum docente cadastrado com aquele código.
     * @param d Instância da Classe Docente.
     * @return retorna true se existir um docente com aquele codigo na Hash
     * de docentes cadastrados e false caso contrário.
     */
    public boolean verificaDocente(Docente d){
        Docente docente = docentesCadastrados.get(d.getCodigo());
        return (docente != null);
    }

    /**
     * Calcula a pontuação do docente baseada na ultima regra registrada.
     * @param regra é uma instancia da Classe regra.
     * @param docente é uma instancia da Classe Docente.
     */
    public void calculaPontuacao(Regra regra, Docente docente){
        double pontuacao = 0;
        for (Publicacao p :docente.getPublicacoes()) {
            Veiculo veiculo = veiculosCadastrados.get(p.getVeiculo());
            if(p.getAno() < anoRecredenciamento && anoRecredenciamento - p.getAno() <= regra.getAnosVigencia()){
                if (veiculo.getTipo() == 'P') {
                    pontuacao += regra.getMultiplicador()*regra.getPontos().get(valorQuali(p.getQuali()));
                }else{
                    pontuacao += regra.getPontos().get(valorQuali(p.getQuali()));
                }
            }
        }
        docente.setPontuacao(pontuacao);
    }

    /* ************************* VEICULOS ************************** */

    /**
     * Carrega os veículos do arquivo de entrada no sistema.
     * @param arquivo Arquivo de entrada que contém as informações dos veículos.
     * @throws IOException Tipo de um veículo não é nem ‘C’ nem ‘P’.
     */
    public void carregaArquivoVeiculos(BufferedReader arquivo) throws IOException {
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while(linha != null){
                String[] campos = linha.split(";");
                String sigla = campos[0].trim();            /* limpa espaços antes e depois da String */
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
     * @param v Instância da Classe Veiculo.
     * @throws IOExceptionO mesmo código foi usado para dois 
     * dveículos diferentes.
     */
    public void insereVeiculo(Veiculo v) throws IOException {
        try{
            if (!verificaVeiculo(v)){
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

    /**
     * Retorna true se existir um Veiculo com aquele codigo na Hash
     * de veiculos cadastrados e false caso contrário.
     * @param v
     * @return
     */
    public boolean verificaVeiculo(Veiculo v){
        Veiculo veiculo = veiculosCadastrados.get(v.getSigla());
        return (veiculo != null);
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


    /**
     * Carrega as publicações do arquivo de entrada no sistema.
     * @param arquivo Arquivo de entrada que contém as informações das publicações
     * @throws IOException
     */
    public void carregaArquivoPublicacoes(BufferedReader arquivo) throws IOException{
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while(linha != null){
                Publicacao publicacao = null;
                String[] campos = linha.split(";");
                int ano = Integer.parseInt(campos[0]);
                String veiculo = campos[1].trim();
                String titulo = campos[2].trim();
                String autores = campos[3];
                String[] autor = autores.split(",");
                ArrayList<Long> autorLong = new ArrayList<Long>();
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
                    autorLong.add(Long.parseLong(autor[i].trim()));
                }
                publicacao = new Publicacao(ano, veiculo, titulo, pinicial, pfinal, autorLong);

                registraPublicacao(publicacao);
                Veiculo v = veiculosCadastrados.get(veiculo);
                int menor = 0;
                int menorano = anoRecredenciamento;
                boolean trava = false;
                try{
                    int i = v.getQualis().size();
                    for (Map.Entry<Integer,String> pair : v.getQualis().entrySet()) {
                        if (pair.getKey() > ano){
                            i--;
                            continue;
                        }
                        if (!trava){
                            menorano = pair.getKey();
                            menor = ano - pair.getKey();
                            trava = true;
                        }else{
                            if (ano - pair.getKey() < menor){
                                menorano = pair.getKey();
                                menor = ano - pair.getKey();
                            }
                        }
                    }
                    if (i == 0){
                        throw new IllegalArgumentException();
                    }
                }catch(IllegalArgumentException e){
                    e.printStackTrace();
                }
                publicacao.setQuali(v.getQualis().get(menorano));
                publicacoesCadastradas.add(publicacao);
                atribuiPublicacao(publicacao);
                linha = arquivo.readLine();
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void imprimePublicacoes(){
        for (Publicacao p : publicacoesCadastradas){
            p.imprime(docentesCadastrados, veiculosCadastrados);
        }
    }

    /**
     * Atribui uma publicação a um determinado docente.
     * @param p instancia da Classe publicação
     * @throws IOException O mesmo código foi usado para dois
     * docentes ou veículos diferentes.
     */
    public void atribuiPublicacao(Publicacao p) throws IOException {
        for (Long l: p.getAutores()){
            Docente docente = docentesCadastrados.get(l);
            // Docente docente = docentesCadastrados.get(p.getAutor());
            if (docente == null){
                throw new IOException("Código de docente não definido usado na "
                + "publicação \"" + p.getTitulo() + "\": " + l +".");
            }
            else{
                docente.getPublicacoes().add(p);
                if(!docente.getQualisObtidos().get(valorQuali(p.getQuali()))){
                    docente.getQualisObtidos().set(valorQuali(p.getQuali()), true);
                }
            }
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
                " publicação \"" + p.getTitulo() + "\": " + p.getVeiculo() + ".");
            }
        }catch(IOException e){
            throw new IOException(e.getMessage());
        }
    }


    /* ************************* REGRAS ************************** */

    /**
     * Carrega as regras do arquivo de entrada no sistema.
     * @param arquivo Contém as informações das regras.
     */
    public void carregaArquivoRegras(BufferedReader arquivo){
        try{
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while(linha != null){
                String[] campos = linha.split(";");
                String data [] = campos[0].split("/");

                LocalDate dataInicio = LocalDate.of(Integer.parseInt(data[2]),
                                                    Integer.parseInt(data[1]),
                                                    Integer.parseInt(data[0]));
                data = campos[1].split("/");
                LocalDate dataFim = LocalDate.of(Integer.parseInt(data[2]),
                                                Integer.parseInt(data[1]),
                                                Integer.parseInt(data[0]));
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
                Regra regra = new Regra(dataInicio, dataFim, 
                                        multiplicador, anos, pontuacao,
                                        designaPontosPorQuali(qualis, pontos));
                regrasCadastradas.add(regra);
                linha = arquivo.readLine();
            }
        } catch (IOException e) {
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
            if (j < vetorPosicao.size()){
                if(vetorPosicao.get(j) == i ){
                    pontuacao = Integer.parseInt(stringPontos[j]);
                    j++;
                }
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
            default:
                return 7;
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

    public String retornaQuali(int numero){
        switch (numero){
            case 0:
                return "A1";
            case 1:
                return "A2";
            case 2:
                return "B1";
            case 3:
                return "B2";
            case 4:
                return "B3";
            case 5:
                return "B4";
            case 6:
                return "B5";
            case 7:
            default:
                return "C";
        }
    }

    public Regra escolheRegra(){
        Regra r = null;
        for (Regra regra: regrasCadastradas) {
            if(regra.getDataInicio().getYear()==anoRecredenciamento){
                return regra;
            }else{
                if(r == null || (r.getDataInicio().getYear()<regra.getDataInicio().getYear())&& r.getDataInicio().getYear()<anoRecredenciamento){
                    r = regra;
                }
            }
        }
        return r;
    }

    /* ************************* QUALIS ************************** */

    /**
     * Carrega o arquivo com as qualis de cada veiculo no sistema.
     * @param arquivo
     * @throws IOException
     */
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

    /**
     * 
     * @throws IOException
     */
    public void calculaResultados() throws IOException {
        Regra regra = escolheRegra();
        ArrayList<Docente> docentes = new ArrayList<Docente>();
        for (Map.Entry<Long, Docente> pair : docentesCadastrados.entrySet()) {
            calculaPontuacao(regra, pair.getValue());
            docentes.add(pair.getValue());
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("1-recredenciamento.csv")));
        writer.write("Docente;Pontuação;Recredenciado?");
        writer.newLine();
        Collections.sort(docentes, new Comparator<Docente>() {
            @Override
            public int compare(Docente docente, Docente t1) {
                return docente.getNome().compareTo(t1.getNome());
            }
        });
        for (Docente d : docentes) {
            String pontuacao = String.valueOf(d.getPontuacao());
            writer.append(d.getNome());
            writer.append(";");
            pontuacao = pontuacao.replace(".", ",");
            writer.append(pontuacao);
            writer.append(";");
            if (d.isCoordenador()) {
                writer.append("Coordenador");
            } else {
                long anos = ChronoUnit.YEARS.between(d.getDataIngresso(), regra.getDataInicio());
                if (anos < 3) {
                    writer.append("PPJ");
                } else {
                    anos = ChronoUnit.YEARS.between(d.getDataNascimento(), regra.getDataInicio());
                    if (anos >= 60) {
                        writer.append("PPS");
                    } else {
                        if (d.getPontuacao() > regra.getPontuacaoMinima()) {
                            writer.append("Sim");
                        } else {
                            writer.append("Não");
                        }
                    }
                }
            }
            writer.newLine();
        }
        writer.close();
    }

    /* ========== Publicações ========== */

    /**
     * Produz o arquivo de saida com as informações sobre as publicações cadastradas,
     * são ordenadas usando critério da seguinte forma: 
     * quali decrescente,
     * ano decrescente,
     * sigla do véiculo crescente,
     * titulo de publicação crescente.
     * @throws IOException 
     */
    public void listaPublicacoes() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("2-publicacoes.csv")));
        bw.write("Ano;Sigla Veículo;Veículo;Qualis;Fator de Impacto;Título;Docentes");
        bw.newLine();
        String aux;
        Collections.sort(publicacoesCadastradas, new Comparator<Publicacao>() {
            @Override
            public int compare(Publicacao p1, Publicacao p2) {
                return p1.getTitulo().compareTo(p2.getTitulo());
            }
        });
        Collections.sort(publicacoesCadastradas, new Comparator<Publicacao>() {
            @Override
            public int compare(Publicacao p1, Publicacao p2) {
                return p1.getVeiculo().compareTo(p2.getVeiculo());
            }
        });
        Collections.sort(publicacoesCadastradas, new Comparator<Publicacao>() {
            @Override
            public int compare(Publicacao p1, Publicacao p2) {
                return p1.getAno() > p2.getAno()? -1 : (p1.getAno() < p2.getAno()? 1 : 0);
                // return p1.getAno().compareTo(p2.getAno());
            }
        });
        Collections.sort(publicacoesCadastradas, new Comparator<Publicacao>() {
            @Override
            public int compare(Publicacao p1, Publicacao p2) {
                return p1.getQuali().compareTo(p2.getQuali());
            }
        });
        for (Publicacao p : publicacoesCadastradas){
            aux = String.valueOf(p.getAno());
            bw.append(aux + ';');
            bw.append(p.getVeiculo() + ';');
            bw.append(veiculosCadastrados.get(p.getVeiculo()).getNome() + ';');
            bw.append(p.getQuali() + ';');
            NumberFormat formatter = new DecimalFormat("#0.000");
            aux = String.valueOf(formatter.format(veiculosCadastrados.get(p.getVeiculo()).getFatorImpacto()));
            aux = aux.replace(".", ",");
            bw.append(aux + ';');
            bw.append(p.getTitulo() + ';');
            for (Long l: p.getAutores()){
                bw.append(docentesCadastrados.get(l).getNome());
                if (l != p.getAutores().get(p.getAutores().size() - 1)){
                    bw.append(",");
                }
            }
            bw.newLine();
        }
        bw.close();
    }


    public void calculaEstatisticas() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("3-estatisticas.csv")));
        writer.write("Qualis;Qtd. Artigos;Média Artigos / Docente");
        writer.newLine();
        NumberFormat formatter = new DecimalFormat("#0.00");
        ArrayList<Integer> qtdArtigos = new ArrayList<Integer>();
        ArrayList<Double> artPorDoc = new ArrayList<Double>();
        for(int i = 0; i< 8; i++){
            qtdArtigos.add(0);
            artPorDoc.add(0.0);
        }
        for(Publicacao p: publicacoesCadastradas){
            int ajudante = qtdArtigos.get(valorQuali(p.getQuali()));
            ajudante++;
            qtdArtigos.set(valorQuali(p.getQuali()), ajudante);
            double ajudante2 = artPorDoc.get(valorQuali(p.getQuali()));
            ajudante2+= (1.0/p.getAutores().size());
            artPorDoc.set(valorQuali(p.getQuali()), ajudante2);
        }

        for (int i = 0; i<8; i++){
            String aux = String.valueOf(formatter.format(artPorDoc.get(i)));
            aux = aux.replace(".", ",");
            writer.append(retornaQuali(i)+";"+qtdArtigos.get(i)+";"+aux);
            writer.newLine();
        }
        writer.close();
    }
}