package chap12;

import utilities.StdOut;

/**
 * Implementation of a vector of real numbers.
 *
 * This class is implemented to be immutable
 */
public class Vector {

    private int dimension;
    private double[] data;

    public Vector(int dimension) {
       this.dimension = dimension;
       data = new double[dimension];
    }

    public Vector(double... a) {
       dimension = a.length;
       // depensive copy
       data = new double[dimension];
       for (int i = 0; i < dimension; i++) {
           data[i] = a[i];
       }
    }

    public int dimension() { return dimension; }

    public double dot(Vector that) {
       if (this.dimension != that.dimension) throw new IllegalArgumentException("Dimensions don't agree.");
       double sum = 0.0;
       for (int i = 0; i < dimension; i++) {
           sum += (this.data[i] * that.data[i]);
       }
       return sum;
    }

    public double magnitude() { return Math.sqrt(this.dot(this)); }

    public double distanceTo(Vector that) {
       if (dimension != that.dimension) throw new IllegalArgumentException("Dimensions don't agree.");
       return this.minus(that).magnitude();
    }

    public Vector plus(Vector that) {
       if (dimension != that.dimension) throw new IllegalArgumentException("Dimensions don't agree.");
       Vector c = new Vector(dimension);
       for (int i = 0; i < dimension; i++) {
           c.data[i] = this.data[i] + that.data[i];
       }
       return c;
    }

    public Vector minus(Vector that) {
       if (dimension != that.dimension) throw new IllegalArgumentException("Dimensions don't agree.");
       Vector c = new Vector(dimension);
       for (int i = 0; i < dimension; i++) {
           c.data[i] = this.data[i] - that.data[i];
       }
       return c;
    }

    public double cartesian(int i) {
       if (i < 0 || i >= dimension) throw new IllegalArgumentException("Dimensions don't agree.");
       return data[i];
    }

    public Vector scale(double alpha) {
       Vector c = new Vector(dimension);
       for (int i = 0; i < dimension; i++) {
           c.data[i] = alpha * data[i];
       }
       return c;
    }

    public Vector direction() {
       if (this.magnitude() == 0.0) throw new ArithmeticException("Zero-vector has no direction");
       return this.scale(1.0 / this.magnitude());
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < dimension; i++)
            s.append(data[i] + " ");
        return s.toString();
    }

    public static void main(String[] args) {
        double[] xdata = { 1.0, 2.0, 3.0, 4.0 };
        double[] ydata = { 5.0, 2.0, 4.0, 1.0 };
        Vector x = new Vector(xdata);
        Vector y = new Vector(ydata);

        StdOut.println("   x       = " + x);
        StdOut.println("   y       = " + y);

        Vector z = x.plus(y);
        StdOut.println("   z       = " + z);

        z = z.scale(10.0);
        StdOut.println(" 10z       = " + z);

        StdOut.println("  |x|      = " + x.magnitude());
        StdOut.println(" <x, y>    = " + x.dot(y));
        StdOut.println("dist(x, y) = " + x.distanceTo(y));
        StdOut.println("dir(x)     = " + x.direction());
    }

}
