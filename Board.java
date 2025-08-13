  package snakegame1;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public final class Board extends JPanel implements ActionListener{
    private int dots;
    private Timer time;
    private Image apple;
    private Image head;
    private Image dot;
    private int score;
    
    private int apple_X;
    private int apple_Y;
    
    private final int x[] = new int[900];
    private final int y[] = new int[900];
    private final int RandomPosition = 29;
    private boolean inGame = true;
    
    private final int dot_size = 10;
    
    private boolean leftDirection = false;
    private boolean rightDirection = true; //to move only on right direction
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    Board(){
        addKeyListener(new TAdapter());
        
        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.BLACK);
        setFocusable(true);
        
        initGame();
        loadImage();
    }
    public void loadImage() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame1/icons/apple.png"));
        apple = i1.getImage();
        
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame1/icons/dot.png"));
        dot = i2.getImage();
        
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame1/icons/head.png"));
        head = i3.getImage();
    }
    public void initGame(){
        dots = 3;
        for(int i=0; i<dots; i++){
            y[i] = 50;
            x[i] = 50 - i * dot_size;
        }
        locateApple();
        score = 0;
        
        time = new Timer(180, this);
        time.start();
    }
    
    public void locateApple(){
        int r = (int)(Math.random()*RandomPosition);
        apple_X = r*dot_size;
        r = (int)(Math.random()*RandomPosition);
        apple_Y = r*dot_size;
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple, apple_X, apple_Y, this);
            for( int i=0; i<dots; i++){
                if(i==0){
                    g.drawImage(head, x[i], y[i], this);
                }else{
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            
            String scoreMSG = "Score: " + score;
            Font scorefont = new Font("SAN_SERIF", Font.BOLD, 12);
            g.setColor(Color.WHITE);
            g.setFont(scorefont);
            g.drawString(scoreMSG, 5, 15);
        
            Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }      
    }
    
    public void gameOver(Graphics g){
        String msg = "Game Over !!!";
        String score_msg = "Score: " + score;
        
        Font font1 = new Font("SAN_SERIF", Font.BOLD, 14);
        Font scoreFont = new Font("SAN_SERIF", Font.BOLD, 14);
        
        FontMetrics metrices = getFontMetrics(font1);
        FontMetrics scoremetrices = getFontMetrics(scoreFont);
        
        g.setColor(Color.WHITE);
        g.setFont(font1);
        g.drawString(msg, (300 - metrices.stringWidth(msg))/ 2, 300/2);
        
        g.setFont(scoreFont);
        g.drawString(score_msg, (300 - scoremetrices.stringWidth(score_msg))/ 2, 300/2 +15);
    }
    
    public void move(){
        for(int i=dots; i>0; i-- ){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDirection){
            x[0] = x[0] - dot_size;
        }
        if(rightDirection){
            x[0] = x[0] + dot_size;
        }
        if(upDirection){
            y[0] = y[0] - dot_size;
        }
        if(downDirection){
            y[0] = y[0] + dot_size;
        }
       
    }
    
    public void checkApple(){
        if((x[0] == apple_X) && (y[0] == apple_Y)){
            dots++;
            score += 5;
            locateApple();
        }
    }
    
    public void checkCoilision(){
        for(int i = dots; i>0; i--){
            if((i>4) && (x[0] == x[i]) && (y[0] == y[i])){
                inGame = false;
            }
        }
        if(y[0] >= 300){
            inGame = false;
        }
        if(x[0] >= 300){
            inGame = false;
        }
        if(y[0] < 0){
            inGame = false;
        }
        if(x[0] < 0){
            inGame = false;
        }
        if(!inGame){
            time.stop();
        }
    }
    
    public void actionPerformed(ActionEvent ae){
        if(inGame){
            checkApple();
            checkCoilision();
            move();
        }
        
        repaint();
    }
    
    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if(key == KeyEvent.VK_UP && (!downDirection)){
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            
            if(key == KeyEvent.VK_DOWN && (!upDirection)){
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }
        }
    }

    public static void main(String args[]){
        SnakeGame1 snakegame = new SnakeGame1();
    }
}
