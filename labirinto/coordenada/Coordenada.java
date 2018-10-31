package coordenada;

/**
 * Classe Coordenada faz operacoes com os atributos x e y da posicao
 * de uma coordenada.
 * @author Eduardo Porto 
 * @author Felipe Corerato 
 * @author Joao Henri
*/
public class Coordenada implements Cloneable
{
    /**
	 * Coordenada x de tabuleiro bidimensional
    */
    protected int x;
    /**
	 * Coordenada y de tabuleiro bidimensional
    */
    protected int y;

    /**
     * Construtor da classe Coordenada, caso o usuario passe como parametros valores para os atributos x e y.
     * Os parâmetros passados pelo usuário assumem os atributos protegidos x e y.
     * O metodo entra na excecao se algum dos parametros passados pelo usuario for menor que 0.
     * 
     * @param  coordenadaX posicao de algo no eixo X (horizontal)
     * @param  coordenadaY posicao de algo no eixo Y (vertical)
     * @throws Exception caso a variavel x ou y for menor que zero
    */
    public Coordenada (int coordenadaX, int coordenadaY) throws Exception
    {
        if (coordenadaX < 0)
            throw new Exception ("ERRO: O valor X possui um valor invalido.");
        if (coordenadaY < 0)
            throw new Exception ("ERRO: O valor Y possui um valor invalido.");

        x = coordenadaX;
        y = coordenadaY;
    }
    
    /**
     * Construtor da classe Coordenada caso o usuario nao passe paramentros.
     * Os atributos x e y da classe recebem 0 (zero).
    */
    public Coordenada ()
    {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Retorna o valor do atruibuto x.
     * @return o valor de x da coordenada registrada
    */
    public int getX ()
    {
        return this.x;
    }

    /**
     * Retorna o valor do atruibuto y.
     * @return o valor de y da coordenada registrada
    */
    public int getY ()
    {
        return this.y;
    }

    /**
     * Metodo que ajusta o valor do atributo x conforme o parametro passado pelo usuario e
     * apresentando uma excecao se o parametro for menor que zero.
     * @param  coordenadaX um inteiro que sera o valor do atributo x
     * @throws Exception caso o valor do parametro seja menor que zero
    */
    public void setX (int coordenadaX) throws Exception
    {
        if (coordenadaX < 0)
            throw new Exception ("ERRO: O valor X possui um valor invalido.");

        this.x = coordenadaX;
    }

    /**
     * Metodo que ajusta o valor do atributo y conforme o parametro passado pelo usuario, 
     * apresentando uma excecao se o parametro for menor que zero.
     * @param  coordenadaY um inteiro que sera o valor do atributo y
     * @throws Exception caso o valor do parametro seja menor que zero
    */
    public void setY (int coordenadaY) throws Exception
    {
        if (coordenadaY < 0)
            throw new Exception ("ERRO: O valor Y possui um valor invalido.");

        this.y = coordenadaY;
    }

    /**
     * Funcao que retorna os atributos da classe numa String.
     * @return a String com os atributos.
    */
    public String toString ()
    {
        String ret = "(";

        ret += this.x + ", " + this.y;

        ret += ")";

        return ret;
    }

    /**
     * Funcao que retorna o codigo hash da classe.
     * @return o codigo hash
    */
    public int hashCode ()
    {
        int ret = 3;

        ret = ret * 7 + new Integer(this.x).hashCode();
        ret = ret * 7 + new Integer(this.y).hashCode();

        return ret;
    }

    /**
     * Funcao que verifica se uma classe eh igual a Coordenada.
     * @param  obj o objeto que sera comparado
     * @return se as classes sao ou nao iguais
    */
    public boolean equals (Object obj)
    {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Coordenada))
            return false;

        Coordenada coordenada = (Coordenada)obj;

        if (this.x != coordenada.x)
            return false;

        if (this.y != coordenada.y)
            return false;

        return true;
    }

    /**
     * Construtor de copia da classe.
     * Constroi uma classe Coordenada igual a passada como parametro.
     * @param modelo uma classe Coordenada que sera copiada
    */
    public Coordenada (Coordenada modelo) throws Exception
    {
        if (modelo == null)
            throw new Exception ("ERRO: Modelo ausente");

        this.x = modelo.x;
        this.y = modelo.y;
    }

    /**
     * Executa o contrutor de copia e retorna uma classe igual a "this".
     * @return um clone do seu objeto
    */
    public Object clone ()
    {
        Coordenada ret = null;

        try
        {
            ret = new Coordenada (this);
        }
        catch (Exception erro)
        {}

        return ret;
    }

}