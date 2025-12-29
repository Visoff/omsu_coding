package main;

import java.util.Arrays;

import quadratic.Quadratic;

public class MaxRootQuadratic {
    private Quadratic quadratic;

    public MaxRootQuadratic(Quadratic quadratic) throws NullPointerException {
        if (quadratic == null) {
            throw new NullPointerException("Quadratic cannot be null");
        }
        this.quadratic = quadratic;
    }

    public double getMaxRoot() throws NoRootsException, InfiniteRootsException {
        if (quadratic.areInfiniteRoots()) {
            throw new InfiniteRootsException("Quadratic has infinite roots");
        }
        double[] roots = quadratic.getRoots();
        if (roots.length == 0) {
            throw new NoRootsException("Quadratic has no roots");
        }
        return Arrays.stream(roots).max().getAsDouble();
    }
}
