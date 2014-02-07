import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class Fast {

    public static void main(String[] args) {
        setDrawPresets();

        Point[] points = readInput(args[0]);

        findCollinearPoints(points);
    }

    private static void setDrawPresets() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.BLUE);
    }

    private static Point[] readInput(String arg) {
        String filename = arg;
        In input = new In(filename);

        int N = input.readInt();

        Point[] points = new Point[N];

        for(int i = 0; i < N && !input.isEmpty(); ++i) {
            int x = input.readInt();
            int y = input.readInt();

            Point p = new Point(x, y);
            p.draw();

            points[i] = p;
        }

        return points;
    }

    private static void findCollinearPoints(Point[] points) {
        HashSet<LinkedList<Point>> collinearPointsSet = new HashSet<LinkedList<Point>>();
        Point[] sortedPoints = points.clone();

        for (Point point : points) {
            Point invoking = point;

            Arrays.sort(sortedPoints, invoking.SLOPE_ORDER);

            for (int i = 1; i < sortedPoints.length-1; ++i) {
                LinkedList<Point> collinearPoints = new LinkedList<Point>();

                while (i < sortedPoints.length-1 &&
                        invoking.slopeTo(sortedPoints[i]) == invoking.slopeTo(sortedPoints[i+1])) {
                    collinearPoints.add(sortedPoints[i]);
                    ++i;
                }
                if (collinearPoints.size() >= 2) {
                    collinearPoints.addFirst(invoking);
                    collinearPoints.add(sortedPoints[i]);
                    Collections.sort(collinearPoints);
                    boolean unique = collinearPointsSet.add(collinearPoints);

                    if (unique) {
                        for (Point collinear : collinearPoints) {
                            StdOut.print(String.format("%s", collinear));
                            invoking.drawTo(collinear);

                            if (collinear == collinearPoints.peekLast()) {
                                StdOut.println();
                            } else {
                                StdOut.print(" -> ");
                            }
                            invoking = collinear;
                        }
                    }
                }
            }
        }
    }

}
