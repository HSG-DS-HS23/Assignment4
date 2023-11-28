package main.java.com.assignment4.tasks;

import java.util.Arrays;
import static java.lang.Math.max;

public class VectorClock {

    private final int[] timestamps;
    public VectorClock(int numOfClients){
        timestamps = new int[numOfClients];
        Arrays.fill(timestamps, 0);
    }
    public void setVectorClock(int processId, int time){
        timestamps[processId] = time;
    }
    public void tick(int processId){
        timestamps[processId] += 1; // update the code to increment the clock for the given process id
    }
    public int getCurrentTimestamp(int processId){
        return timestamps[processId];
    }
    public void updateClock(VectorClock other){
        for (int i = 0; i < timestamps.length; i++) {
            timestamps[i] = max(timestamps[i], other.getCurrentTimestamp(i));
        }
    }
    public String showClock(){
        return Arrays.toString(timestamps);
    }

}
