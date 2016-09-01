package engine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class Grid {
    public Paint paint = new Paint();
    public Paint paintShader = new Paint();
    public Rect grid = new Rect();
    //Grid Element Types
    // Grid Element = 0 -> empty
    // Grid Element = 1 -> source
    // Grid Element = 2 -> portal
    // Grid Element = 3 -> bridgeLeft
    // Grid Element = 4 -> bridgeUp
    // Grid Element = 5 -> arrow
    public int gridElement;
    public int direction;
    // Direction Types
    // Direction = 0 -> Null
    // Direction = 1 -> Left
    // Direction = 2 -> Top
    // Direction = 3 -> Right
    // Direction = 4 -> Bottom

    // Color
    public int color;
    public int color2;
    public boolean isFinishPoint = false;

    public Grid(int left, int top, int right, int bottom, Paint paint) {
        grid.left = left;
        grid.top = top;
        grid.right = right;
        grid.bottom = bottom;
        this.paint = paint;
    }

    /*
       public Grid(int left, int top, int right, int bottom, int direction, int gridElement, int color, Paint paint)
       {
           grid.left    = left;
           grid.top     = top;
           grid.right   = right;
           grid.bottom  = bottom;
           this.gridElement = gridElement;
           this.direction   = direction;
           this.color       = color;
           this.paint       = paint;
       }

       public Grid(int left, int top, int right, int bottom, int direction, int gridElement, int color1, int color2, Paint paint)
       {
           grid.left    = left;
           grid.top     = top;
           grid.right   = right;
           grid.bottom  = bottom;
           this.gridElement = gridElement;
           this.direction   = direction;
           this.color       = color1;
           this.color2       = color2;
           this.paint       = paint;
       }
   */
    public void setGrid(int direction, int color, Paint paint) {
        this.direction = direction;
        this.color = color;
        this.paint = paint;
    }

    public void setDirection(int d)
    {
        this.direction = d;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public int getColor()
    {
        return color;
    }

    public abstract void drawGrid(Canvas canvas);

    public boolean isInGrid(int x, int y)
    {
        return grid.contains(x, y);
    }

    public int getGridType()
    {
        return gridElement;
    }

    public int centerX() {
        return grid.centerX();
    }

    public int centerY()
    {
        return grid.centerY();
    }
}