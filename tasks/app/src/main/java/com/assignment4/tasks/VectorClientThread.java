package main.java.com.assignment4.tasks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class VectorClientThread implements Runnable {

    private final DatagramSocket clientSocket;
    VectorClock vcl;
    byte[] receiveData = new byte[1024];

    int id;
    public VectorClientThread(DatagramSocket clientSocket, VectorClock vcl, int id) {

        this.clientSocket = clientSocket;
        this.vcl = vcl;
        this.id = id;
    }

    @Override
    public void run() {
        String response = "example:[1,1.0.0]"; //update this with the real response string from server
        /*
         * Write your code to receive messgaes from the server and update the vector clock
         */
        String[] responseMessageArray = response.split(":");
        /*
         * you could use "replaceAll("\\p{Punct}", " ").trim().split("\\s+");" for filteing the received message timestamps
         * update clock and increament local clock (tick) for receiving the message
         */
        System.out.println("Server:" +responseMessageArray[0] +" "+ vcl.showClock());
    }
}
