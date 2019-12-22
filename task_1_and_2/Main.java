package task3_1.task_1_and_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    // метод переставляющиий местами элементы массива
    static <T> void swapArrElements (T[]arr, int a, int b) {

        T temp = arr[a];
        arr[a] =  arr[b];
        arr[b] = temp;
    }

    // метод преобразующий массив в ArrayList
    static <T> List<T> arrToArrList(T[]arr) {

        ArrayList<T>l  = new ArrayList<T>(Arrays.asList(arr));
        return l;
    }


    public static void main(String[] args) {

        Integer[]arr1  = {1,2,3,4,5,6,7,8};
        String[]arr2 = {"a","b","c","d","e","f"};

        swapArrElements(arr1, 0, 7);
        String intArrayString = Arrays.toString(arr1);
        System.out.println("Array 1 " + intArrayString);

        swapArrElements(arr2, 0, 5);
        String intArrayString2 = Arrays.toString(arr2);
        System.out.println("Array 2 " + intArrayString2);


        System.out.println("ArrayList 1 " + arrToArrList(arr1));
        System.out.println("ArrayList 2 " + arrToArrList(arr2));
    }
}
