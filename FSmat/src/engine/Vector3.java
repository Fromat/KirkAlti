package engine;

public class Vector3
{
    public float x, y, z;

    public  Vector3() {}

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public void setVector(Vector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public Vector3 added (Vector3 v){
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    public Vector3 subtracted (Vector3 v){
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }

    public Vector3 multiplied (float value){
        return new Vector3(x * value, y * value, z * value);
    }

    public Vector3 divided (float value){
        return new Vector3(x / value, y / value, z / value);
    }

    public Vector3 trimmed (float maxValue) {
        float len = length();
        if (len > maxValue)
            return new Vector3(x * maxValue / len, y * maxValue / len, z * maxValue / len);
        else
            return new Vector3(x, y, z);
    }

    public void add (Vector3 v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public void subtract (Vector3 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public void multiply (float value) {
        x *= value;
        y *= value;
        z *= value;
    }

    public void divide (float value) {
        x /= value;
        y /= value;
        z /= value;
    }

    public void trim (float maxValue) {
        float len = length();
        if (len > maxValue) {
            x *= maxValue / len;
            y *= maxValue / len;
        }
    }

    public Vector3 negate(){
        return new Vector3(-x, -y, -z);
    }

    public float length(){
        return (float) Math.sqrt(x * x + y * y + z * z);
    };

    public void normalize() {
        float length = this.length();
        if (length == 0)
            return;

        x /= length;
        y /= length;
        z /= length;
    }

    public Vector3 unit() {
        float length = this.length();

        if (length == 0)
            return new Vector3(0, 0, 0);

        return new Vector3(x / length, y / length, z / length);
    }

    public String toString(){
        return "x: " + x + " y: " + y + " z: " + z;
    }

    public float dot(Vector3 u) {
        return  u.x * this.x +
                u.y * this.y +
                u.z * this.z;
    }

    public double angBtw(Vector3 v2){
        return Math.acos((v2.dot(this)) / (length() * v2.length()));
    }

    public Vector3 rotatedVector(double angle) {
        double rx = x * Math.cos(angle) - y * Math.sin(angle);
        double ry = x * Math.sin(angle) + y * Math.cos(angle);
        return new Vector3((float)rx, (float)ry, 0);
    }

    public void zero() {
        x = 0;
        y = 0;
    }

    //C=AXB
    //return C
    public Vector3 cross(Vector3 b) {
        return new Vector3(y * b.z - z * b.y, z * b.x - x * b.z, x * b.y - y * b.x);
    }

    public Vector3 projection(Vector3 v2) {
        Vector3 projected = v2.unit();
        projected.multiply(dot(v2) / v2.length());
        return projected;
    }

    static float err = 0.1f;
    // is num between n1 and n2

    public static boolean isBtw(float num, float n1, float n2) {
        return (num <= n1 + err && num >= n2 - err) || (num >= n1 - err && num <= n2 + err);
    }
}