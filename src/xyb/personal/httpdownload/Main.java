package xyb.personal.httpdownload;

import java.io.*;

class NewProgress implements DownloadEvent
{
	private long oldPercent = -1;

	public void percent(long n)
	{
		if (n > oldPercent)
		{
			System.out.print("[" + String.valueOf(n) + "%]");
			oldPercent = n;
		}
	}
	public void state(String s)
	{
		System.out.println(s);
	}
	public void viewHttpHeaders(String s)
	{
		System.out.println(s);
	}
}

public class Main
{
	public static void main(String[] args) throws Exception
	{
		args=new String[]{"C:\\Users\\purple_add\\Documents\\download.txt"};
		HttpDownload httpDownload = new HttpDownload();
		DownloadEvent progress = new NewProgress();
		if (args.length < 1)
		{
			System.out.println("用法：java class 下载文件名");
			return;
		}
		FileInputStream fis = new FileInputStream(args[0]);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(
				fis));
		String s = "";
		String[] ss;
	
		while ((s = fileReader.readLine()) != null)
		{
			ss = s.split("[ ]+");
			if (ss.length > 2)
			{
				System.out.println("\r\n---------------------------");
				System.out.println("正在下载:" + ss[0]);
				System.out.println("文件保存位置:" + ss[1]);
				System.out.println("下载缓冲区大小:" + ss[2]);
				System.out.println("---------------------------");
				httpDownload.download(new NewProgress(), ss[0], ss[1], Integer
						.parseInt(ss[2]));
			}
		}
		fileReader.close();
	}
}
