package task2_3;

import java.util.HashMap;
import java.util.Map;



public class PhoneBook {

    HashMap<String, String> phBook;


    public PhoneBook (HashMap<String, String> phBook) {
        this.phBook = phBook;
    }

    public void initBook() {

        phBook.put("Иванов", "89213408289");
        phBook.put("Сидоров", "88762450901");
        phBook.put("Смирнов", "89212309295");
        phBook.put("Иванов", "89216093445");
        phBook.put("Попов", "89056783401");
    }

    public void get(String i) {
        String value = null;


        for (Map.Entry entry :phBook.entrySet()) {
            if (i.equals(entry.getKey())) {
                value = (String) entry.getValue();
            }
        }
        System.out.println(i + " " + value);
    }

    public void add(String name, String phone) {

        phBook.put(name, phone);
        System.out.println();
        System.out.println(name + " " + phone + " добавлен в справочник");
    }
}

