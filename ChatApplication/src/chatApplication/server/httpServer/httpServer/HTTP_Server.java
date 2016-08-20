package chatApplication.server.httpServer.httpServer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sun.net.httpserver.*;


public class HTTP_Server implements Runnable {
	
	private HttpServer http ;
	private JFrame controlPanel;
	private static int num;
	
    public HTTP_Server(JFrame fr){    	
    	controlPanel = fr;
    }
    
    public HTTP_Server(){}
	
	//get the requested page
    private String getPage(String pageName){
		String content = "";
		BufferedReader br = null;
		try {
			String temp;
			br = new BufferedReader(new FileReader(pageName));
			while( (temp = br.readLine()) != null){
				content += temp;
			}
		} catch (FileNotFoundException e) {
			System.out.println("File "+pageName+" not found");	
			JOptionPane.showMessageDialog(null, "File "+pageName+" not found");
			e.printStackTrace();
			return pageName+" Page Not Found";
		}catch (IOException e) {
			System.out.println("IOException.");
			e.printStackTrace();
		}finally {
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {							
					e.printStackTrace();
				}
			}
		}
		return content;
	}
    
	
	public HTTP_Server getThis(){
		return this;
	}
	
	public void stopServer(){
		try {
			http.stop(1);  //close the Socket.
			System.out.println("HTTP_Server socket closed.");			
			//System.exit(0);
		} catch (NullPointerException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "No Server to Stop");
		}
	}
	private void closeNewWindow(JFrame frame){
		frame.dispose();
	}
	
	@Override
	public void run() {
		
		try {
			http = HttpServer.create(new InetSocketAddress("192.168.0.100",80), 0);
			System.out.println("HTTP_Server socket created.");
			http.createContext("/", new RootHandler());
			http.createContext("/about", new AboutHandler());
			http.createContext("/download", new DownloadHandler());
			System.out.println("HTTP_Server All Handlers all set.");
			http.setExecutor(null); // creates a default executor
	        http.start();
	        System.out.println("HTTP_Server listning to : "+http.getAddress().getAddress()+" port : "+http.getAddress().getPort());
		} catch (BindException e) {			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Server is already running");
			closeNewWindow(controlPanel);
		}catch (IOException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "No Window to Close.");
		}
		
		System.out.println("  ==========HTTP SERVER HAS BEEN CONFIGURED ANS STARTED SUCCESSFULLY==========");
				
	}
	
	static class DownloadHandler implements HttpHandler {

		String response;
		OutputStream out;
		
		@Override
		public void handle(HttpExchange t) throws IOException {						
									
			
			String path = t.getRequestURI().getPath();			
			System.out.println("Download path = "+path);	
			if(path.equals("/download/")){
				response = new HTTP_Server().getPage("download.html");
				t.sendResponseHeaders(200, response.length());
				out = t.getResponseBody();
				out.write(response.getBytes());
				out.flush();
				out.close();
				t.close();
			}
			else if(path.equals("/download/video.mp4")){
				
				num++;
				int user = num;
				String fileName = "D:/video.mp4";
				int count;
				int total = 0;
				byte[] buffer = new byte[1024];
				
				t.getResponseHeaders().add("Content-Type", "video/mp4");
				t.getResponseHeaders().add("Content-Disposition", "attachment;filename=gajendarVarma.mp4");
				
				out = t.getResponseBody();
				File file = new File(fileName);
				BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));						
				
				t.sendResponseHeaders(200, file.length());
				System.out.println("Sending data to "+user);
				while( (count = bin.read(buffer)) >0 ){				
					out.write(buffer, 0, count);
					total += count;
					System.out.println(total+" Byte Sent to "+user);
				}
				out.flush();
				out.close();
				t.close();
				System.out.println("File Transfer Successful. to "+user);				
			}
			else if(path.equals("/download/ChatApplicationServer.jar")){
				
				num++;
				int user = num;
				String fileName = "D:/ChatApplicationServer.jar";
				int count;
				int total = 0;
				byte[] buffer = new byte[1024];
				
				t.getResponseHeaders().add("Content-Type", "application/java-archive");
				t.getResponseHeaders().add("Content-Disposition", "attachment;filename=ChatApplication Server.jar");
				
				out = t.getResponseBody();
				File file = new File(fileName);
				BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));						
				
				t.sendResponseHeaders(200, file.length());
				System.out.println("Sending data to "+user);
				while( (count = bin.read(buffer)) >0 ){				
					out.write(buffer, 0, count);
					total += count;
					System.out.println(total+" Byte Sent to "+user);
				}
				out.flush();
				out.close();
				t.close();
				System.out.println("File Transfer Successful. to "+user);				
			}
			else if(path.equals("/download/ChatApplicationClient.jar")){
				
				num++;
				int user = num;
				String fileName = "D:/ChatApplicationClient.jar";
				int count;
				int total = 0;
				byte[] buffer = new byte[1024];
				
				t.getResponseHeaders().add("Content-Type", "application/java-archive");
				t.getResponseHeaders().add("Content-Disposition", "attachment;filename=ChatApplication Client.jar");
				
				out = t.getResponseBody();
				File file = new File(fileName);
				BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));						
				
				t.sendResponseHeaders(200, file.length());
				System.out.println("Sending data to "+user);
				while( (count = bin.read(buffer)) >0 ){				
					out.write(buffer, 0, count);
					total += count;
					System.out.println(total+" Byte Sent to "+user);
				}
				out.flush();
				out.close();
				t.close();
				System.out.println("File Transfer Successful. to "+user);				
			}
			
			
			
			else {
				response = "URL NOT FOUND";
				t.sendResponseHeaders(200, response.length());
				out = t.getResponseBody();
				out.write(response.getBytes());
				out.flush();
				out.close();
				t.close();
			}
			
			
			
			
			
		}
		
	}
	
	static class AboutHandler implements HttpHandler {

		
		
		@Override
		public void handle(HttpExchange t) throws IOException {						
			
			String response;
			String path;
			path = t.getRequestURI().toString();
			System.out.println("About path = "+path);
			if(path.equals("/about/")){
				response = new HTTP_Server().getPage("about.html");
			}
			else {
				response = "URL NOT FOUND";
			}
			
            t.sendResponseHeaders(200, response.length());            
            OutputStream os = t.getResponseBody();            
            os.write(response.getBytes());
            System.out.println("Response send for about.");
            os.flush();
            os.close();
            t.close();
		}
		
	}
	
	 static class RootHandler implements HttpHandler {
	   
		 @Override
	        public void handle(HttpExchange t) throws IOException {
			 String response ;   
			 String path = t.getRequestURI().getPath();
	            System.out.println("PATH to HOME = "+path);
	            if(path.equals("/")){
	            	response = new HTTP_Server().getPage("index.html");
	            }
	           /* else if(path.equals("/favicon.ico")){
	         
	            	num++;
					int user = num;
					String fileName = "/favicon.ico";
					int count;
					int total = 0;
					byte[] buffer = new byte[1024];
					
					t.getResponseHeaders().add("Content-Type", "icon/ico");
					//t.getResponseHeaders().add("Content-Disposition", "attachment;filename=favicon.ico");
					
					OutputStream out = t.getResponseBody();
					File file = new File(fileName);
					BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));						
					
					t.sendResponseHeaders(200, file.length());
					System.out.println("Sending data to "+user);
					while( (count = bin.read(buffer)) >0 ){				
						out.write(buffer, 0, count);
						total += count;
						System.out.println(total+" Byte Sent to "+user);
					}
					System.out.println("icon Transfer Successful. to "+user);		            		           
	            }
	            */
	            else {
	            	response = "URL NOT FOUND";		            
				}	            		           
	            t.sendResponseHeaders(200, response.length());		            
	            OutputStream os = t.getResponseBody();		            
	            os.write(response.getBytes());
	            System.out.println("Response send for index.");
	            os.flush();
	            os.close();
	            t.close();
	        }
	        

	    }
	
}
