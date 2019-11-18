import java.text.ParseException;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
public class Main {
    public static ArrayList<Docente> docentesCadastrados = new ArrayList<Docente>();
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        try  {
            FileReader arquivo = new FileReader("../script-java/testes/01/in/docentes.csv");
            BufferedReader leituraDeArquivo = new BufferedReader(arquivo);
            String linha = leituraDeArquivo.readLine();
            linha = leituraDeArquivo.readLine();
            while(linha !=null){
                String[] campos = linha.split(";");
                long codigo = Long.parseLong(campos[0]);
                String nome = campos[1];
                Date nascimento = new SimpleDateFormat("dd/MM/yyyy").parse(campos[2]);
                // Date nascimento = new SimpleDateFormat("dd/MM/yyyy").parse(campos[2]);
                Date ingresso = new SimpleDateFormat("dd/MM/yyyy").parse(campos[3]);
                // String ing2 = new Date().format(ingresso);
                // Date ingresso = new SimpleDateFormat("dd/MM/yyyy").parse(campos[3]);
                boolean coordenador = false;
                // System.out.println(linha);
                if (campos.length == 5) {
                    if (campos[4] == "X") {
                        coordenador = true;
                    }
                }
                Docente docente = new Docente(codigo, nome, nascimento, ingresso, coordenador);
                // docente.imprimeDocente();
                docentesCadastrados.add(docente);
                linha = leituraDeArquivo.readLine();
            }
            imprimeDocentes();
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

    private static void imprimeDocentes(){
        for (Docente d: docentesCadastrados){
            d.imprimeDocente();
        } 
            
    }
}
