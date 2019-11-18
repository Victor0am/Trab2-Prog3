import java.text.ParseException;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
public class Main {
    // public static ArrayList<Docente> dcentesCadastrados;
    public static void main(String[] args) {
        ArrayList<Docente> docentesCadastrados = new ArrayList<Docente>();
        Scanner leitor = new Scanner(System.in);
        try  {
            FileReader arquivo = new FileReader("../script-java/testes/01/in/docentes.csv");
            BufferedReader leituraDeArquivo = new BufferedReader(arquivo);
            String linha = leituraDeArquivo.readLine();
            linha = leituraDeArquivo.readLine();
            while(linha !=null){

                // System.out.println(linha);
                String[] campos = linha.split(";");
                long codigo = Long.parseLong(campos[0]);
                String nome = campos[1];
                Date nascimento = new SimpleDateFormat("dd/MM/yyyy").parse(campos[2]);
                Date ingresso = new SimpleDateFormat("dd/MM/yyyy").parse(campos[3]);
                boolean coordenador = false;
                if (campos.length == 5) {
                    if (campos[4] == "X") {
                        coordenador = true;
                    }
                }
                Docente docente = new Docente(codigo, nome, nascimento, ingresso, coordenador);
                docente.imprimeDocente();
                docentesCadastrados.add(docente);
                linha = leituraDeArquivo.readLine();
            }
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

    // private static void imprimeDocentes(){
    //     for (Docente d: docentesCadastrados){
    //         System.out.printf("Codigo:\t%ld\n", d.getCodigo());
    //         System.out.printf("Nome:\t%s\n", d.getNome());
    //         System.out.println("Data de Nascimento:\t" +d.getDataNascimento().toString());
    //         System.out.println("Data de Ingresso:\t" + d.getDataIngresso().toString());
    //         System.out.println("Coordenador: " + 
    //                             ((d.isCoordenador() == true)? "Sim" : "Não" ));
    //     } 
            
    // }
}
