package src.main.java.store.chat_and_send_file;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServlet {
    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ServerSocket msgServerSocket = new ServerSocket(8080);
        ServerSocket fileServerSocket = new ServerSocket(8090);
        while (true){
            Socket msgSocket = msgServerSocket.accept();
            Socket fileSocket = fileServerSocket.accept();
            SendFile sendFile = new SendFile(fileSocket);
            threadPool.submit(sendFile);
            ReceiveFile receiveFile = new ReceiveFile(fileSocket);
            threadPool.submit(receiveFile);
            //建立发送消息的连接时,需要把传递文件的通道告诉它,方便调用
            threadPool.submit(new SendMsg(msgSocket,sendFile));
            threadPool.submit(new ReceiveMsg(msgSocket,receiveFile));
        }
    }
}
