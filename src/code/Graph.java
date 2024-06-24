package src.code;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void getAdjacentes() {
        for (Box box : grafo.keySet()) {
            int contador = 0;
            System.out.println("Caixa cabe em: " + box.getId());
            for (Box adj : grafo.get(box)) {
                contador++;
            }
            System.out.println(contador + " caixas");
        }
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
            System.out.print(box.getId() + " -> ");
            for (Box adj : grafo.get(box)) {
                System.out.print(adj.getId() + " ");
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

    public void procuraMaiorSequenciaDeCaixas(Graph g) {
        int maiorSequencia = 0;
        List<Box> sequencia = new ArrayList<>();
        
        for (Box v : g.getVerts()) {
            int sequenciaAtual = 0;
            List<Box> sequenciaAtualList = new ArrayList<>();
            sequenciaAtualList.add(v);
            

            for (Box w : g.getAdj(v)) {
                sequenciaAtual++;
                sequenciaAtualList.add(w);
                for (Box x : g.getAdj(w)) {
                    sequenciaAtual++;
                    sequenciaAtualList.add(x);
                }
                if (sequenciaAtual > maiorSequencia) {
                    maiorSequencia = sequenciaAtual;
                    sequencia = sequenciaAtualList;
                }
                sequenciaAtual = 0;
                sequenciaAtualList = new ArrayList<>();
                sequenciaAtualList.add(v);
            }
        }
        System.out.println("Maior sequência de caixas: " + maiorSequencia);
        System.out.println("Sequência de caixas: " + sequencia);
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
