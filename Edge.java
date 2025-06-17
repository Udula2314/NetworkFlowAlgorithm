// Name :- Udula Jayasekara
// UoW ID :- w2054021
// IIT ID :- 20222314

public class Edge {
    private final int from; // Start node of the edge
    private final int to; // End node of the edge
    private final int capacity; // Maximum capacity of the edge
    private int flow; // Current flow through the edge
    private Edge residual; // Residual edge for this edge

    // Constructor for creating a new edge with validation
    public Edge(int from, int to, int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative.");
        }
        if (from == to) {
            throw new IllegalArgumentException("Self-loops are not allowed.");
        }

        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = 0;
    }

    // Getters for edge properties
    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public int capacity() {
        return capacity;
    }

    public int flow() {
        return flow;
    }

    public Edge getResidual() {
        return residual;
    }

    public void setResidual(Edge residual) {
        this.residual = residual;
    }

    // Get how much more flow this edge can handle
    public int remainingCapacity() {
        return capacity - flow;
    }

    // Add flow to this edge and update the residual edge
    public void augment(int bottleneck) {
        if (bottleneck < 0) {
            throw new IllegalArgumentException("Bottleneck must be non-negative.");
        }
        if (bottleneck > remainingCapacity()) {
            throw new IllegalArgumentException("Bottleneck exceeds remaining capacity.");
        }

        flow += bottleneck;
        residual.flow -= bottleneck;
    }

    // String representation of the edge
    @Override
    public String toString() {
        return String.format("Edge %d → %d | Flow = %d / %d", from, to, flow, capacity);
    }
}
