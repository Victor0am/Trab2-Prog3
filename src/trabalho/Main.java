package trabalho;

import java.text.ParseException;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
public class Main {
    // public static ArrayList<Docente> docentesCadastrados = new ArrayList<Docente>();
    // public static ArrayList<Regra> regras = new ArrayList<Regra>();
    public static void main(String[] args) {
        // FileReader arquivo;
        // String[] flags = new String[args.length - 1];
        // for (int i = 1; i < args.length; i++){
        //     switch (args[i]){
        //     case (args[i] == "-d"){
        //         FileReader arq1 = new FileReader("docentes.csv");
        //         i++;
        //         continue;
        //     }
        //     case (args[i] == "-v"){
        //         FileReader arq2 = new FileReader("veiculos.csv");
        //         i++;
        //         continue;
        //     }
        //     case (args[i] == "-p"){
        //         FileReader arq3 = new FileReader("publicacoes.csv");
        //         i++;
        //         continue;
        //     }
        //     case (args[i] == "-q"){
        //         FileReader arq4 = new FileReader("qualis.csv");
        //         i++;
        //         continue;
        //     }
        //     case (args[i] == "-r"){
        //         FileReader arq5 = new FileReader("regras.csv");
        //         i++;
        //         continue;
        //     }
        //     case (args[i] == "-a"){
        //         FileReader arq6 = new FileReader("docentes.csv");
        //         i++;
        //         continue;
        //     }
        //     }
        // }
        Scanner leitor = new Scanner(System.in);
        Sistema ppgi = new Sistema();
        try  {
            FileReader arq1 = new FileReader("docentes.csv");
            FileReader arq2 = new FileReader("veiculos.csv");
            FileReader arq3 = new FileReader("publicacoes.csv");
            BufferedReader fdocente = new BufferedReader(arq1);
            BufferedReader fveiculo = new BufferedReader(arq2);
            BufferedReader fpublicacoes = new BufferedReader(arq3);
            ppgi.carregaArquivoDocentes(fdocente);
            ppgi.carregaArquivoVeiculos(fveiculo);
            ppgi.carregaArquivoPublicacoes(fpublicacoes);
            // ppgi.imprimeDocentes();
            // ppgi.imprimeVeiculos();
            arq1.close();
            arq2.close();
            arq3.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
