package backend;

import java.util.ArrayList;

import static backend.Methods.getEquationByNumber;

public class DifMath {
    public static class Euler {
        public static Result getValue(Function function, double y0, double a, double b, double h) {
            double x = a;
            double y = y0;
            double previousY;
            double functionValue;

            ArrayList<Double> xs = new ArrayList<>();
            ArrayList<Double> ys = new ArrayList<>();

            while (x <= b) {
                functionValue = function.getValue(x, y);

                xs.add(x);
                ys.add(y);

                System.out.printf("%.4f %6.6f %6.6f\n", x, y, function.getExactFunctionValue(x));
                previousY = y;
                y = previousY + h * functionValue;
                x += h;
            }
            System.out.println();

            return new Result(function, xs, ys);
        }
    }

    public static class RungeKutta {
        public static Result getValue(Function function, double y0, double a, double b, double h) {
            double x = a;
            double y = y0;
            double previousY;

            double k1;
            double k2;
            double k3;
            double k4;

            ArrayList<Double> xs = new ArrayList<>();
            ArrayList<Double> ys = new ArrayList<>();

            while (x <= b) {
                xs.add(x);
                ys.add(y);

                k1 = h * function.getValue(x, y);
                k2 = h * function.getValue(x + h / 2, y + k1 / 2);
                k3 = h * function.getValue(x + h / 2, y + k2 / 2);
                k4 = h * function.getValue(x + h, y + k3);
                System.out.printf("%.4f %6.6f %6.6f\n", x, y, function.getExactFunctionValue(x));
                previousY = y;
                y = previousY + (double) 1 / 6 * (k1 + 2 * k2 + 2 * k3 + k4);

                x += h;
            }
            System.out.println();

            return new Result(function, xs, ys);
        }
    }

    public static Result getAnyMethodLoop(int equationNumber, double y0, double a, double b, double step, double precision) {

        Result previousResult;
        Result result = DifMath.Euler.getValue(getEquationByNumber(equationNumber), y0, a, b, step);
        int previousSize;

        do {
            step /= 2;
            previousResult = result;
            previousSize = previousResult.getX().size();
            result = DifMath.Euler.getValue(getEquationByNumber(equationNumber), y0, a, b, step);
        } while ((result.getY().get(previousSize * 2 - 1) - previousResult.getY().get(previousSize - 1)) / (Math.pow(2, 1) - 1) >= precision);

        return result;
    }
}
