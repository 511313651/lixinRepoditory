package src.main.java.store.chat_and_send_file;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ReceiveFile implements Callable {
    Socket socket;
    String fileName;

    ReceiveFile(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Object call() throws Exception {
        while (true) {
            if(1 == 2)break;
            while (fileName == null){
                Thread.sleep(1000);
            }
            InputStream inputStream = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("d:\\"+fileName));
            byte[] bytes = new byte[1024];
            int len;
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
                bos.flush();
            }
            fileName = null;
        }
        return null;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
