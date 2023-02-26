package kr.pak;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    public static void main(String[] args) {
        MainServer mainServer = new MainServer();
        mainServer.start();
    }

    public void start() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(8093);
            while (true) {
                System.out.println("연결 대기중");
                socket = serverSocket.accept();

                //클라이언트가 접속할때마다 새로운 스레드 생성
                ReceiveThread receiveThread = new ReceiveThread(socket);
                receiveThread.start(); //start = run
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                    System.out.println("Closed");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("ERROR");
                }
            }
        }
    }
}
