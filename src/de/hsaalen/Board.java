package de.hsaalen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    public final int widthInPixels  = 300;
    public final int heightInPixels = 300;
    public final int tileSizeInPixels = 10;
    public final int refreshRateInMS= 140;
    public Snake snake;

    private int apple_x;
    private int apple_y;

    private Direction direction = Direction.right;
    private boolean inGame = true;

    private Timer timer;
    public Image ball;
    public Image apple;
    public Image head;

    public Board() {
        
        initBoard();
    }

    //nötig für Unittests
    public int getAppleX() {
        return apple_x;
    }

    //nötig für Unittests
    public int getAppleY() {
        return apple_y;
    }

    //nötig für Unittests
    public Snake getSnake() {
        return snake;
    }
    
    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(widthInPixels, heightInPixels));
        loadImages();
        initGame();
    }

    private Image loadImage(String path) {
        return new ImageIcon(path).getImage();
    }

    public void loadImages() {
        ball = loadImage("src/resources/dot.png");
        apple = loadImage("src/resources/apple.png");
        head = loadImage("src/resources/head.png");
    }

    private void initGame() {

        place_snake_at_initial_location();
        place_apple_at_random_location();
        start_game_loop_timer();
    }
    public void start_game_loop_timer()
    {
        timer = new Timer(refreshRateInMS, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        
        if (inGame)
        {
            drawApple(g);
            drawSnake(g);
            Toolkit.getDefaultToolkit().sync();
        }
        else
        {
            gameOver(g);
        }        
    }

    private void drawApple(Graphics g)
    {
        g.drawImage(apple, apple_x, apple_y, this);
    }

    private void drawSnake(Graphics g)
    {
        for (int i = 0; i < snake.length(); i++) {
            if (i == 0) {
                g.drawImage(head, snake.position(i).x, snake.position(i).y, this);
            } else {
                g.drawImage(ball, snake.position(i).x, snake.position(i).y, this);
            }
        }
    }

    private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (widthInPixels - metr.stringWidth(msg)) / 2, heightInPixels / 2);
    }

    public void checkApple()
    {
        if ((snake.head_position().x == apple_x) && (snake.head_position().y == apple_y))
        {
            snake.grow( direction );
            place_apple_at_random_location();
        }
    }

    private void move()
    {
        snake.move( direction );
    }

    private void checkCollision()
    {
        if ( snake.is_snake_colliding(widthInPixels, heightInPixels) )
            inGame = false;
        
        if (!inGame) {
            timer.stop();
        }
    }
    public int maximum_tile_index_x()
    {
        return ( widthInPixels / tileSizeInPixels ) - 1;
    }

    public int maximum_tile_index_y()
    {
        return ( heightInPixels/ tileSizeInPixels ) - 1;
    }

    public void place_snake_at_initial_location()
    {
        snake = new Snake( 3, tileSizeInPixels );
    }

    public void place_apple_at_random_location()
    {
        apple_x = getRandomCoordinate(maximum_tile_index_x());
        apple_y = getRandomCoordinate(maximum_tile_index_y());
    }

    private int getRandomCoordinate(int maxSize)
    {
        int r = (int) (Math.random() * maxSize);
        return r * tileSizeInPixels;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ( key == KeyEvent.VK_LEFT )
                direction = Direction.left;

            else if ( key == KeyEvent.VK_RIGHT )
                direction = Direction.right;

            else if ( key == KeyEvent.VK_UP )
                direction = Direction.up;

            else if ( key == KeyEvent.VK_DOWN )
                direction = Direction.down;
        }
    }
}
