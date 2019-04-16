package pipedDemo;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        String x = "c";
        switch (x){
            case "a":
                System.out.println("a");
            case "b":
                System.out.println("b");
            default:
                System.out.println("c");
                
        }
    }
}
