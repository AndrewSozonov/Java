package task3_1.task3;


import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {

    public List<T> list;

    public Box(ArrayList<T> list) {
        this.list = list;
    }


    // Метод рассчитывающий вес коробки
    public float getWeight() {
        float w = 0;
        for (int i = 0; i < this.list.size(); i++) {
            w += this.list.get(i).weight;
        }
        return w;
    }

    //метод сравнивающий веса коробок
    public boolean compareWeight(Box<?> box) {

        boolean result = false;
        if (Math.abs(this.getWeight() - box.getWeight()) < 0.0001)
            result = true;

        System.out.println(result);
        return result;
    }

    //метод пересыпающий из одной коробки в другую
    public void moveToAnotherBox(Box<T> box) {
        for (int i = 0; i < box.list.size(); i++) {
            this.list.add((T) box.list.get(i));
        }
        box.list.clear();
        System.out.println("Фрукты пересыпаны");
    }
}
