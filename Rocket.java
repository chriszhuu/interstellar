public class Rocket extends AirCraft {
	public boolean tookoff;

    public Rocket(Vector2 pos,Vector2 vel, double m, double r, String img) {
        this.pos = pos;
		this.vel = vel;
		this.mass = m;
		this.radius = r;
		this.imgFileName = img;
        this.exploded = false;
        this.tookoff = false;
    }
    
    public void addUserForse(boolean forward) {
    	if (forward) {
    		this.force = this.force.add(this.vel.unit().mul(2e3));
    	} else {
    		this.force = this.force.sub(this.vel.unit().mul(2e3));
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
