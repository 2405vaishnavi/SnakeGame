package snakegame;

import javax.swing.*;

/**
 *
 * -author 91799
 */
public class SnakeGame extends JFrame {

  
       SnakeGame(){
        super("Snake Game ");
        Board board= new Board();                  // call Board class
        add(board);
        
        pack();                                                        // used for refresh the frame
        
        setSize(500,500);
        setLocationRelativeTo(null);                // align a frame to center according to window
        setVisible(true);
    }
 
    public static void main(String[] args) {
        SnakeGame snake = new SnakeGame();    // call default constructor 
        
    }
    
}
