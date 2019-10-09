package chap12;

import utilities.StdDraw;

/**
 * Immutable data type for 2D axis-aligned rectangle.
 */
public final class RectHV {

    private final double xmin, ymin;
    private final double xmax, ymax;

    public RectHV(double xmin, double ymin, double xmax, double ymax) {
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        check();
    }

    private void check() {
        if (Double.isNaN(xmin) || Double.isNaN(xmax)) throw new IllegalArgumentException("x-coordinate is NaN: " + toString());
        if (Double.isNaN(ymin) || Double.isNaN(ymax)) throw new IllegalArgumentException("y-coordinate is NaN: " + toString());
        if (xmax < xmin) {
            throw new IllegalArgumentException("xmax < xmin: " + toString());
        }
        if (ymax < ymin) {
            throw new IllegalArgumentException("ymax < ymin: " + toString());
        }
    }

    public double xmin() { return xmin; }
    public double xmax() { return xmax; }
    public double ymin() { return ymin; }
    public double ymax() { return ymax; }
    public double width() { return xmax - xmin; }
    public double height() { return ymax - ymin; }

    /**
     * returns true if the two rectangles intersect.
     */
    public boolean intersects(RectHV that) {
        return this.xmax >= that.xmin && this.ymax >= that.ymin && that.xmax >= this.xmin && that.ymax >= this.ymin;
    }

    public boolean contains(Point2D p) {
        return (p.x() >= xmin) && (p.x() <= xmax) && (p.y() >= ymin) && (p.y() <= ymax);
    }

    public double distanceTo(Point2D p) {
        return Math.sqrt(this.distanceSquaredTo(p));
    }

    /**
     * returns the square of the Euclidean distance between this rectangle and the point {@code p}
     * @param p the point
     * @return the square of the Euclidean distance between the point {@code p} and the closest point on this rectangle;
     *         0 if the point is contained in this rectangle.
     */
    public double distanceSquaredTo(Point2D p) {
        double dx = 0.0, dy = 0.0;
        if      (p.x() < xmin) dx = p.x() - xmin;
        else if (p.x() > xmax) dx = p.x() - xmax;
        if      (p.y() < ymin) dy = p.y() - ymin;
        else if (p.y() > ymax) dy = p.y() - ymax;
        return dx * dx + dy * dy;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        RectHV that = (RectHV) other;
        if (this.xmin != that.xmin) return false;
        if (this.ymin != that.ymin) return false;
        if (this.xmax != that.xmax) return false;
        if (this.ymax != that.ymax) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash1 = ((Double) xmin).hashCode();
        int hash2 = ((Double) ymin).hashCode();
        int hash3 = ((Double) xmax).hashCode();
        int hash4 = ((Double) ymax).hashCode();
        return 31*(31*(31*hash1 + hash2) + hash3) + hash4;
    }

    @Override
    public String toString() {
        return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
    }

    public void draw() {
        StdDraw.line(xmin, ymin, xmax, ymin);
        StdDraw.line(xmax, ymin, xmax, ymax);
        StdDraw.line(xmax, ymax, xmin, ymax);
        StdDraw.line(xmin, ymax, xmin, ymin);
    }

}
