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
        try {
            String response = null; //update this with the real response string from server
            DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivedPacket);
            response = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            String[] responseMessageArray = response.split(":");
            String message = responseMessageArray[0];
            String intString = responseMessageArray[1];
            String[] stringArray = intString.replaceAll("\\p{Punct}", " ").trim().split("\\s+");
            int[] intArray = new int[stringArray.length];
            for (int i = 0; i < stringArray.length; i++) {
                int number = Integer.parseInt(stringArray[i]);
                intArray[i] = number;
            }
            /*
             * you could use "replaceAll("\\p{Punct}", " ").trim().split("\\s+");" for filteing the received message timestamps
             * update clock and increament local clock (tick) for receiving the message
             */

            VectorClock vcl2 = new VectorClock(intArray.length);
            for (int i = 0; i < intArray.length; i++) {
                vcl2.setVectorClock(i, intArray[i]);
            }
            System.out.println("Server:" + message + " " + vcl.showClock());
            vcl.updateClock(vcl2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
