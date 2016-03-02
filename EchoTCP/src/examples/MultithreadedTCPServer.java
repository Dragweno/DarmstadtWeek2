package examples;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */


public class MultithreadedTCPServer {

    static String line;
    static Socket client;
    static BufferedReader fromClient;
    static DataOutputStream toClient;

    static int response;
    static LinkedList<Ticket> stocks = new LinkedList<Ticket>();

    public static void main(String[] args) throws Exception{
        int port = 9999;
        ServerSocket listenSocket = new ServerSocket(port);
        System.out.println("Multithreaded Server starts on Port "+port);
        while (true){
            Socket client = listenSocket.accept();
            System.out.println("Connection with: " +     // Output connection
                    client.getRemoteSocketAddress());   // (Client) address
            new EchoService(client).start();
        }
    }
    /*static void handleRequests(Socket s) {
        try {
            fromClient = new BufferedReader(        // Datastream FROM Client
                    new InputStreamReader(s.getInputStream()));
            toClient = new DataOutputStream(
                    s.getOutputStream());                 // Datastream TO Client
            while (receiveRequest()) {              // As long as connection exists
                sendResponse();
            }
            fromClient.close();
            toClient.close();
            s.close();
            System.out.println("Session ended, Server remains active");
        } catch (Exception e) {
            System.out.println("The trader "+client.getRemoteSocketAddress()+" is disconnected");
        }
    }

    static boolean receiveRequest() throws IOException {
        boolean holdTheLine = true;

        System.out.println("Received: " + (line = fromClient.readLine()));
        if (line.equals(".")) {                         // End of session?
            holdTheLine = false;
        } else {
            Ticket myTicket = new Ticket(client.getRemoteSocketAddress(), client.getPort(), line);
            System.out.println(myTicket);
            if (myTicket.getType().equals("SELL")){
                stocks.add(myTicket);
                response = 2;
            } else if (myTicket.getType().equals("BUY")){
                Iterator<Ticket> it = stocks.iterator();
                response = -1;
                while (it.hasNext()) {
                    Ticket i = it.next();
                    if(i.getStock_name().equals(myTicket.getStock_name()) &&
                            i.getQuantity() == (myTicket.getQuantity()) &&
                            i.getPrice() == myTicket.getPrice()){
                        stocks.remove(i);
                        System.out.println("The ticket match with an item of our Stock.");
                        response = 1;
                        break;
                    }
                }
            }
        }
        return holdTheLine;
    }

    static void sendResponse() throws IOException {
        switch (response) {
            case 1 :
                toClient.writeBytes("Congratulation, your ticket is resolved" + '\n');
                break;
            case 2 :
                toClient.writeBytes("Thanks, your ticket is now available in our database" + '\n');
                break;
            case -1 :
                toClient.writeBytes("Sorry, your ticket is unresolved" + '\n');
                break;
            default :
                toClient.writeBytes("No response to you for the moment" + '\n');
                break;
        }
    }*/
}
