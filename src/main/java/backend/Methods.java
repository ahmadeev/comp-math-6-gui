package backend;

import java.util.Arrays;

import static backend.math.MatrixOperations.*;

public class Methods {

    public static double[] getPolynomialApproximation(int n, double[] x, double[] y) {

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

/*        for (double[] i : matrix) {
            System.out.println(Arrays.toString(i));
        }*/

        //printMatrix("", matrix);
        matrixToTriangle(matrix);
        //printMatrix("", matrix);
        getAnswer(matrix);
        //printMatrix("", matrix);
        x = getXFromExtendedMatrix(matrix);

        return x;
    }

    public static double getPolynomialValue(double x, double[] coefficients) {
        int size = coefficients.length;

        double result = 0;
        for(int i = 0; i < size; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }

        return result;
    }

    public static double[] getLogarithmicApproximation(double[] x, double[] y) {

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
        double[] coefficients = getPolynomialApproximation(1, modifiedX, modifiedY);
        //  притом сначала должен возвращаться коэффициент b для формулы y = a * lnx + b

        return coefficients;
    }

    public static double getLogarithmicValue(double x, double[] coefficients) {
        return coefficients[1] * Math.log(x) + coefficients[0];
    }

    public static double[] getExponentialApproximation(double[] x, double[] y) {

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
        double[] coefficients = getPolynomialApproximation(1, modifiedX, modifiedY);

        //  изменяем коэффициенты A и B на a и b
        coefficients[0] = Math.exp(coefficients[0]);

        return coefficients;
    }

    public static double getExponentialValue(double x, double[] coefficients) {
        return coefficients[0] * Math.exp(coefficients[1] * x);
    }

    public static double[] getPowerFunctionApproximation(double[] x, double[] y) {

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
        double[] coefficients = getPolynomialApproximation(1, modifiedX, modifiedY);

        //  изменяем коэффициенты A и B на a и b
        coefficients[0] = Math.exp(coefficients[0]);

        return coefficients;
    }

    public static double getPowerFunctionValue(double x, double[] coefficients) {
        return coefficients[0] * Math.pow(x, coefficients[1]);
    }
}
