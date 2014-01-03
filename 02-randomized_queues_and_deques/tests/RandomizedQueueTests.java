import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RandomizedQueueTests {
    @Test(expected = NoSuchElementException.class)
    public void InteratorNextThrowsNoSuchElementIfThereAreNoMoreElements() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.iterator().next();
    }

    @Test(expected = NullPointerException.class)
    public void ThrowsNullPointerExceptionOnAddingNullElement() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void ThrowsNoSuchElementOnDequeueFromEmptyQueue() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void ThrowsNoSuchElementOnSampleFromEmptyQueue() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.sample();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void ThrowsUnsupportedOperationOnRemoveForIterator() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(10);
        rq.iterator().remove();
    }

    @Test
    public void StoresCorrectSize() {
        int N = 10;
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        for (int i = 0; i < N; ++i) {
            rq.enqueue(i);
        }

        assertEquals(N, rq.size());
    }

    @Test
    public void IteratorIteratesOverAllItems() {
        int N = 10;
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        Integer[] expectedItems = new Integer[N];

        for (int i = 0; i < N; ++i) {
            rq.enqueue(i);
            expectedItems[i] = i;
        }

        int numIterations = 0;
        List<Integer> expectedList = Arrays.asList(expectedItems);
        for (int item : rq) {
            assertThat(expectedList, hasItem(item));
            ++numIterations;
        }

        assertThat(numIterations, equalTo(rq.size()));
    }

    @Test
    public void SampleReturnsRandomItems() {
        int N = 10;
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        int [] sequentialItems = new int[N];
        for (int i = 0; i < N; ++i) {
            rq.enqueue(i);
            sequentialItems[i] = i;
        }

        int[] returnedSamples = new int[N];
        for (int i = 0; i < N; ++i) {
            returnedSamples[i] = rq.sample();
        }

        int numRandom = 0;
        for (int i = 0; i < N; ++i) {
            if (sequentialItems[i] != returnedSamples[i]) {
                ++numRandom;
            }
        }
        assertTrue(numRandom > N / 2);
    }

    @Test
    public void DequeueReturnsRandomItems() {
        int N = 10;
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        int [] sequentialItems = new int[N];
        for (int i = 0; i < N; ++i) {
            rq.enqueue(i);
            sequentialItems[i] = i;
        }

        int[] returnedItems = new int[N];
        for (int i = 0; i < N; ++i) {
            returnedItems[i] = rq.dequeue();
        }

        int numRandom = 0;
        for (int i = 0; i < N; ++i) {
            if (sequentialItems[i] != returnedItems[i]) {
                ++numRandom;
            }
        }
        assertTrue(numRandom > N / 2);
    }

    @Test
    public void IteratorReturnsCorrectItemsForSequenceOfEnqueueOperations() {
        int N = 100;

        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 0; i < N; ++i) {
            rq.enqueue(i);
        }

        for (int i = 0; i < N; ++i) {
            assertThat(rq, hasItem(i));
        }
    }

    @Test
    public void IteratorReturnsCorrectItemsAfterEnqueueAndDequeue() {
        int N = 100;

        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        int numEnqueued = N/2+5;
        for (int i = 0; i < numEnqueued; ++i) {
            rq.enqueue(i);
        }
        assertThat(rq.size(), equalTo(numEnqueued));

        int numDequeued = N/2;
        for (int i = 0; i < numDequeued; ++i) {
            rq.dequeue();
        }

        int numLeft = numEnqueued - numDequeued;
        assertThat(rq.size(), equalTo(numLeft));

        for (int i = 0; i < numLeft; ++i) {
            rq.dequeue();
        }
        assertTrue(rq.isEmpty());

        for (int i = 0; i < N; ++i) {
            rq.enqueue(i);
        }
        assertThat(rq.size(), equalTo(N));
    }

    @Test
    public void NoDuplicatedValuesForSequentialInput() {
        int N = 100;

        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        for(int i = 0; i < N; ++i) {
            rq.enqueue(i);
        }

        for (int i = 0; i < N; ++i) {
            int item = rq.dequeue();

            assertThat(rq, not(hasItem(item)));
        }
    }

    @Test
    public void TwoIndependentIteratorsToTheSameQueueReturnDifferentValues() {
        int N = 10;
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        for (int i = 0; i < N; ++i) {
            rq.enqueue(i);
        }

        Iterator<Integer> it1 = rq.iterator();
        Iterator<Integer> it2 = rq.iterator();

        int index = 0;
        int [] it1Values = new int [N];
        int [] it2Values = new int [N];

        while(it1.hasNext()) {
            it1Values[index++] = it1.next();
        }

        index = 0;

        while(it2.hasNext()) {
            it2Values[index++] = it2.next();
        }

        int [] sortedIt1Values = it1Values.clone();
        int [] sortedIt2Values = it2Values.clone();

        Arrays.sort(sortedIt1Values);
        Arrays.sort(sortedIt2Values);

        assertThat(Arrays.asList(sortedIt1Values), hasItems(sortedIt2Values));

        int numDifferent = 0;
        for (int i = 0; i < N; ++i) {
            if (it1Values != it2Values) {
                numDifferent++;
            }
        }
        assertTrue(numDifferent > N/2);
    }

    @Test
    public void RunsUnderOneSecondForThousandItems() {
        int numItems = 4096;

        long start = System.currentTimeMillis();

        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        for (int item = 0; item < numItems; ++item) {
            rq.enqueue(item);
        }

        for (int i = 0; i < numItems; ++i) {
            rq.dequeue();
        }

        long executionTime = System.currentTimeMillis() - start;
        long oneSecond = 1000; // 1 s == 1000 ms

        assertThat(executionTime, is(lessThan(oneSecond)));
    }

    @Test
    public void TestRandomnessForThreeItems() {
        int numItems = 3;

        runRandomnessTest(numItems);
    }

    @Test
    public void TestRandomnessForFiveItems() {
        int numItems = 5;

        runRandomnessTest(numItems);
    }

    @Test
    public void TestRandomnessForEightItems() {
        int numItems = 8;

        runRandomnessTest(numItems);
    }

    @Test
    public void TestRandomnessForTenItems() {
        int numItems = 10;

        runRandomnessTest(numItems);
    }

    @Test
    public void TestRandomnessForTwelveItems() {
        int numItems = 12;

        runRandomnessTest(numItems);
    }

    private void runRandomnessTest(int numItems) {
        int numTests = 500 * numItems;
        int [] observedFrequencies = new int [numItems];

        for (int test = 0; test < numTests; ++test) {
            RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

            for (int item = 0; item < numItems; ++item) {
                rq.enqueue(item);
            }

            int positionFound = 0;
            int lookForItem = numItems/2;

            while (rq.dequeue() != lookForItem) {
                ++positionFound;
            }

            assertTrue(0 <= positionFound && positionFound < numItems);

            observedFrequencies[positionFound]++;
        }

        double expected = 1.0 * numTests / numItems;
        double deviation = calculateDeviation(numTests, numItems, observedFrequencies, expected);

        String assertMessage = deviationAssertMessage(numTests, numItems, observedFrequencies, expected);

        assertThat(assertMessage, deviation, is(lessThan(0.02)));
    }

    private double calculateDeviation(int numTests, int numItems, int[] observedFrequencies, double expected) {
        double deviation = 0.0;

        for (int observed : observedFrequencies) {
            double observedN = 1.0 * observed / numTests;
            double expectedN = 1.0 * expected / numTests;
            deviation += (observedN - expectedN) * (observedN - expectedN);
        }
        deviation /= numItems;
        deviation = Math.sqrt(deviation);

        return deviation;
    }

    private String deviationAssertMessage(int numTests, int numItems, int[] observedFrequencies, double expected) {
        StringBuilder assertMessage = new StringBuilder();
        assertMessage.append(System.getProperty("line.separator"));

        assertMessage.append("expected: ");
        for (int i = 0; i < numItems; ++i) {
            assertMessage.append(String.format("%d ", (int)expected));
        }
        assertMessage.append(String.format("| total = %d", numTests));
        assertMessage.append(System.getProperty("line.separator"));

        assertMessage.append("actual: ");
        for (int i = 0; i < numItems; ++i) {
            assertMessage.append(String.format("%d ", observedFrequencies[i]));
        }
        assertMessage.append(String.format("| total = %d", numTests));
        assertMessage.append(System.getProperty("line.separator"));
        return assertMessage.toString();
    }
}
