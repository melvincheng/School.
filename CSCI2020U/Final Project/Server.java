import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Server{
	ServerSocket serverSocket;
	public Server(int port) throws Exception{
		serverSocket = new ServerSocket(port);
	}
	static public void main(String[] args) throws Exception{
		int port = Integer.parseInt(args[0]);
		Server server = new Server(port);
		server.serverRun();
	}

	public void serverRun() throws Exception{
		System.out.println("Server is running");
		Dictionary dictionary = new Dictionary("dictionary.txt");
		Word2PhoneMapper mapper = new Word2PhoneMapper();
		int userNum = 1;
		while(true){
			Socket socket = serverSocket.accept();
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			DataInputStream dataIn = new DataInputStream(input);
			DataOutputStream dataOut = new DataOutputStream(output);
			Helper helper = new Helper(dictionary, mapper);
			(new Recieve(dataIn,dataOut,userNum,helper)).start();
			(new Send(dataOut,userNum,helper)).start();
			System.out.println(userNum);
			userNum++;
		}
	}

	static class Send extends Thread{
		DataOutputStream dataOut;
		int userNum;
		Helper helper;
		public Send(DataOutputStream dataOut, int userNum, Helper helper){
			this.dataOut = dataOut;
			this.userNum = userNum;
			this.helper = helper;
		}
		public void run(){
			String output;
			while(true){
				try{
					System.out.print("");
					if(helper.choice!=null){
						if(helper.choice){
							dataOut.writeUTF(helper.phoneResult);
							synchronized(System.out){
								System.out.println(userNum + " << " + helper.phoneResult);
							}
						}else if(!helper.choice){
							if(!helper.wordResult.isEmpty()){
								for(String word:helper.wordResult){
									synchronized(System.out){
										dataOut.writeUTF(word);
										System.out.println(userNum + " << " + word);
									}
								}
							}else{
								synchronized(System.out){
									dataOut.writeUTF("No match");
									System.out.println(userNum + " << " + "No match");
								}
							}
						}else{
							System.out.println("Operation failed");
						}
						helper.choice = null;
					}
				}catch(Exception e){
					break;
				}
			}
		}
	}

	static class Recieve extends Thread{
		DataInputStream dataIn;
		DataOutputStream dataOut;
		int userNum;
		Helper helper;
		public Recieve(DataInputStream dataIn, DataOutputStream dataOut, int userNum, Helper helper){
			this.dataIn = dataIn;
			this.dataOut = dataOut;
			this.userNum = userNum;
			this.helper = helper;
		}
		public void run(){
			String clientInput;
			while(true){
				try{
					clientInput = dataIn.readUTF();
					synchronized(System.out){
						System.out.println(userNum + " >> " + clientInput);
					}
					synchronized(helper){
						helper.input(clientInput);
					}
				}catch(Exception e){
					break;
				}
			}
		}
	}
}