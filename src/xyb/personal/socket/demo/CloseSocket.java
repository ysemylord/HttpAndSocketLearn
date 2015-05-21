/**
 * 
 */
package xyb.personal.socket.demo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author purple_add
 *
 */
public class CloseSocket {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket serverSocket=new ServerSocket(Table.PORT,Table.BACKLOG);
			while(true){
				Socket socket=serverSocket.accept();
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
