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
		System.out.println("���ӷ�������������");
		Socket socket=new Socket(Dictionary.IP,Dictionary.PORT);
		System.out.println("���ӷ������ɹ�");
		System.out.println("��ʼ����");		
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
 * �ӷ�������ȡ����
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
			    System.out.println("�Է�: "+mes);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
/**
 * �������д����
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
			//���߷������Լ������ֺ�Ҫ������˵�����
			bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			System.out.println("����������������������������ʽΪ");	
			System.out.println(" �Լ�������&�Է������� ���� ��xuyabo&jack");	
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
			bw.write(consoleMes);//��Ϊ���������ڶ�ȡ����ʱ�õ���BufferedReader.readLine()��ȡһ�����ݣ�����Ҫ������ĩβ���ϡ���\r\n�������readLine()����ʶ���һ�У�����readLine()һֱ����null���Ҳ���ֹ
			bw.flush();	//BufferedWriter�л�������������������ͳ����ݣ�Ҫʹ��flush,��ջ������е�����		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		}
	}	

	public void sendNameAndChatName(BufferedWriter bw,String myName,String chatName){
			
	}
}