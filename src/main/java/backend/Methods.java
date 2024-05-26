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

        printMatrix("", matrix);
        matrixToTriangle(matrix);
        printMatrix("", matrix);
        getAnswer(matrix);
        printMatrix("", matrix);
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

}
