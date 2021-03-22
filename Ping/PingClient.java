// CPSC 441- Fall 2020 - Assignment 3
// Completed By: Mehdi Marzban


import java.io.*; 
import java.net.*; 

public class PingClient {
    public static int BUFFERSIZE = 32;
    public static void main(String args[]) throws Exception 
    { 
        if (args.length != 2)
        {
            System.out.println("Usage: UDPClient <Server IP> <Server Port>"); //request the ip and port that the client wants to connect to 
            System.exit(1);
        }
        
        DatagramSocket clientSocket=new DatagramSocket(); // initializes the socket that's going to connect to the server 
        DatagramPacket myPacket=null;
        
        byte[] outBuffer = null;
        byte[] inBuffer =null;
        long AveTime=0;
        long MinTime=0;
        long MaxTime=0;
        int counter=0;
        String sent="";
       try {
    	   //need to send 10 packets total
    	   for(int i=0; i<10;i++) {
    		   
    		   long milliTime=System.currentTimeMillis();
    		   sent="PING "+i+" "+ milliTime+"\r\n";// this is the ping information that holds the packet number and the time in ms being sent 
    		   
    		   outBuffer=sent.getBytes();
    		   myPacket=new DatagramPacket(outBuffer, outBuffer.length,InetAddress.getByName(args[0]),Integer.parseInt(args[1]));// creates the packet thats being sent to the server.
    		   clientSocket.send(myPacket); // the packet is then sent to the server
    		   
    		   clientSocket.setSoTimeout(1000); // here the socket waits 1 second
    		   
    		   inBuffer=new byte[BUFFERSIZE];
    		   myPacket=new DatagramPacket(inBuffer, inBuffer.length);
    		   //here is where the client waits 1 second to see if there is a reply, if there is no reply it will "continue" which simulates that packet
    		   //being lost
    		   try {
    			   clientSocket.receive(myPacket); 
    		   }catch(SocketTimeoutException exception) {
    			   continue;
    		   }
    		   //here is the packet's RTT 
    		   milliTime=System.currentTimeMillis()-milliTime;
    		   //Sets the first successful packet's time as the mintime
    		   if(MinTime==0) {
    			   MinTime=milliTime;
    		   }
    		   System.out.println("RTT " +i+" = "+milliTime+"ms");
    		   
    		   //Creating the Average time
    		   AveTime+=milliTime;
    		   counter++;
    		   sent=new String(myPacket.getData());
    		   
    		   //Creating MinTime
    		   if(MinTime>milliTime) {
    			   MinTime=milliTime;
    		   }
    		   //Creating MaxTime
    		   if(MaxTime<milliTime) {
    			   MaxTime=milliTime;
    		   }
    		   
    		   sent=sent.substring(0, myPacket.getLength());
    		   
    		   System.out.println("Server: "+sent);
    	   }
       }catch(UnknownHostException ex) {
    	   System.err.println(ex);
       }catch(IOException ex) {
    	   System.err.println(ex);
       }
       System.out.println("The Average RTTs is: "+ AveTime/counter+"ms");
       System.out.println("The Minimum RTTs is: "+ MinTime+"ms");
       System.out.println("The Maximum RTTs is: "+ MaxTime+"ms");
       
    }
    
}
