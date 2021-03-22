import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;


public class Client {
	@SuppressWarnings("resource")
	public static void main(String args[]) throws IOException {
		Socket socket=null;
		//need to make sure that the master ip is proper format!!\
		
		int port=0;
		try {
			port=Integer.parseInt(args[1]);
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Need port number as argument");
			System.exit(-1);
		}catch(NumberFormatException e) {
			 System.out.println("Need port number as an integer. ");
			 System.exit(-1);
		}
		try {//InetAddress.getByName(args[0])
			socket= new Socket("localhost",8888);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//send message to the master server
	
		
		DataOutputStream outBuffer= new DataOutputStream(socket.getOutputStream());
		BufferedReader inBuffer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		String line=""; 
		BufferedReader inFromUser= new BufferedReader(new InputStreamReader(System.in));
		
		while(!line.equals("exit")) {
		System.out.println("\nPlease Enter a Sentence to be changed or exit by inputting !!! : ");
		
			line= inFromUser.readLine();
		
		
			outBuffer.writeBytes(line + '\n');
			if(!line.equals("!!!")) {
			
			//System.exit(1);
			System.out.println("please choose an interaction from the list below by pressing the numbers and return key(ex. 2136): ");
			System.out.println("1. Echo");
			System.out.println("2. Reverse");
			System.out.println("3. Upper");
			System.out.println("4. Lower");
			System.out.println("5. Caesar");		
			System.out.println("6. noSpace"); //Need to replace with whatever I end up doing.
			}
			else {
			System.out.println("Exiting... Goodbye");
			System.exit(1);
			}
			line=inFromUser.readLine();
			
			outBuffer.writeBytes(line + '\n');
			if(line.length()==1) {
			System.out.println(inBuffer.readLine());
			}
			else {
			for(int i=0; i<line.length();i++) {
			System.out.println(inBuffer.readLine());
			}
			}
		}
		    socket.close();
		
	}
	}
