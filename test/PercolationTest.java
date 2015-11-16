import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class PercolationTest {

    private Percolation perc;

    @Before
    public void setUp() {
        perc = new Percolation(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionIfSitesNumberIsNonPositive() {
        Percolation p = new Percolation(0);
    }

    @DataProvider
    public static String[] invalidSiteProvider() {
        return new String[] {
            "0, 0",
            "1, 0",
            "4, 2"
        };
    }

    @Test(expected = IllegalArgumentException.class)
    @UseDataProvider("invalidSiteProvider")
    public void throwsExceptionWhenOpenInvalidSite(int row, int col) {
        perc.open(row, col);
    }

    @Test
    public void marksTopSiteAsFull() {
        perc.open(1, 1);

        assertTrue(perc.isFull(1, 1));
    }

    @Test
    public void marksTopConnectedSiteAsFull() {
        perc.open(1, 1);
        perc.open(1, 2);

        assertTrue(perc.isFull(1, 2));
    }

    @Test
    public void doesNotMarkBottomSitesAsFull() {
        // make the system percolate
        perc.open(1, 1);
        perc.open(2, 1);
        perc.open(3, 1);
        // open bottom site outside percolation tube
        perc.open(3, 3);

        assertFalse(perc.isFull(3, 3));
    }

    @Test
    public void percolatesWhenOpenTopBottom() {
        perc.open(1, 1);
        perc.open(2, 1);
        perc.open(3, 1);

        assertTrue(perc.percolates());
    }

    @Test
    public void percolatesWhenMiddleJoined() {
        perc.open(1, 1);
        perc.open(2, 1);
        perc.open(3, 3);
        perc.open(3, 2);

        assertFalse(perc.percolates());

        // last site to make the percolation true
        perc.open(2, 2);

        assertTrue(perc.percolates());
    }

    @Test
    public void doesNotPercolate() {
        perc.open(1, 1);
        perc.open(3, 3);
        perc.open(2, 2);

        assertFalse(perc.percolates());
    }

}
