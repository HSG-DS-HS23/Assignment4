# Vector Clock

This repository contains the project template for completing Task 1, Task 2 of Assignment 4. Execute the commands from the root folder BCS-DS-Assignment-Package/tasks.

> **_NOTE:_**
> After loading the project depending on which IDE (if using), you might see some file not found errors. However, they will disappear after you have successfully built the project using following command from the tasks folder.

## Table of Contents
- [Project Structure](#ProjectStructure)
- [Run Server](#run-server)
- [Task 1](#Task-1)
- [Task 2](#Task-2)

## Project Structure

```bash
src
    ├── main
    │   └── java
    │       └── com
    │           └── assignment4
    │               └── tasks
    │                   ├── LTClientThread.java
    │                   ├── LamportTimestamp.java (Lamports Timestamp implementaion)
    │                   ├── Message.java (Message format to be exchanged between server and the clients. This class can be instantiated to hold and access the content of the message and the timestamp)
    │                   ├── ServerThread.java (Server thread)
    │                   ├── UdpLTClient.java (UDP client to use lamports Timestamp implementation)
    │                   ├── UdpServer.java (Server main implementation)
    │                   ├── UdpVectorClient.java (UDP client to use vector clock)
    │                   ├── VectorClientThread.java
    │                   └── VectorClock.java (VectorClock implementation)
    └── test
        └── java
            └── com
                └── assignment4
                    └── test
                        ├── LamportTimestampTest.java (Test file for LogicalClock.java)
                        └── VectorClockTest.java (Test file for VectorClock.java)

```

## Run Server
###### On MacOS:
```bash
./gradlew build  -x test
./gradlew run -PchooseMain="main.java.com.assignment4.tasks.UdpServer"  
```
###### On Windows:
```bash
.\gradlew build  -x test
.\gradlew run -PchooseMain="main.java.com.assignment4.tasks.UdpServer"  
```

## Task 1
1. Test your Lamport Timestamp implementation
###### On MacOS:
```bash
./gradlew test 
```
###### On Windows:
```bash
.\gradlew test
```

2. Run your Lamport Timestamp client
###### On MacOS:

```bash
./gradlew run -PchooseMain="main.java.com.assignment4.tasks.UdpLTClient" --console=plain 
```

###### On Windows:
```bash
.\gradlew run -PchooseMain="main.java.com.assignment4.tasks.UdpLTClient" --console=plain 
```

## Task 2

1. Test your vector clock implementation
###### On MacOS:
```bash
./gradlew test 
```
###### On Windows:
```bash
.\gradlew test
```

2. Run your vector clock client
###### MacOS
```bash
 ./gradlew run -PchooseMain="main.java.com.assignment4.tasks.UdpVectorClient" --console=plain 
 ```
###### Windows
```bash
 .\gradlew run -PchooseMain="main.java.com.assignment4.tasks.UdpVectorClient" --console=plain 
 ```