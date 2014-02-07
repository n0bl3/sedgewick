public class Brute {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.BLUE);

        String filename = args[0];
        In input = new In(filename);

        int N = input.readInt();

        Point[] points = new Point[N];

        for(int i = 0; i < N && !input.isEmpty(); ++i) {
            int x = input.readInt();
            int y = input.readInt();

            Point p = new Point(x, y);
            points[i] = p;
            p.draw();
        }

        for (int i = 0; i < N-3; ++i) {
            Point invoking = points[i];
            for (int j = i + 1; j < N-2; ++j) {
                for (int k = j + 1; k < N-1; ++k) {
                    if (invoking.slopeTo(points[j]) == invoking.slopeTo(points[k])) {
                        for (int p = k + 1; p < N; ++p) {
                            if (invoking.slopeTo(points[p]) == points[p].slopeTo(points[j])) {
                                StdOut.println(String.format("%s -> %s -> %s -> %s",
                                        invoking, points[j], points[k], points[p]));

                                invoking.drawTo(points[j]);
                                points[j].drawTo(points[k]);
                                points[k].drawTo(points[p]);
                            }
                        }
                    }
                }
            }
        }
    }
}
