

public class Task_1_and_2 {


    static int[] copyPartArray (int[] arr) {
        int x = -1;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4) {
                x = i + 1;
            }
        }
        if (x == -1) throw new RuntimeException("В массиве нет числа 4");

        int[] r = new int[arr.length - x];
        System.arraycopy(arr, x, r, 0, arr.length - x);
        return r;
    }

    static boolean contArray (int[] arr) {
        boolean result = false;
        boolean result1 = false;
        boolean result2 = false;
        boolean result3 = true;

        for (int x : arr) {
            if (x == 1) result1 = true;
        }
        for (int x : arr) {
            if (x == 4) result2 = true;
        }
        for (int x : arr) {
            if (x != 1 && x != 4) result3 = false;
        }
        if (result1 && result2 && result3)
            result = true;
        return result;
    }

    public static void main(String[] args) {
    }
}
