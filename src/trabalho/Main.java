package trabalho;

import java.text.ParseException;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
public class Main {
    // public static ArrayList<Docente> docentesCadastrados = new ArrayList<Docente>();
    // public static ArrayList<Regra> regras = new ArrayList<Regra>();
    public static void main(String[] args) {
        String[] flags = new String[args.length - 1];
        /* for (int i = 1; i < args.length; i++){
            if (args[i] == "-d"){
                //
                i++;
                continue;
            }
            if (args[i] == "-v"){
                //
                i++;
                continue;
            }
            if (args[i] == "-p"){
                //
                i++;
            }
            if (args[i] == "-q"){
                //
                i++;
            }
            if (args[i] == "-r"){
                //
                i++;
            }
            if (args[i] == "-a"){
                //
                i++;
            }
        } */
        Scanner leitor = new Scanner(System.in);
        Sistema ppgi = new Sistema();
        try  {
            FileReader arquivo = new FileReader("docentes.csv");
            BufferedReader leituraDeArquivo = new BufferedReader(arquivo);
            String linha = leituraDeArquivo.readLine();
            linha = leituraDeArquivo.readLine();
            while(linha !=null){
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
                ppgi.insereDocente(docente);
                linha = leituraDeArquivo.readLine();
            }
            ppgi.imprimeDocentes();
            arquivo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // System.out.println();
    }

    // Métodos Gerais da Main

    /*Imprime as informações de cada docente cadastrado*/
    // private static void imprimeDocentes(){
    //     for (Docente d: docentesCadastrados){
    //         d.imprimeDocente();
    //     }
    // }

}
