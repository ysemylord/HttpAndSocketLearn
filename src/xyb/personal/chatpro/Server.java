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
				//д����
				OutputStream os=socket.getOutputStream();
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os));
				bw.write("�Ѿ����ܵ���������"+"\r\n");//��Ϊ���������ڶ�ȡ����ʱ�õ���BufferedReader.readLine()��ȡһ�����ݣ�����Ҫ������ĩβ���ϡ���\r\n�������readLine()����ʶ���һ�У�����readLine()һֱ����null���Ҳ���ֹ
				bw.flush();	//BufferedWriter�л�������������������ͳ����ݣ�Ҫʹ��flush,��ջ������е�����		
				System.out.println("�������\r\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		MyServerSocket serverSocket;
		sockets=new ArrayList<ClientSocket>();//�����������������ӵĿͻ��˶�Ӧ��Socket
		try {			
			serverSocket = new MyServerSocket(Dictionary.PORT,Dictionary.BLACKLOG);
			while(true){
			ClientSocket socket=(ClientSocket) serverSocket.accept();
			System.out.println("һ���ͻ�������");
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
 * ������ÿ�յ�һ�����ӣ��ô��̴߳���
 * ʹ��setRecieveSocket�����ɸı��������
 * @author purple_add
 *
 */
class ServerThread extends Thread{
	private ClientSocket clientSocket=null;//��������˽������ӵĿͻ��˵�Socket
	private Socket recieveSocket=null;//Ҫ�������ݵ�socket,�ɸı�
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
	 * clientSocket(�Զ����Socket)�д��пͻ��˵��û���������Ψһ��ʶ��Socket
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
			System.out.println(myName+"���ߣ�"+"ϣ����"+chatName+"����");
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
					System.out.println(myName+"�ҵ��������"+chatName);
					break;
				}
			}
			if(recieveSocket==null){
				System.out.println(myName+"δ�ҵ��������"+chatName);
				continue;
			}
		}	
		System.out.println("��"+chatName+"ת����֮"+myName+"����Ϣ:"+mes);
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
 * �Զ���ServerSocket
 * ʹaccept���������Զ����ClientSocket
 * @author purple_add
 *
 */
class MyServerSocket extends ServerSocket{
	//�̳�ServerSocket������acceptͬ����,�����Զ����Socket����

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
 * �Զ���Socket����name����
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
