import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class PercolationStatsTest {

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
            "3, 10",
            "10, 10",
            "30, 3"
        };
    }

    @Test
    @UseDataProvider("argsProvider")
    public void computedResultsWithinThreshold(int gridWidth, int runs) {
        PercolationStats ps = new PercolationStats(gridWidth, runs);
        double[] res = ps.computeResults();
        double sum = 0, avg;
        for (double s: res) {
            sum += s;
        }
        avg = sum / res.length;

        // an average threshold is known, so the randomized results
        // should be somewhere within the bounds
        assertTrue(avg < 0.66 && avg > 0.58);
    }

}
