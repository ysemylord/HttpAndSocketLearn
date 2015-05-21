package xyb.personal.chatpro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {   
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		System.out.println("连接服务器。。。。");
		Socket socket=new Socket(Dictionary.IP,Dictionary.PORT);
		System.out.println("连接服务器成功");
		System.out.println("开始聊天");		
		new WriteInetDateThread(socket).start();
		new ReadInetDataThread(socket).start();
	}
}

class ReadThread extends Thread{
	public ReadThread(){
		
	}
	@Override
	public void run(){
		
	}
}

/**
 * 从服务器读取数据
 * @author purple_add
 *
 */
class ReadInetDataThread extends Thread{
	private Socket clientSocket=null;
	public ReadInetDataThread(Socket clientSocket){
		this.clientSocket=clientSocket;
	}
	@Override
	public void run(){
		try {
			InputStream is=clientSocket.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			while(true){
			    String mes=br.readLine();
			    System.out.println("对方: "+mes);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
/**
 * 向服务器写数据
 * @author purple_add
 *
 */
class WriteInetDateThread extends Thread{
	private Socket clientSocket=null;
	private String clientName=null;
	public WriteInetDateThread(Socket clientSocket){
		this.clientSocket=clientSocket;
	}
	@Override
	public void run(){
		BufferedReader consoleBR=new BufferedReader(new InputStreamReader(System.in));			
		BufferedWriter bw=null;
		try {			
			//告诉服务器自己的名字和要聊天的人的名字
			bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			System.out.println("请输入您的姓名和您的聊天对象格式为");	
			System.out.println(" 自己的姓名&对方的姓名 例如 ：xuyabo&jack");	
			String myNameAndChatName=consoleBR.readLine();			
			bw.write(myNameAndChatName+"\r\n");	
			bw.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){
		try {		
			String consoleMes=consoleBR.readLine()+"\r\n";		
			bw.write(consoleMes);//因为服务器端在读取数据时用的是BufferedReader.readLine()读取一行数据，所以要在数据末尾加上、如\r\n，服务端readLine()才能识别出一行，不让readLine()一直读入null，且不终止
			bw.flush();	//BufferedWriter有缓冲区，如果想立即发送出数据，要使用flush,清空缓冲区中的数据		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		}
	}	

	public void sendNameAndChatName(BufferedWriter bw,String myName,String chatName){
			
	}
}