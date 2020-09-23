package Perceptron;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Algoritmo {
	
	private static int matriz_linha = 9;
	private static int matriz_coluna = 7;
	private static int[] entrada = new int[matriz_linha*matriz_coluna];
	private static int qtd_neuronios;
	private static boolean processado = false;
    private static int[] letraTeste = new int[matriz_linha*matriz_coluna];
    
	private static String rotulo;
	private static String pontos_entrada;
	private static String pesos_do_neuronio;
    
	private static float alfa = 1;
	private static float aprendizado;

	private static String BASE = "Matriz.txt";
	private static  String LOG = "Matriz_log.txt";
	private static String DIRETORIO = "Amostras";
	private static String path_log = DIRETORIO+"\\"+LOG;
	private static String path = DIRETORIO+"\\"+BASE;
	private static Base Base = new Base(DIRETORIO, BASE);
	private static Base Log = new Base(DIRETORIO,LOG);
    
	private static Letra letra = new Letra();    
			
	public static void main(String[] args) {

		// ARQUIVO PARA TESTAR A REDE
		// 9 linhas por 7 colunas <<<<PADRAO 9x7>>>>
		String strTeste =   "..####."+
                                    ".#....."+
                                    ".#....."+
                                    "......."+
                                    ".#....."+
                                    ".#....."+
                                    "......."+
                                    ".#...#."+
                                    "..###..";
		
		
		aprendizado = (float) 0.2;
		
		ensinar();	           
	    aprender();	         
	    
	    letra.processaLetra(letraTeste, strTeste);
	    testar();  
	    
	}
	
	public static void ensinar()
	{
		int[] vetor = new int[matriz_linha*matriz_coluna];
		rotulo = "A";
		letra.carregaLetra("a1", vetor);
		ensinar(vetor);
		letra.carregaLetra("a2", vetor);
		ensinar(vetor);
		letra.carregaLetra("a3", vetor);
		ensinar(vetor);
		
		rotulo = "B";
		letra.carregaLetra("b1", vetor);
		ensinar(vetor);
		letra.carregaLetra("b2", vetor);
		ensinar(vetor);
		letra.carregaLetra("b3", vetor);
		ensinar(vetor);

		rotulo = "C";
		letra.carregaLetra("c1", vetor);
		ensinar(vetor);
		letra.carregaLetra("c2", vetor);
		ensinar(vetor);
		letra.carregaLetra("c3", vetor);
		ensinar(vetor);

		rotulo = "D";
		letra.carregaLetra("d1", vetor);
		ensinar(vetor);
		letra.carregaLetra("d2", vetor);
		ensinar(vetor);
		letra.carregaLetra("d3", vetor);
		ensinar(vetor);

		rotulo = "E";
		letra.carregaLetra("e1", vetor);
		ensinar(vetor);
		letra.carregaLetra("e2", vetor);
		ensinar(vetor);
		letra.carregaLetra("e3", vetor);
		ensinar(vetor);

		rotulo = "J";
		letra.carregaLetra("j1", vetor);
		ensinar(vetor);
		letra.carregaLetra("j2", vetor);
		ensinar(vetor);
		letra.carregaLetra("j3", vetor);
		ensinar(vetor);

		rotulo = "K";
		letra.carregaLetra("k1", vetor);
		ensinar(vetor);
		letra.carregaLetra("k2", vetor);
		ensinar(vetor);
		letra.carregaLetra("k3", vetor);
		ensinar(vetor);

	}
	
	public static void ensinar(int[] vetor)
	{
		boolean status_rotulo = false;         
		pontos_entrada = "";		   
		for (int i = 0; i < (matriz_linha*matriz_coluna); i++) {  
			entrada[i] = vetor[i];
			pontos_entrada = pontos_entrada + String.valueOf(entrada[i]+".");             
		}  
		try 
		{
			status_rotulo = Base.Incluir(path, rotulo, pontos_entrada);
		} catch (IOException ex) {
	    	System.out.println(ex.getMessage());
		}
		if (status_rotulo) 
		{
			System.out.println("Amostra inserida com sucesso.");
		}
	}
	
	public static void aprender()
	{
			       
		try 
   		{
         	qtd_neuronios = Base.contaDados(path);                   
        } catch (IOException ex) {
	    	System.out.println(ex.getMessage());
        }

       	int entradas[][] = new int[Base.rotulo_neuronio.size()][matriz_linha*matriz_coluna];
       	float pesos[][] = new float[qtd_neuronios][matriz_linha*matriz_coluna];
   		float bias[] = new float[qtd_neuronios];
   
   		int saida = 0, resposta;
   
   		for (int i=0; i<Base.rotulo_neuronio.size(); i++)
   		{
	   		String amostras[] = Base.amostra_neuronio.get(i).split("\\.");
      
      		for (int j=0; j<amostras.length; j++){
    	  		entradas[i][j] = Integer.parseInt(amostras[j]);
      		}
   		}
   
   		String rotulo;
   		int continua = 1;
   		int ciclos = 0;
   		float y_in=0;            
   
   		for (int n=0; n<qtd_neuronios; n++) // Step 1
   		{
	   		rotulo = Base.neuronio.get(n);              
	    	System.out.println("...Treinamento do neurônio "+ (n) + ": ("+ rotulo+") ...ok");
	    	continua = 1;
	  		ciclos = 0;
	  
	  		while (continua==1)
	  		{ // Step 2
	 
		  		continua=0;
		 		ciclos++;                 
		 
		 		for(int a=0; a<Base.rotulo_neuronio.size(); a++) // Step 3
			    {
			    	y_in=0; 
			    
			      	for (int j=0; j<(matriz_linha*matriz_coluna); j++) // Step 4 Permuta��o
			      	{                          
			    	  	y_in = y_in + pesos[n][j] * entradas[a][j];                              
			      	}                              
			   
			      	y_in = y_in+bias[n];                        
			   
			   		if ( y_in >= aprendizado )
			   			saida = 1;                       
			   		if ( y_in > (-aprendizado) && y_in < aprendizado ) 
			   			saida = 0;                              
			   		if ( y_in <= (-aprendizado) ) 
			       		saida = -1;                       
			                           
			   		if ( rotulo.equals(Base.rotulo_neuronio.get(a)) ) 
				   		resposta = 1;
			   		else
				   		resposta = -1;
		    
		   			if (saida!=resposta)  // Step 5
		   			{
		              	for (int j=0; j<(matriz_linha*matriz_coluna); j++)
		              	{
		            	  	pesos[n][j] = pesos[n][j] + alfa * entradas[a][j] * resposta;                             
		              	}
						bias[n] = bias[n] + alfa * resposta;                          
						continua = 1;                          
		       		}
	        	}              
	  		}   
	  		//Step 6                  
	   	}  
   
	   	System.out.println("Epocas para aprendizado"+ciclos);
	   	System.out.println("................Aprendizado Concluido.................");
	   
	  	pesos_do_neuronio = "";
	   
	   	for (int i=0; i<pesos.length; i++)
	   	{  
	       	pesos_do_neuronio = "";
	       	for (int j=0; j<(matriz_coluna*matriz_linha); j++)
	   		{
		   		pesos_do_neuronio = pesos_do_neuronio + " " + pesos[i][j];
	   		}
	   		System.out.println("Pesos do Neuronio: "+(i+1)+": " +pesos_do_neuronio);
	   	}    
	   
	   	boolean status_log;
	   	pontos_entrada = "";
	   
	   	try 
	   	{
	        Writer log = new FileWriter(path_log);
	        log.write("");
	        log.flush();
	        log.close();                       
	  
	   	} 
	   	catch (IOException ex) 
	   	{
	    	System.out.println(ex.getMessage());
	    }
	       
		for (int i=0; i<qtd_neuronios; i++)
		{
			pontos_entrada = "";
			for (int j=0; j<(matriz_linha*matriz_coluna); j++)
			{
				pontos_entrada = pontos_entrada + ";" +pesos[i][j];
			}
			pontos_entrada = bias[i]+pontos_entrada;
			
			try {
                status_log = gera_log(path_log, "Pesos Neuronio "+(i+1), pontos_entrada);
            } catch (IOException ex) {
                Logger.getLogger(Algoritmo.class.getName()).log(Level.SEVERE, null, ex);
            }
		} 
		processado = true;
	}
	
	public static void testar(){
   
		if (processado==false) 
		{
			aprender();
		}
        
        System.out.println("............Inicio do teste...........");
		String status = "";
		pontos_entrada=""; 

		for (int i = 0; i < (matriz_linha*matriz_coluna); i++) 
		{  
			entrada[i] = letraTeste[i];
			pontos_entrada = pontos_entrada + String.valueOf(entrada[i]+".");   
		}  
		    
		try 
		{
            qtd_neuronios = Base.contaLog(path_log);
        } 
		catch (IOException ex) 
		{
            Logger.getLogger(Algoritmo.class.getName()).log(Level.SEVERE, null, ex);
        }
  
		float pesos[][] = new float[qtd_neuronios][matriz_linha*matriz_coluna];
		float bias[] = new float[qtd_neuronios];
  
		for (int i=0; i<Base.pesos.size(); i++)
		{
			String linha_pesos[] = Base.pesos.get(i).split(";");
			bias[i] = Float.parseFloat(linha_pesos[0]);
			for (int j=1; j<linha_pesos.length; j++)
			{
				pesos[i][j-1] = Float.parseFloat(linha_pesos[j]);
			}
		}
  
		float valores[] = new float[qtd_neuronios];
		String rotulos[] = new String[Base.neuronio.size()];
  
		for (int n=0; n<qtd_neuronios; n++)
		{
			float saida2=0;
			for (int i=0; i<matriz_linha*matriz_coluna; i++)
			{
				saida2 = saida2 + (pesos[n][i] * entrada[i]);              
			}
			saida2 = saida2 + bias[n];
			if (saida2>aprendizado) 
				status = " [Padrao reconhecido]";   
			if (saida2<-aprendizado) 
				status = " [Fora do padrao]";    
			if (saida2>=-aprendizado && saida2 <=aprendizado) 
				status = " [Incerteza]";   
			rotulos[n] = Base.neuronio.get(n);
			valores[n] = saida2;
			
			System.out.println(Base.neuronio.get(n)+": "+saida2+status);     
		}                  
  
  		int index_maior=-1;
		float aux = -1;
  		
		for (int i=0; i<valores.length; i++)
		{   
			if (aux < valores[i])
			{
				aux = valores[i];
				index_maior=i;  
			}
		}
		
		if (index_maior == -1)
		{
			aux = 1000;			
			for (int i=0; i<valores.length; i++)
			{
				if (aux > Math.abs(valores[i]))
				{
					aux = Math.abs(valores[i]);
					index_maior=i;  
				}
			}
		}

		if (index_maior==-1) 
		{
			System.out.println("Nao foi possivel identificar nenhum padrao.");
		} 
		else 
		{                  
			System.out.println("Classe com padrao mais proximo e a classe " + rotulos[index_maior]);
		}
		System.out.println("............Fim do teste.............\n");
		
		new File(path).delete();
		new File(path_log).delete();
	}   
	

	   public static boolean gera_log(String path_log, String rotulo, String pontos_entrada) throws IOException{
	           
	       boolean status_log; 
	          
	          status_log = Log.log(path_log, rotulo, pontos_entrada);
	       
	       return status_log;
	   }


}
