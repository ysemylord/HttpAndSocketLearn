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
			    //��BACKLOG��������У���С���ͻ����������ԣ�����Ϊ��Ϣ����װ�������²��ֿͻ������Ӳ��Ϸ���ˣ�
			    //�ͻ��˻��׳�SoeckException
			    serverSocket.setReuseAddress(true);
			    int count=0;
				while(true){							
					System.out.println("�ȴ����ܿͻ�����Ϣ");	
					//Thread.sleep(3*1000);
					serverSocket.accept();
					//Thread.sleep(3*1000);
					System.out.println("�յ���"+(++count)+"���ͻ�����Ϣ");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
