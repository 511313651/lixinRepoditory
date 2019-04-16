package src.main.java.store.chat_and_send_file;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpClient {
    public static void main(String[] args) throws IOException {
        Socket msgSocket = new Socket("127.0.0.1", 8080);
        Socket fileSocket = new Socket("127.0.0.1", 8090);
        InputStream inputStream = msgSocket.getInputStream();
        OutputStream outputStream = msgSocket.getOutputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        Scanner sc = new Scanner(System.in);
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        SendFile sendFile = new SendFile(fileSocket);
        threadPool.submit(sendFile);
        ReceiveFile receiveFile = new ReceiveFile(fileSocket);
        threadPool.submit(receiveFile);
        threadPool.submit(new SendMsg(msgSocket,sendFile));
        threadPool.submit(new ReceiveMsg(msgSocket,receiveFile));
    }
}
