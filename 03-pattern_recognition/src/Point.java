import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrderComparator(this);

    private class SlopeOrderComparator implements Comparator<Point> {
        private Point invoking;

        private SlopeOrderComparator(Point invoking) {
            this.invoking = invoking;
        }

        @Override
        public int compare(Point first, Point second) {
            double firstSlope = invoking.slopeTo(first);
            double secondSlope = invoking.slopeTo(second);

            if (firstSlope < secondSlope) return -1;
            else if (firstSlope > secondSlope) return +1;
            else return 0;
        }
    }

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that.x == this.x && that.y == this.y) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.x == that.x) {
            // vertical line
            return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y) {
            // horizontal line
            return +0.0;
        }

        return (double)(that.y - this.y)/(double)(that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that == null) {
            throw new NullPointerException("You can't compare to null since null is not an instance of any object");
        }

        if (this.y < that.y || this.y == that.y && this.x < that.x) {
            return -1;
        } else if (this.x == that.x && this.y == that.y) {
            return 0;
        }

        return +1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
