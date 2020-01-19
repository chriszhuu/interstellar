public class Enemy extends AirCraft {

    private Planet orbit;
    private double theta;
    private double dist;
    private double angvel;

    public Enemy(Planet planet) {
        this.pos = planet.pos;
        this.vel = planet.vel;
        this.mass = 1;
        this.radius = 5e9;
        this.imgFileName = "enemyRed.gif";
        this.orbit = planet;
        this.theta = 0;
        this.dist = planet.radius * 2;
        angvel = (Math.random() + 0.5) * 0.1;
        this.updatePos();
    }

    @Override
    public void update(double dt) {
        if (this.exploded) {
    		this.explodeTime -= dt;
    		return;
    	}
        theta += angvel;
        updatePos();
    }

    private void updatePos() {
        this.pos = orbit.pos.add(new Vector2(Math.cos(theta) * dist, Math.sin(theta) * dist));
    }

    @Override
    public void draw() {
    	if (this.exploded) {
    		if (explodeTime < 0) return;
    		StdDraw.enableDoubleBuffering();
    		StdDraw.picture(this.explodePos.x, this.explodePos.y, "images/explosion.gif", 3e10, 3e10);
    	} else {
    		StdDraw.enableDoubleBuffering();
            StdDraw.picture(this.pos.x, this.pos.y, "images/"+imgFileName, 0.5e10, 0.5e10);
    	}
    }
}
