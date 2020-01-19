public class Planet {
	
	private final double g = 6.67e-11;
	public Vector2 pos;
	public Vector2 vel;
	public Vector2 force;
	public double mass;
	public double radius;
	public String imgFileName;

	public Planet(Vector2 pos,Vector2 vel, double m, double r, String img) {
		this.pos = pos;
		this.vel = vel;
		this.mass = m;
		this.radius = r;
		this.imgFileName = img;
	}

	public double distance2(Vector2 target) {
		return this.pos.sub(target).magnitude();
	}
	
	public boolean collides(Planet[] bodies, double multi) {
		for (Planet p : bodies) {
			if (this.pos.sub(p.pos).magnitude() / multi < this.radius + p.radius) return true;
		}
		return false;
	}
	
	public void applyForces(Planet[] bodies) {
		this.force = Vector2.zero();
		for (Planet p : bodies) {
			this.applyGravity(p);
		}
	}

	protected void applyGravity(Planet other) {
		if (this == other) return;
		double r = this.distance2(other.pos);
		double amount = this.mass * other.mass * g / Math.pow(r,2);
		this.force = this.force.add(other.pos.sub(this.pos).unit().mul(amount));
	}

	public void update(double dt) {
		this.vel = this.vel.add(this.force.mul(1 / this.mass).mul(dt));
		this.pos = this.pos.add(this.vel.mul(dt));
	}

	public void draw() {
		StdDraw.enableDoubleBuffering();
		StdDraw.picture(this.pos.x, this.pos.y, "images/" + imgFileName);
	}

}

