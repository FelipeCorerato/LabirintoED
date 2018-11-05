package fila;
import java.lang.reflect.*;

/**
 * Classe FilaLigada armazena elementos na forma da estrutura de dados fila.
 * @author Artur Morais
 * @author Felipe Corerato 
*/
public class FilaLigada<X> implements Cloneable
{
    private class No
    {
        protected X  info;
        protected No prox;

        public X getInfo ()
        {
            return this.info;
        }

        public No getProx ()
        {
            return this.prox;
        }

        public void setInfo (X x)
        {
            this.info=x;
        }

        public void setProx (No n)
        {
            this.prox=n;
        }

        public No (X x, No n)
        {
            this.info=x;
            this.prox=n;
        }

        public No (X x)
        {
            this (x,null);
        }
    }   
	protected No prim;
    protected No  fim;
	protected int qtd;

    /**
     * Construtor da classe FilaLigada.
     * Inicializa o primeiro No da fila como NULL.
    */
	public FilaLigada ()
	{
		this.prim = null;
	}

    /**
     * Metodo que retorna o valor do atributo <b>inicio</b>, que representa, no vetor da fila, a posicao do primeiro elemento.
     * @return o valor da variavel inteira inicio
    */
	public X getInicio ()
	{
		return this.prim.getInfo();
	}

    /**
     * Metodo que retorna o valor do atributo <b>fim</b>, que representa, no vetor da fila, a posicao do ultimo elemento.
     * @return o valor da variavel inteira fim
    */
	public X getFim ()
	{
		return this.fim.getInfo();
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
     * Metodo que retorna o elemento armazenado no inicio da fila.
     * @return o elemento no inico da fila
     * @throws Exception caso a fila esteja vazia
    */
	public X getElemento () throws Exception
	{
		if (this.vazia())
			throw new Exception ("ERRO: Fila vazia, nao foi possivel retornar seu primeiro elemento.");

		return this.prim.getInfo();
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
		if (!(obj instanceof FilaLigada))
			return false;

		FilaLigada fil = (FilaLigada)obj;

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
     * Constroi uma classe FilaLigada igual à passada como parâmetro.
     * @param modelo uma classe FilaLigada que será copiada
     * @throws Exception se o parâmetro passado for nulo
    */
	public FilaLigada (FilaLigada modelo) throws Exception
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
		FilaLigada ret = null;

		try
		{
			ret = new FilaLigada (this);
		}
		catch (Exception erro)
		{}

		return ret;
    }

}