package backend;

import static backend.Utils.exit;
import static runnable.Main.*;

public class Methods {
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
}
