package task2_5;

public class Main {

    static final int size = 10000000;
    static final int h = size/2;
    static float[] arr = new float[size];


    public static void arrWhole() {  // Метод вычисляющий массив целиком

        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }

        long before = System.currentTimeMillis();

        for (int i = 0; i < size; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i/5)*Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
        }
        long after = System.currentTimeMillis();
        System.out.println("Обычный массив " + (after - before));
    }


    public static void arrThread() {   // Метод разбивающий массив пополам

        float[] arr1 = new float[h];
        float[] arr2 = new float[h];

        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }

        long before = System.currentTimeMillis();  // Засекаем время

        System.arraycopy(arr,0,arr1,0,h);  // Разбиваем массив на половины
        System.arraycopy(arr,h,arr2,0,h);


        Thread a1 = new Thread(new Runnable() {   // Поток вычисляющий первую половину
            @Override
            public void run() {
                for (int i = 0; i < arr1.length; i++){
                    arr1[i] = (float)(arr[i] * Math.sin(0.2f + i/5)*Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
                }
            }
        });

        Thread a2 = new Thread(new Runnable() {   // Поток вычисляющий вторую половину
            @Override
            public void run() {
                for (int i = 0; i < arr2.length; i++){
                    arr2[i] = (float)(arr[i] * Math.sin(0.2f + (h + i)/5)*Math.cos(0.2f + (h + i)/5) * Math.cos(0.4f + (h + i)/2));
                }
            }
        });

        a1.start();  // Запускаем потоки
        a2.start();

        try {  // Просим основной поток дождаться вспомогательных
            a1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            a2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arr1, 0, arr,0, h);  // Собираем массив из двух половин
        System.arraycopy(arr2, 0, arr, h, h);

        long after = System.currentTimeMillis();   // Засекаем время выполнения
        System.out.println("Многопоточный массив " + (after - before));
    }


    public static void main(String[] args) {

        arrWhole();
        arrThread();
    }
}
