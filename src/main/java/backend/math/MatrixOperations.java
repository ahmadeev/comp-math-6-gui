package backend.math;

import java.util.Arrays;

import static java.util.Objects.isNull;

public class MatrixOperations {
    public static int numberOfSwitches = 0;

    public static double[][] matrixToTriangle(double[][] extendedMatrix) {
        int size = extendedMatrix.length;

        for (int i = 0; i < size; i++) {
            if (extendedMatrix[i][i] != 0 && extendedMatrix[i][i] != 1) {
                extendedMatrix[i] = mul(extendedMatrix, 1 / extendedMatrix[i][i], i);
            } else if (extendedMatrix[i][i] == 0) {
                sortArray(extendedMatrix);
            }
            for (int k = i + 1; k < size; k++) {
                double[] multipliedLine = mul(
                        extendedMatrix,
                        findCoefficient(extendedMatrix[i][i], extendedMatrix[k][i]),
                        i);
                extendedMatrix[k] = sum(extendedMatrix, multipliedLine, k);
                switch (checkIfSolutionExists(extendedMatrix, k)) {
                    case (-1): {
                        System.out.println("Система не имеет решений!");
                        System.exit(0);
                        break;
                    }
                    case 1: {
                        System.out.println("Система имеет бесконечно много решений!");
                        System.exit(0);
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        }
        return extendedMatrix;
    }

    public static void getAnswer(double[][] extendedMatrix) {
        int size = extendedMatrix.length;
        for (int i = size - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                double[] multipliedLine = mul(
                        extendedMatrix,
                        findCoefficient(extendedMatrix[i][i], extendedMatrix[j][i]),
                        i);
                extendedMatrix[j] = sum(extendedMatrix, multipliedLine, j);

            }
        }
    }

    public static double[] getXFromExtendedMatrix(double[][] extendedMatrix) {
        int size = extendedMatrix.length;
        double[] x = new double[size];
        for(int i = 0; i < size; i++) {
            //  костыль в виде деления
            x[i] = extendedMatrix[i][size] / extendedMatrix[i][i];
        }
        return x;
    }

    public static double[] mul(double[][] extendedMatrix, double coefficient, int linePosition) {
        int subsize = extendedMatrix[0].length;
        double[] result = new double[subsize];

        for (int i = 0; i < subsize; i++) {
            result[i] = extendedMatrix[linePosition][i] * coefficient;
            if (result[i] == -0.0) result[i] = 0;
        }
        return result;
    }

    public static double[] sum(double[][] extendedMatrix, double[] line, int secondLinePosition) {
        int subsize = extendedMatrix[0].length;
        double[] result = new double[subsize];

        for (int i = 0; i < subsize; i++) {
            result[i] = line[i] + extendedMatrix[secondLinePosition][i];
            if (result[i] == -0.0) result[i] = 0;
        }

        return result;
    }

    public static double findCoefficient(double a, double b) {
        if (a == 0.0d) return 1.0d;
        return -b/a;
    }

    public static int checkIfSolutionExists(double[][] extendedMatrix, int linePosition) {
        int size = extendedMatrix.length;
        for (int i = 0; i < size; i++) {
            if (extendedMatrix[linePosition][i] != 0) {
                return 0;
            }
        }
        if (extendedMatrix[linePosition][size] == 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public static void sortArray(double[][] extendedMatrix) {
        int size = extendedMatrix.length;
        int[] counters = new int[size];

        for (int i = 0; i < size; i++) {
            int zeroCounter = 0;
            for (int j = 0; j < size && extendedMatrix[i][j] == 0; j++) {
                zeroCounter++;
            }
            counters[i] = zeroCounter;
        }

        for (int numberOfRepetions = 0; numberOfRepetions < size; numberOfRepetions++){
            for (int i = 1; i < size; i++) {
                if (counters[i - 1] > counters[i]) {
                    int tempInt = counters[i - 1];
                    counters[i - 1] = counters[i];
                    counters[i] = tempInt;

                    double[] tempArrDbl = extendedMatrix[i - 1];
                    extendedMatrix[i - 1] = extendedMatrix[i];
                    extendedMatrix[i] = tempArrDbl;

                    numberOfSwitches++;
                }
            }
        }

/*        for (int i = size - 1; i > 0; i--) {
            if (counters[i-1] > counters[i]) {
                int tempInt = counters[i-1];
                counters[i-1] = counters[i];
                counters[i] = tempInt;

                double[] tempArrDbl = extendedMatrix[i-1];
                extendedMatrix[i-1] = extendedMatrix[i];
                extendedMatrix[i] = tempArrDbl;

                numberOfSwitches++;
            }
        }*/
    }

    public static Result getResultFromExtendedMatrix(double[][] extendedMatrix) {
        int size = extendedMatrix.length;
        double[][] matrix = new double[size][size];
        double[] matrixExtension = new double[size];
        for (int i = 0; i < size; i++) {
            matrix[i] = Arrays.copyOf(extendedMatrix[i], size);
            matrixExtension[i] = extendedMatrix[i][size];
        }
        return new Result(matrix, matrixExtension);
    }

    public static void printMatrix(String msg, double[][] matrixA, double[] matrixB) {

        if (isNull(matrixA) && isNull(matrixB)) {
            System.out.println("Матрица и матричное дополнение не были заданы!");
            System.exit(1);
        } else if (isNull(matrixA)) {
            System.out.println("Матрица не была задана!");
            System.exit(1);
        } else if (isNull(matrixB)) {
            System.out.println("Матричное дополнение не было задано!");
            System.exit(1);
        }

        System.out.println(msg);
        for (int i = 0; i < matrixA.length; i++) {
            for (double j : matrixA[i]){
                System.out.printf("%8.2f", j);
            }
            System.out.println(" | " + String.format("%8.2f", matrixB[i]));
        }
        System.out.println();

    }

    public static void printMatrix(String msg, Result result) {
        double[][] matrixA = result.matrix();
        double[] matrixB = result.matrixExtension();
        printMatrix(msg, matrixA, matrixB);
    }

    public static void printMatrix(String msg, double[][] extendedMatrix) {
        Result result = getResultFromExtendedMatrix(extendedMatrix);
        printMatrix(msg, result);
    }
}
