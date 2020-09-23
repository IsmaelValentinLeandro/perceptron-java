package Perceptron;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Letra {

	private int qtdLetras = 3;
	
	public void processaLetra(int[] vetor, String letra)
	{
		for (int i=0; i< letra.length(); i++)
		{
			vetor[i] = (letra.charAt(i) == '#') ? 1 : -1;
		}
	}
	
	public void carregaLetra(String letra,  int[] vetor)
	{
		File file = new File("./src/Perceptron/letras/"+letra.toLowerCase()+".txt");
		if (file.exists())
		{
			//System.out.println("Importando Letra: "+letra+l+".txt");
			try
			{
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String linha = br.readLine();
				int count = 0;
				while (linha!=null)
				{
					for (int i=0; i <  7; i++)
						{
						vetor[count] = (( linha.charAt(i) == '#' )? 1: -1);
						count++;
					}
					linha = br.readLine();
				}
			}
			catch(FileNotFoundException fnfe)
			{
				System.out.println("[ERRO 1000]-"+fnfe.getMessage());				
			}
			catch(IOException ioe)
			{
				System.out.println("[ERRO 1001]-"+ioe.getMessage());				
			}
		}
		else
		{
			System.out.println("Arquivo de letra "+letra+" não encontrado.");
		}
	}
	
	public void mostraArray(int[][] entrada)
	{
		String texto= "\r\n";
		for (int i=0; i < 9; i++)	
		{
			for (int j=0; j < 7; j++)	
			{
				texto += entrada[j][i];
			}
			texto += "\r\n"; 
		}
		System.out.println(texto);
	}
	
}
