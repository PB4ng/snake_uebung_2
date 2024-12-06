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
    private Image ball;
    private Image apple;
    private Image head;

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

    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
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
        
        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this);

            for (int i = 0; i < snake.length(); i++) {
                if (i == 0) {
                    g.drawImage(head, snake.position(i).x, snake.position(i).y, this);
                } else {
                    g.drawImage(ball, snake.position(i).x, snake.position(i).y, this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
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

    private void checkApple()
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

    public void place_apple_at_random_location() {

        int r = (int) (Math.random() * maximum_tile_index_x());
        apple_x = ((r * tileSizeInPixels));

        r = (int) (Math.random() * maximum_tile_index_y());
        apple_y = ((r * tileSizeInPixels));
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

            if ( key == KeyEvent.VK_RIGHT )
                direction = Direction.right;

            if ( key == KeyEvent.VK_UP )
                direction = Direction.up;

            if ( key == KeyEvent.VK_DOWN )
                direction = Direction.down;
        }
    }
}
