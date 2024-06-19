package backend;

import java.math.BigDecimal;
import java.util.ArrayList;

import static backend.Utils.exit;
import static java.util.Objects.isNull;
import static runnable.Main.*;

public abstract class Methods {
    public abstract Result getValue(Function function, double y0, double a, double b, double h);

    public static Function getEquationByNumber(int number) {
        switch (number) {
            case 1: {
                return functionOne;
            }
            case 2: {
                return functionTwo;
            }
            case 3: {
                return functionThree;
            }
            default: {
                exit("", 1);
                return null;
            }
        }
    }

    public static Methods getMethodByNumber(int number) {
        switch (number) {
            case 1: {
                return euler;
            }
            case 2: {
                return rungeKutta;
            }
            case 3: {
                return adams;
            }
            default: {
                exit("", 1);
                return null;
            }
        }
    }

    public static class Euler extends Methods {
        public Result getValue(Function function, double y0, double a, double b, double h) {
            System.out.println("начинаю эйлерную");

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

                x = BigDecimal.valueOf(x).add(BigDecimal.valueOf(h)).doubleValue();
            }
            System.out.println();

            return new Result(function, xs, ys);
        }
    }

    public static class RungeKutta extends Methods {
        public Result getValue(Function function, double y0, double a, double b, double h) {
            System.out.println("начинаю рунгекутту");

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

                x = BigDecimal.valueOf(x).add(BigDecimal.valueOf(h)).doubleValue();
            }
            System.out.println();

            return new Result(function, xs, ys);
        }
    }

    public static class Adams extends Methods {

        @Override
        public Result getValue(Function function, double y0, double a, double b, double h) {

            Result firstThree = rungeKutta.getValue(function, y0, a, a + 3 * h, h);

            ArrayList<Double> xs = firstThree.getX();
            ArrayList<Double> ys = firstThree.getY();

            return new Result(function, xs, ys);
        }
    }

    public static Result getAnyMethodLoop(int methodNumber, Function function, double y0, double a, double b, double step, double precision, int power) {

        Methods method = getMethodByNumber(methodNumber);
        if (isNull(method)) {
            exit("", 1);
            return null;
        }

        Result previousResult;
        Result result = method.getValue(function, y0, a, b, step);
        int previousSize;

        do {
            step /= 2;
            previousResult = result;
            previousSize = previousResult.getX().size();
            result = method.getValue(function, y0, a, b, step);
        } while ((result.getY().get(previousSize * 2 - 2) - previousResult.getY().get(previousSize - 1)) / (Math.pow(2, power) - 1) >= precision);

        return result;
    }
}
