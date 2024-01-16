package fr.iutfbleau.SAE;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.event.MouseAdapter;
import java.sql.*;

public class VueJTree extends JFrame {
    private JTree arbre;
    private String userStory;

    public VueJTree(String userStory, JTree arbre ) {
        this.userStory = userStory;
	this.arbre = arbre;
	
        this.setSize(1600, 900);
        this.setLocation(0, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
	
        JPanel panelJTree = new JPanel();
        JPanel panelUserStory = new JPanel();
        JLabel description = new JLabel(userStory);
	
        panelUserStory.add(description);
        panelJTree.add(arbre);
        this.add(panelJTree, BorderLayout.CENTER);
        this.add(panelUserStory, BorderLayout.NORTH);
        
    }
}
