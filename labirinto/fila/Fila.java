package fila;
import java.lang.reflect.*;

/**
 * Classe Fila armazena elementos na forma da estrutura de dados fila.
 * @author Eduardo Porto
 * @author Felipe Corerato
 * @author Joao Henri
*/
public class Fila<X> implements Cloneable
{
	/**
	* Atributo que representa a posicao do primeiro valor do vetor {@link Fila#elemento}
	*/
	protected int inicio;
	/**
	* Atributo que representa a posicao do ultimo valor do vetor {@link Fila#elemento}
	*/
	protected int fim;
	/**
	* Representa a quantidade de valores guardados na fila
	*/
	protected int qtd;
	/**
	* Vetor onde será guardado os objetos da fila
	*/
	protected Object[] elemento;
	/**
	* Taxa de crescimento (em porcentagem) do vetor {@link Fila#elemento}
	*/
	protected float taxaDeCrescimento;

    /**
     * Método que inicia os atributos da classe, recebendo como parâmetros o tamanho do vetor
     * e o valor da taxa de crescimento.
     * @param tam tamanho que o vetor terá (em porcentagem)
     * @param tc  taxa de crescimento do vetor
    */
	protected void iniciacao (int tam, float tc)
	{
		this.elemento          = new Object [tam];
		this.taxaDeCrescimento = tc;
		this.inicio            = 0;
		this.qtd               = 0;
		this.fim               = -1;
	}

    /**
     * Construtor da classe Fila com dois parâmetros.
     * Recebe como parametros o valor do tamanho que a fila tera e o valor da taxa de crescimento.
     * O metodo entra na excecao se o valor do tamanho ou o da taxa de crescimento for igual ou
     * menor que zero.
     * @param  tam tamanho que sera atribuido ao vetor
     * @param  tc  taxa de crescimento
     * @throws Exception caso os parametros sejam iguais ou menores que zero.
    */
	public Fila (int tam, float tc) throws Exception
	{
		if (tam<=0)
			throw new Exception ("ERRO: Tamanho invalido");
		if (tc<=0)
			throw new Exception ("ERRO: Taxa de crescimento invalida");
		this.iniciacao (tam, tc);
	}

    /**
     * Construtor da classe Pilha com apenas o tamanho como parametro. O valor da taxa de crescimento será 10.
     * @param tam tamanho que o vetor tera
     * @throws Exception se o parametro passado for menor ou igual a zero
    */
	public Fila (int tam) throws Exception
	{
		if (tam<=0)
			throw new Exception ("ERRO: Tamanho invalido");
		this.iniciacao (tam, 10);
	}

    /**
     * Construtor da classe Pilha para quando o usuário nao passar nenhum parâmetro
     * quando instanciar a classe.
    */
	public Fila ()
	{
		this.iniciacao (10, 10);
	}

    /**
     * Metodo que retorna o valor do atributo <b>inicio</b>, que representa, no vetor da fila, a posicao do primeiro elemento.
     * @return o valor da variavel inteira inicio
    */
	public int getInicio ()
	{
		return this.inicio;
	}

    /**
     * Metodo que retorna o valor do atributo <b>fim</b>, que representa, no vetor da fila, a posicao do ultimo elemento.
     * @return o valor da variavel inteira fim
    */
	public int getFim ()
	{
		return this.fim;
	}

    /**
     * Metodo que retorna a quantidade de dados guardados na fila.
     * @return quantidade de dados guardados na fila
    */
	public int getQtd ()
	{
		return this.qtd;
	}

    /**
     * Metodo que retorna a taxa de crescimento da fila.
     * @return a taxa de crescimento
    */
	public float getTaxaDeCrescimento ()
	{
		return this.taxaDeCrescimento;
	}

    /**
     * Método que ajusta o valor da taxa de crescimento para o valor passado como
     * parametro.
     * @param  tc        o valor que será atribuido a taxa de crescimento
     * @throws Exception caso o valor passado como parametro seja menor ou igual a zero
    */
	public void setTaxaDeCrescimento (float tc) throws Exception
	{
		if (tc<=0)
			throw new Exception ("ERRO: Taxa de crescimento invalida.");
		this.taxaDeCrescimento = tc;
	}

    /**
     * Metodo que retorna o elemento armazenado no inicio da fila.
     * @return o elemento no inico da fila
     * @throws Exception caso a fila esteja vazia
    */
	public X getElemento () throws Exception
	{
		if (this.vazia())
			throw new Exception ("ERRO: Fila vazia, nao foi possivel retornar seu primeiro elemento.");

		return (X)elemento[inicio];
	}

    /**
     * Metodo que verifica se a fila esta vazia.
     * @return verdadeiro se a fila esta vazia
    */
	public boolean vazia()
	{
		return this.qtd==0;
	}

    /**
     * Metodo que aumenta o tamanho do vetor de acordo com a taxa de crescimento.
     * É criado um novo vetor com o novo tamaho, baseado na taxa de crescimento, e este é populado
     * com os elementos do vetor antigo.
    */
	protected void cresca ()
	{
		float multiplicador = this.taxaDeCrescimento/100+1;
		int   tamNovo       = (int)Math.ceil(this.elemento.length*multiplicador);

		Object[] novo = new Object [tamNovo];

		for (int i=this.inicio; i<=this.fim; i++)
			novo[i] = this.elemento[i];

		this.elemento = novo;
	}

    /**
     * Procedimento que faz a fila "mover para frente". Para isso, é criado um novo vetor e
     * este é populado, nao deixando nenhum espaco entre elementos no vetor.
    */
	protected void moverFila()
	{
		Object[] novo = new Object [this.elemento.length];

		int i1 = this.inicio;
		int i2 = 0;
		for (;;)
		{
			if(i1 >= this.fim)
			{
				this.inicio = 0;
				this.fim    = i2;
				break;
			}
			novo[i2] = this.elemento[i1];
			i1++;
			i2++;
		}
	}

    /**
     * Guarda um elemento passado como parâmetro no final da fila, fazendo algumas operações quando
     * necessário, como mover a fila (moverFila()) e aumentar o vetor (cresca()).
     * @param  x         dado que sera armazenado na fila
     * @throws Exception se o dado passado como parametro for nulo
    */
	public void enfileire (X x) throws Exception
	{
		if (x==null)
			throw new Exception ("ERRO: Valor para enfileirar invalido.");

		if (this.fim+1 > this.elemento.length-1)
			this.moverFila();

		if (this.qtd==this.elemento.length)
			this.cresca();

		this.fim++;

		if (x instanceof Cloneable)
			this.elemento[this.fim] = this.meuCloneDeX(x);
		else
			this.elemento[this.fim] = x;

		this.qtd++;
	}

    /**
     * Retira o elemento guardado no começo da fila.
     * @throws Exception caso a pilha esteja vazia
    */
	public void desenfileire () throws Exception
	{
		if (this.vazia())
			throw new Exception ("ERRO: Nao ha mais elementos na fila, nao foi possivel desenfileirar");

		this.elemento[inicio]=null;
		this.inicio++;
		this.qtd--;
	}

    /**
     * Método que força o programa a executar a função clone de um objeto guardado na fila.
     * @param x elemento que será feito o clone
     * @return um clone do elemento x
    */
	protected X meuCloneDeX (X x)
	{
		X ret = null;

		try
		{
			Class<?> classe = x.getClass();
			Class<?>[] tipoDoParametroFormal = null;
			Method metodo = classe.getMethod ("clone", tipoDoParametroFormal);
			Object[] parametroReal = null;
			ret = ((X)metodo.invoke (x, parametroReal));
		}
		catch (NoSuchMethodException erro)
		{}
		catch (InvocationTargetException erro)
		{}
		catch (IllegalAccessException erro)
		{}

		return ret;
    }

    /**
     * Método que retorna os elementos da fila numa String.
     * @return a String com os elementos.
    */
	public String toString ()
	{
		String ret="{";

		for (int i=inicio; i<=this.fim; i++)
			ret += this.elemento[i]+(i!=this.fim?",":"");

		ret += "}";

		return ret;
	}

    /**
     * Método que retorna o código hash da classe.
     * @return o código hash
    */
	public int hashCode()
	{
		int ret = 2;

		ret = ret*3 + new Integer (this.inicio).hashCode();
		ret = ret*3 + new Integer (this.fim).hashCode();
		ret = ret*3 + new Integer (this.qtd).hashCode();
		ret = ret*3 + new Float (this.taxaDeCrescimento).hashCode();
		for (int i=this.inicio; i<=this.fim; i++)
			ret = ret*3+this.elemento[i].hashCode();

		return ret;
	}

    /**
     * Método que verifica se uma classe passada como parâmetro é igual à classe que invocou
  	 * este método.
     * @param  obj o objeto que será comparado
     * @return se as classes são ou não iguais
    */
	public boolean equals (Object obj)
	{
		if (this==obj)
			return true;
		if (obj==null)
			return false;
		if (!(obj instanceof Fila))
			return false;

		Fila fil = (Fila)obj;

		if (this.inicio != fil.inicio)
			return false;

		if (this.fim != fil.fim)
			return false;

		if (this.qtd != fil.qtd)
			return false;

		if (this.taxaDeCrescimento != fil.taxaDeCrescimento)
			return false;

		for (int i=this.inicio; i<=this.fim; i++)
			if (!this.elemento[i].equals(fil.elemento[i]))
				return false;

		return true;
	}

    /**
     * Construtor de cópia da classe.
     * Constroi uma classe Fila igual à passada como parâmetro.
     * @param modelo uma classe Fila que será copiada
     * @throws Exception se o parâmetro passado for nulo
    */
	public Fila (Fila modelo) throws Exception
	{
		if (modelo==null)
			throw new Exception ("ERRO: Modelo ausente");

		this.elemento = new Object [modelo.elemento.length];
		for (int i=modelo.inicio; i<=modelo.fim; i++)
			if (this.elemento[i] instanceof Cloneable)
				this.elemento[i]  = this.meuCloneDeX((X)modelo.elemento[i]);
			else
				this.elemento[i]  = modelo.elemento[i];

		this.inicio            = modelo.inicio;
		this.fim               = modelo.fim;
		this.qtd               = modelo.qtd;
		this.taxaDeCrescimento = modelo.taxaDeCrescimento;
	}

    /**
     * Executa o contrutor de cópia e retorna uma classe igual a "this".
     * @return um clone do seu objeto
    */
	public Object clone ()
	{
		Fila ret=null;

		try
		{
			ret = new Fila (this);
		}
		catch (Exception erro)
		{}

		return ret;
    }

}