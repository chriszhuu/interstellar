import java.awt.event.KeyEvent;

public class NBody {
    public static double readRadius(String fileName){
        In in = new In (fileName);
        in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String filename){
        In in = new In (filename);
        Planet[] bodies = new Planet[in.readInt()];
        in.readDouble();
        for (int i = 0; i < bodies.length; i++){
            double xpos = in.readDouble();
            double ypos = in.readDouble();
            double xvel = in.readDouble();
            double yvel = in.readDouble();
            double mass = in.readDouble();
            double radius = in.readDouble();
            String imgFileName = in.readString();
            bodies[i] = new Planet (new Vector2(xpos, ypos), new Vector2(xvel, yvel), mass, radius, imgFileName);
        }
        return bodies;
    }

    public static void main (String[] args){
        double T = 157788000000000000000000.0;
        double dt = 25000.0;
        String filename = "data/planets.txt";
        double radius = readRadius(filename);
        Planet[] bodies = readPlanets(filename);
        Planet earth = bodies[0];
        Rocket rocket = new Rocket(new Vector2(earth.pos.x, earth.radius), earth.vel, 2.84e+5, 0, "rocket.png");

        Trajectory traj = new Trajectory();
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius,radius);
        long frame = 0;

        for (double time = 0.0; time <= T; time += dt) {
        	
            StdDraw.picture(0, 0, "images/starfield.jpg",radius*2,radius*2);
            frame++;

            for (Planet b : bodies) {
                b.applyForces(bodies);
                b.update(dt);
            }
            
            rocket.applyForces(bodies, earth);

            if (frame % 10 == 0) {
                traj.add(rocket.pos);
            }
            traj.draw();

            for (Planet b : bodies) {
                b.draw();
            }

            if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
                rocket.addUserForse(true);
                rocket.drawFlame(true);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                rocket.addUserForse(false);
                rocket.drawFlame(false);
            }
            rocket.update(dt);
            rocket.draw();

            if (Math.abs(rocket.pos.x) > radius || Math.abs(rocket.pos.y) > radius) {
                StdDraw.picture(0,0,"images/gameover.gif");
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
    }


}