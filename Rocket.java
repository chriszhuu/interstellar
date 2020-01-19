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


    @Override
    public void draw() {
        double degree = Math.atan(yyVel / xxVel) / Math.PI * 180;
        if (xxVel < 0) {
            degree = 180 + degree;
        }
        StdDraw.enableDoubleBuffering();
        StdDraw.picture(xxPos,yyPos,"images/"+imgFileName,2.5e10,2e10,degree - 90);
        StdDraw.text(2e+11, 2.40e+11,"vel: "+ String.valueOf((int)this.speed())+"m/s");
    }

    public void drawFlameBack() {
        double degree = Math.atan(yyVel / xxVel) / Math.PI * 180;
        if (xxVel < 0) {
            degree = 180 + degree;
        }
        StdDraw.enableDoubleBuffering();
        StdDraw.picture(xxPos - userForceX() * 2.5e7,yyPos - userForceY() * 2.5e7, "images/fireflame.gif",2.2e10,1.8e10,degree);
    }

}
