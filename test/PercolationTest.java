import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.*;

@RunWith(JUnitParamsRunner.class)
public class PercolationTest {

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionIfSitesNumberIsNonPositive() {
        Percolation p = new Percolation(0);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
        "0, 0",
        "1, 0",
        "4, 2"})
    public void throwsExceptionWhenOpenInvalidSite(int row, int col) {
        Percolation perc = new Percolation(3);
        perc.open(row, col);
    }

    @Test
    public void marksTopSiteAsFull() {
        Percolation perc = new Percolation(3);

        perc.open(1, 1);

        assertThat(perc.isFull(1, 1), is(true));
    }

    @Test
    public void marksSiteConnectedToTopAsFull() {
        Percolation perc = new Percolation(3);

        perc.open(1, 1);
        perc.open(1, 2);

        assertThat(perc.isFull(1, 2), is(true));
    }

    @Test
    public void doesNotMarkBottomSitesAsFull() {
        Percolation perc = new Percolation(3);

        // make the system percolate
        perc.open(1, 1);
        perc.open(2, 1);
        perc.open(3, 1);
        // open bottom site outside percolation tube
        perc.open(3, 3);

        assertThat(perc.isFull(3, 3), is(false));
    }

    @Test
    public void percolatesWhenTopAndBottomSitesConnected() {
        Percolation perc = new Percolation(3);

        perc.open(1, 1);
        perc.open(2, 1);
        perc.open(3, 1);

        assertThat(perc.percolates(), is(true));
    }

    @Test
    public void percolatesWhenMiddleSiteCreatesConnection() {
        Percolation perc = new Percolation(3);

        perc.open(1, 1);
        perc.open(2, 1);
        perc.open(3, 3);
        perc.open(3, 2);

        assertThat(perc.percolates(), is(false));

        // last site to make the percolation true
        perc.open(2, 2);

        assertThat(perc.percolates(), is(true));
    }

    @Test
    public void doesNotPercolate() {
        Percolation perc = new Percolation(3);

        perc.open(1, 1);
        perc.open(3, 3);
        perc.open(2, 2);

        assertThat(perc.percolates(), is(false));
    }

}
