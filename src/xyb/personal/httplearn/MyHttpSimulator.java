package xyb.personal.httplearn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

public class MyHttpSimulator {

	private static String host;
	private static String port="80";
	private static String request="";
	private Socket socket;
	/**
	 * @param args
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		MyHttpSimulator mHttpM=new MyHttpSimulator();
		mHttpM.readHostAndPort(reader);
		mHttpM.readHttpRequest(reader);
		mHttpM.sendHttpRequest(host, port, request);
		mHttpM.readHttpResponse(reader);
		//readHostAndPort(reader);
	}

	/**
	 * �ӿ���̨��ȡ�����Ͷ˿����ڽ���Socket����	 
	 * @param reader
	 */
	private  boolean readHostAndPort(BufferedReader reader) {
		// TODO Auto-generated method stub
		System.out.print("host:80>");
		try {
			String hostAndPort=reader.readLine();
		if(hostAndPort.equals("q")){
			return false;
		}			
			String[] hp=hostAndPort.split(":");
			host=hp[0];
			if(host.length()>1){
				port=hp[1];
			}
			System.out.println(host+":"+port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * ��ȡ������
	 * ����ͷ
	 * @param reader
	 */
	public void readHttpRequest(BufferedReader reader){
		try {
			System.out.println("��������������");
			String requestLine=reader.readLine();			
			String method=requestLine.substring(0,4);
			boolean isPost=false;
			if(method.equals("POST")){
				isPost=true;
			}
			request+=requestLine+"\r\n";
			System.out.println("����������ͷ");
			String requestHeadLine="";
			while(true){
				requestHeadLine=reader.readLine();
				request+=requestHeadLine+"\r\n";
				if(requestHeadLine.equals("")){
					System.out.println("�������������");
					break;
				}
			}
			if(isPost){
				System.out.println("��������������");
				String questConent="";
				questConent=reader.readLine();
				request+=questConent;
			}
			System.out.print("����ͷ�������\n"+request);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  /**
   * ʹ��host,port����socket����
   * ������socket��������
   * ע:socketΪͨ�ſ��
   * @param host
   * @param port
   * @param quest
 * @throws IOException 
 * @throws NumberFormatException 
   */
	public void sendHttpRequest(String host,String port,String quest) throws NumberFormatException, IOException{
	    socket=new Socket();
		socket.setSoTimeout(1000*10);
		socket.connect(new InetSocketAddress(host, Integer.parseInt(port)));
		System.out.println("���������ӳɹ���");
		OutputStream out=socket.getOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(out);
		writer.write(quest);
		writer.flush();
		 
	}
	public void readHttpResponse(BufferedReader consolereader) throws IOException{
		InputStream in=socket.getInputStream();
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		String line="";
	/*	byte buffer[]=new byte[100];
		while(in.read(buffer)!=-1){
			String str=new String(buffer,"utf-8");
			System.out.println(str);
		}*/
		while(( line=br.readLine())!=null){			
			System.out.println(line);
			if(line.equals("")){//����
				System.out.println("�Ƿ���ʾ����");
				String content=consolereader.readLine();				
				if(!content.equals("y")){
					break;
				}
			}
		}		
	}
}
