package pilhaligada;
import java.lang.reflect.*;

public class PilhaLigada <X> implements Cloneable
{
	private class No
	{
		private X  info;
		private No prox;

		public No (X i, No p)
		{
			this.info = i;
			this.prox = p;
		}

		public No (X i)
		{
			this.info = i;
			this.prox = null;
		}

        public X getInfo()
        {
            return this.info;
        }

        public No getProx()
        {
            return this.prox;
        }

        public void setInfo(X i)
        {
            this.info = i;
        }

        public void setProx(No novo)
        {
            this.prox = novo;
        }
	}

	protected No prim = null;

	public PilhaLigada ()
	{
        this.prim = null;
	}

	public X getElemento() throws Exception
	{
		if(this.prim == null)
			throw new Exception("Pilha vazia");

		return this.prim.getInfo();
	}

	public int getTopo()
	{
		return this.getQtd();
	}

	public boolean vazia()
	{
		return this.prim == null;
	}

	public void empilhe(X info) throws Exception
	{
		this.insiraNoInicio(info);
	}

	public void insiraNoInicio(X info) throws Exception
	{
        if (info == null)
            throw new Exception ("Informacao ausente!");

		this.prim = new No (info, this.prim);
	}

    public void insiraNoFim(X info) throws Exception
    {
        if (info == null)
            throw new Exception ("Informacao ausente!");

        if (this.prim == null)
            this.prim = new No(info);
        else
        {
            No atual = this.prim;
            while (!(atual.getProx() == null))
                atual = atual.getProx();

            atual.setProx(new No(info));
        }
    }

    public void insiraNoFimSemRepeticao (X i) throws Exception
    {
        if (i == null)
            throw new Exception ("Informacao ausente!");

        No novo = new No (i, null);

        if (this.prim == null)
        {
            this.prim=novo;
            return;
        }

        if (i.equals(this.prim.getInfo()))
            throw new Exception ("Informacao repetida!");

        No atual = this.prim;

		while (atual.getProx() != null)
        {
            if (i.equals(atual.getInfo()))
                throw new Exception ("Informacao repetida!");

			atual = atual.getProx();
        }

        if (i.equals(atual.getInfo()))
            throw new Exception ("Informacao repetida!");

		atual.setProx (novo);
    }

    public boolean tem(X info) throws Exception
    {
        if (info == null)
            throw new Exception ("Informacao ausente!");

        if (this.prim == null)
            throw new Exception ("Lista vazia!");

        No atual = this.prim;
        while(!(atual == null))
        {
            if (atual.getInfo().equals(info))
                return true;

            atual = atual.getProx();
        }

        return false;
    }

    public void desempilhe() throws Exception
    {
		if(this.prim == null)
			throw new Exception("Pilha Vazia");

		this.prim = this.prim.getProx();
	}



    public void remova(X info) throws Exception
    {
        if (info == null)
            throw new Exception ("Informacao ausente!");

        if (this.prim == null)
            throw new Exception ("A lista esta vazia!");

        if (info.equals(this.prim.getInfo()))
            this.prim = this.prim.getProx();
        else
        {
            No atual = this.prim;

            while (atual.getProx() != null && !(info.equals(atual.getProx().getInfo())))
                atual = atual.getProx();

            if (atual.getProx() == null)
                throw new Exception ("Informacao nao encontrada!");

            atual.setProx(atual.getProx().getProx());
        }
    }

    public void inverta()
    {
        if (this.prim == null || this.prim.getProx() == null)
            return;

        No ant   = null;
        No atual = this.prim;
        No seg   = atual.getProx();

        while (seg != null)
        {
            atual.setProx(ant);

            ant   = atual;
            atual = seg;
            seg   = seg.getProx();
        }

        atual.setProx(ant);
        this.prim = atual;
    }

    public int getQtd()
    {
        int ret = 0;

        No atual = this.prim;
        while(atual.getProx() != null)
        {
            ret++;
            atual.getProx();
        }

        return ret;
    }

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

	public String toString()
	{
		String ret = "";

        No atual = this.prim;
        while (atual != null)
        {
            ret += atual.getInfo();
            if (atual.getProx() != null)
                ret += ", ";

            atual = atual.getProx();
        }

		return ret;
	}

	public int hashCode()
	{
		int ret = 1;

        No atual = this.prim;
        while (atual != null)
        {
			ret += ret * 7 + atual.getInfo().hashCode();

			atual = atual.getProx();
		}

        return ret;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;

        if (this.getClass() != obj.getClass())
        	return false;

        PilhaLigada<X> lista = (PilhaLigada<X>)obj;

		No atualThis  = this.prim;
		No atualLista = lista.prim;

		while (atualThis != null && atualLista != null)
		{
			if (!atualThis.getInfo().equals(atualLista.getInfo()))
				return false;

			atualThis  = atualThis.getProx();
			atualLista = atualLista.getProx();
		}

		if (!(atualThis == null && atualLista == null))
				return false;

		return true;
	}

    public PilhaLigada (PilhaLigada<X> modelo) throws Exception
    {
        if (modelo == null)
            throw new Exception ("Modelo ausente!");

        if (modelo.prim == null)
        	this.prim = null;
        else
        {
			this.prim = new No(modelo.prim.getInfo());

			No atualThis     = this.prim;
			No atualModelo   = modelo.prim;
			while (atualModelo.getProx() != null)
			{
				atualThis.setProx(new No(atualModelo.getProx().getInfo()));

				atualThis   = atualThis.getProx();
				atualModelo = atualModelo.getProx();
			}
		}
    }

    public Object clone ()
    {
        PilhaLigada<X> ret = null;

        try
        {
            ret = new PilhaLigada<X> (this);
        }
        catch (Exception erro){}

        return ret;
    }
}