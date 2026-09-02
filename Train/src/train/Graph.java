/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package train;

/**
 *
 * @author WEI LI
 */
public interface Graph<V> {
    
    public int getSize();
    
    public java.util.List<V> getVertices();
    
    public V getVertex(int index);
    
    public int getIndex(V v);
    
    public java.util.List<Integer> getNeighbors(int index);
    
    public int getDegree(int v);
    
    public void printEdges();
    
    public void clear();
    
    public void addVertex(V vertex);
    
    public void removeVertex(V vertex);
    
    public void addEdge(int u, int v);
    
    public void removeEdge(int u, int v);
     
    public AbstractGraph<V>.Tree bfs(int v);
}
