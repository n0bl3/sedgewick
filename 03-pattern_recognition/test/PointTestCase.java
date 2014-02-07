import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PointTestCase {
    @Test
    public void pointIsGreaterWhenXIsGreaterAndYIsEqual() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 1);

        assertThat(p1, is(lessThan(p2)));
        assertThat(p1, is(not(greaterThan(p2))));
        assertThat(p1, is(not(equalTo(p2))));
    }

    @Test
    public void pointsAreEqualWhenCoordinatesAreTheSame() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);

        // cant use isEqual() because it checks equality based on Object.equals()
        boolean pointsAreEqual = p1.compareTo(p2) == 0;

        assertTrue(pointsAreEqual);
        assertThat(p1, is(not(greaterThan(p2))));
        assertThat(p1, is(not(lessThan(p2))));
    }

    @Test
    public void pointIsGreaterThanTheOtherWhenYIsGreater() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 1);

        assertThat(p1, is(greaterThan(p2)));
        assertThat(p1, is(not(equalTo(p2))));
        assertThat(p1, is(not(lessThan(p2))));
    }

    @Test
    public void pointIsGreaterThanTheOtherWhenYIsEqualAndXIsGreater() {
        Point p1 = new Point(2, 1);
        Point p2 = new Point(1, 1);

        assertThat(p1, is(greaterThan(p2)));
        assertThat(p1, is(not(equalTo(p2))));
        assertThat(p1, is(not(lessThan(p2))));
    }

    @Test(expected = NullPointerException.class)
    public void compareToThrowsExceptionOnNullArgument() {
        Point p = new Point(0, 0);

        p.compareTo(null);
    }

    @Test
    public void comparesWithValidSigns() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);

        assertThat(Math.signum(p1.compareTo(p2)), is(equalTo(-Math.signum(p2.compareTo(p1)))));
    }

    @Test
    public void isTransitive() {
        Point x = new Point(1, 1);
        Point y = new Point(2, 2);
        Point z = new Point(3, 3);

        assertThat(z, is(greaterThan(y)));
        assertThat(y, is(greaterThan(x)));
        assertThat(z, is(greaterThan(x)));
    }

    @Test
    public void slopeIsPositiveZeroForHorizontalLine() {
        Point x = new Point(10, 10);
        Point y = new Point(20, 10);

        Double slope = x.slopeTo(y);

        assertThat(slope, is(equalTo(+0.0)));
    }

    @Test
    public void slopeIsPositiveInfinityForVerticalLine() {
        Point x = new Point(10, 10);
        Point y = new Point(10, 20);
        Double slope = x.slopeTo(y);

        assertThat(slope, is(equalTo(Double.POSITIVE_INFINITY)));
    }

    @Test
    public void slopeIsNegativeInfinityForSlopeBetweenPointAndItself() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);
        Double slope = p1.slopeTo(p2);

        assertThat(slope, is(equalTo(Double.NEGATIVE_INFINITY)));
    }

    @Test
    public void slopeIsEqualRegardlessOfOrigin() {
        Point p1 = new Point(123, 456);
        Point p2 = new Point(321, 654);

        assertThat(p1.slopeTo(p2), is(equalTo(p2.slopeTo(p1))));
    }

    @Test
    public void slopeIsCloseToTanOfAngle() {
        int numPoints = 100;
        int radius = 100000;
        int x0 = 0;
        int y0 = 0;
        for (double angle = 0.0; angle < 2 * Math.PI; angle += 2 * Math.PI/numPoints) {
            Double realX = x0 + radius * Math.cos(angle);
            Double realY = y0 + radius * Math.sin(angle);
            int x = realX.intValue();
            int y = realY.intValue();

            Point p = new Point(x, y);
            double slope = p.slopeTo(new Point(x0, y0));

            if (slope != Double.NEGATIVE_INFINITY && slope != Double.POSITIVE_INFINITY) {
                String assertionMessage = String.format("(%f, %f)->(%d, %d), slope = %.2f, angle = %.2f˚",
                        realX, realY, x, y, slope, 180 * angle / Math.PI);
                assertThat(assertionMessage, slope, is(closeTo(Math.tan(angle), 0.001)));
            }
        }
    }

    private int getY(Point p) {
        try {
            Field yField = Point.class.getDeclaredField("y");
            yField.setAccessible(true);
            return yField.getInt(p);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return Integer.MIN_VALUE;
    }

    @Test
    public void slopeOrderSortGroupsElementsTogether() {
        Point[] points = new Point[] {
                new Point(10, 0),
                new Point(20, 0),
                new Point(30, 0),
                new Point(40, 0),
                new Point(50, 0),
                new Point(60, 0),
                new Point(70, 0),

                new Point(0, 32767),
                new Point(1, 32767),

                new Point(32767, 0),
                new Point(32767, 1),

                new Point(32767, 32767),
        };

        // Do not count invoking point itself
        int numOnSameSlope = -1;
        Point invoking = null;

        for (Point p : points) {
            if (getY(p) == 0) {
                invoking = p;
                break;
            }
        }

        if (invoking == null) {
            throw new NullPointerException("You should have at least one point with Y == 0");
        }

        int invokingY = getY(invoking);

        for (Point p : points) {
            int y = getY(p);

            if (y == invokingY) {
                numOnSameSlope++;
            }
        }

        StdRandom.shuffle(points);
        Point[] sorted = points.clone();
        Arrays.sort(sorted, invoking.SLOPE_ORDER);

        int numAdjacent = 0;
        // First element in sorted array is always the invoking point itself since it has -Infinity slope to itself
        for (int i = 1; i < sorted.length; ++i) {
            if (getY(sorted[i]) == getY(invoking)) {
                numAdjacent++;
                for (int j = i + 1; j < sorted.length && getY(sorted[j]) == getY(invoking); ++j) {
                    numAdjacent++;
                }
                break;
            }
        }

        String message = String.format("Invoking: %s\nOriginal: %s\nSorted: %s",
                invoking, Arrays.toString(points), Arrays.toString(sorted));
        assertThat(message, numAdjacent, is(equalTo(numOnSameSlope)));
    }
}
