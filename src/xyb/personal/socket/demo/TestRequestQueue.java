package xyb.personal.socket.demo;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestRequestQueue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestRequestQueue();
	}
	
	public static void TestRequestQueue(){
	
		try { 
			for(int i=0;i<10;i++){
			Socket socket=new Socket("localhost",Table.PORT);
			socket.getOutputStream().write(1);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
