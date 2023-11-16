/*
The message structure for the assignment is given in this class. It can be instantiated to hold and access the content of the message and the timestamp.
 */
package main.java.com.assignment4.tasks;

import java.util.HashMap;
import java.util.Map;

public class Message {
    String content;
    Map<Integer, Integer> messageTime;
    public Message(String content, HashMap<Integer, Integer> messageTime) {
        this.content = content;
        this.messageTime = messageTime;
    }

    public String getContent() {
        return content;
    }

    public Map<Integer, Integer> getMessageTime() {
        return messageTime;
    }
}
