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

}
