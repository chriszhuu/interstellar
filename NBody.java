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
            String imgFileName = in.readString();
            bodies[i] = new Planet (xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
        }
        return bodies;
    }

    public static void main (String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] bodies = readPlanets(filename);

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

            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet b : bodies) {
                b.draw();
            }
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