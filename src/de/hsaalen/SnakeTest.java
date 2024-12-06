package de.hsaalen;

import org.junit.Test;
import static org.junit.Assert.*;

public class SnakeTest {

    @Test
    public void testMoveRight() {
        Snake snake = new Snake(3, 10);
        snake.move(Direction.right);

        assertEquals(60, snake.head_position().x);
        assertEquals(50, snake.head_position().y);
    }

    @Test
    public void testMoveLeft() {
        Snake snake = new Snake(3, 10);
        snake.move(Direction.left);

        assertEquals(40, snake.head_position().x);
        assertEquals(50, snake.head_position().y);
    }

    @Test
    public void testMoveUp() {
        Snake snake = new Snake(3, 10);
        snake.move(Direction.up);

        assertEquals(50, snake.head_position().x);
        assertEquals(40, snake.head_position().y);
    }

    @Test
    public void testMoveDown() {
        Snake snake = new Snake(3, 10);
        snake.move(Direction.down);

        assertEquals(50, snake.head_position().x);
        assertEquals(60, snake.head_position().y);
    }

    @Test
    public void testBodyFollowsHead() {
        Snake snake = new Snake(3, 10);
        snake.move(Direction.right);

        assertEquals(60, snake.head_position().x);
        assertEquals(50, snake.position(1).x);
        assertEquals(40, snake.position(2).x);
    }

    @Test
    public void testCollisionWithSelf() {
        Snake snake = new Snake(5, 10);
        snake.move(Direction.right);
        snake.move(Direction.down);
        snake.move(Direction.left);
        snake.move(Direction.up);

        assertTrue(snake.is_snake_colliding(300, 300));
    }

    @Test
    public void testCollisionWithWalls()
    {
        Snake snake = new Snake(3, 10);


        for (int i = 0; i < 30; i++) {
            snake.move(Direction.right);
        }

        assertTrue(snake.is_snake_colliding(300, 300));
    }

    @Test
    public void testNoCollision() {
        Snake snake = new Snake(3, 10);
        snake.move(Direction.right);
        snake.move(Direction.down);

        assertFalse(snake.is_snake_colliding(300, 300));
    }



    @Test
    public void testIsOutsideBoardHeadInside() {
        Snake snake = new Snake(3, 10);
        snake.place_at_initial_location(3);

        assertFalse(snake.is_outside_board(300, 300));
    }

    @Test
    public void testIsOutsideBoardHeadLeft() {
        Snake snake = new Snake(3, 10);
        snake.place_at_initial_location(3);
        snake.head_position().x = -1;

        assertTrue(snake.is_outside_board(300, 300));
    }

    @Test
    public void testIsOutsideBoardHeadRight() {
        Snake snake = new Snake(3, 10);
        snake.place_at_initial_location(3);
        snake.head_position().x = 300 + 1;

        assertTrue(snake.is_outside_board(300, 300));
    }

    @Test
    public void testIsOutsideBoardHeadTop() {
        Snake snake = new Snake(3, 10);
        snake.place_at_initial_location(3);
        snake.head_position().y = -1;

        assertTrue(snake.is_outside_board(300, 300));
    }

    @Test
    public void testIsOutsideBoardHeadBottom() {
        Snake snake = new Snake(3, 10);
        snake.place_at_initial_location(3);
        snake.head_position().y = 300 + 1;

        assertTrue(snake.is_outside_board(300, 300));
    }

}

