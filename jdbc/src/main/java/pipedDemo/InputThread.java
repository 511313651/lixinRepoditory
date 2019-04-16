package pipedDemo;

import java.io.IOException;
import java.io.PipedInputStream;

public class InputThread implements Runnable{
    PipedInputStream pis;
    public InputThread(PipedInputStream pis) {
        this.pis = pis;
    }

    @Override
    public void run(){
        try {
            int read = pis.read();
            System.out.println(read);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
