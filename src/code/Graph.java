package src.code;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    protected Map<Box, List<Box>> grafo;
    protected static final String NEWLINE = System.getProperty("line.separator");
    ArrayList<Box> caixas = new ArrayList<>();
    private List<Edge> arestas;

    public Graph() {
        grafo = new HashMap<>();
        arestas = new ArrayList<>();
    }

    public Graph(String filename) {
        this();
        In in = new In(filename);
        String line;
        while((line = in.readLine()) != null) {
            String[] caixa = line.split(" ");
            Box box = new Box(Integer.parseInt(caixa[0]), Integer.parseInt(caixa[1]), Integer.parseInt(caixa[2]));
            caixas.add(box);
        }
        in.close();
    }
    
    public void construirGrafo() {
        for (Box caixa1 : caixas) {
            List<Box> list = grafo.get(caixa1);

            if(list == null) {
                list = new ArrayList<>();
            }

            for (Box caixa2 : caixas) {
                if (!caixa1.equals(caixa2) && caixa1.caixaCabe(caixa2)) {
                    list.add(caixa2);
                    Edge edge = new Edge(caixa1, caixa2);
                    arestas.add(edge);
                }
            }
            grafo.put(caixa1, list);
        }
    }

    public List<Edge> getArestas() {
        return arestas;
    }

    public void imprimirGrafo() {
        for (Box box : grafo.keySet()) {
            System.out.print(box + " -> ");
            for (Box adj : grafo.get(box)) {
                System.out.print(adj);
            }
            System.out.println();
        }
    }

    public Set<Box> getVerts() {
        return grafo.keySet();
      }

    public Iterable<Box> getAdj(Box v) {
        return grafo.get(v);
    }
    
    public Box pegarCaixaComMaisAdjacentes() {
        caixasMesmoTamanho.clear();
        Box caixa = null;
        int numAdj = 0;
        for (Box box : grafo.keySet()) {
            if (grafo.get(box).size() > numAdj) {
                numAdj = grafo.get(box).size();
                caixasMesmoTamanho.clear();
                caixa = box;
            }
            if(numAdj == grafo.get(box).size()) {
                caixasMesmoTamanho.add(box);
            }
        }
        System.out.println("Caixa com mais adjacentes: " + caixa + " com " + numAdj + " adjacentes");
        return caixa;
    }

    public static List<Box> maiorSequenciaDeCaixas(Graph graph) {
        Map<Box, List<Box>> maiorResultado = new HashMap<>();
        List<Box> maiorCaminho = new ArrayList<>();
    
        for (Box caixa : graph.getVerts()) {
            List<Box> sequencAtual = dfs(caixa, graph, maiorResultado);
            if (sequencAtual.size() > maiorCaminho.size()) {
                maiorCaminho = sequencAtual;
            }
        }
    
        System.out.println("Maior Caminho: " + maiorCaminho.size());
        return maiorCaminho;
    }
    
    private static List<Box> dfs(Box caixa, Graph graph, Map<Box, List<Box>> maiorResultado) {
        
        if (maiorResultado.containsKey(caixa)) {
            return maiorResultado.get(caixa);
        }
    
        List<Box> maiorSequencia = new ArrayList<>();
        for (Box adj : graph.getAdj(caixa)) {
            List<Box> sequencAtual = dfs(adj, graph, maiorResultado);
            if (sequencAtual.size() > maiorSequencia.size()) {
                maiorSequencia = sequencAtual;
            }
        }
    
        List<Box> resultado = new ArrayList<>();
        resultado.add(caixa);
        resultado.addAll(maiorSequencia);
    
        maiorResultado.put(caixa, resultado);
    
        return resultado;
    }

    public String toDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {"+NEWLINE);
        sb.append("rankdir = LR;"+NEWLINE);
        sb.append("node [shape = circle];"+NEWLINE);
        for(Box v: getVerts()) {
          for (Box w: getAdj(v)) {
            Edge edge = v.toString().compareTo(w.toString()) > 0 ? (new Edge(v, w)) : (new Edge(w, v));
            if(!arestas.contains(edge)) {
              sb.append(v + " -> " + w + NEWLINE);
              arestas.add(edge);
            }
          }
        }
        sb.append("}" + NEWLINE);
        return sb.toString();
      }
}
