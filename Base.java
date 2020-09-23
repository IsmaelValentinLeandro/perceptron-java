package Perceptron;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Base {

    
    boolean statusDir;
    boolean statusArq;
    java.io.File diretorio;
    java.io.File arquivo;
    java.io.File arquivoTMP;
    FileWriter texto_para_escrever;
    PrintWriter gravarArq;
    BufferedWriter escrever, escrever2;
    BufferedReader ler, ler1, ler2;
    
    ArrayList<String> rotulo_neuronio = new ArrayList<>();
    ArrayList<String> amostra_neuronio = new ArrayList<>();
    ArrayList<String> neuronio = new ArrayList<>();
    ArrayList<Integer> qtd_amostras_neuronio = new ArrayList<>();
    ArrayList<String> pesos = new ArrayList<>();
    
  public Base(String dir, String arq) {    
  
      diretorio = new java.io.File(dir);
      statusDir = diretorio.mkdir();
      if (statusDir) { System.out.println("Diretorio criado com sucesso.");} else {System.out.println("Não foi possível criar o diretório ou o diretório já existe.");};
      
      arquivo = new java.io.File(diretorio, arq);
      try {
      statusArq = arquivo.createNewFile();
      } catch (IOException e) { e.printStackTrace();};
      if (statusArq) { System.out.println("Arquivo da base criado com sucesso");} else {System.out.println("Não foi possível criar o arquivo ou o arquivo já existe.");};
           
  }  
  
    public boolean Incluir(String path, String rotulo, String Texto)throws IOException {  
        
    boolean status_ponto=false;
    boolean status_rotulo=false;
    
    String linha;
    String newLine = System.getProperty("line.separator"); 
    
    boolean retorno = false;
    escrever = new BufferedWriter(new FileWriter("BaseTMP.txt"));
    ler = new BufferedReader(new FileReader(path));
   
    while ((linha = ler.readLine()) != null){
       
       if (linha.contains("Inicio Matriz"))
       {
          status_ponto = true;
       }
       
       if (linha.contains(rotulo))
       {
          status_rotulo = true;                   
          escrever.write(linha+newLine+Texto+newLine);
          retorno = true;  
          
       } else {
                 
       if (linha.contains("Fim Matriz") && status_rotulo==false && status_ponto==true)
       {
          status_rotulo = true;                   
          escrever.write(rotulo+newLine+Texto+newLine+"Fim Amostras"+newLine+linha+newLine);
          retorno = true;  
          
       } else {
          escrever.write(linha+newLine);
       }}
       
    }   
    
    if (retorno==false) {
       escrever.write("Inicio Matriz"+newLine+rotulo+newLine+Texto+newLine+"Fim Amostras"+newLine+"Fim Matriz");
       retorno = true;  
    }
    
    escrever.close();
    ler.close();
    
    new File(path).delete();
    new File("BaseTMP.txt").renameTo(new File(path));
    
    return retorno;   
    
}
    
    
    public int contaDados(String path) throws IOException {
    
        int cont_neuronio=1;
        int cont_amostras=1;
        String linha, rotulo="";        
        ler = new BufferedReader(new FileReader(path));
        rotulo_neuronio.clear();
        amostra_neuronio.clear();
        neuronio.clear();
        qtd_amostras_neuronio.clear();
        
        while ((linha = ler.readLine()) != null){ // Montando os Arrays rotulo_neuronio e amostra_neuronio         
           
           if ( !(linha.contains("1")) && !(linha.contains("-1")) && !(linha.contains("Matriz")) && !(linha.contains("Amostras")) ) 
           {
              rotulo = linha;
           }
           
           if (linha.contains("1") || linha.contains("-1"))
           {
              rotulo_neuronio.add(rotulo); 
              amostra_neuronio.add(linha);
           }             
       }
        
        // Quantidade de neurônios e amostras
        
        rotulo =  rotulo_neuronio.get(0);
        neuronio.add(rotulo);
        
        for (int i=1; i<rotulo_neuronio.size(); i++){
        
            if ( (rotulo_neuronio.get(i).equals(rotulo_neuronio.get(i-1)) ))
                cont_amostras++;
            else
            {
              cont_neuronio++;  
              qtd_amostras_neuronio.add(cont_amostras);
              cont_amostras=1;
              neuronio.add(rotulo_neuronio.get(i));
            }
            
            if (i==(rotulo_neuronio.size()-1)) {
               qtd_amostras_neuronio.add(cont_amostras);
            }
            
        }

        
        System.out.println("\n.....................Iniciando aprendizado.....................\nO Arquivo possui "+cont_neuronio+" neur�nios");
        
        for (int i=0; i<neuronio.size(); i++) {
            System.out.println("Neur�nio "+(i+1)+": ("+neuronio.get(i)+") - "+qtd_amostras_neuronio.get(i)+" Amostras");
        }
        
        ler.close();  
        
        return cont_neuronio;
        
    }
    
    
    public boolean log(String path, String rotulo, String Texto)throws IOException {  
        
    String newLine = System.getProperty("line.separator"); 
    String linha; 
    
    boolean retorno = false;
    
    escrever = new BufferedWriter(new FileWriter("LogTMP.txt"));        
    ler = new BufferedReader(new FileReader(path));
     
    while ((linha = ler.readLine()) != null){
       
       if (linha.contains("Fim"))
       {
          escrever.write(Texto+newLine+linha);
          retorno = true;  
          
       } else {         
             
              escrever.write(linha+newLine);
           }
       }       
    
    if (retorno==false) {
       escrever.write(Texto+newLine+"Fim");
       retorno = true;  
    }
       
    escrever.close();   
    ler.close();
    
    new File(path).delete();
    new File("LogTMP.txt").renameTo(new File(path));
    
    return retorno;   
    
}
    
    public int contaLog(String path_log) throws IOException {
    
        int cont_pesos=0;
        String linha;      
        ler = new BufferedReader(new FileReader(path_log));
        pesos.clear();
        
        while ((linha = ler.readLine()) != null){ // Montando os Arrays rotulo_neuronio e amostra_neuronio         
           
           if ( !(linha.contains("Fim")) ) 
           {
              pesos.add(linha);
              cont_pesos++;
           }       
       }
        return cont_pesos;        
    }

}

