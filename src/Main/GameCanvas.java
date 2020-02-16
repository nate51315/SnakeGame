
package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class GameCanvas extends JPanel implements Runnable, KeyListener {
    
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;    
    public static Dimension size = new Dimension(WIDTH, HEIGHT);
    public boolean isRunning = false;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;
    private GameBoard board;

    public GameCanvas() {
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        start();
    }
   
    public void start() {
        addKeyListener(this);
        setFocusable(true);
        isRunning = true;
        new Thread(this, "Game Loop").start();
    }

    public void stop() {
        isRunning = false;
        System.exit(0);
    }

    @Override
    public void run() {
        
        long start;
        long elapsed;
        long wait;
        
        board = new GameBoard();
        
        while(isRunning) {
            start = System.nanoTime();
            
            tick();
            repaint();
            
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed;
            if(wait < 0) wait = 85; // 85
            
            // sleeps amount of time to keep FPS consistent
            try {
                Thread.sleep(wait);
            } catch(Exception e) {
                e.printStackTrace();
            }
            
        }
        
    }
    
    public void update(Graphics g) {
        paint(g);
    }
    
    public void tick() {
        board.update();
    }
    
    public void paint(Graphics g) {
        board.draw(g);
    }
    
    public void keyPressed(KeyEvent e) {
       board.keyPressed(e);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
 
    
}
