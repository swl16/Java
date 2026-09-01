/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package train;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GraphView extends JPanel{
    private static final int VERTEX_RADIUS = 25;
    private final Graph<? extends Displayable> graph;
    private List<? extends Displayable> highlightedPath; // Stores the route to highlight
    
    public GraphView(Graph<? extends Displayable> graph) {
        this.graph = graph;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(950, 800));
    }
    
    // Constructor that accepts a specific route to highlight
    public GraphView(Graph<? extends Displayable> graph, List<? extends Displayable> highlightedPath) {
        this.graph = graph;
        this.highlightedPath = highlightedPath;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(950, 800));
    }
    
    private boolean isEdgeInPath(Station s1, Station s2) {
        if (highlightedPath == null) return false;
        for (int i = 0; i < highlightedPath.size() - 1; i++) {
            Station p1 = (Station) highlightedPath.get(i);
            Station p2 = (Station) highlightedPath.get(i + 1);
            if ((p1.equals(s1) && p2.equals(s2)) || (p1.equals(s2) && p2.equals(s1))) {
                return true;
            }
        }
        return false;
    }

    private boolean isNodeInPath(Station s) {
        if (highlightedPath == null) return true; // If no path, show all nodes fully
        return highlightedPath.contains(s);
    }
    
    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // 1. Draw Edges
            g.setStroke(new BasicStroke(6)); // Thick connecting lines
            for (int i = 0; i < graph.getSize(); i++) {
                Station from = (Station) graph.getVertex(i);
                for (int neighbor : graph.getNeighbors(i)) {
                    Station to = (Station) graph.getVertex(neighbor);
                    
                    // Determine line color based on connecting stations
                    if (from.getColor().equals(to.getColor()) && !from.isInterchange()) {
                        g.setColor(from.getColor());
                    } else if (!from.isInterchange() && to.isInterchange()) {
                        g.setColor(from.getColor());
                    } else {
                        g.setColor(to.getColor());
                    }
                    
                    if (highlightedPath != null && isEdgeInPath(from, to)) {
                        g.setStroke(new BasicStroke(8));
                        g.setColor(new Color(30, 144, 255)); // Bright Blue for active route
                    } else {
                        g.setStroke(new BasicStroke(6));
                        Color edgeColor = (from.getColor().equals(to.getColor()) && !from.isInterchange()) 
                            ? from.getColor() : (to.isInterchange() ? from.getColor() : to.getColor());
                        
                        // Dim edges not in the active route
                        if (highlightedPath != null) {
                            g.setColor(new Color(230, 230, 230)); 
                        } else {
                            g.setColor(edgeColor);
                        }
                    }
                    
                    g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
                }
            }

            // 2. Draw Vertices
            FontMetrics metrics = g.getFontMetrics();
            for (int i = 0; i < graph.getSize(); i++) {
                Station vertex = (Station) graph.getVertex(i);
                int x = vertex.getX();
                int y = vertex.getY();
                
                boolean active = isNodeInPath(vertex);
                
                // Draw filled circle
                g.setColor(active ? vertex.getColor() : new Color(230, 230, 230));
                g.fillOval(x - VERTEX_RADIUS, y - VERTEX_RADIUS, VERTEX_RADIUS * 2, VERTEX_RADIUS * 2);
                
                // Draw border
                g.setColor(active ? Color.BLACK : new Color(200, 200, 200));
                g.setStroke(new BasicStroke(vertex.isInterchange() ? 5 : 1));
                g.drawOval(x - VERTEX_RADIUS, y - VERTEX_RADIUS, VERTEX_RADIUS * 2, VERTEX_RADIUS * 2);
                
                //Draw Text
                g.setColor(vertex.isInterchange() && active ? Color.BLACK : (active ? Color.WHITE : new Color(150, 150, 150)));
                String[] nameLines = vertex.getName().split(" ");
                int textY = y - (nameLines.length > 1 ? 5 : -5);
                for (String line : nameLines) {
                    g.drawString(line, x - metrics.stringWidth(line) / 2, textY);
                    textY += 15;
                }
            }

            // 3. Draw Legend
            drawLegend(g);

        } finally {
            g.dispose();
        }
    }

    private void drawLegend(Graphics2D g) {
        int startX = 750;
        int startY = 50;
        
        g.setStroke(new BasicStroke(1));
        
        // LRT Kelana Jaya
        g.setColor(new Color(228, 56, 52));
        g.fillOval(startX, startY, 20, 20);
        g.setColor(Color.BLACK);
        g.drawString("LRT Kelana Jaya", startX + 35, startY + 15);
        
        // MRT Kajang
        g.setColor(new Color(46, 161, 79));
        g.fillOval(startX, startY + 40, 20, 20);
        g.setColor(Color.BLACK);
        g.drawString("MRT Kajang", startX + 35, startY + 55);
        
        // MRT Putrajaya
        g.setColor(new Color(237, 193, 37));
        g.fillOval(startX, startY + 80, 20, 20);
        g.setColor(Color.BLACK);
        g.drawString("MRT Putrajaya", startX + 35, startY + 95);
        
        // Interchange
        g.setColor(Color.WHITE);
        g.fillOval(startX, startY + 120, 20, 20);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));
        g.drawOval(startX, startY + 120, 20, 20);
        g.drawString("Interchange station", startX + 35, startY + 135);
        
        if (highlightedPath != null) {
            g.setColor(new Color(30, 144, 255));
            g.setStroke(new BasicStroke(4));
            g.drawLine(startX, startY + 170, startX + 20, startY + 170);
            g.setColor(Color.BLACK);
            g.drawString("Shortest Route", startX + 35, startY + 175);
        }
        
        // Footer Label
        g.drawString("Selected LRT and MRT stations in Klang Valley Graph", 300, 750);
    }
}

