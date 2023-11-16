/*
 * Keep the server class running and on the client end use the vector clock implementation to order the events
 * to show causal reasoning based on their vectors that has been sent along the messages.
 * In this task we do not use any global clock since the distributed systems lack such reference points. The vector clocks
 * in this situation can help create a partial ordering of the messages.
 */
package main.java.com.assignment4.tasks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;


public class UdpServer {

    //check if the timestamp part of the response from the server is integer or not
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static void main(String[] args) {
        int port = 4040;
        //list for recording the activities on the server
        ArrayList<String> activities = new ArrayList<>();
        // set the server port
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {

            // inform the status of the server to the users
            System.out.println("Server is now Listening...");
            int startTime = 0;

            LamportTimestamp cl;
            VectorClock vcl;

            // create instances of Logical Clocks
            cl = new LamportTimestamp(startTime);
            vcl = new VectorClock(4);

            // initialize the vector clock
            vcl.setVectorClock(0,0);

            while (true) {
                try {
                    // create empty buffers for sending and receiving
                    byte[] receivedData = new byte[1024];
                    ServerThread server;

                    // create empty datagram packet
                    DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);

                    // receive data from the client
                    serverSocket.receive(receivePacket);

                    // print the status
                    InetAddress ClientIP = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();
                    System.out.println("Now communicating with: " + ClientIP.getHostAddress() + 0);

                    // run server thread once the connection is established
                    server = new ServerThread(serverSocket, receivePacket, ClientIP, clientPort, cl, vcl, activities);
                    new Thread(server).start();
                }
                catch (IOException e){
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

    }

}


