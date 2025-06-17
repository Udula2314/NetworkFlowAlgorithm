// Name :- Udula Jayasekara
// UoW ID :- w2054021
// IIT ID :- 20222314

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int n; // Number of nodes
    private final List<Edge>[] adjList; // Adjacency list representation
    private int edgeCount = 0; // Cache the number of edges

    @SuppressWarnings("unchecked")
    public Graph(int n) {
        if (n < 2) {
            throw new IllegalArgumentException("Graph must contain at least 2 nodes.");
        }

        this.n = n;
        adjList = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    public void addEdge(int from, int to, int capacity) {
        validateVertex(from);
        validateVertex(to);

        if (from == to) {
            throw new IllegalArgumentException("Self-loops are not allowed.");
        }

        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative.");
        }

        Edge e1 = new Edge(from, to, capacity);
        Edge e2 = new Edge(to, from, 0);

        e1.setResidual(e2);
        e2.setResidual(e1);

        adjList[from].add(e1);
        adjList[to].add(e2);

        edgeCount++; // Increment edge count
    }

    public int getNumNodes() {
        return n;
    }

    public List<Edge>[] getAdjList() {
        return adjList;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= n) {
            throw new IllegalArgumentException("Invalid node index: " + v);
        }
    }

    public int getNumEdges() {
        return edgeCount; // Return cached value
    }
}
