package src.code;

public class Main {
    public static void main(String[] args) {

        //tempo de execucao
        long tempoInicial = System.currentTimeMillis();

        Graph grafo = new Graph("chucknorris.txt");
        
        grafo.construirGrafo();
        //grafo.imprimirGrafo();
        //System.out.println(grafo.toDot());
        //grafo.pegarCaixaComMaisAdjacentes();
        //grafo.metodo();
        //grafo.metodoNovo();
        grafo.maiorSequenciaDeCaixas(grafo);

        long tempoFinal = System.currentTimeMillis();
        System.out.println("Tempo de execucao: " + (tempoFinal - tempoInicial) + "ms");
    }   
}