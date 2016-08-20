package chatApplication.server.chatServer;

import chatApplication.server.chatServer.view.CHAT_Server_CP;

public class LoadChatServer {

	public static void main(String[] args) {
		new Thread(new CHAT_Server_CP()).start();
	}

}
