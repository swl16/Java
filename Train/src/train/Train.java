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
            System.out.println("Enter your choice: ");
            
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
            System.out.println("Enter your choice: ");
        
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
}


