package xyb.personal.httpdownload;

import java.net.*;
import java.io.*;
import java.util.*;

public class HttpDownload
{
	private HashMap httpHeaders = new HashMap();
	private String responseCode;
   
	
	/**
	 * 构造请求消息头部（包含请求行）
	 * 
	 */
	private void generateHttpRequest(OutputStream out, String host,
			String path, long startPos) throws IOException
	{
		OutputStreamWriter writer = new OutputStreamWriter(out);
		writer.write("GET " + path + " HTTP/1.1\r\n");
		writer.write("Host: " + host + "\r\n");
		writer.write("Accept: */*\r\n");
		writer.write("User-Agent: My First Http Download\r\n");
		if (startPos > 0)
			writer.write("Range: bytes=" + String.valueOf(startPos) + "-\r\n");
		writer.write("Connection: close\r\n\r\n");
		writer.flush();
	}
/**
 * 将响应消息头加入Map集合中
 * @param inputStream
 * @param de
 * @throws Exception
 */
	private void analyzeHttpHeader(InputStream inputStream, DownloadEvent de)
			throws Exception
	{
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
					de.viewHttpHeaders(s);
					addHeaderToMap(s);
					s = "";
				}
			}
			else
				s += (char) b;
		}
	}
	/**
	 * 返回状态码
	 * 响应消息的状态行
	 * HTTP协议版本 响应码 响应码的描述
	 * @param s
	 */
	private void analyzeFirstLine(String s)
	{
		String[] ss = s.split("[ ]+");
		if (ss.length > 1)
			responseCode = ss[1];
	}
	
	/**
	 * 讲响应消息头的某个域名和其值加入Map集合中
	 * @param s
	 */
	private void addHeaderToMap(String s)
	{
		int index = s.indexOf(":");
		if (index > 0)
			httpHeaders.put(s.substring(0, index), s.substring(index + 1)
					.trim());
		else
			analyzeFirstLine(s);
	}
	/**
	 * 从保存响应消息信息的HashMap中根据域名获取其值
	 * @param header
	 * @return
	 */
	private String getHeader(String header)
	{
		return (String) httpHeaders.get(header);
	}
	
	/**
	 *  从保存响应消息信息的HashMap中根据域名获取其值,并转化整型
	 * @param header
	 * @return
	 */
    private int getIntHeader(String header)
	{
		return Integer.parseInt(getHeader(header));
	}
    /**
     * 获取已下载文件的大小
     * @return
     */
	public long getFileLength()
	{
		long length = -1;
		try
		{
			length = getIntHeader("Content-Length");
			String[] ss = getHeader("Content-Range").split("[/]");
			if (ss.length > 1)
				length = Integer.parseInt(ss[1]);
			else
				length = -1;
		}
		catch (Exception e)
		{
		}
		return length;
	}
	
	/**
	 * 下载文件
	 * @param de
	 * @param url
	 * @param localFN
	 * @param cacheSize
	 * @throws Exception
	 */
	public void download(DownloadEvent de, String url, String localFN,
			int cacheSize) throws Exception
	{
		File file = new File(localFN);
		long finishedSize = 0;
		long contentLength = 0;
		FileOutputStream fileOut = new FileOutputStream(localFN, true);
		URL myUrl = new URL(url);
		Socket socket = new Socket();
		byte[] buffer = new byte[cacheSize]; // 下载数据的缓冲

		if (file.exists())
			finishedSize = file.length();		
		
		// 得到要下载的Web资源的端口号，未提供，默认是80
		int port = (myUrl.getPort() == -1) ? 80 : myUrl.getPort();
		de.state("正在连接" + myUrl.getHost() + ":" + String.valueOf(port));
		socket.connect(new InetSocketAddress(myUrl.getHost(), port), 20000);
		de.state("连接成功!");
		
		// 产生HTTP请求消息
		generateHttpRequest(socket.getOutputStream(), myUrl.getHost(), myUrl
				.getPath(), finishedSize);
		
		InputStream inputStream = socket.getInputStream();
		// 分析HTTP响应消息头
		analyzeHttpHeader(inputStream, de);
		contentLength = getFileLength();
		if (finishedSize >= contentLength)
			return;
		else
		{
			if (finishedSize > 0 && responseCode.equals("200"))
				return;
		}
		if (responseCode.charAt(0) != '2')
			throw new Exception("不支持的响应码");
		int n = 0;
		long m = finishedSize;
		while ((n = inputStream.read(buffer)) != -1)
		{
			fileOut.write(buffer, 0, n);
			m += n;
			if (contentLength != -1)
			{
				de.percent(m * 100 / contentLength);
			}
		}
		fileOut.close();
		socket.close();
	}
}
