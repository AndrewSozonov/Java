import java.util.concurrent.*;

public class Car implements Callable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    CyclicBarrier readyBarrier;
    CountDownLatch start;
    CountDownLatch finish;

    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CyclicBarrier barrier, CountDownLatch str, CountDownLatch fin) {
        this.race = race;
        this.speed = speed;
        this.readyBarrier = barrier;
        this.start = str;
        this.finish = fin;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public String call() {
        try {
            System.out.println(this.name + " готовится");   //Этап подготовки
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            readyBarrier.await();
            start.countDown();
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {  //Гонка
            race.getStages().get(i).go(this);

        }
        finish.countDown();
        return this.name;
    }
}