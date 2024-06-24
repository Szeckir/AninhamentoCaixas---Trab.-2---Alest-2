package src.code;

public class Main {
    public static void main(String[] args) {

        Graph grafo = new Graph("teste10.txt");
        
        grafo.construirGrafo();
        //grafo.imprimirGrafo();
        grafo.procuraMaiorSequenciaDeCaixas(grafo);
        //System.out.println(grafo.toDot());
        grafo.getAdjacentes();
    }   
}