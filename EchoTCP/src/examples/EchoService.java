package examples;

// 22.10. 10

/**
 *
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Iterator;
import java.util.LinkedList;

public class EchoService extends Thread {
    Socket client;
    static int response;
    static LinkedList<Ticket> stocks = new LinkedList<Ticket>();
    static DataOutputStream toClient;
    EchoService(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        String line;
        BufferedReader fromClient;
        DataOutputStream toClient;
        boolean verbunden = true;
        System.out.println("Thread started: " + this); // Display Thread-ID
        try {
            fromClient = new BufferedReader              // Datastream FROM Client
                    (new InputStreamReader(client.getInputStream()));
            toClient = new DataOutputStream(client.getOutputStream()); // TO Client
            while (verbunden) {     // repeat as long as connection exists
/*                line = fromClient.readLine();              // Read Request
                System.out.println("Received: "+ line);
                if (line.equals(".")) verbunden = false;   // Break Conneciton?
                else toClient.writeBytes(line.toUpperCase()+'\n'); // Response
*/
                //toClient.writeBytes(sendResponse());
                System.out.println("Received: " + (line = fromClient.readLine()));
                if (line.equals(".")) {                         // End of session?
                    verbunden = false;
                } else {
                    Ticket myTicket = new Ticket(client.getRemoteSocketAddress(), client.getPort(), line);
                    System.out.println(myTicket);
                    if (myTicket.getType().equals("SELL")) {
                        stocks.add(myTicket);
                        response = 2;
                    } else if (myTicket.getType().equals("BUY")) {
                        Iterator<Ticket> it = stocks.iterator();
                        response = -1;
                        while (it.hasNext()) {
                            Ticket i = it.next();
                            if (i.getStock_name().equals(myTicket.getStock_name()) &&
                                    i.getQuantity() == (myTicket.getQuantity()) &&
                                    i.getPrice() == myTicket.getPrice()) {
                                stocks.remove(i);
                                System.out.println("The ticket match with an item of our Stock.");
                                response = 1;
                                break;
                            }
                        }
                    }
                    System.out.println(response);
                    //sendResponse();
                    toClient.writeBytes(sendResponse());
                }


            }

            fromClient.close();
            toClient.close();
            client.close(); // End
            System.out.println("Thread ended: " + this);
        } catch (
                Exception e
                )

        {
            System.out.println(e);
        }

    }
    static String sendResponse() throws IOException {
        String retResponse;

        switch (response) {
            case 1:
                retResponse = ("Congratulation, your ticket is resolved" + '\n');
                break;
            case 2:
                retResponse = ("Thanks, your ticket is now available in our database" + '\n');
                break;
            case -1:
                retResponse = ("Sorry, your ticket is unresolved" + '\n');
                break;
            default:
                retResponse = ("No response to you for the moment" + '\n');
                break;
        }
        return retResponse;
    }
}

