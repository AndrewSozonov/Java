package task2_2;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

public class MyArraySizeException extends Exception {
    String message;

    public MyArraySizeException(String message) {
        this.message = message;
    }

    public String toString(){
        return this.message;
    }
}
