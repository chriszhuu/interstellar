public class Rocket extends Planet {

    public Rocket(Vector2 pos,Vector2 vel, double m, double r, String img) {
        super(pos, vel, m, r, img);
    }
    
    public void applyForces(Planet[] bodies, Planet earth) {
    	this.force = Vector2.zero();
		for (Planet p : bodies) {
			if (p == earth && this.distance2(earth.pos) < earth.radius * 100) continue;
			this.applyGravity(p);
		}
    }
    
    public void addUserForse(boolean forward) {
    	if (forward) {
    		this.force = this.force.add(this.vel.unit().mul(5e2));
    	}
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
        StdDraw.enableDoubleBuffering();
        StdDraw.picture(this.pos.x, this.pos.y, "images/"+imgFileName, 2.5e10, 2e10, this.headAngle() - 90);
        StdDraw.text(2e+11, 2.40e+11,"vel: "+ String.valueOf((int)this.vel.magnitude())+"m/s");
    }

    public void drawFlameBack() {
        StdDraw.enableDoubleBuffering();
        Vector2 flamePos = this.pos.sub(this.vel.unit().mul(12.5e9));
        StdDraw.picture(flamePos.x, flamePos.y, "images/fireflame.gif", 2.2e10, 1.8e10, this.headAngle());
    }

}
