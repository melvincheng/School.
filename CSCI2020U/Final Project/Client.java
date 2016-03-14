import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Client{
	Socket commSocket;
	InputStream input;
	OutputStream output;
	DataInputStream dataIn;
	DataOutputStream dataOut;
	Boolean status = false;
	static String fromServer;
	static String toServer;

	public Client(String fromServer, String toServer){
		this.fromServer = fromServer;
		this.toServer = toServer;
	}

	public void clientSetup(String hostname,int socket) throws Exception{
		try{
			commSocket = new Socket(hostname, socket);
		}catch(Exception e){
			
		}
		input = commSocket.getInputStream();
		output = commSocket.getOutputStream();
		dataIn = new DataInputStream(input);
		dataOut = new DataOutputStream(output);
		status = true;
	}
	public void connect() throws Exception{
		Thread send = new Send(dataOut);
		Thread receive = new Receive(dataIn);
		send.start();
		receive.start();
		send.join();
		receive.join();
	}
	public Boolean connected(){
		return status;
	}
	static class Send extends Thread{
		DataOutputStream dataOut;
		DataInputStream dataIn;
		String numMatch = "[\\d]*";
		String wordMatch = "[a-zA-Z]*";

		public Send(DataOutputStream dataOut){
			this.dataOut = dataOut;
			this.dataIn = dataIn;
		}
		public void run(){
			Pattern numPattern = Pattern.compile(numMatch);
			Pattern wordPattern = Pattern.compile(wordMatch);
			Matcher numMatcher;
			Matcher wordMatcher;
			while(true){
				try{
					if(toServer==""){
						numMatcher = numPattern.matcher(toServer);
						wordMatcher = wordPattern.matcher(toServer);
						if(numMatcher.find()||wordMatcher.find()){
							dataOut.writeUTF(toServer);
						}
					}
				}catch(Exception e){
					break;
				}
			}
		}
	}
	static class Receive extends Thread{
		DataInputStream dataIn;

		public Receive(DataInputStream dataIn){
			this.dataIn = dataIn;
		}
		public void run(){
			while(true){
				try{
					synchronized(System.out){
						fromServer = dataIn.readUTF();
					}
				}catch(Exception e){
					break;
				}
			}
		}
	}
}