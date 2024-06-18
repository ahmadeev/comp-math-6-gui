package backend;

import java.util.ArrayList;

public class Result {
    private ArrayList<Double> x;
    private ArrayList<Double> y;

    public Result(ArrayList<Double> x, ArrayList<Double> y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Double> getX() {
        return x;
    }

    public void setX(ArrayList<Double> x) {
        this.x = x;
    }

    public ArrayList<Double> getY() {
        return y;
    }

    public void setY(ArrayList<Double> y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + "\n" + y + "\n";
    }
}
