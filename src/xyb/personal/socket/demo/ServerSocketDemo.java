package xyb.personal.socket.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
/**
 * 端口 1~65535
 * 用户端口 1~1023
 * @author purple_add
 *
 */
public class ServerSocketDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//resuseAdress();
		checkPort();
	}
	
	public static void resuseAdress(){
		try {
			/*ServerSocket serverSocket=new ServerSocket();
			serverSocket.bind(new InetSocketAddress(1234));
			System.out.println(serverSocket.getReuseAddress());
			
			ServerSocket serverSocket2=new ServerSocket();
			serverSocket2.setReuseAddress(true);
			serverSocket2.bind(new InetSocketAddress(1234));
			System.out.println(serverSocket2.getReuseAddress());*/
			ServerSocket serverSocket1 = new ServerSocket(1234);
	        System.out.println(serverSocket1.getReuseAddress());
	        
	        ServerSocket serverSocket2 = new ServerSocket();
	        serverSocket2.setReuseAddress(true);
	        serverSocket2.bind(new InetSocketAddress(1234));
	        
	        ServerSocket serverSocket3 = new ServerSocket();
	        serverSocket3.setReuseAddress(true);
	        serverSocket3.bind(new InetSocketAddress(1234));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//第一节创建ServerSocket对象
	
	public static void checkPort(){
		int minPort=1;
		int maxPort=1023;
		for(int i=minPort;i<=maxPort;i++){
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket(i);
				serverSocket.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getClass());
				System.out.println(i+"端口被占用");
				//e.printStackTrace();
			}
		}
	}

}
