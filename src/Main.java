// Nasir Johnson
// CS 2336
// Brian Ricks
// 04/29/2024

import java.util.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.File;

public class Main {
    public static Map<Integer, SimpleHost> hostMap = new HashMap<Integer, SimpleHost>();

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scnr = new Scanner(System.in);
        FutureEventList listObj = new LinkedEventList();    // Reference to fel
        SimpleHost host = new SimpleHost();     // Used for creating hosts


        String[] lineArr;
        int lineCount = 0;

        System.out.print("SRC Path: ");     // Enter path of simulation file
        String fileName = scnr.nextLine();
        FileInputStream fileStream = new FileInputStream(fileName);
        Scanner fileScanner = new Scanner(fileStream);

        /*
            While loop used for creating host start topology

            Scanner reads from text file until .hasNext() == null
            Gets individual lines and splits elements to store them in lineArr

            For each host, we add to host hash map to later access when sending pings
            Key will be host number, value will be the host object itself

            If -1 encountered start sending pings
         */
        while (fileScanner.hasNext()) {
            lineCount++;
            lineArr = fileScanner.nextLine().split(" ", 10);

            if (Integer.parseInt(lineArr[0]) == -1) {
                break;
            }

            else {

                // Start creating new neighbor nodes to central host after first line
                if (lineCount > 1) {
                    SimpleHost newHost = new SimpleHost();
                    newHost.setHostParameters(Integer.parseInt(lineArr[0]), listObj);

                    host.addNeighbor(newHost, Integer.parseInt(lineArr[1]));
                    newHost.addNeighbor(host, Integer.parseInt(lineArr[1]));
                    hostMap.put(Integer.parseInt(lineArr[0]),newHost);      // Adds host to hashmap
                }

                // First line therefore create central host
                else {
                    host.setHostParameters(Integer.parseInt(lineArr[0]), listObj);
                    hostMap.put(Integer.parseInt(lineArr[0]),host);     // Adds host to hashmap
                }
            }
        }

        /*
            While loop used for sending pings between host nodes

            Splits lines up into src, dest, interval and duration
            Src itn will be used to access host object by its key
            Will access object sendPings method and pass dest, interval and duration

         */
        while (fileScanner.hasNext()) {

            lineArr = fileScanner.nextLine().split(" ", 10);

            SimpleHost sendHost = hostMap.get(Integer.parseInt(lineArr[0]));
            sendHost.sendPings(Integer.parseInt(lineArr[1]), Integer.parseInt(lineArr[2]), Integer.parseInt(lineArr[3]));

        }


        while(listObj.size() != 0){
            listObj.removeFirst().handle();     // Remove from fel
        }


    }
}