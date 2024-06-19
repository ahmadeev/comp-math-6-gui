package backend;

public class SetOfThree {
    private double x;
    private double y;
    private double exactY;

    public SetOfThree() {
    }

    public SetOfThree(double x, double y, double exactY) {
        this.x = x;
        this.y = y;
        this.exactY = exactY;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getExactY() {
        return exactY;
    }

    public void setExactY(double exactY) {
        this.exactY = exactY;
    }
}
