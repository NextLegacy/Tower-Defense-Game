package engine.math;

import static engine.math.MathUtility.*;

public class Vector
{
    public double x;
    public double y;

    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector setX(double x) { this.x = x; return this; }
    public Vector setY(double y) { this.y = y; return this; }

    public double getX() { return this.x; }
    public double getY() { return this.y; }

    public Vector set(Vector vec) 
    { 
        this.setX(vec.x);
        this.setY(vec.y);

        return this;
    }

    public Vector set(double x, double y) { return this.setX(x).setY(y); }

    public Vector set(double x) { return this.setX(x); }
    
    public Vector fill(double n) { return this.set(n, n); }

    public Vector mul(Vector vec) { return new Vector(this.x * (vec.x), this.y * (vec.y)); }
    public Vector div(Vector vec) { return new Vector(this.x / (vec.x), this.y / (vec.y)); }
    public Vector add(Vector vec) { return new Vector(this.x + (vec.x), this.y + (vec.y)); }
    public Vector sub(Vector vec) { return new Vector(this.x - (vec.x), this.y - (vec.y)); }

    public Vector mul(double n) { return new Vector(this.x * n, this.y * n); }
    public Vector div(double n) { return new Vector(this.x / n, this.y / n); }
    public Vector add(double n) { return new Vector(this.x + n, this.y + n); }
    public Vector sub(double n) { return new Vector(this.x - n, this.y - n); }

    public Vector mul(double x, double y) { return new Vector(this.x * x, this.y * y); }
    public Vector div(double x, double y) { return new Vector(this.x / x, this.y / y); }
    public Vector add(double x, double y) { return new Vector(this.x + x, this.y + y); }
    public Vector sub(double x, double y) { return new Vector(this.x - x, this.y - y); }

    public Vector clampX(double min, double max) { this.x = this.x < min ? min : this.x > max ? max : this.x; return this; }
    public Vector clampY(double min, double max) { this.y = this.y < min ? min : this.y > max ? max : this.y; return this; }
    public Vector clamp(Vector min, Vector max)
    {
        return this.clampX(min.x, max.x)
                   .clampY(min.y, max.y);
    }

    public Vector floorX() { this.x = FLOOR(this.x); return this; }
    public Vector floorY() { this.y = FLOOR(this.y); return this; }
    public Vector floor()
    {
        return this.floorX()
                   .floorY();
    }

    public Vector roundX() { this.x += this.x < 0 ? -0.5 : 0.5; return this.floorX(); }
    public Vector roundY() { this.y += this.y < 0 ? -0.5 : 0.5; return this.floorY(); }
    public Vector round()
    {
        return this.roundX()
                   .roundY();
    }

    public Vector absX() { return this.setX(ABS(this.x)); }
    public Vector absY() { return this.setY(ABS(this.y)); }
    public Vector abs()
    {
        return this.absX()
                   .absY();
    }

    public Vector lerpX(double from, double to, double t) { return this.setX(LERP(from, to, t)); }
    public Vector lerpY(double from, double to, double t) { return this.setY(LERP(from, to, t)); }
    public Vector lerp(Vector from, Vector to, double t)
    {
        return this.lerpX(from.x, to.x, t)
                   .lerpY(from.y, to.y, t);
    }
    
    public boolean isInRangeX(double min, double max) { return this.x >= min && this.x <= max; }
    public boolean isInRangeY(double min, double max) { return this.y >= min && this.y <= max; }
    public boolean isInRange(Vector from, Vector to)
    {
        return this.isInRangeX(from.x, to.x) && 
               this.isInRangeY(from.y, to.y);
    }

    public boolean isInBounds(Vector position, Vector size)
    {
        return this.isInRangeX(position.x, position.x + size.x) &&
               this.isInRangeY(position.y, position.y + size.y);
    }

    public boolean isOutOfBounds(Vector position, Vector size)
    {
        return !this.isInBounds(position, size);
    }

    public Vector inverse()
    { 
        return this.mul(-1);
    }

    public double distance(Vector vec) 
    {
        return this.sub(vec).magnitude();
    }

    public Vector directionTo(Vector vec)
    {
        return vec.sub(this).normalized();
    }

    public double magnitude() 
    {
        return Math.sqrt(this.dot(this));
    }

    public Vector normalized()
    {
        return this.div(this.magnitude());
    }

    public double angle()
    {
        Vector n = normalized();

        return n.y >= 0 ? Math.acos(n.x) : -Math.acos(n.x);
    }
    
    public double angleTo(Vector vec) 
    {
        return Math.acos(this.dot(vec) / this.magnitude());
    }
    
    public double dot(Vector vec) 
    {
        return this.x * vec.x +
               this.y * vec.y;
    }

    public boolean equals(Object obj)
    {
        if (obj == null) 
            return false;

        if (this == obj)
            return true;

        if(!(obj instanceof Vector))
            return false;

        Vector v = (Vector) obj;

        if (this.x == v.x && this.y == v.y)
            return true;
        
        return false;
    }

    public String toCompactString()
    {
        return String.format("x:%s y:%s", this.x, this.y);
    }

    public String toString()
    {
        return String.format("Vector(%s)", this.toCompactString());
    }

    public Vector clone()
    {
        return new Vector(this.x, this.y);
    }

    public static Vector zero() { return new Vector( 0, 0); }
    public static Vector one () { return new Vector( 1, 1); }

    public static Vector up     () { return new Vector( 0, -1); }
    public static Vector right  () { return new Vector( 1, 0); }

    public static Vector down   () { return Vector.up()   .inverse(); }
    public static Vector left   () { return Vector.right().inverse(); }
    
    public static Vector fromAngle(double rotation)
    {
        return new Vector(Math.cos(rotation), Math.sin(rotation));
    }
}
