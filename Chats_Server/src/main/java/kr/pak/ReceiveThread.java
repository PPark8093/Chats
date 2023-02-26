package kr.pak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReceiveThread extends Thread {

    static List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());

    Socket socket = null;
    BufferedReader in = null;
    PrintWriter out = null;

    public ReceiveThread(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            list.add(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String name = "";
        try {
            //최초 1회는 client이름을 수신
            name = in.readLine();
            System.out.println(name + ") 새 연결 생성");
            sendAll(name + "(이)가 접속함");

            while (in != null) {
                String inputMsg = in.readLine();
                if ("quit".equals(inputMsg)) {
                    break;
                }
                sendAll(name + " :: " + inputMsg);
                System.out.println("::" + "<" + name + ">" + inputMsg);
            }
        } catch (IOException e) {
            System.out.println(name + "(이)가 접속끊김");
        } finally {
            sendAll(name + "(이)가 접속을 끊음");
            list.remove(out);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + "(이)가 연결 종료");
    }

    private void sendAll(String s) {
        for (PrintWriter cut: list) {
            out.println(s);
            out.flush();
        }
    }
}
