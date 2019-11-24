package task2_2;


import java.lang.reflect.Array;

public class Main {

    public static void main(String[] args) {
        String[][] arr = {{"2", "4", "5","yt"}, {"3", "5", "4", "6"}, {"7", "4", "8", "4"}, {"3", "8", "1", "0"}};

        try {
            checkArr(arr);
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        } catch (MyArrayDataException e) {
            e.printStackTrace();
        }
    }

    public static void checkArr(String arr[][]) throws MyArraySizeException, MyArrayDataException {
        int x = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length != 4 || arr.length != 4) {
                throw new MyArraySizeException("Неккоректное количество элементов массива");
            }
            for (int j = 0; j < arr.length; j++) {
                if (!isNumber(arr[i][j])) {
                    throw new MyArrayDataException("Неккоректный элемент массива в ячейке ", i, j);
                }
                    x += Integer.parseInt(arr[i][j]);
            }
        }
        System.out.println("Сумма элементов массива: " + x);
    }


    public static boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }
}




