import java.util.*;

public class CityNetworkNavigator {
    // Graph data structure
    static class Graph {
        private final Map<String, Map<String, Integer>> adjList = new HashMap<>();

        // Add a city
        public void addCity(String city) {
            adjList.putIfAbsent(city, new HashMap<>());
        }

        // Add a road (edge) with a distance (weight)
        public void addRoad(String city1, String city2, int distance) {
            adjList.get(city1).put(city2, distance);
            adjList.get(city2).put(city1, distance); // Assuming undirected graph
        }

        // Display all cities and their connections
        public void displayCities() {
            for (String city : adjList.keySet()) {
                System.out.println(city + " is connected to: " + adjList.get(city));
            }
        }

        // Find the shortest path using Dijkstra's Algorithm
        public void findShortestPath(String start, String end) {
            Map<String, Integer> distances = new HashMap<>();
            Map<String, String> previous = new HashMap<>();
            PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

            for (String city : adjList.keySet()) {
                distances.put(city, Integer.MAX_VALUE);
            }
            distances.put(start, 0);
            pq.add(Map.entry(start, 0));

            while (!pq.isEmpty()) {
                String current = pq.poll().getKey();

                if (current.equals(end)) break;

                for (Map.Entry<String, Integer> neighbor : adjList.get(current).entrySet()) {
                    String nextCity = neighbor.getKey();
                    int newDist = distances.get(current) + neighbor.getValue();

                    if (newDist < distances.get(nextCity)) {
                        distances.put(nextCity, newDist);
                        previous.put(nextCity, current);
                        pq.add(Map.entry(nextCity, newDist));
                    }
                }
            }

            printPath(start, end, distances, previous);
        }

        private void printPath(String start, String end, Map<String, Integer> distances, Map<String, String> previous) {
            if (distances.get(end) == Integer.MAX_VALUE) {
                System.out.println("No path found from " + start + " to " + end);
                return;
            }

            List<String> path = new ArrayList<>();
            for (String at = end; at != null; at = previous.get(at)) {
                path.add(at);
            }
            Collections.reverse(path);

            System.out.println("Shortest path from " + start + " to " + end + ": " + path);
            System.out.println("Total distance: " + distances.get(end));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph cityGraph = new Graph();

        while (true) {
            System.out.println("\n--- City Network Navigator ---");
            System.out.println("1. Add City");
            System.out.println("2. Add Road");
            System.out.println("3. Display Cities");
            System.out.println("4. Find Shortest Path");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter city name: ");
                    String city = scanner.next();
                    cityGraph.addCity(city);
                    System.out.println(city + " added!");
                    break;
                case 2:
                    System.out.print("Enter first city: ");
                    String city1 = scanner.next();
                    System.out.print("Enter second city: ");
                    String city2 = scanner.next();
                    System.out.print("Enter distance: ");
                    int distance = scanner.nextInt();
                    cityGraph.addRoad(city1, city2, distance);
                    System.out.println("Road added between " + city1 + " and " + city2 + "!");
                    break;
                case 3:
                    cityGraph.displayCities();
                    break;
                case 4:
                    System.out.print("Enter start city: ");
                    String start = scanner.next();
                    System.out.print("Enter end city: ");
                    String end = scanner.next();
                    cityGraph.findShortestPath(start, end);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
