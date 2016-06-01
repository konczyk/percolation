import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class QuickUnionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    @Parameters({"-1", "0"})
    public void constructorWithInvalidNodesNumberThrowsException(int n) {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("n must be larger than 0");

        new QuickUnion(n);
    }

    @Test
    @Parameters({"-1", "3"})
    public void findInvalidNodeThrowsException(int node) {
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("invalid node: " + node);

        new QuickUnion(3).find(node);
    }

    @Test
    @Parameters({"0", "1", "2"})
    public void defaultRoots(int node) {
        QuickUnion qu = new QuickUnion(3);

        assertThat(qu.find(node), is(node));
        assertThat(qu.count(), is(3));
    }

    @Test
    public void union() {
        QuickUnion qu = new QuickUnion(3);

        qu.union(1, 2);

        assertThat(qu.count(), is(2));
        assertThat(qu.connected(1, 2), is(true));
    }

    @Test
    public void rootsAfterUnionSameSizedComponents() {
        QuickUnion qu = new QuickUnion(3);

        qu.union(1, 2);

        assertThat(qu.find(1), is(1));
        assertThat(qu.find(2), is(1));
    }

    @Test
    public void rootsAfterUnionWithOneComponentBigger() {
        QuickUnion qu = new QuickUnion(3);

        qu.union(1, 2);
        qu.union(2, 0);

        assertThat(qu.find(0), is(1));
        assertThat(qu.find(1), is(1));
        assertThat(qu.find(2), is(1));
    }

}
