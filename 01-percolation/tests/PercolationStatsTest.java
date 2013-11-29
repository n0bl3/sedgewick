import org.junit.Assert;
import org.junit.Test;

public class PercolationStatsTest {

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionIsThrownOnZeroN() {
        new PercolationStats(0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionIsThrownOnZeroT() {
        new PercolationStats(1, 0);
    }

    @Test
    public void meanValueIsBetweenZeroAndOne() {
        int N = 5;
        int T = 5;
        PercolationStats ps = new PercolationStats(N, T);
        double mean = ps.mean();
        Assert.assertTrue(0 <= mean && mean <= 1);
    }

    @Test
    public void meanValueIsBetweenPointFiveAndPointSeven() {
        int N = 200;
        int T = 100;
        PercolationStats ps = new PercolationStats(N, T);
        double mean = ps.mean();
        double dev = ps.stddev();
        double lo = ps.confidenceLo();
        double hi = ps.confidenceHi();

        String errorMessage = String.format(
                "mean value should be around 0.593, " +
                "while it's %f; deviation is %f; " +
                "95%% confidence inteval - [%f, %f]", mean, dev, lo, hi);

        StdOut.printf("N = %d, T = %d: µ = %f; dev = %f; [%f, %f]%n", N, T, mean, dev, lo, hi);

        Assert.assertTrue(errorMessage, 0.55 <= mean && mean <= 0.62);
    }

    @Test
    public void statsAreValidForTwoByTwoMatrix() {
        int N = 2;
        int T = 1000;

        PercolationStats ps = new PercolationStats(N, T);

        double mean = ps.mean();
        double dev = ps.stddev();
        double lo = ps.confidenceLo();
        double hi = ps.confidenceHi();

        String errorMessage = String.format(
                "mean value should be around 0.666 for 2x2 matrix, " +
                        "while it's %f; deviation is %f; " +
                        "95%% confidence inteval - [%f, %f]", mean, dev, lo, hi);

        StdOut.printf("N = %d, T = %d: µ = %f; dev = %f; [%f, %f]%n", N, T, mean, dev, lo, hi);

        Assert.assertTrue(errorMessage, 0.6 <= mean && mean <= 0.7);
    }

}
