import java.text.ParseException;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
public class Main {
    ArrayList<Docente> docentesCadastrados;
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        try  {
            FileReader arquivo = new FileReader("script-java/testes/01/in/docentes.csv");
            BufferedReader leituraDeArquivo = new BufferedReader(arquivo);
            String linha = leituraDeArquivo.readLine();
            linha = leituraDeArquivo.readLine();
            while(linha !=null){

                System.out.println(linha);
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
        System.out.println();
    }
}
