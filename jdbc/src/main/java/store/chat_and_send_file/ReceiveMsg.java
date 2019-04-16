package src.main.java.store.chat_and_send_file;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ReceiveMsg implements Callable {
    Socket socket;
    ReceiveFile receiveFile;
    ReceiveMsg(Socket socket,ReceiveFile receiveFile) {
        this.socket = socket;
        this.receiveFile = receiveFile;
    }

    @Override
    public Integer call() throws Exception {
        InputStream inputStream = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            //接收消息
            String revMsg = br.readLine();
            System.out.println(revMsg);
            if ("886".equals(revMsg)) break;
            if("sendFile".equals(revMsg)){
                String filePath = br.readLine();
                String[] split = filePath.split("\\\\");
                String fileName = split[split.length-1];
                receiveFile.setFileName(fileName);
            }
        }
        return 1;
    }
}
