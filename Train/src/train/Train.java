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
    
    private static final Map<String, List<String>> graph = new HashMap<>();

    public static void main(String[] args) {
       
        Scanner sc = new Scanner(System.in);
        
        //load graph
        
        while(true){
            System.out.println("---------------------------------");
            System.out.println("Welcome to LRT & MRT Route System");
            System.out.println("---------------------------------");
            System.out.println("1. Create Graph");
            System.out.println("2. Search for a Route");
            System.out.println("3. View the Transit Network (Console)");
            System.out.println("4. View LRT & MRT Graph");
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
                    break;
                case "3":
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
        
        sc.close();
    }
    
    private static void graphMenu(Scanner sc){
        
        while(true){
            System.out.println("1. Add a vertex (Station)");
            System.out.println("2. Remove a vertex (Station)");
            System.out.println("3. Add an edge (Connection)");
            System.out.println("4. Remove an edge (Connection)");
            System.out.println("0. Return");
            System.out.println("-------------------------------");
            System.out.print("Enter your choice: ");
        
            String choice = sc.nextLine();
            
            if(choice.equals("0")){
                break;
            }
            
            switch(choice){
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }
    
    private static void addVertex(String station){
        if(!graph.containsKey(station)){
            graph.put(station, new ArrayList<>());
            System.out.println("Station '" + station + "' added.");
        }else{
            System.out.println("Station already exists.");
        }
    }
    
    private static void removeVertex(String station){
        if(!graph.containsKey(station)){
            graph.values().forEach(list -> list.remove(station));
            graph.remove(station);
            System.out.println("Station '" + station + "' removed.");
        }else{
            System.out.println("Station not found.");
        }
    }
    
    private static void addEdge(String s1, String s2){
        if(!graph.containsKey(s1)) addVertex(s1);
        if(!graph.containsKey(s2)) addVertex(s2);
        
        if(!graph.get(s1).contains(s2)){
            graph.get(s1).add(s2);
            graph.get(s2).add(s1);
            System.out.println("There is now a connection between " + s1 + " and " + s2 + ".");
        }else{
            System.out.println("Connection already exists.");
        }
        
    }
    
    private static void removeEdge(String s1, String s2){
        if(graph.containsKey(s1) && graph.containsKey(s2)){
            graph.get(s1).remove(s2);
            graph.get(s2).remove(s1);
            System.out.println("Connection removed between " + s1 + " and " + s2 + ".");
        }else{
            System.out.println("One or both stations not found.");
        }
    }
    
    private static void searchRoute(Scanner sc){
        System.out.print("Enter Starting Station: ");
        String start = sc.nextLine().trim();
        
        System.out.print("Ending Station: ");
        String end = sc.nextLine().trim();
        
        if(!graph.containsKey(start) || !graph.containsKey(end)){
            System.out.println("Invalid stations. Please check the network.");
            return;
        }
        
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> parentMap = new HashMap<>();
        
        queue.add(start);
        visited.add(start);
        parentMap.put(start,null);
        
        boolean found = false;
        
        while(!queue.isEmpty()){
            String currentStation = queue.poll();
            
            if(currentStation.equalsIgnoreCase(end)){
                found = true;
                break;
            }
            
            for(String neighbour : graph.get(currentStation)){
                if(!visited.contains(neighbour)){
                    visited.add(neighbour);
                    parentMap.put(neighbour, currentStation);
                    queue.add(neighbour);
                }
            }
        }
        
        if(found){
            List<String> route = constructRoute(parentMap, end);
            System.out.println("\nRoute found! (" + (route.size() -1) + " stops): ");
            System.out.println(String.join("->", route));
        }else{
            System.out.println("\n No route found between " + start + " and " + end);
        }
    }
    
    private static List<String> constructRoute(Map<String, String> parent, String endStation){
        List<String> route = new ArrayList<>();
        String current = endStation;
        
        while(current != null){
            route.add(0, current);
            current = parent.get(current);
        }
        
        return route;
    }
    
    private static void loadGraphData(){
        
        addEdge("Ampang Park", "KLCC");
    }
}


