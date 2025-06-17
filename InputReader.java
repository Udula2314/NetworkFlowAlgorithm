// Name :- Udula Jayasekara
// UoW ID :- w2054021
// IIT ID :- 20222314

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputReader {
    // This method reads the graph from a file
    public static Graph readGraphFromFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        // First line contains the number of nodes in the graph
        int n = Integer.parseInt(br.readLine().trim());
        Graph graph = new Graph(n);

        String line;
        // Read edges from the file
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length < 3) {
                // Skip invalid lines
                System.out.println("Warning: Skipping invalid line: " + line);
                continue;
            }

            try {
                // Parse the edge details
                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                int capacity = Integer.parseInt(parts[2]);

                // Check if the nodes are valid
                if (from < 0 || from >= n || to < 0 || to >= n) {
                    System.out.println("Warning: Invalid node index in line: " + line);
                    continue;
                }

                // Add the edge to the graph
                graph.addEdge(from, to, capacity);
            } catch (NumberFormatException e) {
                // Handle invalid numbers
                System.out.println("Warning: Invalid number format in line: " + line);
            }
        }

        br.close();
        return graph;
    }
}