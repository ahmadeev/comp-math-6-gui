package backend;

public abstract class Function {
    public abstract double getValue(double x, double y);

    public static class FunctionOne extends Function {
        public static final String EQUATION = "y' = y + (1 + x) * y^2";
        public double getValue(double x, double y) {
            return y + (1 + x) * Math.pow(y, 2);
        }
    }

    public static class FunctionTwo extends Function {
        public static final String EQUATION = "y' = y + (1 + x) * y^2";
        public double getValue(double x, double y) {
            return y + (1 + x) * Math.pow(y, 2);
        }
    }

    public static class FunctionThree extends Function {
        public static final String EQUATION = "y' = y + (1 + x) * y^2";
        public double getValue(double x, double y) {
            return y + (1 + x) * Math.pow(y, 2);
        }
    }
}