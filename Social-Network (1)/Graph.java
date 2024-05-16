import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;


public class Graph {
    private int V, E;
    private ArrayList<ArrayList<Integer>> follows, followers;
    
    private ArrayList<String> names;
    private HashMap<String, Integer> nameToIndex;


    // Constructor
    public Graph() {
        V = 0; E = 0;
        nameToIndex = new HashMap<String, Integer>();
        names = new ArrayList<String>();

        names.add(""); // 1-indexed
    }


    // Function to add a person to the graph
    void addPerson(String name) {
        if(nameToIndex.containsKey(name)) return;

        V++;
        nameToIndex.put(name, V);
        names.add(name);
    }


    // Function to initialize the graph
    public void init() {
        follows = new ArrayList<ArrayList<Integer>>(V+1);
        followers = new ArrayList<ArrayList<Integer>>(V+1);

        for (int i = 0; i <= V; ++i){
            follows.add(new ArrayList<Integer>());
            followers.add(new ArrayList<Integer>());
        }
    }


    // Function to add an edge into the graph
    public void addEdge(String u, String v) {
        if(u == v) return;

        int uIndex = nameToIndex.get(u);
        int vIndex = nameToIndex.get(v);
        
        follows.get(uIndex).add(vIndex);
        followers.get(vIndex).add(uIndex);
        E++;
    }


    // Function to print the adjacency list representation of graph
    public void printNetwork() {
        for (int i = 1; i <= V; ++i) {
            System.out.print(names.get(i) + " follows: ");
            for (Integer v : follows.get(i)) {
                System.out.print(names.get(v) + " ");
            }
            System.out.println();

            System.out.print("Followers of " + names.get(i) + ": ");
            for (Integer v : followers.get(i)) {
                System.out.print(names.get(v) + " ");
            }
            System.out.println();
            System.out.println();
        }
    }


    // Function to get the first person in the graph
    public String getFirstPerson(){
        return names.get(1);
    }


    // Function to perform DFS
    private int bfs(int st, boolean print) {
        int size = 1;
        
        Queue<Integer> queue = new LinkedList<Integer>();
        boolean[] visited = new boolean[V+1];

        System.out.println("\nSource: " + names.get(st));
        queue.add(st);
        visited[st] = true;

        while(!queue.isEmpty()) {
            int u = queue.poll();

            if(print) System.out.print(names.get(u) + " -> ");

            for(int v: followers.get(u)){
                if(!visited[v]){
                    queue.add(v);
                    visited[v] = true;
                    size++;

                    if(print) System.out.print(names.get(v) + ", ");
                }
            }

            if(print) System.out.println();
        }

        if(print){
            System.out.println("-----------------------------------");
            System.out.println("Reach: " + size);
            System.out.println("-----------------------------------");
        }

        return size;
    }


    // Part 1
    public double getDensity() {
        return (double) E / (V * (V - 1));
    }


    // Part 2
    public String getMostFollowers(){
        int max = 0;
        String name = "";
        for (int i = 1; i <= V; i++) {
            int numFollowers = followers.get(i).size();

            if (numFollowers > max) {
                max = numFollowers;
                name = names.get(i);
            } else if (numFollowers== max && name.compareTo(names.get(i)) > 0) {
                name = names.get(i);
            }
        }
        return name;
    }


    // Part 3
    public String getMostFollows(){
        int max = 0;
        String name = "";
        for (int i = 1; i <= V; i++) {
            int numFollows = follows.get(i).size();

            if (numFollows > max) {
                max = numFollows;
                name = names.get(i);
            } else if (numFollows == max && name.compareTo(names.get(i)) > 0) {
                name = names.get(i);
            }
        }
        return name;
    }


    // Part 4
    public Integer NoOfTwoDegreeSeperation(String name){
        int index = nameToIndex.get(name);
        ArrayList<Integer> oneDegreeFollowers = new ArrayList<Integer>();
        ArrayList<Integer> twoDegreeFollowers = new ArrayList<Integer>();

        for(int follower: followers.get(index)){
            oneDegreeFollowers.add(follower);
        }

        for(int oneDegreeFollower: oneDegreeFollowers){
            for(int follower: followers.get(oneDegreeFollower)){
                if(follower == index) continue;
                if(oneDegreeFollowers.contains(follower)) continue;
                if(twoDegreeFollowers.contains(follower)) continue;

                twoDegreeFollowers.add(follower);
            }
        }

        return twoDegreeFollowers.size();
    }


    // Part 5
    public Double getMedianOfFollowers(){
        ArrayList<Integer> followersCount = new ArrayList<Integer>();
        for(int i = 1; i <= V; i++){
            followersCount.add(followers.get(i).size());
        }

        followersCount.sort(null);

        if(V % 2 == 0){
            return (double) (followersCount.get(V/2) + followersCount.get(V/2 + 1)) / 2;
        } else {
            return (double) followersCount.get(V/2);
        }
    }


    // Part 6
    public String getBestAdvertiser() {
        int max = 0;
        String name = "";
        for (int i = 1; i <= V; i++) {
            int size = bfs(i, true);

            if (size > max) {
                max = size; 
                name = names.get(i);
            } else if (size == max && name.compareTo(names.get(i)) > 0) {
                name = names.get(i);
            }
        }
        
        return name;
    }
}
