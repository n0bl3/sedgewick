import org.junit.Test;
import org.junit.Assert;

public class PercolationTestCase {

    private class Pair {
        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public int i;
        public int j;
    }

    @Test
    public void fullyClosedSiteDoesNotPercolate() {
        // - - - - -
        // - - - - -
        // - - - - -
        // - - - - -
        // - - - - -

        int n = 5;
        Percolation p = new Percolation(n);
        Assert.assertFalse(p.percolates());
    }

    @Test
    public void singleElementSiteIsNotOpenOnInit() {
        int n = 1;
        Percolation p = new Percolation(n);
        Assert.assertFalse(p.isOpen(1, 1));
    }

    @Test
    public void singleElementSiteIsNotFullOnInit() {
        int n = 1;
        Percolation p = new Percolation(n);
        Assert.assertFalse(p.isFull(1, 1));
    }

    @Test
    public void singleElementSiteDoesNotPercolateOnInit() {
        int n = 1;
        Percolation p = new Percolation(n);
        Assert.assertFalse(p.percolates());
    }

    @Test
    public void singleElementSitePercolatesIfOpened() {
        int n = 1;
        Percolation p = new Percolation(n);
        p.open(1, 1);
        Assert.assertTrue(p.percolates());
    }

    @Test
    public void singleElementSiteIsOpenIfOpened() {
        int n = 1;
        Percolation p = new Percolation(n);
        p.open(1, 1);
        Assert.assertTrue(p.isOpen(1, 1));
    }

    @Test
    public void singleElementSiteIsFullIfOpened() {
        int n = 1;
        Percolation p = new Percolation(n);
        p.open(1, 1);
        Assert.assertTrue(p.isFull(1, 1));
    }

    @Test
    public void standaloneOpenedSiteInBottomRowIsNotFull() {
        // * - -
        // * - -
        // * - *
        int n = 3;
        Percolation p = new Percolation(n);
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(3, 3);
        Assert.assertFalse(p.isFull(3, 3));
    }

    @Test
    public void twoByTwoMatrixPercolatesOnLeftColumnOpened() {
        // * -
        // * -
        int n = 2;
        Percolation p = new Percolation(n);
        p.open(1, 1);
        p.open(2, 1);
        Assert.assertTrue(p.percolates());
    }

    @Test
    public void twoByTwoMatrixPercolatesOnRightColumnOpened() {
        // - *
        // - *
        int n = 2;
        Percolation p = new Percolation(n);
        p.open(1, 2);
        p.open(2, 2);
        Assert.assertTrue(p.percolates());
    }

    @Test
    public void twoByTwoMatrixPercolatesOnAllSitesOpened() {
        // * *
        // * *
        int n = 2;
        Percolation p = new Percolation(n);
        p.open(1, 1);
        p.open(1, 2);
        p.open(2, 1);
        p.open(2, 2);
        Assert.assertTrue(p.percolates());
    }

    @Test
    public void twoByTwoMatrixPercolatesInRandomOrder() {
        int n = 2;
        Percolation p = new Percolation(n);
        p.open(2, 2);
        Assert.assertFalse(p.percolates());
        p.open(2, 1);
        Assert.assertFalse(p.percolates());
        p.open(1, 2);
        Assert.assertTrue(p.percolates());
        p.open(1, 1);
        Assert.assertTrue(p.percolates());
    }

    @Test
    public void twoByTwoMatrixPercolatesOnMostSitesOpened() {
        // * *
        // - *
        int n = 2;
        Percolation p = new Percolation(n);
        p.open(1, 1);
        p.open(1, 2);
        p.open(2, 2);
        Assert.assertTrue(p.percolates());
    }

    @Test
    public void standaloneOpenedSiteInBottomRowIsNotFullForSinglePercolationPaths() {
        //   | 1 2 3 4 5 |
        //---|-----------|
        // 1 | * - - * - |
        // 2 | * - * - * |
        // 3 | * - * * * |
        // 4 | * - * * * |
        // 5 | * - * - * |
        //---+-----------+
        int n = 5;

        Pair[] fullSites = new Pair[] {
                new Pair(4, 1),
                new Pair(1, 1),
                new Pair(3, 1),
                new Pair(2, 1),
                new Pair(1, 4)
        };
        Pair[] notFullSites = new Pair[] {
                new Pair(5, 5),
                new Pair(2, 3),
                new Pair(3, 4),
                new Pair(4, 3),
                new Pair(3, 5),
                new Pair(4, 4),
                new Pair(4, 5),
                new Pair(5, 3),
                new Pair(2, 5),
                new Pair(3, 3),
        };

        Percolation p = new Percolation(n);
        for (Pair opened : fullSites) {
            p.open(opened.i, opened.j);
        }
        for (Pair opened : notFullSites) {
            p.open(opened.i, opened.j);
        }

        for (Pair notFull : notFullSites) {
            Assert.assertFalse(p.isFull(notFull.i, notFull.j));
        }
    }

    @Test
    public void bottomToTopFillPercolates() {
        int n = 5;
        Percolation p = new Percolation(n);

        for (int i = n; i >= 1; --i) {
            for (int j = n; j >= 1; --j) {
                p.open(i, j);
            }
        }

        Assert.assertTrue(p.percolates());
    }

    @Test
    public void standaloneOpenedSiteInBottomRowIsNotFullForMultiplePercolationPaths() {
        //   | 1 2 3 4 5 |
        //---|-----------|
        // 1 | * - * - - |
        // 2 | * - * - * |
        // 3 | * - * - * |
        // 4 | * - * - * |
        // 5 | * - * - * |
        //---+-----------+
        int n = 5;

        Pair[] fullSites = new Pair[] {
                new Pair(1, 1),
                new Pair(2, 1),
                new Pair(3, 1),
                new Pair(4, 1),
                new Pair(5, 1),
                new Pair(1, 3),
                new Pair(2, 3),
                new Pair(3, 3),
                new Pair(4, 3),
                new Pair(5, 3)
        };
        Pair[] notFullSites = new Pair[] {
                new Pair(2, 5),
                new Pair(3, 5),
                new Pair(4, 5),
                new Pair(5, 5)
        };

        Percolation p = new Percolation(n);
        for (Pair opened : fullSites) {
            p.open(opened.i, opened.j);
        }
        for (Pair opened : notFullSites) {
            p.open(opened.i, opened.j);
        }

        for (Pair notFull : notFullSites) {
            Assert.assertFalse(p.isFull(notFull.i, notFull.j));
        }
    }

    @Test
    public void openedSitesAreOpen() {
        // - - -
        // - * -
        // * - *

        int n = 3;
        Pair[] openedSites = new Pair[] {
                new Pair(2, 2),
                new Pair(3, 1),
                new Pair(3, 3)
        };
        Percolation p = new Percolation(n);

        for (Pair opened : openedSites) {
            p.open(opened.i, opened.j);
        }

        for (Pair opened : openedSites) {
            Assert.assertTrue(p.isOpen(opened.i, opened.j));
        }
    }

    @Test
    public void sitesConnectedToTheTopAreFull() {
        //   | 1 2 3
        //---|-------
        // 1 | * - *
        // 2 | * * -
        // 3 | - - -

        int n = 3;
        Pair[] openedSites = new Pair[] {
                new Pair(1, 1),
                new Pair(1, 3),
                new Pair(2, 1),
                new Pair(2, 2)
        };

        Percolation p = new Percolation(n);
        
        for (Pair opened : openedSites) {
            p.open(opened.i, opened.j);
        }

        for (Pair opened : openedSites) {
            Assert.assertTrue(p.isFull(opened.i, opened.j));
        }
    }

    @Test
    public void fullyOpenedSitePercolates() {
        // * * * * *
        // * * * * *
        // * * * * *
        // * * * * *
        // * * * * *

        int n = 5;
        Percolation p = new Percolation(n);
        // valid indices are [1; n]
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                p.open(i, j);
            }
        }
        Assert.assertTrue(p.percolates());
    }

    @Test
    public void leftLineOpenedSitePercolates() {
        //   | 1 2 3 4 5 |
        //---|-----------|
        // 1 | * - - - - |
        // 2 | * - - - - |
        // 3 | * - - - - |
        // 4 | * - - - - |
        // 5 | * - - - - |
        //---+-----------+

        int n = 5;
        Percolation p = new Percolation(n);

        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(4, 1);
        p.open(5, 1);

        Assert.assertTrue(p.percolates());
    }

    @Test
    public void leftToRightDiagonalLineOpenedSitePercolates() {
        //   | 1 2 3 4 5 |
        //---|-----------|
        // 1 | * - - - - |
        // 2 | * * * - - |
        // 3 | - - * - - |
        // 4 | - - * * * |
        // 5 | - - - - * |
        //---+-----------+

        int n = 5;
        Percolation p = new Percolation(n);

        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(2, 3);
        p.open(3, 3);
        p.open(4, 3);
        p.open(4, 4);
        p.open(4, 5);
        p.open(5, 5);

        Assert.assertTrue(p.percolates());
    }

    @Test
    public void spiderShapedOpenSitePercolates() {
        //   | 1 2 3 4 5 |
        //---|-----------|
        // 1 | * - - - * |
        // 2 | * * * * * |
        // 3 | - - * - - |
        // 4 | * * * * * |
        // 5 | * - - - * |
        //---+-----------+

        int n = 5;
        Percolation p = new Percolation(n);

        p.open(5, 1);
        p.open(5, 5);

        p.open(1, 1);
        p.open(1, 5);

        p.open(2, 1);
        p.open(2, 2);
        p.open(2, 3);
        p.open(2, 4);
        p.open(2, 5);

        p.open(3, 3);

        p.open(4, 1);
        p.open(4, 2);
        p.open(4, 3);
        p.open(4, 4);
        p.open(4, 5);

        Assert.assertTrue(p.percolates());
    }

    @Test
    public void diagonalOpenedSitePercolates() {
        //   | 1 2 3 4 5 |
        //---|-----------|
        // 1 | - - - - * |
        // 2 | - - - * * |
        // 3 | - - * * - |
        // 4 | * * * - - |
        // 5 | * - - - - |
        //---+-----------+

        int n = 5;
        Percolation p = new Percolation(n);

        p.open(1, 5);

        p.open(2, 5);
        p.open(2, 4);

        p.open(3, 4);
        p.open(3, 3);

        p.open(4, 3);
        p.open(4, 2);
        p.open(4, 1);

        p.open(5, 1);

        Assert.assertTrue(p.percolates());
    }

    @Test
    public void rightLineOpenedSitePercolates() {
        //   | 1 2 3 4 5 |
        //---|-----------|
        // 1 | - - - - * |
        // 2 | - - - - * |
        // 3 | - - - - * |
        // 4 | - - - - * |
        // 5 | - - - - * |
        //---+-----------+

        int n = 5;
        Percolation p = new Percolation(n);

        p.open(1, 5);
        p.open(2, 5);
        p.open(3, 5);
        p.open(4, 5);
        p.open(5, 5);

        Assert.assertTrue(p.percolates());
    }

    @Test
    public void closedSiteDoesNotPercolate() {
        int n = 3;
        Percolation p = new Percolation(n);

        // * * *
        // - - -
        // * * *

        p.open(1, 1);
        p.open(1, 2);
        p.open(1, 3);

        p.open(3, 1);
        p.open(3, 2);
        p.open(3, 3);

        Assert.assertFalse(p.percolates());
    }

    @Test
    public void openSitePercolates() {
        int n = 3;
        Percolation p = new Percolation(n);

        // * * *
        // - * -
        // * * *

        p.open(1, 1);
        p.open(1, 2);
        p.open(1, 3);

        p.open(2, 2);

        p.open(3, 1);
        p.open(3, 2);
        p.open(3, 3);

        Assert.assertTrue(p.percolates());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void firstIndexOutOfBoundsOnIsOpen() {
        int n = 5;
        Percolation p = new Percolation(n);
        p.isOpen(n + 1, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void secondIndexOutOfBoundsOnIsOpen() {
        int n = 5;
        Percolation p = new Percolation(n);
        p.isOpen(n, n + 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void firstIndexOutOfBoundsOnIsFull() {
        int n = 5;
        Percolation p = new Percolation(n);
        p.isFull(n + 1, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void secondIndexOutOfBoundsOnIsFull() {
        int n = 5;
        Percolation p = new Percolation(n);
        p.isFull(n, n + 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void outOfBoundsOnInvalidIndexForIsFull() {
        int n = 5;
        Percolation p = new Percolation(n);
        p.isFull(n + 1, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void outOfBoundsOnInvalidIndexForOpen() {
        int n = 5;
        Percolation p = new Percolation(n);
        p.open(n + 1, 0);
    }

}
