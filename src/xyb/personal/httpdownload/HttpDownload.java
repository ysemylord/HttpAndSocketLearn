package xyb.personal.httpdownload;

import java.net.*;
import java.io.*;
import java.util.*;

public class HttpDownload
{
	private HashMap httpHeaders = new HashMap();
	private String responseCode;
   
	
	/**
	 * ����������Ϣͷ�������������У�
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
 * ����Ӧ��Ϣͷ����Map������
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
	 * ����״̬��
	 * ��Ӧ��Ϣ��״̬��
	 * HTTPЭ��汾 ��Ӧ�� ��Ӧ�������
	 * @param s
	 */
	private void analyzeFirstLine(String s)
	{
		String[] ss = s.split("[ ]+");
		if (ss.length > 1)
			responseCode = ss[1];
	}
	
	/**
	 * ����Ӧ��Ϣͷ��ĳ����������ֵ����Map������
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
	 * �ӱ�����Ӧ��Ϣ��Ϣ��HashMap�и���������ȡ��ֵ
	 * @param header
	 * @return
	 */
	private String getHeader(String header)
	{
		return (String) httpHeaders.get(header);
	}
	
	/**
	 *  �ӱ�����Ӧ��Ϣ��Ϣ��HashMap�и���������ȡ��ֵ,��ת������
	 * @param header
	 * @return
	 */
    private int getIntHeader(String header)
	{
		return Integer.parseInt(getHeader(header));
	}
    /**
     * ��ȡ�������ļ��Ĵ�С
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
	 * �����ļ�
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
		byte[] buffer = new byte[cacheSize]; // �������ݵĻ���

		if (file.exists())
			finishedSize = file.length();		
		
		// �õ�Ҫ���ص�Web��Դ�Ķ˿ںţ�δ�ṩ��Ĭ����80
		int port = (myUrl.getPort() == -1) ? 80 : myUrl.getPort();
		de.state("��������" + myUrl.getHost() + ":" + String.valueOf(port));
		socket.connect(new InetSocketAddress(myUrl.getHost(), port), 20000);
		de.state("���ӳɹ�!");
		
		// ����HTTP������Ϣ
		generateHttpRequest(socket.getOutputStream(), myUrl.getHost(), myUrl
				.getPath(), finishedSize);
		
		InputStream inputStream = socket.getInputStream();
		// ����HTTP��Ӧ��Ϣͷ
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
			throw new Exception("��֧�ֵ���Ӧ��");
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
