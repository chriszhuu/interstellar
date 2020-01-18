import java.awt.event.KeyEvent;

public class NBody {
    public static double readRadius(String fileName){
        In in = new In (fileName);
        int num = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String filename){
        In in = new In (filename);
        Planet[] bodies = new Planet[in.readInt()];
        double radius = in.readDouble();
        for (int i = 0; i < bodies.length; i++){
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            double planetRadius = in.readDouble();
            String imgFileName = in.readString();
            bodies[i] = new Planet (xxPos,yyPos,xxVel,yyVel,mass,planetRadius,imgFileName);
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
        Rocket rocket = new Rocket(earth.xxPos, earth.radius, earth.xxVel, earth.yyVel, 2.84e+5, 0, "acorn3.gif");

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius,radius);

        for (double time = 0.0; time <= T; time += dt) {
            double[] xForces = new double[bodies.length];
            double[] yForces = new double[bodies.length];
            int i = 0;
            for (Planet b : bodies) {
                xForces[i] = b.calcNetForceExertedByX(bodies);
                yForces[i] = b.calcNetForceExertedByY(bodies);
                b.update(dt, xForces[i], yForces[i]);
                i++;
            }
            double rocketX = rocket.calcNetForceExertedByX(bodies);
            rocketX += rocket.calcSupportX(earth);
            double rocketY = rocket.calcNetForceExertedByY(bodies);
            rocketY += rocket.calcSupportY(earth);

            if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
                rocketX += rocket.userForceX();
                rocketY += rocket.userForceY();
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                rocketX -= rocket.userForceX();
                rocketY -= rocket.userForceY();
            }

            rocket.applyForce();


            rocket.update(dt,rocketX,rocketY);

            StdDraw.picture(0, 0, "images/starfield.jpg",radius*2,radius*2);
            for (Planet b : bodies) {
                b.draw();
            }
            rocket.draw();
            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }

    }


}