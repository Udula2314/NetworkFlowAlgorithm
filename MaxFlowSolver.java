// Name :- Udula Jayasekara
// UoW ID :- w2054021
// IIT ID :- 20222314

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class MaxFlowSolver {
    private final Graph graph;
    private int[] parent; // To track the path
    private Edge[] pathEdges; // Edges used in the path
    private boolean[] visited; // To track visited nodes

    public MaxFlowSolver(Graph graph) {
        this.graph = graph;
    }

    // BFS to find augmenting path
    private int bfs(int source, int sink) {
        Queue<Integer> queue = new LinkedList<>();
        visited = new boolean[graph.getNumNodes()];
        parent = new int[graph.getNumNodes()]; // Initialize parent array
        pathEdges = new Edge[graph.getNumNodes()];

        // Initialize visited and parent arrays
        Arrays.fill(parent, -1);
        visited[source] = true;

        // Start BFS from source
        queue.offer(source);

        // Track the bottleneck value along the path
        int[] bottleneck = new int[graph.getNumNodes()];
        Arrays.fill(bottleneck, Integer.MAX_VALUE);

        // BFS loop
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // Check all edges from current node
            for (Edge edge : graph.getAdjList()[current]) {
                int remainingCapacity = edge.remainingCapacity();
                if (!visited[edge.to()] && remainingCapacity > 0) {
                    visited[edge.to()] = true;
                    parent[edge.to()] = current; // Update parent array
                    pathEdges[edge.to()] = edge;

                    // Update bottleneck value
                    bottleneck[edge.to()] = Math.min(bottleneck[current], remainingCapacity);

                    // If we reached the sink, return bottleneck
                    if (edge.to() == sink) {
                        return bottleneck[sink];
                    }

                    queue.offer(edge.to());
                }
            }
        }

        // No augmenting path found
        return 0;
    }

    // Function to reconstruct the path from source to sink
    private List<Integer> reconstructPath(int source, int sink) {
        List<Integer> path = new ArrayList<>();

        if (parent[sink] == -1)
            return path; // No path exists

        // Start from sink and work backwards
        for (int at = sink; at != source; at = parent[at]) {
            path.add(at);
        }
        path.add(source); // Add the source

        // Reverse to get path from source to sink
        Collections.reverse(path);
        return path;
    }

    // Edmonds-Karp algorithm implementation with optional logging
    public int edmondsKarp(int source, int sink, boolean enableLogging) {
        int maxFlow = 0;
        int iteration = 1;

        if (enableLogging) {
            System.out.println("Running Edmonds-Karp algorithm...");
            System.out.println("Source: " + source + ", Sink: " + sink);
            System.out.println();
        }

        while (true) {
            // Find an augmenting path
            int bottleNeck = bfs(source, sink);

            // If no path found, we're done
            if (bottleNeck == 0) {
                if (enableLogging) {
                    System.out.println("No more augmenting paths found.");
                    System.out.println();
                }
                break;
            }

            // Reconstruct the path for display
            List<Integer> path = reconstructPath(source, sink);

            if (enableLogging) {
                System.out.println("Iteration " + iteration + ":");
                System.out.print("Path: ");
                for (int i = 0; i < path.size(); i++) {
                    System.out.print(path.get(i));
                    if (i < path.size() - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println();
                System.out.println("Bottleneck value: " + bottleNeck);
            }

            // Update flow along the path
            int node = sink;
            while (node != source) {
                Edge edge = pathEdges[node];
                edge.augment(bottleNeck);
                node = edge.from();
            }

            // Add bottleneck flow to max flow
            maxFlow += bottleNeck;

            if (enableLogging) {
                System.out.println("Current max flow: " + maxFlow);
                System.out.println();
            }

            iteration++;
        }

        if (enableLogging) {
            System.out.println("Maximum flow from source to sink: " + maxFlow);
            System.out.println();
        }

        return maxFlow;
    }

    // Method to display the final flow distribution
    public void printFlows() {
        System.out.println("Flow Details:");
        for (int i = 0; i < graph.getNumNodes(); i++) {
            for (Edge edge : graph.getAdjList()[i]) {
                if (edge.capacity() > 0) { // Only print forward edges
                    System.out.println("Edge " + edge.from() + " -> " + edge.to() +
                            ": Flow = " + edge.flow() + "/" + edge.capacity());
                }
            }
        }
    }
}