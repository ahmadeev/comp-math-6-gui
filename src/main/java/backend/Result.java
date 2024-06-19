package backend;

import java.util.ArrayList;

public class Result {
    private Function function;
    private ArrayList<Double> x;
    private ArrayList<Double> y;
    private ArrayList<Double> exactY;

    public Result(Function function, ArrayList<Double> x, ArrayList<Double> y) {
        this.function = function;
        this.x = x;
        this.y = y;
        this.exactY = function.getExactValue(x);
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
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

    public ArrayList<Double> getExactY() {
        return exactY;
    }

    public void setExactY(ArrayList<Double> exactY) {
        this.exactY = exactY;
    }

    @Override
    public String toString() {
        return x + "\n" + y + "\n" + exactY + "\n";
    }
}
