package engine;

import java.util.LinkedList;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class GameObject {

    protected Vector2 location, velocity;
    protected ObjectID id;

    protected boolean falling = true;
    protected boolean jumping = false;
    protected boolean enabled = true;

    public GameObject(Vector2 loc, ObjectID objectId) {
        super();
        this.location = loc;
        this.id = objectId;
        velocity = new Vector2(0, 0);
    }

    public abstract void update(LinkedList<GameObject> object);
    public abstract void draw(Canvas canvas);
    public abstract RectF getBounds();

    public ObjectID getId(){
        return id;
    }

    public Vector2 getLocation() {
        return location;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 vel) {
        velocity.x += vel.x;
        velocity.y += vel.y;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}