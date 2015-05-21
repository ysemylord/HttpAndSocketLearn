package xyb.personal.socket.demo;

import java.io.IOException;
import java.net.ServerSocket;



public class SetRequestQueue {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub   
		setRequestQueue();
	}
	public static void setRequestQueue() throws InterruptedException{
		final int  PORT=Table.PORT;
		final int  BACKLOG=Table.BACKLOG;
		ServerSocket serverSocket = null;
		try {
			    serverSocket = new ServerSocket(PORT,BACKLOG);
			    //当BACKLOG（请求队列）过小，客户端请求过多对，会因为消息队列装满，导致部分客户端连接不上服务端，
			    //客户端会抛出SoeckException
			    serverSocket.setReuseAddress(true);
			    int count=0;
				while(true){							
					System.out.println("等待接受客户端消息");	
					//Thread.sleep(3*1000);
					serverSocket.accept();
					//Thread.sleep(3*1000);
					System.out.println("收到第"+(++count)+"个客户端消息");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
