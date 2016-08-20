//this Thread will create Server on a IP and Port.
//and will accept connection if space is available
//and forward this connection to another thread to handle exchange.
package chatApplication.server.chatServer.chatServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import chatApplication.server.chatServer.view.CHAT_Server_CP;

public class CHAT_Server extends Thread{
		
	private int serverPort = 12345;
	private ServerSocket chatServerSocket ;
	private Socket client;
	private static int maxConcurrentUsers = 3; //default maxUsers
	private static CHAT_Server_Child clientThreads[];
	private String serverIP = "localhost";
	
	private static final String errorCodeTooBusy = "2k1606";
	private static final String seperator = "||";
	private static final String errorMessageTooBusy = "Server is too busy. Try again later.";
	private boolean serverStatus;
	private CHAT_Server_CP window;
	
	public boolean isStarted(){
		return serverStatus;
	}
	
	public CHAT_Server(int maxUser,String ip,int port,CHAT_Server_CP win){
		maxConcurrentUsers = maxUser;
		serverIP = ip;
		serverPort = port;
		clientThreads = new CHAT_Server_Child[maxConcurrentUsers];
		window = win;
	}
	
	private void startChatServer(){
		try {
			chatServerSocket = new ServerSocket(serverPort, maxConcurrentUsers, InetAddress.getByName(serverIP));
			System.out.println("Server Started."+"\nMax Concurrent Users "+maxConcurrentUsers);		
			serverStatus = true;
			window.setWindowComponentDisable();
			while(true){
				System.out.println("Waiting For Incomming Request.");
				int i = 0;
				client = chatServerSocket.accept();
				System.out.println("Conn acc");
				for(i=0;i<maxConcurrentUsers;i++){
					if(clientThreads[i] == null){
						clientThreads[i] = new CHAT_Server_Child(client,clientThreads,i);
						clientThreads[i].start();
						break;
					}
				}
				if(i == maxConcurrentUsers){
					DataOutputStream out = new DataOutputStream(client.getOutputStream());
					out.writeUTF(errorCodeTooBusy+seperator+errorMessageTooBusy);					
					out.close();
					client.close();
				}
			}
			
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, serverIP+" is Unknown. Failed to Start Chat Server");
			e.printStackTrace();			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IOException Occured. Failed to Start Chat Server  ");
			System.out.println("IO ");
			e.printStackTrace();
		} finally {
			if(chatServerSocket != null)
				try {
					chatServerSocket.close();					
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Failed to Close Server Socket.");
					e1.printStackTrace();
				}
		}
	}

	@Override
	public void run() {
		startChatServer();
	}

}
