import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.*;

@RunWith(JUnitParamsRunner.class)
public class QuickUnionTest {

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionIfNodesNumberIsNonPositive() {
        new QuickUnion(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    @Parameters({"-1", "3"})
    public void throwsExceptionOnInvalidNode(int node) {
        QuickUnion qu = new QuickUnion(3);
        qu.find(node);
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
