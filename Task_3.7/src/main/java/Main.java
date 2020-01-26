
import java.lang.reflect.*;



public class Main {

    static void start(final Class obj) throws InvocationTargetException, IllegalAccessException {

        Method[] methods = obj.getDeclaredMethods();
        int countBefore = 0;
        int countAfter = 0;
        Method beforeMethod = null;
        Method afterMethod = null;

        try {
            for (Method m : methods) {

                if (m.isAnnotationPresent(BeforeSuite.class)) {
                    beforeMethod = m;
                    countBefore++;
                }
                if (m.isAnnotationPresent(AfterSuite.class)) {
                    afterMethod = m;
                    countAfter++;
                }
            }
            if (countBefore != 1 || countAfter != 1) throw  new RuntimeException();
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.err.println("Методы с аннотацией BeforeSuite или AfterSuite отсутствуют или не в единственном экземпляре");
        }

        if (countBefore == 1) {
            beforeMethod.invoke(null);
        }

        for (int i = 10; i >=1; i--) {
            for (Method m : methods) {
                if (m.isAnnotationPresent(Test.class)) {
                    if (m.getAnnotation(Test.class).priority() == i) {
                        System.out.print(" >>>priority " + i + " <<< ");
                        m.invoke(null);
                    }
                }
            }
        }

        if (countAfter == 1) {
            afterMethod.invoke(null);
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        start(TestTask.class);
    }
}
