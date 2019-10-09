package chap12;

import utilities.StdOut;

/**
 * Immutable ADT for Rational numbers.
 */
public class Rational implements Comparable<Rational> {

    private static Rational zero = new Rational(0, 1);

    private long numerator;
    private long denominator;

    public Rational(long numerator, long denominator) {
        // deal with x / 0
        if (denominator == 0) throw new ArithmeticException("denominator is zero.");

        // reduce fraction using Euclidean algorithm (GCD)
        long g = gcd(numerator, denominator);
        this.numerator = numerator / g;
        this.denominator = denominator / g;

        // only needed for negative numbers
        if (this.denominator < 0) {
            this.denominator = -this.denominator;
            this.numerator = -this.numerator;
        }
    }

    public long numerator()   { return numerator;   }
    public long denominator() { return denominator; }

    public double toDouble() { return (double) numerator / denominator; }

    @Override
    public String toString() {
        if (denominator == 1) return numerator + "";
        else                  return numerator + "/" + denominator;
    }

    @Override
    public int compareTo(Rational that) {
        long lhs = this.numerator * that.denominator;
        long rhs = this.denominator * that.numerator;
        return Long.compare(lhs, rhs);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Rational that = (Rational) other;
        return this.compareTo(that) == 0;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * create and return a new rational (r.numerator + s.numerator) / (r.denominator + s.denominator)
     */
    public static Rational mediant(Rational r, Rational s) {
        return new Rational(r.numerator + s.numerator, r.denominator + s.denominator);
    }

    private static long gcd(long m, long n) {
        if (m < 0) m = -m;
        if (n < 0) n = -n;
        if (0 == n) return m;
        else        return gcd(n, m % n);
    }

    private static long lcm(long m, long n) {
        if (m < 0) m = -m;
        if (n < 0) n = -n;
        return m * (n / gcd(m, n)); // parantheses important to avoid overflow
    }

    public Rational plus(Rational that) {

        // special cases
        if (this.compareTo(zero) == 0) return new Rational(that.numerator, that.denominator);
        if (that.compareTo(zero) == 0) return new Rational(numerator, denominator);

        // find gcd of numerators and denominators
        long f = gcd(numerator, that.numerator);
        long g = gcd(denominator, that.denominator);

        assert (this.numerator / f) * (that.denominator / g) <= Integer.MAX_VALUE : "Operation would cause overflow";
        assert (that.numerator / f) * (this.denominator / g) <= Integer.MAX_VALUE : "Operation would cause overflow";
        assert (this.numerator / f) * (that.denominator / g) + (that.numerator / f) * (this.denominator / g) <= Integer.MAX_VALUE : "Operation would cause overflow";
        assert this.denominator * (that.denominator / g) <= Integer.MAX_VALUE : "Operation would cause overflow";

        // add cross-product terms for numerator
        Rational s = new Rational(
                (this.numerator / f) * (that.denominator / g) + (that.numerator / f) * (this.denominator / g),
                this.denominator * (that.denominator / g)
        );
        s.numerator *= f;
        return s;
    }

    public Rational minus(Rational that) {
        return this.plus(that.negate());
    }

    /**
     * return this * that. staving off overflow as much as possible by cross-cancellation
     * @param that
     * @return
     */
    public Rational times(Rational that) {
        // reduce p1/q2 and p2/q1. then multiply, where a = p1/q1 and b = p2/q2
        Rational c = new Rational(this.numerator, that.denominator);
        Rational d = new Rational(that.numerator, this.denominator);

        assert c.numerator * d.numerator <= Integer.MAX_VALUE : "Operation would cause overflow";
        assert c.denominator * d.denominator <= Integer.MAX_VALUE : "Operation would cause overflow";

        return new Rational(c.numerator * d.numerator, c.denominator * d.denominator);
    }

    public Rational dividedBy(Rational that) {
        return this.times(that.reciprocal());
    }

    public Rational negate() {
        return new Rational(-numerator, denominator);
    }

    public Rational abs() {
        return (numerator >= 0) ? new Rational(numerator, denominator) : negate();
    }

    public Rational reciprocal() {
        return new Rational(denominator, numerator);
    }

    public static void main(String[] args) {
        Rational x, y, z;

        // 1/2 + 1/3 = 5/6
        x = new Rational(1, 2);
        y = new Rational(1, 3);
        z = x.plus(y);
        StdOut.println(z);

        // 8/9 + 1/9 = 1
        x = new Rational(8, 9);
        y = new Rational(1, 9);
        z = x.plus(y);
        StdOut.println(z);

        // 1/200000000 + 1/300000000 = 1/120000000
        x = new Rational(1, 200000000);
        y = new Rational(1, 300000000);
        z = x.plus(y);
        StdOut.println(z);

        // 1073741789/20 + 1073741789/30 = 1073741789/12
        x = new Rational(1073741789, 20);
        y = new Rational(1073741789, 30);
        z = x.plus(y);
        StdOut.println(z);

        //  4/17 * 17/4 = 1
        x = new Rational(4, 17);
        y = new Rational(17, 4);
        z = x.times(y);
        StdOut.println(z);

        // 3037141/3247033 * 3037547/3246599 = 841/961
        x = new Rational(3037141, 3247033);
        y = new Rational(3037547, 3246599);
        z = x.times(y);
        StdOut.println(z);

        // 1/6 - -4/-8 = -1/3
        x = new Rational(1, 6);
        y = new Rational(-4, -8);
        z = x.minus(y);
        StdOut.println(z);
    }

}
