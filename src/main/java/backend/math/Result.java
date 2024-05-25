package backend.math;

public record Result(double[][] matrix, double[] matrixExtension) {
/*  record-класс вместо
        private final double[][] matrix;
        private final double[] matrixExtension;

        public Result(double[][] matrix, double[] matrixExtension) {
            this.matrix = matrix;
            this.matrixExtension = matrixExtension;
        }

        public double[][] getMatrix() {
            return matrix;
        }

        public double[] getMatrixExtension() {
            return matrixExtension;
        }
*/
}
