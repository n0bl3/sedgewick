import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SubsetTests {
    @Test
    public void WhitespaceAtTheEndOfUserInputIsIgnored() {
        int outputLimit = 3;

        String testInput = "AA BB BB BB BB BB CC CC ";
        String [] args = {String.format("%d", outputLimit)};

        // redirect input
        InputStream redirectedInputStream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(redirectedInputStream);

        // redirect output
        ByteArrayOutputStream redirectedOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(redirectedOutputStream);
        System.setOut(printStream);

        Subset.main(args);

        String[] output = redirectedOutputStream.toString().split(System.getProperty("line.separator"));

        assertThat(output, is(notNullValue()));
        assertThat(output.length, equalTo(outputLimit));

        for (String outputValue : output) {
            assertThat(testInput, containsString(outputValue));
        }
    }

}
