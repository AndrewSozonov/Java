public class TestTask
{

    @BeforeSuite
    public static void before(){
        System.out.println("@before");
    }

    @AfterSuite
    public static void after(){
        System.out.println("@after");
    }

    @Test (priority = 6)
    public static void test1(){
        System.out.println("test1");
    }

    @Test
    public static void test2(){
        System.out.println("test2");
    }

    @Test (priority = 10)
    public static void test3(){
        System.out.println("test3");
    }

    @Test (priority = 3)
    public static void test4(){
        System.out.println("test4");
    }

    @Test (priority = 8)
    public static void test5(){
        System.out.println("test5");
    }

    @Test (priority = 1)
    public static void test6(){
        System.out.println("test6");
    }

    @Test
    public static void test7(){
        System.out.println("test7");
    }


}

