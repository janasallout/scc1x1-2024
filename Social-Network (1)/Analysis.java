import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Analysis {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Analysis <inputFile>");
            return;
        }

        String filename = args[0];

        Graph network = new Graph();

        try {
            File file = new File(filename);

            // add persons to the network
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) break;

                String[] edges = line.split(" ");
                for (String e : edges) {
                    network.addPerson(e);
                }
            }
            scanner.close();


            // Initialize the graph
            network.init();


            // add edges to the network
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) break;

                String[] edges = line.split(" ");
                String u = edges[0];

                for (String v : edges) {
                    network.addEdge(u, v);
                }
            }
            scanner.close();


            // Print the adjacency list representation of the follwers network
            System.out.println("\nNetwork:\n");
            network.printNetwork();


            // Part-1: Calculate the density of the network
            double density = network.getDensity();
            System.out.println("Part-1: Density of the Network: " + density);


            // Part-2: Find the person who have highest number of followers
            String person = network.getMostFollowers();
            System.out.println("\nPart-2: Person with most followers: " + person);


            // Part-3: Find the person who have highest number of follows
            person = network.getMostFollows();
            System.out.println("\nPart-3: Person with most follows: " + person);


            // Part-4: Find the number of persons that are at two degrees of separation from the first person
            person = network.getFirstPerson();
            System.out.println("\nFirst person in the graph: " + person);

            int num = network.NoOfTwoDegreeSeperation(person);
            System.out.println("Part-4: Number of persons that are at two degrees of separation from the first person: " + num);


            // Part=5: Find the median value for the number of followers in the network
            double median = network.getMedianOfFollowers();
            System.out.println("\nPart-5: Median value for the number of followers in the network: " + median);


            // Part-6: Find the best person for advertisement
            System.out.println("\nPart-6: Finding Best person for advertisement:");
            person = network.getBestAdvertiser();
            System.out.println("\nBest person for advertisement: " + person);
            System.out.println();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }
}