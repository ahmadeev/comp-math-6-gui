package backend;

import java.util.Arrays;

import static backend.Methods.*;
import static runnable.Main.*;

public class CalculatedData {

    Functions function;

    private String functionName;

    private double[] x;
    private double[] y;
    private double[] phi;
    private double[] coefficients;

    private double deflectionAmount;
    private double standardDeviation;
    private double determinationCoefficient;

    public CalculatedData(Functions function, double[] x, double[] y) {
        this.function = function;
        this.functionName = function.getClass().getSimpleName();

        this.x = x;
        this.y = y;

        this.coefficients = function.getApproximation(x, y);

        int size = x.length;
        this.phi = new double[size];
        for(int i = 0; i < size; i++) {
            phi[i] = function.getValue(x[i], coefficients);
        }

        this.deflectionAmount = calculateDeflectionAmount(function, x, y, coefficients);
        this.standardDeviation = calculateStandardDeviation(deflectionAmount, size);
        this.determinationCoefficient = calculateDeterminationCoefficient(deflectionAmount, y, phi);
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public double[] getY() {
        return y;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    public double[] getPhi() {
        return phi;
    }

    public void setPhi(double[] phi) {
        this.phi = phi;
    }

    public double[] getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public double getDeflectionAmount() {
        return deflectionAmount;
    }

    public void setDeflectionAmount(double deflectionAmount) {
        this.deflectionAmount = deflectionAmount;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public double getDeterminationCoefficient() {
        return determinationCoefficient;
    }

    public void setDeterminationCoefficient(double determinationCoefficient) {
        this.determinationCoefficient = determinationCoefficient;
    }

    public static class LinearData extends CalculatedData {
        public LinearData(double[] x, double[] y) {
            super(linear, x, y);
        }
    }

    public static class QuadraticData extends CalculatedData {
        public QuadraticData(double[] x, double[] y) {
            super(quadratic, x, y);
        }
    }

    public static class CubicData extends CalculatedData {
        public CubicData(double[] x, double[] y) {
            super(cubic, x, y);
        }
    }

    public static class ExponentialData extends CalculatedData {
        public ExponentialData(double[] x, double[] y) {
            super(exponential, x, y);
        }
    }

    public static class LogarithmicData extends CalculatedData {
        public LogarithmicData(double[] x, double[] y) {
            super(logarithmic, x, y);
        }
    }

    public static class PowerFunctionData extends CalculatedData {
        public PowerFunctionData(double[] x, double[] y) {
            super(powerFunction, x, y);
        }
    }

    @Override
    public String toString() {
        return
        "x: " + Arrays.toString(x) + "\n" +
        "y: " + Arrays.toString(y) + "\n" +
        "phi: " + Arrays.toString(phi) + "\n" +
        "coefficients: " + Arrays.toString(coefficients) + "\n" +
        "deflectionAmount: " + deflectionAmount + "\n" +
        "standardDeviation: " + standardDeviation + "\n" +
        "determinationCoefficient: " + determinationCoefficient + "\n";
    }
}
