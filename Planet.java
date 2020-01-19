public class Planet extends HeavenlyBody{

	public Planet(Vector2 pos,Vector2 vel, double m, double r, String img) {
		this.pos = pos;
		this.vel = vel;
		this.mass = m;
		this.radius = r;
		this.imgFileName = img;
	}
}

