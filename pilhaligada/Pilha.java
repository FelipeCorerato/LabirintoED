package pilha;
import java.lang.reflect.*;

/**
 * Classe Pilha armazena elementos em um vetor em forma de pilha.
 * @author Eduardo Porto
 * @author Felipe Corerato
 * @author Joao Henri
*/
public class Pilha <X> implements Cloneable
{
	protected int topo;
	protected Object[] elemento;
	protected float taxaDeCrescimento;

    /**
     * Método que inicia os atributos da classe.
     * @param tam tamanho que o vetor tera
     * @param tc  taxa de crecimento do vetor
    */
	protected void iniciacao (int tam, float tc)
	{
		this.elemento          = new Object [tam];
		this.topo              = -1;
		this.taxaDeCrescimento = tc;
	}

    /**
     * Construtor da classe Pilha com dois parâmetros.
     * Recebe como parametros o valor do tamanho que a pilha terá e o valor da taxa de crescimento da pilha.
     * O metodo entra na excecao se o valor do tamanho ou o da taxa de crescimento for igual ou 
     * menor que zero.
     * @param tam tamanho que o vetor elemento terá, em porcentagem
     * @param tc taxa de crescimento que a classe vai ter
     * @throws Exception caso os parametros sejam iguais ou menores que zero.
    */
	public Pilha (int tam, float tc) throws Exception
	{
		if (tam <= 0)
			throw new Exception ("ERRO: Tamanho invalido");
		if (tc <= 0)
			throw new Exception ("ERRO: Taxa de crescimento invalida");
		this.iniciacao (tam, tc);
	}

    /**
     * Construtor da classe Pilha com apenas o tamanho como parametro passado.
     * @param tam tamanho que o vetor tera
     * @throws Exception se o parametro passado for menor ou igual a zero
    */
	public Pilha (int tam) throws Exception
	{
		if (tam <= 0)
			throw new Exception ("ERRO: Tamanho invalido");
		this.iniciacao (tam, 10);
	}

    /**
     * Construtor da classe Pilha para quando o usuario nao passar nenhum parametro
     * quando instanciar a classe.
    */
	public Pilha ()
	{
		this.iniciacao (10, 10);
	}

    /**
     * Método que retorna o valor do atributo <b>topo</b>, que representa, no vetor da fila, a posicao do ultimo elemento.
     * @return o valor da variavel inteira topo
    */
	public int getTopo ()
	{
		return topo;
	}

    /**
     * Método que retorna a taxa de crescimento da pilha.
     * @return a taxa de crescimento
    */
	public float getTaxaDeCrescimento ()
	{
		return taxaDeCrescimento;
	}

    /**
     * Método que ajusta o valor da taxa de crescimento para o valor passado como 
     * parâmetro.
     * @param  tc        o valor que será atribuido à taxa de crescimento 
     * @throws Exception caso o valor passado como parâmetro seja menor ou igual a zero
    */
	public void setTaxaDeCrescimento (float tc) throws Exception
	{
		if (tc <= 0)
			throw new Exception ("ERRO: Taxa de crescimento invalida.");
		this.taxaDeCrescimento = tc;
	}

    /**
     * Procedimento que aumenta o tamanho do vetor de acordo com a taxa de crescimento.
     * É criado um novo vetor com o novo tamaho, baseado na taxa de crescimento, e este é populado
     * com os elementos do vetor antigo.
    */
	protected void cresca ()
	{
		float multiplicador = this.taxaDeCrescimento/100 + 1;
		int   tamNovo       = (int)Math.ceil(this.elemento.length*multiplicador);

		Object[] novo = new Object [tamNovo];

		for (int i = 0; i <= this.topo; i++)
			novo[i] = this.elemento[i];

		this.elemento = novo;
	}

    /**
     * Guarda um elemento passado como parâmetro no topo da pilha.
     * @param  x         objeto que será armazenado na pilha
     * @throws Exception se o objeto passado como parâmetro for nulo
    */
	public void empilhe (X x) throws Exception
	{
		if (x == null)
			throw new Exception ("ERRO: Valor para empilhar invalido.");

		if (this.elemento.length==this.topo)
			this.cresca();

		this.topo++;
		if (x instanceof Cloneable)
			this.elemento[this.topo] = this.meuCloneDeX(x);
		else
			this.elemento[this.topo] = x;
	}

    /**
     * Retira o ultimo elemento guardado na pilha (guardado no topo).
     * @throws Exception caso a pilha esteja vazia
    */
	public void desempilhe () throws Exception
	{
		if (this.topo == -1)
			throw new Exception ("ERRO: Pilha vazia");

		this.elemento[topo] = null;
		this.topo--;
	}

    /**
     * Método que retorna o valor do elemento armazenado no topo da pilha.
     * @return o elemento no topo do vetor
     * @throws Exception se a pilha estiver vazia
    */
	public X getElemento () throws Exception
	{
		if (this.vazia())
			throw new Exception ("ERRO: Pilha vazia");

		return (X)this.elemento[this.topo];
	}

    /**
     * Método que verifica se a pilha está vazia.
     * @return verdadeiro se a pilha está vazia
    */
	public boolean vazia ()
	{
		return this.topo == -1;
	}

    /**
     * Método que força o programa a executar a função clone de um objeto guardado na pilha. 
     * @param x elemento que sera feito o clone
     * @return  clone do elemento x
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
     * Funcao que retorna os elementos da pilha numa String.
     * @return a String com os elementos.
    */
    public String toString ()
	{
		String ret = "{";

		for (int i = 0; i <= this.topo; i++)
			ret += this.elemento[i] + (i!=this.topo?",":"");

		ret += "}";

		return ret;
	}

    /**
     * Retorna o codigo hash da classe.
     * @return o codigo hash
    */
	public int hashCode ()
	{
		int ret = 7;

		ret = ret * 3 + new Integer (this.topo).hashCode();
		ret = ret * 3 + new Float (this.taxaDeCrescimento).hashCode();
		for (int i = 0; i <= this.topo; i++)
			ret = ret * 3 + this.elemento[i].hashCode();

		return ret;
	}

    /**
     * Verifica se uma classe passada como parâmetro é igual à classe que chamou este método.
     * @param  obj o objeto que sera comparado
     * @return se as classes sao ou nao iguais
    */
	public boolean equals (Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pilha))
			return false;

		Pilha pil = (Pilha)obj;

		if (this.topo != pil.topo)
			return false;

		if (this.taxaDeCrescimento != pil.taxaDeCrescimento)
			return false;

		for (int i = 0; i <= this.topo; i++)
			if (!this.elemento[i].equals(pil.elemento[i]))
            	return false;

        return true;
	}

    /**
     * Construtor de cópia da classe.
     * Constroi uma classe Pilha igual à passada como parâmetro.
     * @param  modelo uma classe Pilha que será copiada
     * @throws Exception se o parametro passado for nulo
    */
	public Pilha (Pilha modelo) throws Exception
	{
		if (modelo == null)
			throw new Exception ("ERRO: Modelo ausente");

		this.topo = modelo.topo;

		this.elemento = new Object [modelo.elemento.length];
		for (int i = 0; i <= modelo.topo; i++)
			if (this.elemento[i] instanceof Cloneable)
				this.elemento[i] = this.meuCloneDeX((X)modelo.elemento[i]);
			else
				this.elemento[i] = modelo.elemento[i];

		this.taxaDeCrescimento = modelo.taxaDeCrescimento;
    }

    /**
     * Executa o contrutor de cópia e retorna uma classe igual a "this".
     * @return um clone do seu objeto
    */
    public Object clone ()
    {
        Pilha ret = null;

        try
        {
            ret = new Pilha (this);
        }
        catch (Exception erro)
        {}

        return ret;
    }

}