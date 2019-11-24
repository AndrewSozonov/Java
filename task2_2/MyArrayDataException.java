package task2_2;

public class MyArrayDataException extends Exception {

    String message;
    int i,j;

    public MyArrayDataException(String message, int x, int y) {
        this.message = message;
        this.i = x;
        this.j = y;
    }

    public String toString() {
        return this.message + this.i + this.j;
    }

}
