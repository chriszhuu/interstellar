public class Vector2 {
    public double x;
    public double y;

    public Vector2 (double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2 add(Vector2 other) {
    	return new Vector2(this.x + other.x, this.y + other.y);
    }
    
    public Vector2 sub(Vector2 other) {
    	return new Vector2(this.x - other.x, this.y - other.y);
    }
    
    public Vector2 mul(double scalar) {
    	return new Vector2(this.x * scalar, this.y * scalar);
    }
    
    public double magnitude() {
    	return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    public Vector2 unit() {
    	double magnitude = this.magnitude();
    	if (magnitude == 0) return this;
    	return new Vector2(this.x / magnitude, this.y / magnitude);
    }
    
    @Override
    public String toString() {
    	return "(" + this.x + ", " + this.y + ")";
    }
    
    public static Vector2 zero() {
    	return new Vector2(0, 0);
    }
}
