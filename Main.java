/**
 * Main
 */

import com.google.common.base.Stopwatch;

public class Main {

    public static void main(String[] args) {
        exec_tests();
    }
    
    public static void exec_tests(){
        test(0.0, 10.0, 20, 1226.01164);
        test(1.0, 10.0, 9, 1227.88713);
        test(1.0, 10.0, 4, 1252.15426);
        test(1.0, 10.0, 5, 1241.26669); 
        /**
         * https://es.symbolab.com/solver/step-by-step/trapezoidal%20%5Cint_%7B1%7D%5E%7B10%7D4x%C2%B2-3x%2B2%2Bx%5E%7B%5Cfrac%7B1%7D%7B2%7D%7Ddx%2C%20n%3D5?or=input
         */
        test(1.0, 10.0, 50, 1222.108661850008444);
        /**
         * https://www.emathhelp.net/calculators/calculus-2/trapezoidal-rule-calculator/?f=4+*+x+*+x+-+3+*+x+%2B+2+%2B+sqrt%28x%29&a=1&b=10&n=50
         */
    }

    public static void test(double ini, double fin, int ite, double ans){
        Riemman R = new Riemman(ini, fin, ite);
        double sum = 0.0;
        
        sum = R.parallel();
        System.out.println(eqDouble(sum, ans)? 
            "Test correct (sec), ite= " + ite + "\tans= " + sum  : 
            "Test fail in secuential (" + ans + " : " + sum  + ") (Expected:Given)"
        );
        
        sum = R.parallel();
        System.out.println(eqDouble(sum, ans)? 
            "Test correct (par), ite= " + ite + "\tans= " + sum  : 
            "Test fail in parallel (" + ans + " : " + sum  + ") (Expected:Given)"
        );
    }

    public static boolean eqDouble(double a, double b){
        return Math.abs(a-b) < 1e-5;
    }
}