import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Task_1_Test {

    private int[] a;
    private int[] b;
    Task_1_and_2 task_1_and_2;

    public Task_1_Test(int[] a, int[] b) {
        this.a = a;
        this.b = b;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{5,8,9,0}, new int[]{4, 1, 4, 4, 5, 8, 9, 0}},
                {new int[]{2,6}, new int[]{4, 1, 8, 0, 0, 4, 2, 6}},
                {new int[]{0}, new int[]{4, 4, 4, 4, 4, 4, 4, 0}},
                {new int[]{5,8,9,0}, new int[]{8, 9, 0, 1, 5, 8, 9, 0}}
        });
    }

    @Before
    public void init(){ task_1_and_2 = new Task_1_and_2(); }

    @Test
    public void testTask1(){ Assert.assertArrayEquals(a, task_1_and_2.copyPartArray(b)); }
}
