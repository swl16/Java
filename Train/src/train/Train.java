/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package train;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Train {
    
    private static UnweightedGraph<Station> graph;
    private static List<Station> stations = new ArrayList<>();
    private static List<AbstractGraph.Edge> edges = new ArrayList<>();

    public static void main(String[] args) {
       
        Scanner sc = new Scanner(System.in);
        
        //load graph
        loadGraphData();
        
        while(true){
            System.out.println("---------------------------------");
            System.out.println("Welcome to LRT & MRT Route System");
            System.out.println("---------------------------------");
            System.out.println("1. Create Graph");
            System.out.println("2. Search for a Route");
            System.out.println("3. View LRT & MRT Graph");
            System.out.println("0. Exit");
            System.out.println("---------------------------------");
            System.out.print("Enter your choice: ");
            
            String choice = sc.nextLine();
            
            if(choice.equals("0")){
                System.out.println("Exiting system. Goodbye!");
                break;
            }
            
            switch(choice){
                case "1":
                    graphMenu(sc);
                    break;
                case "2":
                    searchRoute(sc);
                    break;
                case "3":
                    SwingUtilities.invokeLater(Train::showVisualMap);
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
        
        sc.close();
    }
    
    private static void graphMenu(Scanner sc){
        
        while(true){
            System.out.println("1. Add a Station");
            System.out.println("2. Remove a Station");
            System.out.println("3. Add a Connection");
            System.out.println("4. Remove a Connection");
            System.out.println("0. Return");
            System.out.println("-------------------------------");
            System.out.print("Enter your choice: ");
        
            String choice = sc.nextLine();
            
            if(choice.equals("0")){
                break;
            }
            
            switch(choice){
                case "1":
                    System.out.print("Enter station name: ");
                    String name = sc.nextLine().trim();
                    Station newStation = new Station(name, (int)(Math.random()*600 + 50), (int)(Math.random()*300 + 50), Color.GRAY, false);
                    graph.addVertex(newStation); 
                    System.out.println("Added.");
                    break;
                    
                case "2":
                    System.out.print("Enter station name: ");
                    String Name = sc.nextLine().trim();
                    Station s = getStationByName(Name);
                    if(s != null){
                        graph.removeVertex(s); 
                        System.out.println("Station '" + Name + "' removed.");
                    }else{
                        System.out.println("Station not found.");
                    }
                    break;
                    
                case "3":
                    System.out.print("Enter Start Index: ");
                    int u = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Enter End Index: ");
                    int v = Integer.parseInt(sc.nextLine().trim());
                    graph.addEdge(u, v);
                    System.out.println("Connection added.");
                    break;
                    
                case "4":
                    System.out.print("Enter Start Index: ");
                    int U = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Enter End Index: ");
                    int V = Integer.parseInt(sc.nextLine().trim());
                    graph.removeEdge(U, V);
                    graph.removeEdge(V, U);
                    System.out.println("Connection removed.");
                    break;
                    
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }
    
    
    
    private static void searchRoute(Scanner sc){
        System.out.print("Enter Starting Station: ");
        String start = sc.nextLine().trim();
        
        System.out.print("Ending Station: ");
        String end = sc.nextLine().trim();
        
        Station startStation = getStationByName(start);
        Station endStation = getStationByName(end);
        
        
        if(startStation != null && endStation != null){
            int startIndex = graph.getIndex(startStation);
            int endIndex = graph.getIndex(endStation);
            
            AbstractGraph<Station>.Tree bfsTree = graph.bfs(startIndex);
            List<Station> path = bfsTree.getPath(endIndex);
            
            System.out.print("Shortest Route: ");
            for (int i = path.size() - 1; i >= 0; i--) {
                System.out.print(path.get(i).getName() + (i > 0 ? " -> " : "\n"));
            }
            SwingUtilities.invokeLater(() -> showHighlightedMap(path));
        } else {
            System.out.println("Station(s) not found.");
        }
    }
    
    private static void showHighlightedMap(List<Station> path) {
        JFrame frame = new JFrame("Shortest Route Found");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new GraphView(graph, path));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static void showVisualMap() {
        JFrame frame = new JFrame("LRT & MRT Route Network");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new GraphView(graph));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static void loadGraphData(){
        Color lrtRed = new Color(228, 56, 52);
        Color mrtGreen = new Color(46, 161, 79);
        Color mrtYellow = new Color(237, 193, 37);
        
        stations.add(new Station("Ampang Park", 100, 100, Color.WHITE, true)); //0
        stations.add(new Station("KLCC", 200, 100, lrtRed, false)); // 1
        stations.add(new Station("Kampung Baru", 300, 100,lrtRed, false)); // 2
        stations.add(new Station("Dang Wangi", 400, 100, lrtRed, false)); // 3
        stations.add(new Station("Masjid Jamek", 500, 100, lrtRed, false)); // 4
        
        stations.add(new Station("Pasar Seni", 600, 200, Color.WHITE, true)); // 5
        stations.add(new Station("Merdeka", 500, 250, mrtGreen, false)); // 6
        stations.add(new Station("Bukit Bintang", 400, 300, mrtGreen, false)); // 7
        
        stations.add(new Station("TRX", 300, 350, Color.WHITE, true)); // 8
        
        stations.add(new Station("Persiaran KLCC", 150, 200, mrtYellow, false)); // 9
        stations.add(new Station("Conlay", 220, 280,  mrtYellow, false)); // 10
        stations.add(new Station("Chan Sow Lin", 200, 420,  mrtYellow, false)); // 11
        
        stations.add(new Station("Cochrane", 400, 420,  mrtGreen, false)); // 12
        
        int[][] edgeArray ={
            {0,1},{1,2},{2,3},{3,4},
            {4,5},{5,6},{6,7},{7,8},
            {0,9},{9,10},{10,8},{8,11},
            {8,12}
        };
        
        for(int[] e: edgeArray){
            edges.add(new AbstractGraph.Edge(e[0],e[1]));
            edges.add(new AbstractGraph.Edge(e[1],e[0]));
        }
        
        graph = new UnweightedGraph<>(stations, edges);
    }
    
    private static Station getStationByName(String name) {
        for (Station s : graph.getVertices()) { 
            if (s.getName().equalsIgnoreCase(name)) return s;
        }
        return null;
    }
}


class Station implements Displayable{
    
    private final String name;
    private final int x,y;
    
    private final Color color;
    private final boolean isInterchange;
    
    public Station(String name, int x, int y, Color color, boolean isInterchange){
        this.name = name;
        this.x = x;
        this.y = y;
        this.color = color;
        this.isInterchange = isInterchange;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public Color getColor() {
        return color;
    }
    
    public boolean isInterchange() { 
        return isInterchange; 
    }
    
    @Override
    public String toString() {
        return name;
    }

}
