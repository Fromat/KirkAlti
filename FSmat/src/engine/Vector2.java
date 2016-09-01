package engine;

public class Vector2 {

    public float x, y;

    public  Vector2(){}

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void setVector(Vector2 v) {
        x = v.x;
        y = v.y;
    }

    public Vector2 added (Vector2 v){
        return new Vector2(x + v.x, y + v.y);
    }

    public Vector2 subtracted (Vector2 v){
        return new Vector2(x - v.x, y - v.y);
    }
}