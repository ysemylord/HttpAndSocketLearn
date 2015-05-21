package xyb.personal.httplearn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpRequestDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
            try {
				URL url=new URL("http://www.csdn.net:80/");
				HttpURLConnection connection=(HttpURLConnection) url.openConnection();
				connection.connect();
				InputStream is=connection.getInputStream();
				BufferedReader br=new BufferedReader(new InputStreamReader(is,"utf-8"));
				String mes=null;
				while((mes=br.readLine())!=null){
					System.out.println(mes);
				}
				Map headerMaps=connection.getHeaderFields();
				Set<Entry<String, List>> set=headerMaps.entrySet();
				Iterator<Entry<String, List>> iterator=set.iterator();
				while(iterator.hasNext()){
					Entry entry=iterator.next();
					System.out.println(entry);	   				
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
