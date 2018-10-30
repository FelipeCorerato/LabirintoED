package labirinto;

import java.io.*;
import coordenada.*;
import pilha.*;
import fila.*;

/**
 * Classe utilizada para registrar um labirinto e verificar sua solução.
 * @author Eduardo Porto
 * @author Felipe Corerato
 * @author Joao Henri
 */
public class Labirinto implements Cloneable
{
	/**
	* Número de linhas que o labirinto possui
	*/
	protected int numeroLinhas;
	/**
	* Número de colunas que o labirinto possui
	*/
	protected int numeroColunas;
	/**
	* Matriz que registra o labirinto
	*/
	protected char[][] labirinto;
	/**
	* Objeto da classe Coordenada que representa o local da entrada na matriz {@link Labirinto#labirinto}
	*/
	protected Coordenada entrada;
	/**
	* Objeto da classe Coordenada que representa o local da saída na matriz {@link Labirinto#labirinto}
	*/
	protected Coordenada saida;
	/**
	* Indica se a instância de Labirinto já foi resolvido (passou pelo método {@link Labirinto#labirintoResolvido()})
	*/
	protected boolean resolvido;
	/**
	* Indica de qual arquivo o labirinto foi carregado
	*/
	protected String localDoArquivo;
	/**
	* Indica se o labirinto registrado possui solução<br>
	* Possiveis valores: <br>
	* 	<b>-1</b> - caso ainda não tenha sido calculada a resolução do labirinto<br>
	* 	<b>0</b> - caso não haja solução para o labirinto<br>
	* 	<b>1</b> - caso haja solução para o labirinto<br>
	*/
	protected int haSolucao;
	/**
	* Caso {@link Labirinto#resolvido} seja verdadeiro, este atributo guardará o caminho que o método {@link Labirinto#labirintoResolvido()}
	* utilizou para resolver o labirinto
	*/
	protected Pilha<Coordenada> inverso;

	/**
	* Método construtor - armazena na memória um labirinto encontrado em um arquivo de texto através do caminho passado como parâmetro.
	* @param nomeArquivo caminho no disco até um arquivo onde está armazenado um labirinto
	* @throws Exception se: <br>
	* <ul>
	* 	<li>o labirinto for pequeno demais (se o labirinto for um quadrado menor de 2x2 ou possuir linha ou coluna menor ou igual a 0)</li>
	* 	<li>houver algum caracter não esperado no labirinto</li>
	* 	<li>não for encontrado o arquivo com o labirinto passado como parâmetro</li>
	*   <li>o número de linhas ou colunas no início do arquivo estiver incorreto</li>
	*   <li>houve falha na leitura do número de linhas ou colunas no início do arquivo</li>
	*   <li>não for possível ler o arquivo</li>
	*   <li>a entrada ou a saída do labirinto estiverem em posições inválidas - só são aceitos nas linhas e colunas dos cantos do labirinto</li>
	* </ul>
	*/
	public Labirinto (String nomeArquivo) throws Exception
	{
		try
		{
			this.haSolucao = -1;  // Significa que nao foi checado ainda se ha solucao
			this.resolvido = false;

			BufferedReader arquivo = new BufferedReader(new FileReader(nomeArquivo));
			this.localDoArquivo = nomeArquivo;

			int nLinhas  = Integer.parseInt(arquivo.readLine());
			int nColunas = Integer.parseInt(arquivo.readLine());

			if ((nLinhas<2 && nColunas<2) || (nLinhas<=0 || nColunas<=0))
				throw new Exception ("ERRO: O labirinto possui tamanho inválido");

			numeroLinhas=nLinhas;
			numeroColunas=nColunas;

			labirinto = new char[numeroLinhas][numeroColunas];

			String aux = "";
			String charsPossiveis = "# ES";   // Esta String contem todos os valores que sao validos para o labirinto
			for (int i1=0; i1<=numeroLinhas-1; i1++)
			{
				aux = arquivo.readLine();
				for (int i2=0; i2<=numeroColunas-1; i2++)
				{
					if (charsPossiveis.indexOf(aux.charAt(i2))==-1) // Checa se o valor lido esta entre os valores esperadoss
						throw new Exception("ERRO: Há um caracter invalido no labirinto");
					if (aux.charAt(i2)=='E')
						this.entrada = new Coordenada(i1, i2);
					if (aux.charAt(i2)=='S')
						this.saida   = new Coordenada(i1, i2);

					this.labirinto[i1][i2]=aux.charAt(i2);
				}
			}

			this.inverso = new Pilha<Coordenada> (this.numeroLinhas*this.numeroColunas);

			arquivo.close();

		}
		catch (FileNotFoundException erro1)
		{
			throw new Exception ("ERRO: Arquivo com o labirinto nao encontrado");
        }
        catch (ArrayIndexOutOfBoundsException erro2)
		{
			throw new Exception ("ERRO: Erro de leitura do arquivo - numero de linhas incorreto");

        }
        catch (StringIndexOutOfBoundsException erro2)
		{
			throw new Exception ("ERRO: Erro de leitura do arquivo - numero de colunas incorreto");

        }
        catch (NumberFormatException erro3)
        {
			throw new Exception ("ERRO: Nao foi possivel ler o numero de linhas ou de colunas");
		}
		catch (IOException erro4)
		{
			throw new Exception ("ERRO: Falha na leitura de arquivo");
		}

		if (!entradaESaidaSaoValidas())
			throw new Exception ("ERRO: O labirinto possui entrada e/ou saida invalida");

	}

	/**
	* Verifica se a entrada e a saída do labirinto estão em linhas e colunas do canto do labirinto
	* @return verdadeiro caso a entrada E a saída estão em posições válidas e falso caso o contrário
	*/
	protected boolean entradaESaidaSaoValidas()
	{
		Coordenada[] vetEntrada = new Coordenada[1]; // Estes vetores possuem apenas uma posição, caso o programa tente
		Coordenada[] vetSaida   = new Coordenada[1]; // guardar mais de uma entrada ou saida, havera throw ArrayIndexOutOfBoundsException
		try   // Percorre o vetor, checa se foram encontradas exatamente uma entrada
			  // e uma saida e onde foram encontradas
		{
			int indexDoVetorEntrada=0;
			int indexDoVetorSaida=0;
			for (int i1=0; i1<=this.numeroLinhas-1; i1++)
				for (int i2=0; i2<=this.numeroColunas-1; i2++)
				{
					if (this.labirinto[i1][i2] == 'E')
					{
						vetEntrada[indexDoVetorEntrada] = new Coordenada(i1,i2);
						indexDoVetorEntrada++;
					}
					if (this.labirinto[i1][i2] == 'S')
					{
						vetSaida[indexDoVetorSaida]     = new Coordenada(i1,i2);
						indexDoVetorSaida++;
					}
				}
		}
		catch (ArrayIndexOutOfBoundsException erro)
		{
			return false;
		}
		catch (Exception erro) {} // Sei que nao havera erro na construcao de Coordenada, portanto, irei ignorar

		if (vetEntrada[0]==null || vetSaida[0]==null)
			return false;

		// Caso o programa chegue ate aqui, ha exatamente uma entrada e uma saida.
		// Agora, o programa deve verificar se a entrada e a saida estao em posicoes validas

		Coordenada entrada = (Coordenada)vetEntrada[0].clone();
		Coordenada saida   = (Coordenada)vetSaida[0].clone();
		if ( (((entrada.getX()!=0) && (entrada.getX()!=this.numeroLinhas-1))   &&
			 ((entrada.getY()!=0)  && (entrada.getY()!=this.numeroColunas-1))) ||
			 (((saida.getX()!=0)   && (saida.getX()!=this.numeroLinhas-1))     &&
			 ((saida.getY()!=0)    && (saida.getY()!=this.numeroColunas-1)))      )
				return false;   // Vai retornar falso caso a saida e a entrada nao estejam nos cantos do labirinto

		return true;  // A entrada e a saida estao em posicoes validas
	}

	/**
	* Método get do atributo {@link Labirinto#numeroLinhas}
	* @return o valor de {@link Labirinto#numeroLinhas}
	*/
	public int getNumeroLinhas() { return this.numeroLinhas; }

	/**
	* Método get do atributo {@link Labirinto#numeroColunas}
	* @return o valor de {@link Labirinto#numeroColunas}
	*/
	public int getNumeroColunas() { return this.numeroColunas; }

	/**
	* Método get do atributo {@link Labirinto#haSolucao}
	* @return o valor de {@link Labirinto#haSolucao}
	*/
	public int getHaSolucao() { return this.haSolucao; }

	/**
	* Método get do atributo {@link Labirinto#resolvido}
	* @return o valor de {@link Labirinto#resolvido}
	*/
	public boolean getResolvido() { return this.resolvido; }

	/**
	* Método get do atributo {@link Labirinto#inverso}
	* @return o valor de {@link Labirinto#inverso}
	*/
	public Pilha<Coordenada> getInverso() { return this.inverso; } // Retornara uma pilha vazia caso o labirinto nao esteja resolvido

	/**
	* Método get do atributo {@link Labirinto#entrada}
	* @return o valor de {@link Labirinto#entrada}
	*/
	public Coordenada getEntrada() { return this.entrada; }

	/**
	* Método get do atributo {@link Labirinto#saida}
	* @return o valor de {@link Labirinto#saida}
	*/
	public Coordenada getSaida() { return this.saida; }

	/**
	* Método get do atributo {@link Labirinto#labirinto}, que representa a matriz na qual é guardada o labirinto
	* @return o valor de {@link Labirinto#labirinto}
	*/
	public char[][] getLabirinto() { return this.labirinto; }

	/**
	* Método get do caracter guardado em uma determinada posição do labirinto, passada como parâmetro
	* @param coord Coordenada que representa a posição do labirinto a ser revistada
	* @return o valor, em char, do que está registrado naquela posição
	* @throws Exception caso a coordenada passada como parâmetro seja ou nula ou maior do que o labirinto registrado
	*/
	public char getItemNoLabirinto(Coordenada coord) throws Exception
	{
		if (coord==null)
			throw new Exception ("ERRO: Coordenada invalida para verificar um item no labirinto");
		if (coord.getX()>this.numeroLinhas-1)
			throw new Exception ("ERRO: Coordenada invalida para verificar um item no labirinto - posicao X invalida");
		if (coord.getY()>this.numeroColunas-1)
			throw new Exception ("ERRO: Coordenada invalida para verificar um item no labirinto - posicao Y invalida");

		return this.labirinto[coord.getX()][coord.getY()];
	}

	/**
	* Resolve o labirinto guardado com o construtor
	* @return uma instância de labirinto com sua solução registrada através de asteriscos.
	* Os valores de {@link Labirinto#inverso} e {@link Labirinto#haSolucao} serão válidos na instância retornada
	*/
	public Labirinto labirintoResolvido()
	{
		if (this.resolvido)
			return this;

		try
		{
			Coordenada atual                       = (Coordenada)this.entrada.clone();
			Pilha<Coordenada> caminho              = new Pilha<Coordenada> (this.numeroLinhas*this.numeroColunas);
			Pilha<Fila<Coordenada>> possibilidades = new Pilha<Fila<Coordenada>> (this.numeroLinhas*this.numeroColunas);
			Labirinto labirintoResolvido           = (Labirinto)this.clone();

			Fila<Coordenada> fila;
			Coordenada proximaCoord;

			boolean fim = false;
			int haCaminho=-1;

			for (;;)
			{
				fila = labirintoResolvido.posicoesAdjacentesValidasA(atual);

				if (fila.vazia())
				{
					for (;;)
					{
						if (!possibilidades.vazia())
						{
							atual = (Coordenada)caminho.getElemento().clone();
							caminho.desempilhe();
							labirintoResolvido.labirinto[atual.getX()][atual.getY()] = ' ';
							fila = (Fila)possibilidades.getElemento().clone();
							possibilidades.desempilhe();
							if (!fila.vazia())
								break;
						}
						else
						{
							haCaminho=0;
							break;
						}
					}
				}

				if (haCaminho==0)
					break;

				atual = (Coordenada)fila.getElemento().clone();
				fila.desenfileire();

				if (labirintoResolvido.labirinto[atual.getX()][atual.getY()] == 'S')
				{
					haCaminho = 1;
					break;
				}
				else
					labirintoResolvido.labirinto[atual.getX()][atual.getY()] = '*';

				caminho.empilhe(atual);
				possibilidades.empilhe(fila);
			}

			Pilha<Coordenada> pilhaAux = new Pilha<Coordenada> (this.numeroLinhas*this.numeroColunas);
			for (int i=0; i<=caminho.getTopo(); i++)
			{
				pilhaAux.empilhe(caminho.getElemento());
				caminho.desempilhe();
			}

			labirintoResolvido.inverso=(Pilha)pilhaAux.clone();
			labirintoResolvido.resolvido=true;
			labirintoResolvido.haSolucao=haCaminho;
			return labirintoResolvido;

		}
		catch (Exception erro) {} // Sei que nao havera nenhum erro

		return null; // O programa nao chegara ate esta parte, apenas existe para o compilador do Java parar de reclamar
	}

	/**
	* Verifica quais sãos os caminhos pelos quais a solução do labirinto poderá seguir a partir da coordenada passada como parâmetro
	* @param posicao Representa a posicao atual da solução do labirinto
	* @return uma fila de coordenada que representa todos os caminhos pelos quais o programa poderá seguir
	* @throws Exception caso a coordenada passada como parâmetro seja nula
	*/
	protected Fila<Coordenada> posicoesAdjacentesValidasA(Coordenada posicao) throws Exception
	{
		if (posicao==null)
			throw new Exception ("ERRO: Coordenada para verificar posicoes adjacentes invalida");

		try
		{
			Coordenada coord;
			String caminhosAceitos = " S";
			Fila<Coordenada> ret = new Fila<Coordenada>(3);
			try   // Caso a nova coordenada seja invalida, o programa ira checar a proxima possibilidade de movimento
			{
				coord = new Coordenada (posicao.getX(), posicao.getY()-1);
				if (caminhosAceitos.indexOf(this.labirinto[coord.getX()][coord.getY()])!=-1)
					ret.enfileire(coord);
			}
			catch (Exception erro){}

			try
			{
				coord = new Coordenada (posicao.getX()-1, posicao.getY());
				if (caminhosAceitos.indexOf(this.labirinto[coord.getX()][coord.getY()])!=-1)
					ret.enfileire(coord);
			}
			catch (Exception erro){}

			try
			{
				coord = new Coordenada (posicao.getX(), posicao.getY()+1);
				if (caminhosAceitos.indexOf(this.labirinto[coord.getX()][coord.getY()])!=-1)
					ret.enfileire(coord);
			}
			catch (Exception erro){}

			try
			{
				coord = new Coordenada (posicao.getX()+1, posicao.getY());
				if (caminhosAceitos.indexOf(this.labirinto[coord.getX()][coord.getY()])!=-1)
					ret.enfileire(coord);
			}
			catch (Exception erro){}

			return ret;
		}
		catch (Exception erro) {}

		return null; // não ira acontecer, apenas existe para o compilador do Java parar de reclamar

	}

	/**
	* Registra em um arquivo de texto de qualquer extensão a resolução do labirinto
	* @param nomeArquivo o caminho para o arquivo que será salvo
	* @throws Exception caso o caminho passado como parâmetro seja inalcançável
	*/
	public void escreverLabirintoResolvidoNoArquivo (String nomeArquivo) throws Exception
	{
		try
		{
			PrintWriter escritor = new PrintWriter(nomeArquivo, "UTF-8");
			escritor.println(this.labirintoResolvido());
			escritor.close();
		}
		catch (FileNotFoundException erro1)
		{
			throw new Exception ("ERRO: Nao foi possivel alcancar o arquivo para se salvar o labirinto resolvido");
        }
	}

	/**
	* Transforma o labirinto em uma string
	* @return uma String que representa o labirinto registrado, seja ele resolvido ou não
	*/
	public String toString()
	{
		String ret="";

		for (int i1=0; i1<=this.numeroLinhas-1; i1++)
		{
			for (int i2=0; i2<=this.numeroColunas-1; i2++)
				ret+=labirinto[i1][i2];

			ret+=(i1!=this.numeroLinhas-1?System.lineSeparator():"");
		}

		return ret;
	}

	/**
	* Verifica se o objeto que chamou o método é congruente ao objeto passado como parâmetro
	* @param obj Objeto para ser comparado com o objeto que chamou o método equals()
	* @return um valor verdadeiro ou falso caso os objetos sejam congruentes ou não
	*/
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof Labirinto))
			return false;

		Labirinto lab = (Labirinto)obj;

		if (this.numeroLinhas != lab.numeroLinhas)
			return false;
		if (this.numeroColunas != lab.numeroColunas)
			return false;
		if (!this.entrada.equals(lab.entrada))
			return false;
		if (!this.saida.equals(lab.saida))
			return false;
		if (this.resolvido != lab.resolvido)
			return false;
		if (!this.localDoArquivo.equals(lab.localDoArquivo))
			return false;
		if (this.haSolucao != lab.haSolucao)
			return false;
		if (!this.inverso.equals(lab.inverso))
			return false;
		for (int i = 0; i <= this.numeroLinhas-1; i++)
			for (int j = 0; j <= this.numeroColunas-1; j++)
				if (this.labirinto[i][j] != lab.labirinto[i][j])
					return false;

		return true;
    }

    /**
	* Calcula o código hash do labirinto guardado
	* @return o código hash do labirinto
    */
    public int hashCode()
    {
        int ret = 2;

        ret = ret * 3 + new Integer(this.numeroLinhas).hashCode();
        ret = ret * 3 + new Integer(this.numeroColunas).hashCode();
        ret = ret * 3 + this.entrada.hashCode();
        ret = ret * 3 + this.saida.hashCode();
		ret = ret * 3 + this.localDoArquivo.hashCode();
		ret = ret * 3 + new Boolean(this.resolvido).hashCode();
		ret = ret * 3 + this.inverso.hashCode();

        for (int i = 0; i <= this.numeroLinhas-1; i++)
            for (int j = 0; j <= this.numeroColunas-1; j++)
                ret = ret * 3 + new Character(this.labirinto[i][j]).hashCode();

        return ret;
    }

    /**
	* Construtor de cópias - cria um objeto igual ao passado como parâmetro
	* @param modelo um objeto da classe Labirinto o qual se quer copiar
	* @throws Exception caso o parâmetro passado seja nulo
    */
	public Labirinto (Labirinto modelo) throws Exception
	{
		if (modelo==null)
			throw new Exception ("ERRO: Modelo para a construcao de copia ausente");

		this.resolvido      = modelo.resolvido;
		this.localDoArquivo = new String (modelo.localDoArquivo);
		this.numeroLinhas   = modelo.numeroLinhas;
		this.numeroColunas  = modelo.numeroColunas;
		this.entrada        = (Coordenada)modelo.entrada.clone();
		this.saida          = (Coordenada)modelo.saida.clone();
		this.labirinto      = new char[modelo.numeroLinhas][modelo.numeroColunas];
		this.haSolucao      = modelo.haSolucao;
		this.inverso        = (Pilha)modelo.inverso.clone();
		for (int i1=0; i1<=modelo.numeroLinhas-1; i1++)
			for (int i2=0; i2<=modelo.numeroColunas-1; i2++)
				this.labirinto[i1][i2] = modelo.labirinto[i1][i2];
	}

	/**
	* Executra o construtor de cópias e retorna um objeto igual à classe que o chamou
	* @return um objeto igual à classe que chamou o método clone()
	*/
	public Object clone ()
	{
		Labirinto ret = null;

		try
		{
			ret = new Labirinto (this);
		}
		catch (Exception erro)
		{}

		return ret;
    }

}
