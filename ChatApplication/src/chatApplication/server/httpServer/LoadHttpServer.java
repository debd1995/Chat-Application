package chatApplication.server.httpServer;

import chatApplication.server.httpServer.view.HTTP_Server_CP;

public class LoadHttpServer {

	public static void main(String[] args) {				
		System.out.println("HTTP_Server Control Panel Called");
		new HTTP_Server_CP().setVisible(true);		
	}

}