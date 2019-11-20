package trabalho;

import java.text.ParseException;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
public class Main {
    // public static ArrayList<Docente> docentesCadastrados = new ArrayList<Docente>();
    // public static ArrayList<Regra> regras = new ArrayList<Regra>();
    public static void main(String[] args) {
        int anoDaRegra;
        // String arq1s = null;
        // String arq2s = null;
        // String arq3s = null;
        // String arq4s = null;
        // String arq5s = null;
        FileReader arq1 = null;
        FileReader arq2 = null;
        FileReader arq3 = null;
        FileReader arq4 = null;
        FileReader arq5 = null;
        try{
            for (int i = 0; i < args.length; i++){
                switch (args[i]){
                    case "-d":
                    arq1 = new FileReader(args[i+1]);
                    i++;
                    break;
                case "-v":
                    arq2 = new FileReader(args[i+1]);
                    i++;
                    break;
                case "-p":
                    arq3 = new FileReader(args[i+1]);
                    i++;
                    break;
                case "-q":
                    arq4 = new FileReader(args[i+1]);
                    i++;
                    break;
                case "-r":
                    arq5 = new FileReader(args[i+1]);
                    i++;
                    break;
                case "-a":
                    anoDaRegra = Integer.parseInt(args[i+1]);
                    i++;
                    break;
                default:
                    break;
                }
            }
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        // Scanner leitor = new Scanner(System.in);
        Sistema ppgi = new Sistema();
        try  {
            // FileReader arq1 = new FileReader(args[1]);
            // FileReader arq2 = new FileReader("veiculos.csv");
            // FileReader arq3 = new FileReader("publicacoes.csv");
            // FileReader arq4 = new FileReader("regras.csv");
            // FileReader arq5 = new FileReader("qualis.csv");
            BufferedReader fdocente = new BufferedReader(arq1);
            BufferedReader fveiculo = new BufferedReader(arq2);
            BufferedReader fpublicacoes = new BufferedReader(arq3);
            BufferedReader fregras = new BufferedReader(arq5);
            BufferedReader fqualis = new BufferedReader(arq4);
            ppgi.carregaArquivoDocentes(fdocente);
            ppgi.carregaArquivoVeiculos(fveiculo);
            ppgi.carregaArquivoRegras(fregras);
            ppgi.carregaArquivoPublicacoes(fpublicacoes);
            ppgi.carregaArquivoQualis(fqualis);
            // ppgi.imprimeDocentes();
            // ppgi.imprimeVeiculos();
            // ppgi.imprimePublicacoes();
            ppgi.imprimeRegras();
            arq1.close();
            arq2.close();
            arq3.close();
            arq4.close();
            arq5.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
