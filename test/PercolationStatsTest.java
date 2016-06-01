import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class PercolationStatsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    @Parameters({
        "0, 0",
        "1, 0",
        "0, 1"})
    public void constructorWithInvalidParamsThrowsException(int n, int t) {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("grid width and trials must be greater than 0");

        new PercolationStats(n, t);
    }

    @Test
    public void mean() {
        PercolationStats ps = new PercolationStats(10, 100);

        assertThat(ps.mean(), is(closeTo(0.59, 0.2)));
    }

    @Test
    public void stddev() {
        PercolationStats ps = new PercolationStats(10, 100);

        assertThat(ps.stddev(), is(closeTo(0.06, 0.03)));
    }

    @Test
    public void confidenceInterval() {
        PercolationStats ps = new PercolationStats(10, 100);

        assertThat(ps.confidenceLo(), is(closeTo(0.59, 0.05)));
        assertThat(ps.confidenceHi(), is(closeTo(0.59, 0.05)));
    }
}
