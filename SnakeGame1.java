package snakegame1;

import javax.swing.*;



public class SnakeGame1 extends JFrame{
      
    SnakeGame1(){
        super("Snake Game");
        
        add(new Board());
        pack();
        
        setLocationRelativeTo(null); // center the window on the screen
        setResizable(false);
    }
    
    public static void main(String[] args) {
        new SnakeGame1().setVisible(true);
    }
    
}
