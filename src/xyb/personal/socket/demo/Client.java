package xyb.personal.socket.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		try {
			Socket socket=new Socket("localhost",Table.PORT);
			Thread.sleep(3*1000);
			//socket.getOutputStream().write(1); 服务端Socket已经close汇报 SocketException异常
			InputStream is=socket.getInputStream();
			System.out.println("read is "+is.read());
			System.out.println("close is "+socket.isClosed());
			System.out.println("connnection is "+socket.isConnected());
			System.out.println("bound is "+socket.isBound());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
