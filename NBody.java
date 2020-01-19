import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

public class NBody {

    public static double readRadius(String fileName) {
        In in = new In(fileName);
        in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        Planet[] bodies = new Planet[in.readInt()];
        in.readDouble();
        for (int i = 0; i < bodies.length; i++) {
            double xpos = in.readDouble();
            double ypos = in.readDouble();
            double xvel = in.readDouble();
            double yvel = in.readDouble();
            double mass = in.readDouble();
            double radius = in.readDouble();
            String imgFileName = in.readString();
            bodies[i] = new Planet(new Vector2(xpos, ypos), new Vector2(xvel, yvel), mass, radius, imgFileName);
        }
        return bodies;
    }

    public static void main(String[] args) {
        double dt = 25000.0;
        String filename = "data/planets.txt";

        double radius = readRadius(filename);
        Planet[] bodies = readPlanets(filename);
        Planet earth = bodies[0];

        Rocket rocket = new Rocket(earth.pos, earth.vel, 2.84e+5, 0, "rocket.png");

        Trajectory traj = new Trajectory();

        List<Bullet> bullets = new LinkedList<>();

        List<Enemy> enemies = new LinkedList<>();

        for (Planet p : bodies) {
            enemies.add(new Enemy(p));
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);

        long frame = 0;
        int bulletcooldown = 0;
        boolean gameover = false;
        boolean win = false;

        while (true) {
            frame++;
            if (bulletcooldown > 0) {
                bulletcooldown--;
            }

            StdDraw.picture(0, 0, "images/starfield.jpg", radius * 2, radius * 2);

            if (frame % 10 == 0) {
                traj.add(rocket.pos);
            }
            traj.draw();

            for (Planet b : bodies) {
                b.applyForces(bodies);
                b.update(dt);
                b.draw();
            }

            for (Enemy e : enemies) {
                e.update(dt);
                if (e.collides(bullets.toArray(new Planet[0]), 1)) {
                    e.explode();
                }
                e.draw();
            }

            win = true;
            for (Enemy e : enemies) {
                if (e.exploded == false) {
                    win = false;
                }
            }

            if (win) {
                StdDraw.picture(0, 0, "images/victory.gif");
                StdDraw.picture(1.3e11, 0, "images/victory.gif");
                StdDraw.picture(-1.3e11, 0, "images/victory.gif");
            }

            rocket.applyForces(bodies, earth);
            if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
                rocket.addUserForse(true);
                rocket.drawFlame(true);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                rocket.addUserForse(false);
                rocket.drawFlame(false);
            }
            rocket.update(dt);
            if (!rocket.tookoff && rocket.distance2(earth.pos) > earth.radius * 3) {
                rocket.tookoff = true;
            }
            rocket.draw();

            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                if (bulletcooldown == 0) {
                    bulletcooldown = 10;
                    bullets.add(new Bullet(rocket.pos, rocket.vel.unit().mul(15e4), 1, 0, "pluto.gif"));
                }
            }
            for (Bullet bullet : bullets) {
                bullet.update(dt);
                bullet.draw();
            }

            if (!win) {
                if (Math.abs(rocket.pos.x) > radius || Math.abs(rocket.pos.y) > radius) {
                    gameover = true;
                } else if (rocket.collides(bodies, 1) && rocket.tookoff) {
                    gameover = true;
                    rocket.explode();
                }
            }

            if (gameover) {
                StdDraw.picture(0, 0, "images/gameover.gif");
            }

            StdDraw.show();
            StdDraw.pause(10);
        }
    }


}