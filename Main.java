
// Name :- Udula Jayasekara
// UoW ID :- w2054021
// IIT ID :- 20222314
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // Input file path
            String filePath = "cwInputs.txt";

            // Check if file exists
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Error: Input file not found: " + filePath);
                System.out.println("Please create the input file with the following format:");
                System.out.println("First line: number of nodes (n)");
                System.out.println("Following lines: from to capacity");
                return;
            }
            System.out.println("The file used :" + filePath);

            // Read graph from file
            Graph graph = InputReader.readGraphFromFile(filePath);

            // Create solver
            MaxFlowSolver solver = new MaxFlowSolver(graph);

            // Source is node 0, sink is node n-1
            int source = 0;
            int sink = graph.getNumNodes() - 1;

            // Calculate max flow with logging disabled for large graphs
            solver.edmondsKarp(source, sink, true);

            solver.printFlows();

        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find input file - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: Could not read input file - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}