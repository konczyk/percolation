import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.*;

@RunWith(JUnitParamsRunner.class)
public class PercolationStatsTest {

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
        "0, 0",
        "1, 0",
        "0, 1"})
    public void throwsExceptionIfSitesOrRunsNumberIsNonPositive(int n, int t) {
        PercolationStats ps = new PercolationStats(n, t);
    }

    @Test
    @Parameters(method="argsProvider")
    public void percolationThresholdsWithinBounds(int gw, int r, double delta) {
        PercolationStats ps = new PercolationStats(gw, r);
        double[] res = ps.collectPercolationThresholds();
        double sum = 0, avg;
        for (double s: res) {
            sum += s;
        }
        avg = sum / res.length;

        // an average threshold is known as ~0.59, so the randomized results
        // should be somewhere within the bounds +- delta
        assertThat(avg, is(closeTo(0.59, delta)));
    }

    private Object[] argsProvider() {
        return new Object[]{
            // small sites have unusually high threshold
            new Object[]{2,  100,  0.1},
            new Object[]{8,  100,  0.02},
            new Object[]{10, 1000, 0.02},
            new Object[]{15, 1000, 0.02}
        };
    }

    @Test
    public void mean() {
        PercolationStats statsMock = spy(new PercolationStats(1, 2));
        when(statsMock.collectPercolationThresholds())
            .thenReturn(new double[]{2, 3, 5});

        assertThat(statsMock.mean(), is(closeTo(3.3, 0.1)));
    }

    @Test
    public void stddev() {
        PercolationStats statsMock = spy(new PercolationStats(1, 2));
        when(statsMock.collectPercolationThresholds())
            .thenReturn(new double[]{2, 3, 5});
        when(statsMock.mean()).thenReturn(3.3);

        assertThat(statsMock.stddev(), is(closeTo(1.5, 0.1)));
    }

    @Test
    public void confidenceInterval() {
        PercolationStats statsMock = spy(new PercolationStats(1, 2));
        when(statsMock.mean()).thenReturn(4.5);
        when(statsMock.stddev()).thenReturn(1.2);

        assertThat(statsMock.confidenceLo(), is(closeTo(2.8, 0.1)));
        assertThat(statsMock.confidenceHi(), is(closeTo(6.1, 0.1)));
    }
}
