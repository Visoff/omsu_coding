package main;

import quadratic.Quadratic;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Quadratic quadratic = new Quadratic(1, 0, -1);
        double[] roots = quadratic.getRoots();
        System.out.println(roots[0]);
        System.out.println(roots[1]);
    }
}
