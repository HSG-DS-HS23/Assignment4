package main.java.com.assignment4.tasks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.*;


public class UdpVectorClient {

    public static void main(String[] args) throws Exception
    {
        System.out.println("Enter your id (1 to 3): ");
        Scanner id_input = new Scanner(System.in);
        int id = id_input.nextInt();

        // prepare the client socket
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");

        // initialize the buffers
        byte[] sendData;
        byte[] receiveData = new byte[1024];
        int port = 4040;
        List<String> logs;

        int startTime = 0;
        VectorClock vcl = new VectorClock(4);
        vcl.setVectorClock(id, startTime);

        //ask for user input aka message to the server
        System.out.println(id+": Enter any message:");
        Scanner input = new Scanner(System.in);

        while(true) {
            String messageBody = input.nextLine();
            // increment clock
            if (!messageBody.isEmpty()){
                vcl.tick(id);
            }
            HashMap<Integer, Integer> messageTime = new HashMap<>();
            messageTime.put(id,vcl.getCurrentTimestamp(id));
            Message msg = new Message(messageBody, messageTime);
            String responseMessage = msg.content + ':' + msg.messageTime;
            // check if the user wants to quit
            if(messageBody.equals("quit")){
                clientSocket.close();
                System.exit(1);
            }

            // send the message to the server
            sendData = responseMessage.getBytes();
            DatagramPacket packetToSend = new DatagramPacket(sendData,sendData.length,IPAddress,4040);
            clientSocket.send(packetToSend);
            /*
             * write your code to send message to the server. clientSocket.send(messageTosend);
             */


            // check if the user wants to see the history
            if(messageBody.equals("history")) {
                System.out.println("Receiving the chat history...");
                logs = new ArrayList<>();

                /*
                 * write your code to receive the logs, clientSocket.receive(getack);
                 * it should keep receiving till all the messages are reached.
                 * You can use the clientSocket.setSoTimeout(timeinmiliseconds); to detect if the all the messages have been received
                 * update the logs list
                 */
                clientSocket.setSoTimeout(1000);

                while (true){
                    try {
                        DatagramPacket getack = new DatagramPacket(receiveData, receiveData.length);
                        clientSocket.receive(getack);
                        String response = new String(getack.getData(), 0, getack.getLength());
                        logs.add(response);
                        if(!response.isEmpty()){
                            clientSocket.setSoTimeout(1000);
                        }
                    }catch (SocketTimeoutException e){
                        break;
                    }
                }

                UdpVectorClient uc = new UdpVectorClient();
                uc.showHistory(logs); // gives out all the unsorted logs stored at the server
                uc.showSortedHistory(logs); // shows sorted logs
            }
            else
            {
                VectorClientThread client;
                client = new VectorClientThread(clientSocket, vcl, id);
                Thread receiverThread = new Thread(client);
                receiverThread.start();
            }
        }
    }
    public void showHistory(List<String> logs){

        // prints the unsorted logs (history) coming form the server
        for (String message : logs) {

            System.out.println(message);
        }
    }
    public void showSortedHistory(List<String> logs){

        // prints sorted logs (history) received
        System.out.println("Print sorted conversation using attached vector clocks");
        Map<int[], String> logMap = new HashMap<>();

        /*
         * write your code to sort the logs (history) in ascending order
         * to sort the logs, use the clock array, for example, [0,0,1,1] as key the to the logMap.
         * Since this is a custom sorting, create a custom comparator to sort logs
         * once sorted print the logs that are following the correct sequence of the message flow
         * to store the sorted logs for printing you could use LinkedHashMap
         */
        for (String message: logs){
            String chat = message.split(":")[0];
            String clock = message.split(":")[1];
            clock = clock.replaceAll("\\[|\\]", "");
            clock = clock.replace(" ", "");
            String[] vectorClockStringArray = clock.split(",");
            int[] vectorClock = new int[vectorClockStringArray.length];
            for (int i = 0; i < vectorClock.length; i++) {
                vectorClock[i] = Integer.parseInt(vectorClockStringArray[i]);
            }
            logMap.put(vectorClock, chat);
        }
        printSortedLogs(logMap);

    }
    public void printSortedLogs(Map<int[], String> map){
        List<Map.Entry<int[], String>> list = new ArrayList<>(map.entrySet());
        list.sort(compareVectorTimestamps);
        //Print all entries of sorted list
        for (Map.Entry<int[], String> entry : list) {
            System.out.println(Arrays.toString(entry.getKey()) + " " + entry.getValue());
        }
    }

    // Custom comparator to compare Vector clocks
    public Comparator<Map.Entry<int[], String>> compareVectorTimestamps = new Comparator<>() {
        @Override
        public int compare(Map.Entry<int[], String> o1, Map.Entry<int[], String> o2) {
            ArrayList<Boolean> isLarger = new ArrayList<>();
            for (int i = 0; i < o1.getKey().length; i++) {
                isLarger.add(o1.getKey()[i] > o2.getKey()[i]);
            }
            if(!isLarger.contains(false)){
                return 1;
            } else if (!isLarger.contains(true)) {
                return -1;
            }else{
                return 0;
            }

        }
    };

}
