import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Riemman extends RecursiveTask<Double> {
    /**
     * Particion: [x0, x1, x2, ... , xn]
     * ini = x0 
     * fin = xn
     * h * [ (f(ini) + f(fin))/2 + Sum(f(xi), i = 1 to n-1) ] 
     */

    public int ite;
    public double ini;
    public double fin;
    public double delta;

    public Riemman(double ini, double fin, int ite) {
        this.ite = ite;
        this.ini = ini;
        this.fin = fin;
        
        this.delta = (this.fin - this.ini)/ this.ite;
    }

    public double f(double x){
        return 4 * x * x - 3 * x + 2 + Math.sqrt(x);
    }

    public double secuential(){
        return recSeqSumTrapezoids(ini, fin, ite);
    }

    public double recSeqSumTrapezoids(double ini, double fin, int ite){
        /** Recursive sequential sum of trapezoids */
        if (ite == 1)
            return this.delta * (f(ini) + f(fin)) / 2;
    
        int ite_sub1 = ite / 2;
        int ite_sub2 = ite - ite_sub1;

        double middle = ini + this.delta * ite_sub1;
        
        double s1 = recSeqSumTrapezoids(ini, middle, ite_sub1);
        double s2 = recSeqSumTrapezoids(middle, fin, ite_sub2);

        return s1 + s2;
    }
    
    public double parallel(){
        ForkJoinPool pool = new ForkJoinPool();
        Riemman s1 = new Riemman(ini, fin, this.ite);
        return pool.invoke(s1);
    }
    
    @Override
    protected Double compute() {
        // TODO Auto-generated method stub
        /** Recursive parallel sum of trapezoids */
        
        if (ite == 1)
            return this.delta * (f(ini) + f(fin)) / 2;
        
        int ite_sub1 = ite / 2;
        int ite_sub2 = ite - ite_sub1;
        
        double middle = ini + this.delta * ite_sub1;
        
        Riemman s1 = new Riemman(ini, middle, ite_sub1);
        Riemman s2 = new Riemman(middle, fin, ite_sub2);
        
        s2.fork();
        return s1.compute() + s2.join();
        
    }
}
