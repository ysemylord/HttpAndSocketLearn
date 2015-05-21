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
	 * 从控制台读取域名和端口用于建立Socket连接	 
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
	 * 读取请求行
	 * 请求头
	 * @param reader
	 */
	public void readHttpRequest(BufferedReader reader){
		try {
			System.out.println("请先输入请求行");
			String requestLine=reader.readLine();			
			String method=requestLine.substring(0,4);
			boolean isPost=false;
			if(method.equals("POST")){
				isPost=true;
			}
			request+=requestLine+"\r\n";
			System.out.println("请输入请求头");
			String requestHeadLine="";
			while(true){
				requestHeadLine=reader.readLine();
				request+=requestHeadLine+"\r\n";
				if(requestHeadLine.equals("")){
					System.out.println("请求行输入完毕");
					break;
				}
			}
			if(isPost){
				System.out.println("请输入请求正文");
				String questConent="";
				questConent=reader.readLine();
				request+=questConent;
			}
			System.out.print("请求头构造完毕\n"+request);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  /**
   * 使用host,port建立socket连接
   * 再利用socket发送请求
   * 注:socket为通信框架
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
		System.out.println("服务器连接成功！");
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
			if(line.equals("")){//空行
				System.out.println("是否显示报文");
				String content=consolereader.readLine();				
				if(!content.equals("y")){
					break;
				}
			}
		}		
	}
}
