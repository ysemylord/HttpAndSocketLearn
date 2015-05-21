package xyb.personal.socket.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpEchoServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket serverSocket=new ServerSocket(Table.PORT,Table.BACKLOG);
			while(true){
				Socket socket=serverSocket.accept();
				handleClientMessage(socket);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void handleClientMessage(Socket socket) {
		// TODO Auto-generated method stub
		new HandleThread(socket).start();
		
	}
}
class HandleThread extends Thread{	
	Socket socket=null;
	public HandleThread(Socket socket){
		this.socket=socket;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			InputStream is=socket.getInputStream();
			OutputStream os=socket.getOutputStream();
			InputStreamReader isr=new InputStreamReader(is, "utf-8");
			OutputStreamWriter osw=new OutputStreamWriter(os,"GBK");
			BufferedReader br=new BufferedReader(isr);
			//BufferedWriter bw=new BufferedWriter(osw);
		    osw.write("HTTP/1.1 200 OK\r\n");
		    osw.write("\r\n");//响应头和响应正文之间用空行隔开
			String clientMes="";
			while(!(clientMes=br.readLine()).equals("")/*(clientMes=br.readLine())!=null*/){
				System.out.println(clientMes+"");
				osw.write("<html><body>" + clientMes + "<br></body></html>");						 
			}
			System.out.println("结束");
			osw.flush();	
			osw.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
