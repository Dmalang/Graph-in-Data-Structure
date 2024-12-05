import java.util.*;

class Graph {
    private Map<String, List<String>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addVertex(String vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(String vertex1, String vertex2) {
        adjacencyList.get(vertex1).add(vertex2);
        adjacencyList.get(vertex2).add(vertex1);
    }

    public List<String> dfs(String start) {
        Set<String> visited = new HashSet<>();
        List<String> result = new ArrayList<>();
        dfsRecursive(start, visited, result);
        return result;
    }

    private void dfsRecursive(String vertex, Set<String> visited, List<String> result) {
        visited.add(vertex);
        result.add(vertex);
        for (String neighbor : adjacencyList.get(vertex)) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited, result);
            }
        }
    }

    public List<String> bfs(String start) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        List<String> result = new ArrayList<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            result.add(current);
            for (String neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        for (String vertex : new String[]{"A", "B", "C", "D", "E"}) {
            graph.addVertex(vertex);
        }
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "E");
        graph.addEdge("D", "E");
        System.out.println("DFS Traversal: " + graph.dfs("A"));
        System.out.println("BFS Traversal: " + graph.bfs("A"));
    }
}
