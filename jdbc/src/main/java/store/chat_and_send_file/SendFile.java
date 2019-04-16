package src.main.java.store.chat_and_send_file;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;

public class SendFile implements Callable {
    Socket socket = null;
    String filePath;
    SendFile(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Object call() throws Exception {
        while (true) {
            if(1==2)break;
            //循环观察是否有fileName输入,如果有就继续下一步.
            while (true) {
                Thread.sleep(1000);
                if (filePath != null && new File(filePath).exists()) break;
            }
            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] bytes = new byte[1024];
            int len;
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
                bos.flush();
            }
            System.out.println("传输已完成!");
            setFilePath(null);
        }
        return null;
    }
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }
}
