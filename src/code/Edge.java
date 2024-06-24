package src.code;

public class Edge{
    private int entrada;
    private int saida;

    public Edge(Box entrada, Box saida) {
        this.entrada = entrada.getId();
        this.saida = saida.getId();
    }

    public int getEntrada(){
        return entrada;
    }

    public int getSaida(){
        return saida;
    }

    @Override
    public String toString(){
        return entrada + " --> " + saida;
    }
}
