import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class PercolationStatsTest {

    private PercolationStats mockedStats;

    @Before
    public void setUp() {
        mockedStats = spy(new PercolationStats(1, 2));
        when(mockedStats.computeResults()).thenReturn(new double[]{2, 3, 5});
    }

    @DataProvider
    public static String[] invalidArgsProvider() {
        return new String[] {
            "0, 0",
            "1, 0",
            "0, 1"
        };
    }

    @Test(expected = IllegalArgumentException.class)
    @UseDataProvider("invalidArgsProvider")
    public void throwsExceptionIfSitesOrRunsNumberIsNonPositive(int n, int t) {
        PercolationStats ps = new PercolationStats(n, t);
    }

    @DataProvider
    public static String[] argsProvider() {
        return new String[] {
             // small sites have unusually high threshold
            "2,  100,  0.1",
            "8,  100,  0.02",
            "10, 1000, 0.02",
            "15, 1000, 0.02",
        };
    }

    @Test
    @UseDataProvider("argsProvider")
    public void computedResultsWithinThreshold(int gw, int r, double delta) {
        PercolationStats ps = new PercolationStats(gw, r);
        double[] res = ps.computeResults();
        double sum = 0, avg;
        for (double s: res) {
            sum += s;
        }
        avg = sum / res.length;

        // an average threshold is known as ~0.59, so the randomized results
        // should be somewhere within the bounds +- delta
        assertTrue(avg < 0.59 + delta && avg > 0.59 - delta);
    }

    @Test
    public void calculatesMean() {
        assertEquals(3.3, mockedStats.mean(), 0.1);
    }

    @Test
    public void calculatesStdDeviation() {
        when(mockedStats.mean()).thenReturn(3.3);

        assertEquals(1.5, mockedStats.stddev(), 0.1);
    }

    @Test
    public void calculatesConfidence() {
        when(mockedStats.mean()).thenReturn(4.5);
        when(mockedStats.stddev()).thenReturn(1.2);


        assertEquals(2.8, mockedStats.confidenceLo(), 0.1);
        assertEquals(6.1, mockedStats.confidenceHi(), 0.1);
    }

}
