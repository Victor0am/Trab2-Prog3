package trabalho;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;


public class Sistema implements Serializable {
    private HashMap<Long, Docente> docentesCadastrados = new HashMap<Long, Docente>();
    private HashMap<String, Veiculo> veiculosCadastrados = new HashMap<String, Veiculo>();
    private ArrayList<Publicacao> publicacoesCadastradas = new ArrayList<Publicacao>();
    private ArrayList<Regra> regrasCadastradas = new ArrayList<Regra>();
    private int anoRecredenciamento;

    public Sistema(int ano) {
        this.anoRecredenciamento = ano;
    }

    public int getAnoRecredenciamento() {
        return anoRecredenciamento;
    }

    /* ************************* DOCENTES ************************** */

    /**
     * Carrega dados de docentes dados no arquivo de entrada no sistema.
     *
     * @param arquivo Arquivo que contém dados sobre docentes.
     * @throws IOException
     */
    public void carregaArquivoDocentes(BufferedReader arquivo) throws IOException, NumberFormatException {
        String linha = arquivo.readLine();
        linha = arquivo.readLine();
        while (linha != null) {
            String[] campos = linha.split(";");
            long codigo = Long.parseLong(campos[0]);
            String nome = campos[1];
            for (int i = 0; i < nome.length(); i++) {
                if (nome.charAt(i) >= '0' && nome.charAt(i) <= '9') {
                    throw new NumberFormatException("nome que possui um numero");
                }
            }
            if (campos[2].length() != 10 || campos[3].length() != 10) {
                throw new NumberFormatException("data com fomato errado");
            } else {
                if (campos[2].charAt(2) != '/' || campos[2].charAt(5) != '/' || campos[3].charAt(2) != '/' || campos[3].charAt(5) != '/') {
                    throw new NumberFormatException("data com fomato errado");
                }
            }
            String data[] = campos[2].split("/");
            LocalDate nascimento = LocalDate.of(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0]));
            data = campos[3].split("/");
            LocalDate ingresso = LocalDate.of(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0]));
            boolean coordenador = false;
            if (campos.length == 5) {
                if (campos[4].equals("X")) {
                    coordenador = true;
                }
            }
            Docente docente = new Docente(codigo, nome, nascimento, ingresso, coordenador);
            insereDocente(docente);
            linha = arquivo.readLine();
        }
    }

    /**
     * Insere um docente na Hash de docentes cadastrados.
     *
     * @param d é uma instancia da Classe Docente.
     * @throws IOException O mesmo código foi usado para dois
     *                     docentes ou veículos diferentes.
     */
    public void insereDocente(Docente d) throws IOException {
        if (!verificaDocente(d)) {
            this.docentesCadastrados.put(d.getCodigo(), d);
        } else {
            throw new IOException("Código repetido para docente" +
                    ": " + d.getCodigo() + ".");
        }
    }

    /**
     * Verifica se já existe algum docente cadastrado com aquele código.
     *
     * @param d Instância da Classe Docente.
     * @return retorna true se existir um docente com aquele codigo na Hash
     * de docentes cadastrados e false caso contrário.
     */
    public boolean verificaDocente(Docente d) {
        Docente docente = docentesCadastrados.get(d.getCodigo());
        return (docente != null);
    }

    /**
     * Calcula a pontuação do docente baseada na ultima regra registrada.
     *
     * @param regra   é uma instancia da Classe Regra.
     * @param docente é uma instancia da Classe Docente.
     */
    public void calculaPontuacao(Regra regra, Docente docente) {
        double pontuacao = 0;
        for (Publicacao p : docente.getPublicacoes()) {
            Veiculo veiculo = veiculosCadastrados.get(p.getVeiculo());
            if (p.getAno() < anoRecredenciamento && anoRecredenciamento - p.getAno() <= regra.getAnosVigencia()) {
                if (veiculo.getTipo() == 'P') {
                    pontuacao += regra.getMultiplicador() * regra.getPontos().get(valorQuali(p.getQuali()));
                } else {
                    pontuacao += regra.getPontos().get(valorQuali(p.getQuali()));
                }
            }
        }
        docente.setPontuacao(pontuacao);
    }

    /* ************************* VEICULOS ************************** */

    /**
     * Carrega os veículos do arquivo de entrada no sistema.
     *
     * @param arquivo Arquivo de entrada que contém as informações dos veículos.
     * @throws IOException Tipo de um veículo não é nem ‘C’ nem ‘P’.
     */
    public void carregaArquivoVeiculos(BufferedReader arquivo) throws IOException {
        String linha = arquivo.readLine();
        linha = arquivo.readLine();
        while (linha != null) {
            String[] campos = linha.split(";");
            String sigla = campos[0].trim();            /* limpa espaços antes e depois da String */
            String nome = campos[1].trim();
            if (isNumeric(nome)) {
                throw new NumberFormatException("nome numérico");
            }
            char tipo = campos[2].charAt(0);
            String stipo = campos[2];
            campos[3] = campos[3].replace(',', '.');
            double impacto = Double.parseDouble(campos[3]);
            Veiculo veiculo = null;
            switch (stipo) {
                case "C":
                    veiculo = new Conferencia(sigla, nome, tipo, impacto);
                    insereVeiculo(veiculo);
                    break;
                case "P":
                    String issn = campos[4];
                    if (issn.length() != 9 || issn.charAt(4) != '-') {
                        throw new NumberFormatException("erro de formatação de issn");
                    }
                    veiculo = new Periodico(sigla, nome, tipo, impacto, issn);
                    insereVeiculo(veiculo);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de veículo desconhecido " +
                            "para veículo " + sigla + ": " + stipo + ".");
            }
            linha = arquivo.readLine();
        }
    }

    /**
     * Verifica se a string é puramente numérica
     *
     * @param strNum é a string que será verificada
     * @return Retorna um booleano correspondente a ele ser ou não numérico
     */
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Adiciona um veiculo à Hash de veículos cadastrados.
     *
     * @param v Instância da Classe Veiculo.
     * @throws IOException mesmo código foi usado para dois
     *                     dveículos diferentes.
     */
    public void insereVeiculo(Veiculo v) throws IOException {
        if (!verificaVeiculo(v)) {
            this.veiculosCadastrados.put(v.getSigla(), v);
        } else {
            throw new IOException("Código repetido para " + v.getNome()
                    + ": " + v.getSigla() + ".");
        }
    }

    /**
     * Verifica se o veiculo se o veiculo ja foi cadastrado.
     *
     * @param v Instância da Classe Veiculo.
     * @return Retorna true se existir um Veiculo com aquele codigo na Hash
     * de veiculos cadastrados e false caso contrário.
     */
    public boolean verificaVeiculo(Veiculo v) {
        Veiculo veiculo = veiculosCadastrados.get(v.getSigla());
        return (veiculo != null);
    }

    public void imprimeVeiculos() {
        for (Map.Entry<String, Veiculo> pair : veiculosCadastrados.entrySet()) {
            pair.getValue().imprimeVeiculo();
            System.out.println();
        }
    }

    /**
     * Dada uma sigla de veículo, verifica se o veiculo cadastrado
     * é do tipo Periodico.
     *
     * @param veiculo é uma sigla de um veículo
     * @return Retorna true se o veiculo for de fato um periódico,
     * false caso contrário.
     */
    public boolean isPeriodico(String veiculo) {
        veiculosCadastrados.get(veiculo);
        if (veiculosCadastrados.get(veiculo) == null) {
            return false;
        }
        if (veiculosCadastrados.get(veiculo).getTipo() == 'P') {
            return true;
        }
        return false;
    }

    /* ************************* PUBLICAÇÕES ************************** */


    /**
     * Carrega as publicações do arquivo de entrada no sistema.
     *
     * @param arquivo Arquivo de entrada que contém as informações das publicações
     * @throws IOException O mesmo código foi usado para dois
     *                     docentes ou veículos diferentes.
     */
    public void carregaArquivoPublicacoes(BufferedReader arquivo) throws IOException {
        String linha = arquivo.readLine();
        linha = arquivo.readLine();
        while (linha != null) {
            Publicacao publicacao = null;
            String[] campos = linha.split(";");
            int ano = Integer.parseInt(campos[0]);
            String veiculo = campos[1].trim();
            String titulo = campos[2].trim();
            if (isNumeric(titulo)) {
                throw new NumberFormatException("Titulo é numerico");
            }
//            for (int i = 0; i < titulo.length(); i++) {
//                if(titulo.charAt(i)>='0' && titulo.charAt(i)<='9'){
//                    throw new NumberFormatException("nome que possui um numero");
//                }
//            }
            String autores = campos[3];
            String[] autor = autores.split(",");
            ArrayList<Long> autorLong = new ArrayList<Long>();
            int numero = Integer.parseInt(campos[4]);
            int volume;
            String local;
            int pinicial = Integer.parseInt(campos[7]);
            if (isPeriodico(veiculo)) {
                volume = Integer.parseInt(campos[5]);
            } else {
                local = campos[6];
                for (int i = 0; i < local.length(); i++) {
                    if (local.charAt(i) >= '0' && local.charAt(i) <= '9') {
                        throw new NumberFormatException("número no local");
                    }
                }
            }
            int pfinal = Integer.parseInt(campos[8]);
            for (int i = 0; i < autor.length; i++) {
                autorLong.add(Long.parseLong(autor[i].trim()));
            }
            publicacao = new Publicacao(ano, veiculo, titulo, pinicial, pfinal, autorLong);

            registraPublicacao(publicacao);
            Veiculo v = veiculosCadastrados.get(veiculo);
            int menor = 0;
            int menorano = anoRecredenciamento;
            boolean trava = false;
            try {
                int i = v.getQualis().size();
                for (Map.Entry<Integer, String> pair : v.getQualis().entrySet()) {
                    if (pair.getKey() > ano) {
                        i--;
                        continue;
                    }
                    if (!trava) {
                        menorano = pair.getKey();
                        menor = ano - pair.getKey();
                        trava = true;
                    } else {
                        if (ano - pair.getKey() < menor) {
                            menorano = pair.getKey();
                            menor = ano - pair.getKey();
                        }
                    }
                }
                if (i == 0) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            publicacao.setQuali(v.getQualis().get(menorano));
            publicacoesCadastradas.add(publicacao);
            atribuiPublicacao(publicacao);
            linha = arquivo.readLine();
        }
    }

    public void imprimePublicacoes() {
        for (Publicacao p : publicacoesCadastradas) {
            p.imprime(docentesCadastrados, veiculosCadastrados);
        }
    }

    /**
     * Atribui uma publicação a um determinado docente.
     *
     * @param p instancia da Classe publicação.
     * @throws IOException O mesmo código foi usado para dois
     *                     docentes ou veículos diferentes.
     */
    public void atribuiPublicacao(Publicacao p) throws IOException {
        for (Long l : p.getAutores()) {
            Docente docente = docentesCadastrados.get(l);
            // Docente docente = docentesCadastrados.get(p.getAutor());
            if (docente == null) {
                throw new IOException("Código de docente não definido usado na "
                        + "publicação \"" + p.getTitulo() + "\": " + l + ".");
            } else {
                docente.getPublicacoes().add(p);
                if (!docente.getQualisObtidos().get(valorQuali(p.getQuali()))) {
                    docente.getQualisObtidos().set(valorQuali(p.getQuali()), true);
                }
            }
        }
    }

    /**
     * Adiciona uma publicação a lista de publicações com base no sigla
     * do veiculo da publicação de entrada.
     *
     * @param p Instância da Classe Publicação.
     * @throws IOException Sigla de veículo especificada para uma
     *                     publicação não foi definida na planilha de veículos.
     */
    public void registraPublicacao(Publicacao p) throws IOException {
        Veiculo veiculos = null;
        veiculos = veiculosCadastrados.get(p.getVeiculo());
        try {
            if (veiculos != null) {
                veiculos.getPublicacoes().add(p);
            } else {
                throw new IOException("Sigla de veículo não definida usada na" +
                        " publicação \"" + p.getTitulo() + "\": " + p.getVeiculo() + ".");
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }


    /* ************************* REGRAS ************************** */

    /**
     * Carrega as regras do arquivo de entrada no sistema.
     *
     * @param arquivo Contém as informações das regras.
     * @throws IOException
     */

    public void carregaArquivoRegras(BufferedReader arquivo) throws IOException {
        try {
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while (linha != null) {
                String[] campos = linha.split(";");
                if (campos[0].length() != 10 || campos[1].length() != 10) {
                    throw new NumberFormatException("data com fomato errado");
                } else {
                    if (campos[0].charAt(2) != '/' || campos[0].charAt(5) != '/' || campos[1].charAt(2) != '/' || campos[1].charAt(5) != '/') {
                        throw new NumberFormatException("data com fomato errado");
                    }
                }
                String data[] = campos[0].split("/");

                LocalDate dataInicio = LocalDate.of(Integer.parseInt(data[2]),
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[0]));
                data = campos[1].split("/");
                LocalDate dataFim = LocalDate.of(Integer.parseInt(data[2]),
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[0]));
                String qualis[] = campos[2].split(",");
                String pontos[] = campos[3].split(",");
                for (String quali : qualis) {
                    if (!checaQuali(quali)) {
                        throw new IOException("Qualis desconhecido para regras de " + campos[0] + ": " + quali);
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
        }
    }

    /**
     * Interpreta as informações de ponstos por qualis das regras
     *
     * @param stringQualis é a lista dos qualis onde tem mudança de pontuação nas regras.
     * @param stringPontos é a lista da pontuação dos qualis que tem mudança de pontuação.
     * @return Retorna um vetor(ArrayList) de 8 posições (1 posição por quali), onde cada posição indica a pontuação de um qualis
     * Qualis de cada posição:
     * A1 = 0, A2 = 1, B1 = 2, B2 = 3, B3 = 4, B4 = 5, B5 = 6, C = 7
     */
    public ArrayList<Integer> designaPontosPorQuali(String stringQualis[], String stringPontos[]) {
        ArrayList<Integer> vetorPosicao = new ArrayList<Integer>();
        ArrayList<Integer> vetorPontos = new ArrayList<Integer>();
        Qualis[] qualis = Qualis.values();
        for (int i = 0; i < stringQualis.length; i++) {
            vetorPosicao.add(valorQuali(stringQualis[i]));
        }
        for (int i = 0, pontuacao = 0, j = 0; i < 8; i++) {
            if (j < vetorPosicao.size()) {
                if (vetorPosicao.get(j) == i) {
                    pontuacao = Integer.parseInt(stringPontos[j]);
                    j++;
                }
            }
            vetorPontos.add(pontuacao);
        }
        return vetorPontos;
    }


    public void imprimeRegras() {
        for (Regra r : regrasCadastradas) {
            r.imprime();
        }
    }


    /**
     * Retorna a posição de uma qualis no vetor de regras
     *
     * @param quali é uma string que contem uma sigla do quali
     * @return Retorna um inteiro correspondente à posição do quali no vetor de pontuação da regra
     */
    public int valorQuali(String quali) {
        switch (quali) {
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

    /**
     * Checa se um qualis é um dos permitidos pelo sistema
     *
     * @param quali é uma string com a sigla qualis
     * @return Retorna true se a string for uma das 8 siglas de qualis do programa e false se não for
     */
    public boolean checaQuali(String quali) {
        switch (quali) {
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

    /**
     * Retorna o qualis da posição desejada
     *
     * @param numero é o numero da posição do vetor do qualis
     * @return Retorna o qualis da posição indicada
     */
    public String retornaQuali(int numero) {
        switch (numero) {
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

    /**
     * Escolhe a regra vigente com base no ano de recredenciamento
     *
     * @return Retorna a Regra do ano de recredenciamento ou a regra mais próxima ao ano de recredenciamento de forma coerente
     * por exemplo: se o recredenciamento escolhido for 2018 e existirem 2 regras cadastradas, mas nenhuma delas é de 2018,
     * uma é de 2014 e a outra é de 2019, a função vai escolher a regra de 2014
     */
    public Regra escolheRegra() {
        Regra r = null;
        for (Regra regra : regrasCadastradas) {
            if (regra.getDataInicio().getYear() == anoRecredenciamento) {
                return regra;
            } else {
                if (r == null || (r.getDataInicio().getYear() < regra.getDataInicio().getYear()) && r.getDataInicio().getYear() < anoRecredenciamento) {
                    r = regra;
                }
            }
        }
        return r;
    }

    /* ************************* QUALIS ************************** */

    /**
     * Carrega o arquivo com as qualis de cada veiculo no sistema.
     *
     * @param arquivo Arquivo que contém as informações das qualificações.
     * @throws IOException Qualis desconhecido para qualificação do
     *                     veículo <sigla> no ano <ano>: <qualis> ou
     *                     Qualis especificado para uma regra de pontuação
     *                     não é nenhuma das categorias existentes:
     *                     A1, A2, B1, B2, B3, B4, B5 ou C.
     */
    public void carregaArquivoQualis(BufferedReader arquivo) throws IOException {
        try {
            String linha = arquivo.readLine();
            linha = arquivo.readLine();
            while (linha != null) {
                String[] campos = linha.split(";");
                int ano = Integer.parseInt(campos[0]);
                String veiculo = campos[1].trim();
                if (veiculosCadastrados.get(veiculo) == null) {
                    throw new IOException("Sigla de veículo não definida usada na "
                            + "qualificação do ano \"" + ano + "\": " + veiculo + ".");
                }
                String classificacao = campos[2];
                if (checaQuali(classificacao)) {
                    veiculosCadastrados.get(veiculo).qualis.put(ano, classificacao);
                } else {
                    throw new IOException("Qualis desconhecido para qualificação do" +
                            " veículo " + veiculo + " no ano " + ano + ": " + classificacao + ".");
                }
                linha = arquivo.readLine();
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }


    /* ************************* SAIDAS ************************** */

    /**
     * Cria e escreve o arquivo de saída 1-recredenciamento.csv.
     * Nesse arquivo são listados os docentes, seus pontos e se foram recredenciados em ordem alfabética.
     * As opções de recredenciamento envolvem:
     * 1)se é coordenador, recredenciado automaticamente.
     * 2)se tem menos de 3 anos credenciado, recredenciado automaticamente.
     * 3)se tem 60 anos ou mais, recredenciamento automático.
     * 4)se tem pontuação maior que a pontuação mínima da regra vigente, é recredenciado.
     * 5)se não possui nenhum dos critérios acima, não é recredenciado.
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
                        if (d.getPontuacao() >= regra.getPontuacaoMinima()) {
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
     *
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
                return p1.getAno() > p2.getAno() ? -1 : (p1.getAno() < p2.getAno() ? 1 : 0);
            }
        });
        Collections.sort(publicacoesCadastradas, new Comparator<Publicacao>() {
            @Override
            public int compare(Publicacao p1, Publicacao p2) {
                return p1.getQuali().compareTo(p2.getQuali());
            }
        });
        for (Publicacao p : publicacoesCadastradas) {
            aux = String.valueOf(p.getAno());
            bw.append(aux + ';');
            bw.append(p.getVeiculo() + ';');
            bw.append(veiculosCadastrados.get(p.getVeiculo()).getNome() + ';');
            bw.append(p.getQuali() + ';');
            NumberFormat formatter = new DecimalFormat("#0.000"); // Formatar para 3 digitos decimais
            aux = String.valueOf(formatter.format(veiculosCadastrados.get(p.getVeiculo()).getFatorImpacto()));
            aux = aux.replace(".", ",");
            bw.append(aux + ';');
            bw.append(p.getTitulo() + ';');
            for (Long l : p.getAutores()) {
                bw.append(docentesCadastrados.get(l).getNome());
                if (l != p.getAutores().get(p.getAutores().size() - 1)) {
                    bw.append(",");
                }
            }
            bw.newLine();
        }
        bw.close();
    }

    /**
     * Cria e escreve o arquivo 3-estatisticas.csv
     * Nesse arquivo são lsitados os qualis, a quantidade de publicações por qualis e a média de artigos por docente
     * se para calcular a quantidade de publicações é necessário somar 1 a cada publicação com o quali selecionado,
     * essa média é dada por ao invés de somar 1, somar 1/(número de docentes que participaram da publicação).
     *
     * @throws IOException Exceção que ocorrerá se o arquivo não tiver permissao para ser escrito.
     */
    public void calculaEstatisticas() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("3-estatisticas.csv")));
        writer.write("Qualis;Qtd. Artigos;Média Artigos / Docente");
        writer.newLine();
        NumberFormat formatter = new DecimalFormat("#0.00");
        ArrayList<Integer> qtdArtigos = new ArrayList<Integer>();
        ArrayList<Double> artPorDoc = new ArrayList<Double>();
        for (int i = 0; i < 8; i++) {
            qtdArtigos.add(0);
            artPorDoc.add(0.0);
        }
        for (Publicacao p : publicacoesCadastradas) {
            int ajudante = qtdArtigos.get(valorQuali(p.getQuali()));
            ajudante++;
            qtdArtigos.set(valorQuali(p.getQuali()), ajudante);
            double ajudante2 = artPorDoc.get(valorQuali(p.getQuali()));
            ajudante2 += (1.0 / p.getAutores().size());
            artPorDoc.set(valorQuali(p.getQuali()), ajudante2);
        }

        for (int i = 0; i < 8; i++) {
            String aux = String.valueOf(formatter.format(artPorDoc.get(i)));
            aux = aux.replace(".", ",");
            writer.append(retornaQuali(i) + ";" + qtdArtigos.get(i) + ";" + aux);
            writer.newLine();
        }
        writer.close();
    }
}
