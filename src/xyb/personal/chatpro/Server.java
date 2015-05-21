package xyb.personal.chatpro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;
import java.util.ArrayList;
import java.util.List;

public class Server {

	public static ArrayList<ClientSocket> sockets;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*try {
			ServerSocket serverSocket=new ServerSocket(Dictionary.PORT,Dictionary.BLACKLOG);
			Socket socket=serverSocket.accept();			
			String mes="";
			while(true){
				InputStream is=socket.getInputStream();
				BufferedReader br=new BufferedReader(new InputStreamReader(is));
				mes=br.readLine()+"";				
				System.out.println(mes);
				//写数据
				OutputStream os=socket.getOutputStream();
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os));
				bw.write("已经接受到您的数据"+"\r\n");//因为服务器端在读取数据时用的是BufferedReader.readLine()读取一行数据，所以要在数据末尾加上、如\r\n，服务端readLine()才能识别出一行，不让readLine()一直读入null，且不终止
				bw.flush();	//BufferedWriter有缓冲区，如果想立即发送出数据，要使用flush,清空缓冲区中的数据		
				System.out.println("发送完成\r\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		MyServerSocket serverSocket;
		sockets=new ArrayList<ClientSocket>();//存放所有与服务器连接的客户端对应的Socket
		try {			
			serverSocket = new MyServerSocket(Dictionary.PORT,Dictionary.BLACKLOG);
			while(true){
			ClientSocket socket=(ClientSocket) serverSocket.accept();
			System.out.println("一个客户端连接");
			sockets.add(socket);			
			ServerThread serverThread=new ServerThread(socket);
			serverThread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}		
}
/**
 * 服务器每收到一个连接，用此线程处理
 * 使用setRecieveSocket函数可改变聊天对象
 * @author purple_add
 *
 */
class ServerThread extends Thread{
	private ClientSocket clientSocket=null;//与服务器端建立连接的客户端的Socket
	private Socket recieveSocket=null;//要接受数据的socket,可改变
	private BufferedReader br;
	private String myName=null;
	private String chatName=null;
	private boolean change=false;
	public ServerThread(ClientSocket clientSocket){
		this.clientSocket=clientSocket;
		init();
	}
	private void changeChatName(String chatName){
		this.chatName=chatName;
		change=true;
	}
	/**
	 * clientSocket(自定义的Socket)中存有客户端的用户名，用来唯一标识此Socket
	 */
	private void init() {
		// TODO Auto-generated method stub
		 try {
			br=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String initmess=br.readLine()+"";
			String mess[]=initmess.split("&");	
			myName=mess[0];
			chatName=mess[1];
			clientSocket.setName(myName);
			System.out.println(myName+"上线，"+"希望与"+chatName+"聊天");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public  void setRecieveSocket(Socket recieveSocket){
		this.recieveSocket=recieveSocket;
	};
	public void trasmit(Socket clientSocket){
	try {
		BufferedWriter bw=null;
		while(true){						
		String mes;
		mes = br.readLine()+""+"\r\n";		
		if(recieveSocket==null||change==true){			
			for(ClientSocket recieveSocket:Server.sockets){
				if(recieveSocket.getName().equals(chatName)){
					setRecieveSocket(recieveSocket);
					bw = new BufferedWriter(new OutputStreamWriter(recieveSocket.getOutputStream()));
					change=false;
					System.out.println(myName+"找到聊天对象"+chatName);
					break;
				}
			}
			if(recieveSocket==null){
				System.out.println(myName+"未找到聊天对象"+chatName);
				continue;
			}
		}	
		System.out.println("向"+chatName+"转发来之"+myName+"的消息:"+mes);
		bw.write(mes);
		bw.flush();		
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	@Override
	public void run(){
		while(true){
			trasmit(clientSocket);
		}
	}
}


/**
 * 自定义ServerSocket
 * 使accept函数返回自定义的ClientSocket
 * @author purple_add
 *
 */
class MyServerSocket extends ServerSocket{
	//继承ServerSocket，覆盖accept同方法,返回自定义的Socket对象

	public MyServerSocket(int port, int backlog) throws IOException {
		super(port, backlog);
		// TODO Auto-generated constructor stub
	}
	public Socket accept() throws IOException {
	        if (isClosed())
	            throw new SocketException("Socket is closed");
	        if (!isBound())
	            throw new SocketException("Socket is not bound yet");
	        Socket s = new ClientSocket();
	        implAccept(s);
	        return s;
	    }
}
/**
 * 自定义Socket具有name属性
 * @author purple_add
 *
 */
class ClientSocket extends Socket{
	String name;
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
}
