package backend;

import java.util.ArrayList;

public abstract class Function {
    public abstract double getValue(double x, double y);
    public abstract double getExactFunctionValue(double x);

    public ArrayList<Double> getExactValue(ArrayList<Double> x) {
        ArrayList<Double> y = new ArrayList<>();
        for (Double aDouble : x) y.add(getExactFunctionValue(aDouble));
        return y;
    }

    public static class FunctionOne extends Function {
        public static final String EQUATION = "y' = y + (1 + x) * y^2";

        public double getExactFunctionValue(double x) {
            return -Math.exp(x) / (x * Math.exp(x));
        }

        public double getValue(double x, double y) {
            return y + (1 + x) * Math.pow(y, 2);
        }


    }

    public static class FunctionTwo extends Function {
        public static final String EQUATION = "y' = y + (1 + x) * y^2";

        public double getExactFunctionValue(double x) {
            return -Math.exp(x) / (x * Math.exp(x));
        }

        public double getValue(double x, double y) {
            return y + (1 + x) * Math.pow(y, 2);
        }
    }

    public static class FunctionThree extends Function {
        public static final String EQUATION = "y' = y + (1 + x) * y^2";

        public double getExactFunctionValue(double x) {
            return -Math.exp(x) / (x * Math.exp(x));
        }

        public double getValue(double x, double y) {
            return y + (1 + x) * Math.pow(y, 2);
        }
    }
}