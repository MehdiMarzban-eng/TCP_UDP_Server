import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Master {
	
	public static int BUFFERSIZE = 100;
	static int[] MicroPort= new int[] {8889,8890,8891,8892,8893,8894};
	static String[] transformations= new String[] {"echo","Reverse","Upper","Lower","Caesar","noSpace"};
	
	private static int portNum= 8888;
	private static InetAddress masterIP;
	public static void main(String[] args) throws IOException {
			ServerSocket connect=null;
			Socket fromClient=null;
			String holder="";
			String line="";
			int[] trans;
			BufferedReader inBuffer;
			DataOutputStream outBuffer;
			
		    DatagramSocket clientSocket = new DatagramSocket();
		    DatagramPacket myPacket = null;

		        // Initialize input and an output stream for the connection(s)
		    byte[] outputBuffer = null;
		    byte[] inputBuffer = null;
			
			try {
				
				
				connect= new ServerSocket(portNum);
				connect.setSoTimeout(10000);
			}catch(IOException e) {
				System.out.println(e);
			}
			
			try {
				
					//accept connections
					System.out.println("looking for new connections...");
					fromClient=connect.accept();
					System.out.println("Connection Accepted");
					
					//create input and output streams
					inBuffer=new BufferedReader(new InputStreamReader(fromClient.getInputStream()));
					outBuffer=new DataOutputStream(fromClient.getOutputStream());
					String transform="";
					while(!line.contentEquals("!!!")) {
						line="";
						line=inBuffer.readLine();
						System.out.println("client: "+ line);
						if(line.equals("!!!")) {
							transform=holder;
							System.out.println(transform);
							if(holder==null) {
								holder="123456";
							}
						}
						else {
						transform=inBuffer.readLine();
						holder=transform;
						}
						trans=toArray(Integer.parseInt(transform));
						System.out.println(trans[0]);
						
						for(int i=0; i< trans.length;i++) {
							
							 outputBuffer = line.getBytes();
				             myPacket = new DatagramPacket(outputBuffer, outputBuffer.length, InetAddress.getByName("localhost"), MicroPort[trans[i]-1]);
				             clientSocket.send(myPacket); 
				          
				                // Getting response from the server
				             inputBuffer = new byte[BUFFERSIZE];
				             myPacket = new DatagramPacket(inputBuffer, inputBuffer.length);
				            
				                // Receive from the UDP socket
				             clientSocket.receive(myPacket);
				            
				                // Convert the packet to a string
				             line = new String(myPacket.getData());
				            
				                // Trim the buffer data and get the actual received data
				             line = line.substring(0, myPacket.getLength());
				             System.out.println("Server: " + line);
				                             
				             
				             
				             outBuffer.writeBytes(transformations[trans[i]-1]+" Output: "+line+"\n");
						
						
						}
						   if(line.equals("exit")||line.equals("EXIT")) {
				 				System.out.println("Exiting... Goodbye");
				 			//	System.exit(1);
				 			//	fromClient.close();
				 			}
					}
					System.out.println("Connection Terminated!");
		                
		           inBuffer.close();
		           outBuffer.close();
		                
		                // close the connections
		           fromClient.close();
				
			}catch(IOException e) {
				System.out.println(e);
			}
			
	}
	static int[] toArray(int tran) {
		int a=0;
		int length=Integer.toString(tran).length();
		int [] order= new int[length];
		while(tran!=0) {
			order[length-a-1]=tran%10;
			tran=tran/10;
			a++;
		}
		return order;
	}
}
		