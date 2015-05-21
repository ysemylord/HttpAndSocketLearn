package xyb.personal.chatpro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class commonMethod {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readDataFromConso();
	}
	
	public static String readDataFromConso(){
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String mes="";
		while(true){
		 try {
			mes= br.readLine();
			if(mes!="end"){
				System.out.println(mes);
			}else{
				break;
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return null;
	}
}
