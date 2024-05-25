package backend;

import java.util.Arrays;

import static backend.math.MatrixOperations.*;

public class Methods {

    public static double[] getPolynomialApproximation(int n, double[] x, double[] y) {

        int size = x.length;

        double[][] matrix = new double[n][n + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < size; k++) {
                    matrix[i][j] += Math.pow(x[k], i + j);
                }
            }

            for (int l = 0; l < size; l++) {
                matrix[i][n] += Math.pow(x[l], i) * y[l];
            }
        }

/*        for (double[] i : matrix) {
            System.out.println(Arrays.toString(i));
        }*/

        matrixToTriangle(matrix);
        getAnswer(matrix);
        x = getXFromExtendedMatrix(matrix);

        return x;
    }

}
