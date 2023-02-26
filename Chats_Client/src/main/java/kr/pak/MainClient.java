package kr.pak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainClient {

    public static void main(String[] args) {
        MainClient mainClient = new MainClient();
        System.out.println("------!!!!quit을 치면 서버와 연결종료!!!!------");
        mainClient.start();
    }

    public void start() {
        Socket socket = null;
        BufferedReader in = null;

        try {
            socket = new Socket("120.143.190.29", 8093);
            System.out.println("서버와 연결됨");

            String name = "User_" + (int)(Math.random() * 10);
            Thread sendThread = new SendThread(socket, name);
            sendThread.start(); //start = run

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (in != null) {
                String inputMsg = in.readLine();
                if ((name + "(이)가 접속을 끊음").equals(inputMsg)) {
                    break;
                }
                System.out.println(inputMsg);
            }
        } catch (IOException e) {
            System.out.println("서버 접속 끊김");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("서버 연결 종료");
    }
}
