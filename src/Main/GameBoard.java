
package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class GameBoard {
    
    private int x[] = new int[600];
    private int y[] = new int[600];
    private int linkSize = 20;
    
    private Image link;
    private Image food;
    private Image head;
    
    private int links;
    private int xFood;
    private int yFood;
    
    private int upKey, rightKey, downKey, leftKey;
    private boolean up, right, down, left;
    private boolean gameStatus = true;
    
    public GameBoard() {
        initGameBoard();
    }
    
    public void initGameBoard() {
        loadImages();
        upKey = KeyEvent.VK_W;
        rightKey = KeyEvent.VK_D;
        downKey = KeyEvent.VK_S;
        leftKey = KeyEvent.VK_A;
        links = 3;
        start();
    }
    
    public void start() {
        links = 2;
        for (int i = 0; i < links; i++) {
            x[i] = 100 - i * 10;
            y[i] = 100;
        }
        generateFoodLocation();  
    }
    
    private void loadImages() {
        ImageIcon imageLink = new ImageIcon("src\\resources\\link.png");
        link = imageLink.getImage();
        ImageIcon imageFood = new ImageIcon("src\\resources\\food-block.png");
        food = imageFood.getImage();
        ImageIcon imageHead = new ImageIcon("src\\resources\\master-link.png");
        head = imageHead.getImage();
    }
    
    private void generateFoodLocation() {
        int r = (int)(Math.random() * (GameCanvas.WIDTH / linkSize));
        xFood = ((r * linkSize));

        r = (int)(Math.random() * (GameCanvas.WIDTH / linkSize));
        yFood = ((r * linkSize));
    }

    private void checkFood() {
        if ((x[0] == xFood) && (y[0] == yFood)) {
            links++;
            generateFoodLocation();
        }
    }
    
    public void move() {
        for (int i = links; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }
        if (up)    { y[0] -= linkSize; }
        if (right) { x[0] += linkSize; }
        if (down)  { y[0] += linkSize; }
        if (left)  { x[0] -= linkSize; }
    }
    
    private void checkCollision() {
        
        for (int i = links; i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                gameStatus = false;
            }
        }
        
        if (y[0] >= GameCanvas.HEIGHT) { gameStatus = false; }
       
        if (y[0] < 0) { gameStatus = false; }
        
        if (x[0] >= GameCanvas.WIDTH) { gameStatus = false; }
        
        if (x[0] < 0) { gameStatus = false; }
        
        if (!gameStatus) {  }
    }
    
    // TICK
    public void update() {
        if (gameStatus) {
            checkFood();
            move();
            checkCollision();
        } else {
            try {
                Thread.sleep(5000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
    
    // UPDATE
    public void paint(Graphics g) {}
    
    // PAINT
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameCanvas.WIDTH, GameCanvas.HEIGHT);
        
        if (gameStatus) {
            g.drawImage(food, xFood, yFood, null);
            for (int i = 0; i < links; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], null);
                } else {
                    g.drawImage(link, x[i], y[i], null);
                }
            }
        } else {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 36));
            g.drawString("Game Over", GameCanvas.WIDTH / 3, GameCanvas.HEIGHT / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Score: " + Integer.toString(links), (GameCanvas.WIDTH / 3) + 50, (GameCanvas.HEIGHT / 2) + 100);
        }
    }
    
    public void keyPressed(KeyEvent k) {
        int key = k.getKeyCode();
        if(key == upKey && !down) {
            up = true;
            right = false;
            down = false;
            left = false;
        }
        if(key == rightKey && !left) {
            right = true;
            down = false;
            left = false;
            up = false;
        }
        if(key == downKey && !up) {
            down = true;
            left = false;
            up = false;
            right = false;
        }
        if(key == leftKey && !right) {
            left = true;
            up = false;
            right = false;
            down = false;
        }
    }

    
}
