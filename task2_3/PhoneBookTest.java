package task2_3;

import java.util.HashMap;

public class PhoneBookTest {

    public static void main(String[] args) {

        HashMap<String, String> phBook = new HashMap<>();

        PhoneBook book = new PhoneBook(phBook);

        book.initBook();

        book.get("Сидоров");

        book.add("Михайлов", "9211208929");
    }

}
