package examples;

// 22. 10. 10

/**
 *
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.LinkedList;

public class Broker {

  static String line;
  static BufferedReader fromClient;
  static DataOutputStream toClient;
  static int response;
  
  static LinkedList<Ticket> stocks = new LinkedList<Ticket>();

  public static void main(String[] args) throws Exception {
	  ServerSocket contactSocket = new ServerSocket(9999);
	  while (true) {                            // Handle connection request
		  Socket client = contactSocket.accept(); // creat communication socket
		  System.out.println("Connection with: "+client.getRemoteSocketAddress());
		  handleRequests(client);
	  }
  }

  static void handleRequests(Socket s) {
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
      System.out.println(e);
    }
  }

  static boolean receiveRequest() throws IOException {
    boolean holdTheLine = true;
    
    System.out.println("Received: " + (line = fromClient.readLine()));   
    if (line.equals(".")) {                         // End of session?
      holdTheLine = false;
    } else {
    	Ticket myTicket = new Ticket(fromClient.readLine());
    	if (myTicket.getType().equals("SELL")){
    		stocks.add(myTicket);
    	} else if (myTicket.getType().equals("BUY")){
    		Iterator<Ticket> it = stocks.iterator();
    		while (it.hasNext()) {
    			Ticket i = it.next();
    			if(it.next().getStock_name().equals(myTicket.getStock_name()) &&
    				it.next().getQuantity() == (myTicket.getQuantity()) &&
    				it.next().getPrice() == myTicket.getPrice()){
    				stocks.remove(i);
    				System.out.println("The ticket match with an item of our Stock.");
    				response = 1;
    			} else {
    				response = -1;
    				System.out.println("The ticket has no match.");
    			}
    		}
    	}
    	
    }
    return holdTheLine;
  }

  static void sendResponse() throws IOException {
	  if (response == 1) {
		  toClient.writeBytes("Congratulation, your ticket is resolved" + '\n');
	  } else if (response == -1) {
		  toClient.writeBytes("Sorry, your ticket is unresolved" + '\n');
	  }
  }
  
}
