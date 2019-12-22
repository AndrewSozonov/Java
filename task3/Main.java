package task3_1.task3;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Apple>appleList1 = new ArrayList<>();
        ArrayList<Orange>orangeList1 = new ArrayList<>();
        ArrayList<Apple>appleList2 = new ArrayList<>();
        ArrayList<Orange>orangeList2 = new ArrayList<>();

        for (int i=0; i<5; i++) {
            appleList1.add(new Apple(1));
            orangeList1.add(new Orange(1.5f));
        }
        for (int i=0; i<10; i++) {
            appleList2.add(new Apple(1));
            orangeList2.add(new Orange(1.5f));
        }

        Box boxWithApples1 = new Box(appleList1);
        Box boxWithOranges1 = new Box(orangeList1);
        Box boxWithApples2 = new Box(appleList2);
        Box boxWithOranges2 = new Box(orangeList2);

        boxWithApples1.compareWeight(boxWithApples2);

        System.out.println(boxWithOranges1.getWeight());
        System.out.println(boxWithOranges2.getWeight());

        boxWithOranges1.moveToAnotherBox(boxWithOranges2);

        System.out.println(boxWithOranges1.getWeight());
        System.out.println(boxWithOranges2.getWeight());


    }
}
