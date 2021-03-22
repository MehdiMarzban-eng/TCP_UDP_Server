import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Echo {
	private static int portNum= 8889;
	public static int BUFFERSIZE=128;
	
	public static void main(String args[]) {
		DatagramSocket echoServer=null;
		DatagramPacket myPacket= null;
		
		String line=" ";
		String hold="";
		 
		byte[] inBuffer=null;
		
		try {
			echoServer=new DatagramSocket(portNum);
		}catch (IOException e) {
			System.out.println(e);
		}
		
		try {
			while(!hold.contentEquals("!!!")) {
				inBuffer=new byte[BUFFERSIZE];
				
                // Initlize a datagram packet for the receive operation
				myPacket=new DatagramPacket(inBuffer,inBuffer.length);
				
                // Receive data into myPacket from the socket
				echoServer.receive(myPacket);
				
				//convert packet to a string
				line=new String(myPacket.getData());
				
				 // Trim the buffer data and get the actual received data
                line = line.substring(0, myPacket.getLength());
                
                hold=line;
            
                
                System.out.println("Client " + myPacket.getAddress() + ":" + myPacket.getPort() + ": " + line);
                
                 // Echo the packet back to the client
                echoServer.send(myPacket);
				
           
			}
			System.out.println("Exiting... Goodbye");
			echoServer.close();
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	//DatagramSocket echoServer
}
