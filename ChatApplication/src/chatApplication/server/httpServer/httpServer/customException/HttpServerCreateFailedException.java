package chatApplication.server.httpServer.httpServer.customException;

public class HttpServerCreateFailedException extends Exception{
	
	private String message;
	
	public HttpServerCreateFailedException(){
		message = "Http Server Create Failed";
	}
	public HttpServerCreateFailedException(String msg){
		message = msg;
	}

	@Override
	public String toString() {
		return message;
	}

}
