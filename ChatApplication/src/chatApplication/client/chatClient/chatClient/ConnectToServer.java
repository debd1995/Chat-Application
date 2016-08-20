package chatApplication.client.chatClient.chatClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import chatApplication.client.chatClient.view.Chat_Home;

public class ConnectToServer extends Thread{
	private String serverIP;
	private int port;	
	private JFrame connWindow;
	private static final String errorCodeTooBusy = "2k1606";
	private static final String codeConnectionSuccessfull = "2k1609";
	private static final String seperator = "||";
	
	public ConnectToServer(String ip,int prt, JFrame connWin){
		serverIP = ip;
		port = prt;
		connWindow = connWin;
	}
	
	private void closeConnWindow(){
		connWindow.dispose();
	}
	
	public void run() {
		connectToChatServer();
	}
	
	private void connectToChatServer(){
		try {
			DataInputStream in = null;
			DataOutputStream out = null;
			String responseFromServer;
			Socket clientSocket;
			String name ="";
			String msg[] = null;
			clientSocket = new Socket(serverIP, port);
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			responseFromServer = in.readUTF();
			System.out.println("Server Res : "+responseFromServer);			
			msg = responseFromServer.split("\\|\\|");
			System.out.println("====Split Message====\n");
			for(String i : msg){
				System.out.print(i);
				System.out.println();				
			}
			System.out.println("====SplitClosed====");
			System.out.println("code = "+msg[0]);
			if(msg[0].equals(errorCodeTooBusy)){
				JOptionPane.showMessageDialog(null, "Connection refused.\n======Message from Server======\n"
						+msg[1]+"\n============================");				
			} else if(msg[0].equals(codeConnectionSuccessfull)) {
				JOptionPane.showMessageDialog(null, "Connected.\nMessage From Server\n********************\n"
						+msg[1]+"\n********************");
				closeConnWindow();
				while( (name = JOptionPane.showInputDialog("Enter Your Name :", "eg. Debasish")).equals("")){
					name = JOptionPane.showInputDialog("Enter Your Name :", "eg. Debasish");
				}
				out.writeUTF(name);
				
				// pass clientSocket to A new Thread To Live chat.
				
				new Thread(new Chat_Home(clientSocket)).start();
				
				
			} else {
				JOptionPane.showMessageDialog(null, "Unknown Response From Server.");
			}						
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Unknown Server.");
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "I/O error");
			e.printStackTrace();
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Could not Connect to the Server");
		}
		
	}

}
