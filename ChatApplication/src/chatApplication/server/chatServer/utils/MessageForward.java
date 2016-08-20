package chatApplication.server.chatServer.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import chatApplication.server.chatServer.chatServer.CHAT_Server_Child;

public class MessageForward extends Thread{
	
	private CHAT_Server_Child clientThreads[];
	private int sender;
	private String message;
	private int maxUser;
	
	public MessageForward(CHAT_Server_Child[] clients,String msg,int sen) {
		clientThreads = clients;
		message = msg;
		sender = sen;
		maxUser = clientThreads.length;
	}

	@Override
	public void run() {
		System.out.println("Forwarding Message "+message);
		int i=0;
		DataOutputStream out = null;
		for(i=0;i<maxUser;i++){
			if( i!=sender && clientThreads[i]!=null){
				try {
					out = new DataOutputStream(clientThreads[i].getClient().getOutputStream());
					out.writeUTF(message);
				} catch (IOException e) {					
					e.printStackTrace();
				} /*finally {
					if(out != null){
						try {
							out.close();  // if prob rem
						} catch (IOException e) {							
							e.printStackTrace();
						}
					}
				}*/
			}
		}
	}

}
