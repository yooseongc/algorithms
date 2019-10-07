package chap12;

import utilities.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 1-D interval data type.
 */
public class Interval1D {

    public static final Comparator<Interval1D> MIN_ENDPOINT_ORDER = (a, b) -> {
        if      (a.min < b.min) return -1;
        else if (a.min > b.min) return +1;
        else if (a.max < b.max) return -1;
        else if (a.max > b.max) return +1;
        else                    return 0;
    };
    public static final Comparator<Interval1D> MAX_ENDPOINT_ORDER = (a, b) -> {
        if      (a.max < b.max) return -1;
        else if (a.max > b.max) return +1;
        else if (a.min < b.min) return -1;
        else if (a.min > b.min) return +1;
        else                    return  0;
    };
    public static final Comparator<Interval1D> LENGTH_ORDER = (a, b) -> {
        double alen = a.length();
        double blen = b.length();
        if      (alen < blen) return -1;
        else if (alen > blen) return +1;
        else                  return  0;
    };

    private final double min;
    private final double max;

    public Interval1D(double min, double max) {
        if (Double.isInfinite(min) || Double.isInfinite(max)) throw new IllegalArgumentException("Endpoints must be finite");
        if (Double.isNaN(min) || Double.isNaN(max)) throw new IllegalArgumentException("Endpoints cannot be NaN");

        // convert -0.0 to +0.0
        if (min == 0.0) min = 0.0;
        if (max == 0.0) max = 0.0;

        if (min > max) throw new IllegalArgumentException("Illegal interval");
        this.min = min;
        this.max = max;
    }

    public double min() { return min; }
    public double max() { return max; }
    public double length() { return max - min; }

    public boolean intersects(Interval1D that) {
        if (this.max < that.min) return false;    // [  this.min ... this.max < that.min ... that.max ]
        if (that.max < this.min) return false;    // [  that.min ... that.max < this.min ... this.max ]
        return true;
    }

    public boolean contains(double x) {
        return (min <= x) && (x <= max);
    }

    @Override
    public String toString() {
        return "[" + min + ", " + max + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Interval1D that = (Interval1D) other;
        return this.min == that.min && this.max == that.max;
    }

    @Override
    public int hashCode() {
        int hash1 = ((Double) min).hashCode();
        int hash2 = ((Double) max).hashCode();
        return 31*hash1 + hash2;
    }

    public static void main(String[] args) {
        Interval1D[] intervals = new Interval1D[4];
        intervals[0] = new Interval1D(15.0, 33.0);
        intervals[1] = new Interval1D(45.0, 60.0);
        intervals[2] = new Interval1D(20.0, 70.0);
        intervals[3] = new Interval1D(46.0, 55.0);

        StdOut.println("Unsorted");
        for (int i = 0; i < intervals.length; i++)
            StdOut.println(intervals[i]);
        StdOut.println();

        StdOut.println("Sort by min endpoint");
        Arrays.sort(intervals, Interval1D.MIN_ENDPOINT_ORDER);
        for (int i = 0; i < intervals.length; i++)
            StdOut.println(intervals[i]);
        StdOut.println();

        StdOut.println("Sort by max endpoint");
        Arrays.sort(intervals, Interval1D.MAX_ENDPOINT_ORDER);
        for (int i = 0; i < intervals.length; i++)
            StdOut.println(intervals[i]);
        StdOut.println();

        StdOut.println("Sort by length");
        Arrays.sort(intervals, Interval1D.LENGTH_ORDER);
        for (int i = 0; i < intervals.length; i++)
            StdOut.println(intervals[i]);
        StdOut.println();
    }

}
