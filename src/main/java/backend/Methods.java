package backend;

import static backend.math.MatrixOperations.*;
import static backend.math.Utils.exit;
import static runnable.Main.*;

public class Methods {

    /*  Порядок функций:
    1. Линейная
    2. Полиноминальная второй степени
    3. Полиноминальная третьей степени
    4. Экспоненциальная
    5. Логарифмическая
    6. Степенная
     */

    public static Functions getFunctionByNumber(int number) {
        switch (number) {
            case 1: {
                return linear;
            }
            case 2: {
                return quadratic;
            }
            case 3: {
                return cubic;
            }
            case 4: {
                return exponential;
            }
            case 5: {
                return logarithmic;
            }
            case 6: {
                return powerFunction;
            }
            default: {
                exit("че-то с выбором функций блин", 1);
                return null;
            }
        }
    }

    public static double getDeflectionAmount(Functions function, double[] x, double[] y) {
        double result = 0;
        for(int i = 0; i < x.length; i++) {
            result += Math.pow(function.getValue(x[i], function.getApproximation(x, y)), 2);
        }
        return result;
    }

    public static double getStandardDeviation(Functions function, double[] x, double[] y) {
        return Math.pow(getDeflectionAmount(function, x, y) / x.length, 0.5);
    }

    public static class Polynomial {
        public double[] getApproximation(int n, double[] x, double[] y) {

            int size = x.length;

            double[][] matrix = new double[n + 1][n + 1 + 1];

            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= n; j++) {
                    for (int k = 0; k < size; k++) {
                        matrix[i][j] += Math.pow(x[k], i + j);
                    }
                }

                for (int l = 0; l < size; l++) {
                    matrix[i][n + 1] += Math.pow(x[l], i) * y[l];
                }
            }

            matrixToTriangle(matrix);
            getAnswer(matrix);
            x = getXFromExtendedMatrix(matrix);

            return x;
        }

        public double getValue(double x, double[] coefficients) {
            int size = coefficients.length;

            double result = 0;
            for(int i = 0; i < size; i++) {
                result += coefficients[i] * Math.pow(x, i);
            }

            return result;
        }
    }

    public static class Linear extends Functions {

        @Override
        public double[] getApproximation(double[] x, double[] y) {
            return polynomial.getApproximation(1, x, y);
        }

        @Override
        public double getValue(double x, double[] coefficients) {
            return polynomial.getValue(x, coefficients);
        }
    }

    public static class Quadratic extends Functions {

        @Override
        public double[] getApproximation(double[] x, double[] y) {
            return polynomial.getApproximation(2, x, y);
        }

        @Override
        public double getValue(double x, double[] coefficients) {
            return polynomial.getValue(x, coefficients);
        }
    }

    public static class Cubic extends Functions {

        @Override
        public double[] getApproximation(double[] x, double[] y) {
            return polynomial.getApproximation(3, x, y);
        }

        @Override
        public double getValue(double x, double[] coefficients) {
            return polynomial.getValue(x, coefficients);
        }
    }

    public static class Exponential extends Functions {
        @Override
        public double[] getApproximation(double[] x, double[] y) {

            //  общая часть всех подобных функций
            int size = x.length;
            double[] modifiedX = new double[size];
            double[] modifiedY = new double[size];

            //  изменяемая часть
            for(int i = 0; i < size; i++) {
                modifiedX[i] = x[i];
                modifiedY[i] = Math.log(y[i]);
            }

            //  общая часть всех подобных функций
            double[] coefficients = polynomial.getApproximation(1, modifiedX, modifiedY);

            //  изменяем коэффициенты A и B на a и b
            coefficients[0] = Math.exp(coefficients[0]);

            return coefficients;
        }

        @Override
        public double getValue(double x, double[] coefficients) {
            return coefficients[0] * Math.exp(coefficients[1] * x);
        }
    }

    public static class Logarithmic extends Functions {
        Polynomial polynomial = new Polynomial();

        @Override
        public double[] getApproximation(double[] x, double[] y) {

            //  общая часть всех подобных функций
            int size = x.length;
            double[] modifiedX = new double[size];
            double[] modifiedY = new double[size];

            //  изменяемая часть
            for(int i = 0; i < size; i++) {
                modifiedX[i] = Math.log(x[i]);
                modifiedY[i] = y[i];
            }

            //  общая часть всех подобных функций
            double[] coefficients = polynomial.getApproximation(1, modifiedX, modifiedY);
            //  притом сначала должен возвращаться коэффициент b для формулы y = a * lnx + b

            return coefficients;
        }

        @Override
        public double getValue(double x, double[] coefficients) {
            return coefficients[1] * Math.log(x) + coefficients[0];
        }
    }

    public static class PowerFunction extends Functions {
        @Override
        public double[] getApproximation(double[] x, double[] y) {
            Polynomial polynomial = new Polynomial();

            //  общая часть всех подобных функций
            int size = x.length;
            double[] modifiedX = new double[size];
            double[] modifiedY = new double[size];

            //  изменяемая часть
            for(int i = 0; i < size; i++) {
                modifiedX[i] = Math.log(x[i]);
                modifiedY[i] = Math.log(y[i]);
            }

            //  общая часть всех подобных функций
            double[] coefficients = polynomial.getApproximation(1, modifiedX, modifiedY);

            //  изменяем коэффициенты A и B на a и b
            coefficients[0] = Math.exp(coefficients[0]);

            return coefficients;
        }

        @Override
        public double getValue(double x, double[] coefficients) {
            return coefficients[0] * Math.pow(x, coefficients[1]);
        }
    }
}
