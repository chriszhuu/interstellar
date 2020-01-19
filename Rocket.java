public class Rocket extends Planet {
	
	private boolean exploded;
	private Vector2 explodePos;
	private double explodeTime;

    public Rocket(Vector2 pos,Vector2 vel, double m, double r, String img) {
        super(pos, vel, m, r, img);
        this.exploded = false;
    }
    
    public void applyForces(Planet[] bodies, Planet earth) {
    	this.force = Vector2.zero();
		for (Planet p : bodies) {
			if (p == earth && this.distance2(earth.pos) < earth.radius * 3) continue;
			this.applyGravity(p);
		}
    }
    
    public void addUserForse(boolean forward) {
    	if (forward) {
    		this.force = this.force.add(this.vel.unit().mul(2e3));
    	} else {
    		this.force = this.force.sub(this.vel.unit().mul(2e3));
    	}
    }
    
    public double headAngle() {
    	 double degree = Math.atan(this.vel.y / this.vel.x) / Math.PI * 180;
         if (this.vel.x < 0) {
             degree += 180;
         }
         return degree;
    }
    
    public void explode() {
    	this.exploded = true;
    	this.explodePos = this.pos;
    	this.explodeTime = 1e6;
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

    public void drawFlame(boolean back) {
    	if (this.exploded) return;
        StdDraw.enableDoubleBuffering();
        Vector2 flamePos;
        if (back) {
        	flamePos = this.pos.sub(this.vel.unit().mul(12.5e9));
        } else {
        	flamePos = this.pos.add(this.vel.unit().mul(13e9));
        }
        double angle = this.headAngle();
        if (!back) angle += 180;
        StdDraw.picture(flamePos.x, flamePos.y, "images/fireflame.gif", 2.2e10, 1.8e10, angle);
    }

}
