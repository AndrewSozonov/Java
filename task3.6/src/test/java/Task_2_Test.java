import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Task_2_Test {
    Task_1_and_2 task_1_and_2;
    boolean a;
    private int[] b;


    public Task_2_Test(boolean a, int[] b) {
        this.a = a;
        this.b = b;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {true, new int[]{4, 1, 1, 4, 4, 4, 1, 4}},
                {false, new int[]{1, 1, 1, 1, 1, 1, 1, 1}},
                {false, new int[]{4, 4, 4, 4, 4, 4, 4, 4}},
                {false, new int[]{8, 9, 0, 1, 5, 8, 9, 0}}
        });
    }

    @Before
    public void init(){ task_1_and_2 = new Task_1_and_2(); }

    @Test
    public void testTask2(){Assert.assertEquals(a, task_1_and_2.contArray(b)); }
}
