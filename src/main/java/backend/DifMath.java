package backend;

import java.util.ArrayList;

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

                System.out.printf("%.1f %6.6f %6.6f\n", x, y, functionValue);
                previousY = y;
                y = previousY + h * functionValue;
                x += h;
            }
            System.out.println();

            return new Result(xs, ys);
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
                System.out.printf("%.1f %6.6f\n", x, y);
                previousY = y;
                y = previousY + (double) 1 / 6 * (k1 + 2 * k2 + 2 * k3 + k4);

                x += h;
            }
            System.out.println();

            return new Result(xs, ys);
        }
    }
}
