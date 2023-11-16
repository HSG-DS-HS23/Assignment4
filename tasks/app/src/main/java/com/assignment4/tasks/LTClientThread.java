package com.assignment4.tasks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class LTClientThread implements Runnable {

    private final DatagramSocket clientSocket;
    LamportTimestamp lc;

    byte[] receiveData = new byte[1024];

    public LTClientThread(DatagramSocket clientSocket, LamportTimestamp lc) {
        this.clientSocket = clientSocket;
        this.lc = lc;
    }

    @Override
    public void run() {
        String response = null;
        DatagramPacket receivedPacket = new DatagramPacket(receiveData,receiveData.length());
        clientSocket.receive(receivedPacket);
        response = new String(receivedPacket.getData(),0,receivedPacket.getLength());

        String[] fullMessage = response.split(":");
        String message = fullMessage[0];
        int timestamp = Integer.parseInt(fullMessage[1]);

        System.out.println("Server:" + response + ":" + lc.getCurrentTimestamp());

        lc.updateClock(timestamp);
        /*
         * write your code to parse the response. Remember the response you receive is in message:timestamp format.
         * response.split(":");
         * update clock every time the client receives a message
         */


    }
}
