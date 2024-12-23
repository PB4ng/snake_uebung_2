package de.hsaalen;

import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardTest {
    @Test
    public void test_maximum_tile_index_x() {
        Board board = new Board();
        int maximum_tile_index_x = board.maximum_tile_index_x();
        assertEquals((maximum_tile_index_x + 1) * board.tileSizeInPixels, board.widthInPixels);
    }

    @Test
    public void test_maximum_tile_index_y() {
        Board board = new Board();
        int maximum_tile_index_y = board.maximum_tile_index_y();
        assertEquals((maximum_tile_index_y + 1) * board.tileSizeInPixels, board.heightInPixels);
    }

    @Test
    public void testConcatenate() {
        Board board = new Board();
        assertNotNull(board);
    }

    @Test
    public void testPlaceAppleAtRandomLocation() {
        Board board = new Board();

        board.place_fruit_at_random_location();

        int appleX = board.getAppleX();
        int appleY = board.getAppleY();

        assertTrue(appleX >= 0 && appleX <= board.maximum_tile_index_x() * board.tileSizeInPixels);
        assertTrue(appleY >= 0 && appleY <= board.maximum_tile_index_y() * board.tileSizeInPixels);

        assertEquals(0, appleX % board.tileSizeInPixels);
        assertEquals(0, appleY % board.tileSizeInPixels);
    }

    @Test
    public void testPlaceSnakeAtInitialLocation() {
        Board board = new Board();
        board.place_snake_at_initial_location();

        Snake snake = board.getSnake();

        assertNotNull(snake);
        assertEquals(3, snake.length());

        for (int i = 0; i < snake.length(); i++) {
            assertTrue(snake.position(i).x >= 0 && snake.position(i).x <= board.maximum_tile_index_x() * board.tileSizeInPixels);
            assertTrue(snake.position(i).y >= 0 && snake.position(i).y <= board.maximum_tile_index_y() * board.tileSizeInPixels);

            assertEquals(0, snake.position(i).x % board.tileSizeInPixels);
            assertEquals(0, snake.position(i).y % board.tileSizeInPixels);
        }
    }

    @Test
    public void testCheckFruit() {
        Board board = new Board();
        board.place_snake_at_initial_location();

        board.place_fruit_at_random_location();
        IntPair applePosition = new IntPair(board.getAppleX(), board.getAppleY());

        board.getSnake().position(0).x = applePosition.x;
        board.getSnake().position(0).y = applePosition.y;

        int originalLength = board.getSnake().length();

        board.checkFruit();

        assertEquals(originalLength + 1, board.getSnake().length());

        assertNotEquals(applePosition.x, board.getAppleX());
        assertNotEquals(applePosition.y, board.getAppleY());
    }

    @Test
    public void testLoadImages() {
        Board board = new Board();
        board.loadImages();

        assertNotNull(board.ball);
        assertNotNull(board.apple);
        assertNotNull(board.head);
        assertNotNull(board.superfruit);
    }




    @Test
    public void testAddInitialObstacles() {

        Board board = new Board();

        List<Rectangle> obstacles = board.obstacles;

        assertEquals(8, obstacles.size());

        assertTrue(obstacles.contains(new Rectangle(40, 40, 130, 10)));
        assertTrue(obstacles.contains(new Rectangle(140, 160, 130, 10)));

    }

    @Test
    public void testGetRandom()
    {
        Board board = new Board();

        int test1 = board.getRandom(15);

        assertTrue(test1 > 0);
        assertTrue(test1 <= 15);
        assertFalse(test1 > 16);
    }
}