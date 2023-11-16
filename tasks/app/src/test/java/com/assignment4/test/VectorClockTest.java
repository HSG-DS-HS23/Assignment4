package test.java.com.assignment4.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import main.java.com.assignment4.tasks.VectorClock;


class VectorClockTest {
    VectorClock cl1 = new VectorClock(4);
    int timestamp;
    @Test
    void tick() {
        cl1.tick(1);
        timestamp = cl1.getCurrentTimestamp(1);
        Assertions.assertEquals(1, timestamp);
    }

    @Test
    void updateClock() {
        VectorClock cl2=new VectorClock(4);
        cl2.setVectorClock(2,1);
        cl1.updateClock(cl2);
        String now = cl1.showClock();
        Assertions.assertEquals("[0, 0, 1, 0]", now);
    }


}