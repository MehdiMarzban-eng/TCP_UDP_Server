import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Reverse {
	private static int portNum= 8890;
	public static int BUFFERSIZE=128;
	
	public static void main(String args[]) {
		DatagramSocket reverseServer=null;
		DatagramPacket myPacket= null;
		
		String line=" ";
		String hold="";
		byte[] inBuffer=null;
		
		try {
			reverseServer=new DatagramSocket(portNum);
		}catch (IOException e) {
			System.out.println(e);
		}
		
		try {
			while(!hold.contentEquals("!!!")) {
				inBuffer=new byte[BUFFERSIZE];
				
                // Initlize a datagram packet for the receive operation
				myPacket=new DatagramPacket(inBuffer,inBuffer.length);
				
                // Receive data into myPacket from the socket
				reverseServer.receive(myPacket);
				
				//convert packet to a string
				line=new String(myPacket.getData());
				
				 // Trim the buffer data and get the actual received data
                line = line.substring(0, myPacket.getLength());
           
                hold=line;
                
                line= reverse(line);
                
                myPacket.setData(line.getBytes());
                
                System.out.println("Client " + myPacket.getAddress() + ":" + myPacket.getPort() + ": " + line);
                
                 // Echo the packet back to the client
                reverseServer.send(myPacket);
				
            
			}
			System.out.println("Exiting... Goodbye");
			reverseServer.close();
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	private static String reverse(String a) {
		byte[] byteArray=a.getBytes();
		byte[] output= new byte[byteArray.length];
		
		for(int i=0;i<byteArray.length;i++) {
			output[i]=byteArray[byteArray.length-i-1];
		}
		return new String(output);
	}
}