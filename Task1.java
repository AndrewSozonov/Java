

public class Task1 {

    static Object object = new Object();
    static volatile char currentChar = 'A';

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {

                    synchronized (object) {
                        while (currentChar != 'A') {
                            object.wait();
                        }
                        System.out.print("A");
                        currentChar = 'B';
                        object.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i <= 5; i++) {

                    synchronized (object) {
                        while (currentChar != 'B') {
                            object.wait();
                        }
                        System.out.print("B");
                        currentChar = 'C';
                        object.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i <= 5; i++) {

                    synchronized (object) {
                        while (currentChar != 'C') {
                            object.wait();
                        }
                        System.out.print("C");
                        currentChar = 'A';
                        object.notifyAll();
                    }
                    System.out.print(" ");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
