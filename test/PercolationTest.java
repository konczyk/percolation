import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class PercolationTest {

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionIfSitesNumberIsNonPositive() {
        new Percolation(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    @Parameters({
        "0, 0",
        "1, 0",
        "4, 2"})
    public void throwsExceptionWhenOpenInvalidSite(int row, int col) {
        Percolation p = new Percolation(3);
        p.open(row, col);
    }

    @Test
    public void topSiteIsFull() {
        Percolation p = new Percolation(3);

        p.open(1, 1);

        assertThat(p.isFull(1, 1), is(true));
    }

    @Test
    public void topConnectedSiteIsFull() {
        Percolation p = new Percolation(3);

        p.open(1, 1);
        p.open(1, 2);

        assertThat(p.isFull(1, 2), is(true));
    }

    @Test
    public void disconnectedSiteIsNotFull() {
        Percolation p = new Percolation(3);

        // make the system percolate
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        // open bottom site outside percolation tube
        p.open(3, 3);

        assertThat(p.isFull(3, 3), is(false));
    }

    @Test
    public void percolates() {
        Percolation p = new Percolation(3);

        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);

        assertThat(p.percolates(), is(true));
    }

    @Test
    public void percolatesWhenMiddleSiteCreatesConnection() {
        Percolation p = new Percolation(3);

        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 3);
        p.open(3, 2);

        assertThat(p.percolates(), is(false));

        // last site to make the percolation true
        p.open(2, 2);

        assertThat(p.percolates(), is(true));
    }

    @Test
    public void doesNotPercolate() {
        Percolation p = new Percolation(3);

        p.open(1, 1);
        p.open(3, 3);
        p.open(2, 2);

        assertThat(p.percolates(), is(false));
    }

}
