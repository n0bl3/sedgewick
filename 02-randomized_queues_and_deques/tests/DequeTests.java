import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class DequeTests {

    @Test(expected = NullPointerException.class)
    public void NullPointerExceptionIsThrownOnAddFirstWithNullArgument() {
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(null);
    }

    @Test(expected = NullPointerException.class)
    public void NullPointerExceptionIsThrownOnAddLastWithNullArgument() {
        Deque<Integer> d = new Deque<Integer>();
        d.addLast(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void NoSuchElementExceptionIsThrownOnRemovingFirstItemFromEmptyDeque() {
        Deque<Integer> d = new Deque<Integer>();
        d.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void NoSuchElementExceptionIsThrownOnRemovingLastItemFromEmptyDeque() {
        Deque<Integer> d = new Deque<Integer>();
        d.removeLast();
    }

    @Test
    public void SizeIncrementsOnAddFirst() {
        int N = 10;
        Deque<Integer> d = new Deque<Integer>();
        for (int i = 0; i < N; ++i) {
            d.addFirst(i);
            assertEquals(d.size(), i + 1);
        }
    }

    @Test
    public void SizeIncrementsOnAddLast() {
        int N = 10;
        Deque<Integer> d = new Deque<Integer>();
        for (int i = 0; i < N; ++i) {
            d.addLast(i);
            assertEquals(d.size(), i + 1);
        }
    }

    @Test
    public void SizeDecrementsOnRemoveFirst() {
        int N = 10;
        Deque<Integer> d = new Deque<Integer>();
        for (int i = 0; i < N; ++i) {
            d.addFirst(i);
        }

        for (int i = N - 1; i > 0; --i) {
            d.removeFirst();
            assertEquals(d.size(), i);
        }
    }

    @Test
    public void SizeDecrementsOnRemoveLast() {
        int N = 10;
        Deque<Integer> d = new Deque<Integer>();
        for (int i = 0; i < N; ++i) {
            d.addLast(i);
        }

        for (int i = N - 1; i > 0; --i) {
            d.removeLast();
            assertEquals(d.size(), i);
        }
    }

    @Test
    public void DequeIsEmptyOnInit() {
        Deque<Integer> d = new Deque<Integer>();
        assertTrue(d.isEmpty());
    }

    @Test
    public void RemovingElementsFromBeginningForElementsAddedFromEndSucceeds() {
        int N = 10;
        Deque<Integer> d = new Deque<Integer>();
        for(int i = 0; i < N; ++i) {
            d.addFirst(i);
        }
        for(int i = N; i > 0; --i) {
            d.removeLast();
        }
        assertEquals(d.size(), 0);
    }

    @Test
    public void RemovingElementsFromEndForElementsAddedFromBeginningSucceeds() {
        int N = 10;
        Deque<Integer> d = new Deque<Integer>();
        for(int i = 0; i < N; ++i) {
            d.addLast(i);
        }
        for(int i = N; i > 0; --i) {
            d.removeFirst();
        }
        assertEquals(d.size(), 0);
    }

    @Test
    public void EmptyDequeIsOfSizeZero() {
        int N = 10;
        Deque<Integer> d = new Deque<Integer>();
        for (int i = 0; i < N; ++i) {
            d.addFirst(i);
        }
        for (int i = 0; i < N; ++i) {
            d.removeFirst();
        }
        assertTrue(d.isEmpty());
        assertEquals(d.size(), 0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void UnsupportedOperationThrownOnRemoveCalledOnIterator() {
        Deque<Integer> d = new Deque<Integer>();
        Iterator<Integer> it = d.iterator();
        it.remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void NoSuchElementThrownOnLastElementWithIterator() {
        Deque<Integer> d = new Deque<Integer>();
        int expectedValue = 10;
        d.addLast(expectedValue);
        Iterator<Integer> it = d.iterator();
        assertThat(it.next(), equalTo(expectedValue));

        // must throw since there's only one element in the deque
        it.next();
    }

    @Test
    public void IteratorIteratesOverAllElementsAfterAddLast() {
        int N = 10;
        Deque<Integer> d = new Deque<Integer>();
        for (int i = 0; i < N; ++i) {
            d.addLast(i);
        }

        for (int item = 0; item < N; ++item) {
            assertThat(d, hasItem(item));
        }
        assertThat(d.size(), equalTo(N));
    }

    @Test
    public void IteratorIteratesOverAllElementsAfterRandomCalls() {
        int N = 10;
        Deque<Integer> d = new Deque<Integer>();
        for (int i = 0; i < N; ++i) {
            if (StdRandom.uniform(2) == 1) {
                d.addLast(i);
            } else {
                d.addFirst(i);
            }
        }

        for (int item = 0; item < N; ++item) {
            assertThat(d, hasItem(item));
        }
        assertThat(d.size(), equalTo(N));
    }

    @Test
    public void ContainsAllElements() {
        int N = 10;
        Deque<Integer> deque = new Deque<Integer>();
        for (int i = 0; i < N; ++i) {
            deque.addLast(i);
        }

        assertThat(deque.size(), equalTo(N));
        for (int i = 0; i < N; ++i) {
            int item = deque.removeFirst();
            assertThat(item, equalTo(i));
        }

        assertThat(deque, is(emptyIterable()));
    }

    @Test
    public void ContainsAllElementsWhenAddedAndRemovedInRandomOrder() {
        int N = 10;
        int expected = 1234;
        Deque<Integer> deque = new Deque<Integer>();

        for (int i = 0; i < N; ++i) {
            if (StdRandom.uniform(2) == 1) {
                deque.addFirst(expected);
            } else {
                deque.addLast(expected);
            }
        }

        assertThat(deque.size(), equalTo(N));

        for (int i = 0; i < N; ++i) {
            int item;
            if (StdRandom.uniform(2) == 0) {
                item = deque.removeFirst();
            } else {
                item = deque.removeLast();
            }
            assertThat(item, equalTo(expected));
        }
    }

    @Test
    public void IntermixedCallsSucceed() {
        Integer N = 10;
        Deque<Integer> d = new Deque<Integer>();

        for (int i = 0; i < N; ++i) {
            if (StdRandom.uniform(2) == 1) {
                d.addFirst(i);
            } else {
                d.addLast(i);
            }
        }

        for (int i = 0; i < N; ++i) {
            Integer item;
            if (StdRandom.uniform(2) == 1) {
                item = d.removeFirst();
            } else {
                item = d.removeLast();
            }

            assertThat(item, is(not(nullValue())));
            assertTrue(0 <= item && item < N);
        }

        assertThat(d.size(), equalTo(0));
        assertThat(d.isEmpty(), is(true));
        assertThat(d, is(emptyIterable()));
    }
}
