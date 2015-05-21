package xyb.personal.httplearn;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFormat.Encoding;









public class MySimpleUseHttp {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
/*		 String strTemp="¥∫";
		 byte bu[]=strTemp.getBytes("utf-8");
		 byte bu2[]=new byte[30];
		 ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bu);
		   while((byteArrayInputStream.read(bu2))!=-1){
	        	System.out.println(new String(bu2,"utf-8"));
	        	
	        }*/
		
		//String urlStr="http://www.baidu.com/";
		String urlStr="http://www.csdn.net/";
        URL url=new URL(urlStr);
        HttpURLConnection httpurlconnection=(HttpURLConnection) url.openConnection();
        httpurlconnection.setRequestMethod("GET"); 
        httpurlconnection.setRequestProperty("Connection","Keep-Alive");
        httpurlconnection.setRequestProperty("Host","www.baidu.com");        
        httpurlconnection.connect(); 
        
       /* DataOutputStream out = new DataOutputStream(httpurlconnection.getOutputStream());    
        String content = "username=–Ï—«≤®";    
        out.writeBytes(content);    
        out.flush();    
        out.close();*/    
        
        int responseCode=httpurlconnection.getResponseCode();
        
        
        Map<String, List<String>> map = httpurlconnection.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println( entry.getKey() + 
                               " : " + entry.getValue());
        }
        InputStream is=httpurlconnection.getInputStream();
        InputStreamReader isr=new InputStreamReader(is,"utf-8");
        BufferedReader br=new BufferedReader(isr);
        String line;
        while((line=br.readLine())!=null){
        	System.out.println(line);
        }
       /* byte buffer[]=new byte[600];
        int n;
        while((n=is.read(buffer))!=-1){
        	System.out.println(new String(buffer,"utf-8"));
        	
        }*/
        System.out.println(responseCode);
		
	}
	

}
