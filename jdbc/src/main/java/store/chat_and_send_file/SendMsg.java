package src.main.java.store.chat_and_send_file;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class SendMsg implements Callable{
    Socket socket;
    SendFile sendFile;
    SendMsg(Socket socket,SendFile sendFile){
        this.socket = socket;
        this.sendFile = sendFile;
    }
    @Override
    public Object call() throws Exception {
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        Scanner sc = new Scanner(System.in);
        while (true) {
            //发消息
            String sendMsg = sc.next();
            bw.write(sendMsg);
            bw.newLine();
            bw.flush();
            if("886".equals(sendMsg))break;
            //如果发送的是sendFile,就代表准备发送文件了
            if("sendFile".equals(sendMsg)){
                System.out.println("请输入要传输的文件的完整路径");
                String path;
                while (!new File(path = sc.next()).exists()){
                    System.out.println("该路径下的文件未找到,请重新输入");
                }
                bw.write(path);
                bw.newLine();
                bw.flush();
                sendFile.setFilePath(path);
            }
        }
        return 1;
    }
}
