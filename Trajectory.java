import java.util.LinkedList;
import java.util.Queue;

public class Trajectory {

    private Queue<Vector2> traj = new LinkedList<>();
    private int size = 0;

    public void add(Vector2 point) {
        if (size > 200) {
            traj.poll();
            size--;
        }
        traj.offer(point);
        size++;
    }

    public void draw(){
        StdDraw.enableDoubleBuffering();
        for(Vector2 p : traj) {
            StdDraw.picture(p.x,p.y,"images/star.gif");
        }

    }
}
