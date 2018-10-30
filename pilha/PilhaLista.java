package desordenada;
import java.lang.reflect.*;

public class PilhaLista <X>
{
    protected class No
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
    protected int qtd;

    public ListaDesordenada ()
    {
        this.prim = null;
    }

    protected X meuCloneDeX (X x)
    {
        X ret = null;

        try
        {
            Class<?> classe = x.getClass();
            Class<?>[] tipoDoParametroFormal = null; // pq clone tem 0 parametros
            Method metodo = classe.getMethod ("clone", tipoDoParametroFormal);
            Object[] parametroReal = null;// pq clone tem 0 parametros
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

    public void insiraNoInicio (X x) throws Exception
    {
        if (x==null)
            throw new Exception ("Informacao ausente");

        X info;
        if (x instanceof Cloneable)
            info = meuCloneDeX(x);
        else
            info = x;

        this.prim = new No (info,this.prim);

        this.qtd++;
    }

    public void insiraNoFim (X x) throws Exception
    {
        if (x==null)
            throw new Exception ("Informacao ausente");

        X info;
        if (x instanceof Cloneable)
            info = meuCloneDeX(x);
        else
            info = x;

        if (this.prim==null)
            this.prim = new No (info);
        else
        {
            No atual=this.prim;
            while (atual.getProx()!=null)
                atual=atual.getProx();

            atual.setProx (new No (info));
        }

        this.qtd++;
    }

    public void jogueForaPrimeiro ()
    {
        this.prim = this.prim.getProx();
    }

    public void jogueForaUltimo ()
    {
        No atual;
        atual = this.prim;

        if(atual.getProx() == null)  // caso a lista tenha apenas um item
        {
            atual = null;
            return;
        }

        for(;;)
        {
            if(atual.getProx().getProx() == null)
            {
                atual.setProx(null);
                return;
            }

            atual = atual.getProx();
        }
    }

    public No getPrim()
    {
        return this.prim;
    }

    public String toString ()
    {
        String ret   = "{";
        No     atual = this.prim;

        while (atual!=null)
        {
            ret += atual.getInfo();

            if (atual.getProx()!=null) // se atual nao é o ultimo
                ret += ",";

            atual = atual.getProx();
        }

        ret += "}";

        return ret;
    }

    public int hashCode ()
    {
        // vimos solucao de aluno
    }

    public boolean equals (Object obj)
    {
        // vimos solucao de aluno
    }

    public Object clone ()
    {
        ListaDesordenada<X> ret = new ListaDesordenada<X> ();

        if (this.prim==null)
            return ret;

        ret.prim = new No (this.prim.getInfo());

        No atual = this.prim;

        while (atual!=null)
        {


            atual = atual.getProx();
        }
    }

    public ListaDesordenada (ListaDesordenada<X> modelo)
    {
        // vimos solucao de aluno
    }
}