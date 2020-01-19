public class Bullet extends Planet{


    public Bullet(Vector2 pos,Vector2 vel, double m, double r, String img) {
        super(pos, vel, m, r, img);
    }

    @Override
	public void update(double dt) {
		this.pos = this.pos.add(this.vel.mul(dt));
	}


}
