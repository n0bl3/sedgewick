import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WeightedUnionFindTest {

    @Test
    public void valueIsConnectedToItself() {
        int n = 10;
        int p = 5;
        WeightedUnionFind wuf = new WeightedUnionFind(n);
        assertTrue(wuf.connected(p, p));
    }

    @Test
    public void valuesAreNotConnectedAfterInitialization() {
        int n = 10;
        WeightedUnionFind wuf = new WeightedUnionFind(n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (j != i) {
                    assertFalse(wuf.connected(i, j));
                }
            }
        }
    }
    
    @Test
    public void allValuesGetConnected() {
        int n = 10;
        WeightedUnionFind wuf = new WeightedUnionFind(n);
        for (int i = 0; i < n; ++i) {
            wuf.union(0, i);
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                assertTrue(wuf.connected(i, j));
            }
        }
    }
    
    @Test
    public void valuesGetSplitIntoTwoGroups() {
        int n = 10;
        WeightedUnionFind wuf = new WeightedUnionFind(n);
        // union i five values
        for (int i = 0; i < n/2; ++i) {
            wuf.union(0, i);
        }
        // union last five values
        for (int i = n/2; i < n; ++i) {
            wuf.union(n/2, i);
        }
        // verify i group
        for (int i = 0; i < n/2; ++i) {
            assertTrue(wuf.connected(0, i));
        }
        // verify j group
        for (int i = n/2; i < n; ++i) {
            assertTrue(wuf.connected(n / 2, i));
        }
        // verify i and j group are not connected
        for (int i = 0; i < n/2; ++i) {
            int q = i + n/2;
            String errMessage = String.format("values %d and %d should not be connected", i, q);
            assertFalse(errMessage, wuf.connected(i, q));
        }
    }

}
