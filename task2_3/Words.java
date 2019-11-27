package task2_3;

import java.lang.reflect.Array;
import java.util.*;

public class Words {


    public static void main(String[] args) {

        List<String> list =
                Arrays.asList("Таня","Ира","Света","Аня","Ира","Марина","Аня","Таня","Ира","Рита","Маша","Даша");

        HashMap<String, Integer> hm = new HashMap<>();

        Integer am;

        for (String i : list) {
            am = hm.get(i);
            hm.put(i, am == null ? 1 : am+1);
        }

        System.out.println("Количество имен в списке " + hm);
    }
}

