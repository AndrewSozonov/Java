import java.util.concurrent.*;

public class MainClass {
    public static final int CARS_COUNT = 4;


    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        System.out.println("");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40)); // конструктор гонки из этапов
        CyclicBarrier readyBarrier = new CyclicBarrier(CARS_COUNT);
        CountDownLatch start = new CountDownLatch(CARS_COUNT);
        CountDownLatch finish = new CountDownLatch(CARS_COUNT);

        Car[] cars = new Car[CARS_COUNT];  // Массив машин
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), readyBarrier, start, finish);
        }

        ExecutorService pool = Executors.newFixedThreadPool(CARS_COUNT);
        ExecutorCompletionService exec = new ExecutorCompletionService(pool);
        Future<String> winner;


        for (int i = 0; i < cars.length; i++) {   // запуск потоков
            exec.submit(cars[i]);
        }

        try {
            start.await();
            System.out.println();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            System.out.println();

            winner = exec.take();
            System.out.println(winner.get() + " - WIN !");

            finish.await();
            System.out.println();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
            System.out.println();
            pool.shutdown();

        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}





