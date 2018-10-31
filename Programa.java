import java.io.*;
import labirinto.*;
import fila.*;
import pilha.*;
import coordenada.*;

public class Programa
{
	public static void main (String[] args)
	{
		boolean parar = false;
		boolean houveErro;
		try
		{
			while (!parar)
			{
				houveErro = false;

				BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
				Labirinto lab;

				System.out.println("============================================");
				System.out.println("Resolvedor de Labirinto");
				System.out.println("============================================");
				System.out.println();
				System.out.println("Escreva o caminho para o arquivo de um labirinto: ");
				System.out.println();
				String caminhoParaOArquivo = teclado.readLine();
				System.out.println ();
				try
				{
					lab = new Labirinto (caminhoParaOArquivo);
					Labirinto labRes = (Labirinto)lab.labirintoResolvido().clone();
					if (labRes.getHaSolucao()==0)
					{
						houveErro = true;
						System.out.println ("Desculpe, mas nao ha solucao para este labirinto.");
					}
					else
					{
						System.out.println (labRes);
						System.out.println ();
						System.out.println ("Caminho percorrido pelo labirinto: ");
						System.out.println (labRes.getInverso());
						System.out.println ();
					}

					if (!houveErro)
					{
						System.out.println ("Deseja salvar a solucao do labirinto em um arquivo? (S/N)");
						char resp = Character.toUpperCase(teclado.readLine().charAt(0));
						System.out.println ();
						if (resp == 'S')
						{
							System.out.println ("Digite o caminho para o arquivo para salvar: ");
							String caminhoParaOArquivoParaSalvar = teclado.readLine();
							try
							{
								labRes.escreverLabirintoResolvidoNoArquivo (caminhoParaOArquivoParaSalvar);
								System.out.println ("Labirinto salvo com sucesso!");
							}
							catch (Exception e1) { System.out.println(e1); }
						}
					}
				}
				catch (Exception e2) { System.out.println(e2); }

				System.out.println ();
				System.out.println ("Deseja executar o programa novamente com outro labirinto? (S/N)");
				char resp = Character.toUpperCase(teclado.readLine().charAt(0));
				if (resp == 'N')
					parar=true;
			}
		}
		catch (Exception erro) {} // nao ocorrera

	}
}