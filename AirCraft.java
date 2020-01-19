public class AirCraft extends HeavenlyBody{
    protected boolean exploded;
	protected Vector2 explodePos;
	protected double explodeTime;

	public void applyForces(Planet[] bodies, Planet earth) {
    	this.force = Vector2.zero();
		for (Planet p : bodies) {
			if (p == earth && this.distance2(earth.pos) < earth.radius * 3) continue;
			this.applyGravity(p);
		}
    }

    public void explode() {
    	if (this.exploded) return;
    	this.exploded = true;
    	this.explodePos = this.pos;
    	this.explodeTime = 5e5;
    }

    @Override
    public void update(double dt) {
    	if (this.exploded) {
    		this.explodeTime -= dt;
    		return;
    	}
		this.vel = this.vel.add(this.force.mul(1 / this.mass).mul(dt));
		this.pos = this.pos.add(this.vel.mul(dt));
	}

    public double headAngle() {
    	 double degree = Math.atan(this.vel.y / this.vel.x) / Math.PI * 180;
         if (this.vel.x < 0) {
             degree += 180;
         }
         return degree;
    }

    @Override
    public void draw() {
    	if (this.exploded) {
    		if (explodeTime < 0) return;
    		StdDraw.enableDoubleBuffering();
    		StdDraw.picture(this.explodePos.x, this.explodePos.y, "images/explosion.gif", 3e10, 3e10);
    	} else {
    		StdDraw.enableDoubleBuffering();
            StdDraw.picture(this.pos.x, this.pos.y, "images/"+imgFileName, 2.5e10, 2e10, this.headAngle() - 90);
            StdDraw.text(2e+11, 2.40e+11,"vel: "+ String.valueOf((int)this.vel.magnitude())+"m/s");
    	}
    }
}
