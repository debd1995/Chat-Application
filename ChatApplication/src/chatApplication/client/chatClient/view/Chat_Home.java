package chatApplication.client.chatClient.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import chatApplication.client.chatClient.utils.ButtonThread;
import chatApplication.client.chatClient.utils.ReadMessageFromServer;
import chatApplication.client.chatClient.utils.SendMessageToServer;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;

public class Chat_Home extends JFrame implements Runnable{

	private JPanel contentPane;
	private Socket clientSocket;
		
	@Override
	public void run() {
		this.setVisible(true);
		
	}
	
	/**
	 * Create the frame.
	 */
	public Chat_Home(Socket soc) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		}catch(Exception e){
			e.printStackTrace();
		}
		clientSocket = soc;
		System.out.println("Under Chat_Home() "+clientSocket);
		setTitle("Chat Application Client");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 66, 644, 240);
		contentPane.add(scrollPane);
		
		JTextArea txtDisplayArea = new JTextArea();
		txtDisplayArea.setLineWrap(true);
		txtDisplayArea.setEditable(false);
		txtDisplayArea.setRows(2);
		DefaultCaret caret = (DefaultCaret)txtDisplayArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollPane.setViewportView(txtDisplayArea);
		
		JLabel lblMessages = new JLabel("Messages :");
		lblMessages.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMessages.setBounds(37, 26, 147, 29);
		contentPane.add(lblMessages);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 348, 542, 74);
		contentPane.add(scrollPane_1);
		
		JTextArea txtmsg = new JTextArea();
		txtmsg.setLineWrap(true);
		scrollPane_1.setViewportView(txtmsg);
		System.out.println("before btn send "+clientSocket);
		
		JLabel lblWriteNewMessage = new JLabel("Write New Message :");
		lblWriteNewMessage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblWriteNewMessage.setBounds(37, 317, 181, 20);
		contentPane.add(lblWriteNewMessage);
		
		JButton btnSend = new JButton("Send");
		btnSend.setEnabled(false);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
						send(txtDisplayArea,txtmsg);
						txtmsg.setText("");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Failed to create OutputStream.Reconnect");
					e.printStackTrace();
				}							
			}
		});
		btnSend.setBounds(562, 385, 89, 29);
		contentPane.add(btnSend);
		
		try {
			receive(txtDisplayArea);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to create InputStream.Reconnect");
			e.printStackTrace();
		}
		txtmsg.append("");
		btnThread(txtmsg, btnSend);
		
	}
	private void send(JTextArea txtA,JTextArea msg) throws IOException{
		JTextArea txtmsg = msg;
		JTextArea txtDisplayArea = txtA;
		new SendMessageToServer(clientSocket.getOutputStream(), txtmsg.getText().trim()+"\n").start();
		txtDisplayArea.append("ME-->"+txtmsg.getText().trim()+"\n");
	}
	private void receive(JTextArea txt) throws IOException{
		JTextArea txtDisplayArea = txt;
		InputStream is = clientSocket.getInputStream();
		new ReadMessageFromServer(is,txtDisplayArea).start();;
	}
	private void btnThread(JTextArea txt, JButton btn){
		new ButtonThread(txt, btn).start();
	}
}
