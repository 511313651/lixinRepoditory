package pipedDemo;

import java.io.PipedOutputStream;

public class OutPutThread implements Runnable {
    PipedOutputStream pos;

    public OutPutThread(PipedOutputStream pos) {
        this.pos = pos;
    }

    @Override
    public void run() {
        try {
            pos.write(66);
            pos.flush();
            pos.close();
        } catch (Exception e) {

        }

    }
}
