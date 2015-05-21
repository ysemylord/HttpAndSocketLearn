package xyb.personal.httpdownload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MyMain {
   Socket socket=null;
   String resUrl=null;
   String port=null;
   String host=null;
   byte[] buffers=new byte[100];//缓冲区大小2M


	/**
	 * @param args
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		MyMain mm=new MyMain();
		//http://d.hiphotos.baidu.com/zhidao/pic/item/562c11dfa9ec8a13e028c4c0f603918fa0ecc0e4.jpg
		
		/*mm.host="files.cnblogs.com";
		mm.port="80";
		mm.resUrl="/files/nokiaguy/HttpSimulator.rar";
		String filePath="C:\\Users\\purple_add\\Desktop\\dowdemo.rar";*/
		mm.host="d.hiphotos.baidu.com";
		mm.port="80";
		mm.resUrl="/zhidao/pic/item/562c11dfa9ec8a13e028c4c0f603918fa0ecc0e4.jpg";
		String filePath="1.jpg";
		mm.downFile(mm.host, mm.port, mm.resUrl, filePath);
	}
   
	public void downFile(String host,String port,String resUrl,String filePath) throws NumberFormatException, IOException{
		File file=new File(filePath);
		FileOutputStream fileOut=new FileOutputStream(file);
		long Downdedsize=file.length();
		String Range=Downdedsize+"-";
		//generateRequest(Range);
		buildSocketConnection(host, port);
		generateRequest(socket.getOutputStream(),resUrl,host,Range);
		InputStream isSocket = socket.getInputStream();
		//剔除头部
		discardHeader(isSocket);
		int n=0;
		while((n=isSocket.read(buffers))!=-1){
			fileOut.write(buffers,0,n);
			System.out.println("读取");
			//break;
		}
		isSocket.close();
		fileOut.close();
	}

	private void discardHeader(InputStream inputStream) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("响应头部");
	
		String s = "";
		byte b = -1;
		while (true)
		{
			b = (byte) inputStream.read();
			if (b == '\r')
			{
				b = (byte) inputStream.read();
				if (b == '\n')
				{
					if (s.equals(""))
						break;								
					s = "";
				}
			}
			else
				s += (char) b;
		}
		 
		 System.out.println("响应头部结束");
		
	}

	private void generateRequest(OutputStream out,String resUrl,String host,String range) throws IOException {
		// TODO Auto-generated method stub		
		StringBuffer sb=new StringBuffer();
		sb.append("GET "+resUrl+" "+"HTTP/1.1"+"\r\n");
		sb.append("Host:"+host+"\r\n");
		sb.append("Range:bytes="+range+"\r\n");
		sb.append("Connection:close"+"\r\n");
		sb.append("\r\n");
		System.out.println("请求消息头为\n"+sb.toString());		
		OutputStreamWriter outWriter=new OutputStreamWriter(out);
		System.out.println("发送请求");
		outWriter.write(sb.toString());
		outWriter.flush();	
		System.out.println("请求成功");
	}

	private void buildSocketConnection(String host, String port) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		socket=new Socket();
		socket.connect(new InetSocketAddress(host, Integer.parseInt(port)), 1000*5);
		System.out.println("建立Socket连接");
	}
}
