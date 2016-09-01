package engine;

import android.graphics.Rect;
import android.graphics.RectF;

public class Line {

    public float startX, startY, endX, endY, m, n;

    public Vector3 vector, normal, endVector;

    public static Vector3 zVec = new Vector3(0, 0, 1);

    public Line(float sx, float sy, float ex, float ey) {
        startX = sx;
        startY = sy;
        endX = ex;
        endY = ey;
        vector = new Vector3(startX - endX, startY - endY, 0);
        normal = vector.cross(zVec);
        calcM();
        calcN();
        endVector = new Vector3(ex, ey, 0);
    }

    void updateEnd(float x, float y) {
        endX = x;
        endY = y;
    }

    public void calcM(){
        float xdif = startX - endX;
        float ydif = startY - endY;
        if (xdif == 0)
            xdif = 0.001f;
        m = ydif / xdif;
        if (m == 0)
            m = 0.001f;
    }

    public float getM() {
        return m;
    }

    public void calcN(){
        n = startY - m * startX;
    }

    public float getN() {
        return n;
    }

    public float getDistance(float x, float y) {
        float m = getM();
        float n = getN();
        return (float) (Math.abs(m * x - y + n)) / (float) (Math.sqrt(m * m + 1));
    }

    // ax+by+c
    // mx-1y+n
    public boolean isInside(Vector3 point) {
        Vector3 b = new Vector3(point.x - startX, point.y - startY, 0);
        double cosThetaSign = vector.cross(zVec).dot(b);
        return cosThetaSign < 0;
    }

    // AxB<0 => true
    // does not work
    public boolean isInsideOld(Vector3 point) {
        Vector3 b = new Vector3(point.x - startX, point.y - startY, 0);
        Vector3 c = vector.cross(b);
        double sinTheta = c.length() / (vector.length() * b.length());
        System.out.println(Math.toDegrees(sinTheta));
        if (sinTheta > 0)
            return false;
        else
            return true;
    }

    public static void main(String[] args) {
        Line l = new Line(0, 0, 1, 1);
        l.isInside(new Vector3(1, 0.99f, 0));
    }

    public static boolean isBtw(float i, float i1, float i2) {
        return (i2 < i && i < i1) || (i1 < i && i < i2);
    }
    public static boolean isInside(float x, float y, float left, float top, float wid, float heg) {
        return isBtw(x, left, left + wid) && isBtw(y, top, top + heg);
    }
    public static boolean isInside(float x, float y, Rect rect) {
        return isBtw(x, rect.left, rect.left + rect.width()) && isBtw(y, rect.top, rect.top + rect.height());
    }
}