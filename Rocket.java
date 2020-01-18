public class Rocket extends Planet {

    public Rocket(double xP, double yP, double xV, double yV, double m, double r, String img) {
        super(xP, yP, xV, yV, m, r, img);
    }

    public double calcSupportX(Planet earth) {
        if (this.calcDistance(earth) < earth.radius * 100) {
            return -this.calcForceExertedByX(earth);
        }
        else return 0;
    }

    public double calcSupportY(Planet earth) {
        if (this.calcDistance(earth) < earth.radius * 100) {
            return -this.calcForceExertedByY(earth);
        }
        else return 0;
    }

    public double speed() {
        return Math.pow((Math.pow(this.xxVel,2) + Math.pow(this.yyVel,2)),0.5);
    }

    public double userForceX(){
        return this.xxVel / this.speed() * 0.5e+3;
    }

    public double userForceY(){
        return this.yyVel / this.speed() * 0.5e+3;
    }

    public double applyForce(){
        //button exerts x amount of force in velocity direction
        //add that to the net force and let super.update() decide the new position
        //press button for t amount of time determines the total amount of velocity change
        //
        return 0;
    }
    @Override
    public void draw() {
        super.draw();
        StdDraw.enableDoubleBuffering();
//		StdDraw.picture(xxPos + xxVel * 1e+6, yyPos + yyVel * 1e+6, "images/"+imgFileName);
    }

}
