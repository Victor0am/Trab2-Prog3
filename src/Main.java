
import trabalho.*;
import java.io.*;
import java.text.ParseException;
public class Main {
    public static void main(String[] args) {
        int anoDaRegra  = 0;
        FileReader arq1 = null;
        FileReader arq2 = null;
        FileReader arq3 = null;
        FileReader arq4 = null;
        FileReader arq5 = null;
        boolean read = false;
        boolean write = false;
        try{
            flow: 
            for (int i = 0; i < args.length; i++){
                switch (args[i]){
                case "--write-only":
                    write = true;
                    break flow;
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
                case "--read-only":
                    read = true;
                    break;
                default:
                    break;
                }
            }
        }catch(FileNotFoundException e){
            System.err.println("Erro de I/O");
            e.printStackTrace();
        }
        try  {
            if (!write){
                Sistema ppgi = new Sistema(anoDaRegra);
                BufferedReader fdocente = new BufferedReader(arq1);
                BufferedReader fveiculo = new BufferedReader(arq2);
                BufferedReader fpublicacoes = new BufferedReader(arq3);
                BufferedReader fregras = new BufferedReader(arq5);
                BufferedReader fqualis = new BufferedReader(arq4);
                ppgi.carregaArquivoDocentes(fdocente);
                ppgi.carregaArquivoVeiculos(fveiculo);
                ppgi.carregaArquivoRegras(fregras);
                ppgi.carregaArquivoQualis(fqualis);
                ppgi.carregaArquivoPublicacoes(fpublicacoes);
                arq1.close();
                arq2.close();
                arq3.close();
                arq4.close();
                arq5.close();
                if (!read){
                    ppgi.listaPublicacoes();
                    ppgi.calculaResultados();
                    ppgi.calculaEstatisticas();
                }else{
                    FileOutputStream file = new FileOutputStream("recredenciamento.dat");
                    ObjectOutputStream out = new ObjectOutputStream(file);
                    out.writeObject(ppgi);
                    out.close();
                }
            }else{
                FileInputStream file = new FileInputStream("recredenciamento.dat");
                ObjectInputStream in = new ObjectInputStream(file);
                Sistema ppgi = (Sistema)in.readObject();
                ppgi.listaPublicacoes();
                ppgi.calculaResultados();
                ppgi.calculaEstatisticas();
                in.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro de I/O");
            // e.printStackTrace();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            // System.err.println("a");
            // e.printStackTrace();
        }
        catch (NumberFormatException e) {
            System.err.println("Erro de formatação");
             e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
            // System.err.println("b");
            // e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
