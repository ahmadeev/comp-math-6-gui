package backend;

import static backend.Utils.exit;
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
}
