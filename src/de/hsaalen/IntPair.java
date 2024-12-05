package de.hsaalen;
public class IntPair
{
    public int x;
    public int y;
    public IntPair( int x, int y )
    {
        this.x = x;
        this.y = y;
    }

    public void move( Direction direction, int tile_size_in_pixels )
    {
        switch (direction)
        {
            case Direction.left:
                x -= tile_size_in_pixels;
                break;
            case Direction.right:
                x += tile_size_in_pixels;
                break;
            case Direction.up:
                y -= tile_size_in_pixels;
                break;
            case Direction.down:
                y += tile_size_in_pixels;
                break;
        }
    }

    public IntPair clone( )
    {
        return new IntPair( this.x, this.y );
    }

    public String toString()
    {
        String result = "(" + x + "/" + y + ")";
        return result;
    }
}