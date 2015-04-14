import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class PushMessage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket server = null;
//		Socket accept = null;
//		DataInputStream in;
//		DataOutputStream out;
//		String result ;
//		JSONObject message;
//		List<String> cid;
//		String title, text;
//		try {
//			server = new ServerSocket(5888);
//		} catch (IOException e) {
//			System.out.println(e.getStackTrace());
//		}
//		try {
//			accept = server.accept();
//			out = new DataOutputStream(accept.getOutputStream());
//			in = new DataInputStream(accept.getInputStream());
//			while (true) {
//				result = "false";
//				message = JSONObject.parseObject(in.readLine());
//				System.out.println(message);
//				cid = (List<String>) message.get("cid");
//				title = message.getString("title");
//				text = message.getString("text");
//				PushtoList.push(cid, title, text);
//				result = "true";
//				out.writeUTF(result);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		try {
			server = new ServerSocket(5888);  
		      while (true) {  
			         Socket socket = server.accept();  
			         //每接收到一个Socket就建立一个新的线程来处理它  
			         new Thread(new Task(socket)).start();  
			      }  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }  
	     
	   /** 
	    * 用来处理Socket请求的 
	    */  
	   static class Task implements Runnable {  
	   
	      private Socket socket;  
	        
	      public Task(Socket socket) {  
	         this.socket = socket;  
	      }  
	        
	      public void run() {  
	         try {  
	            handleSocket();  
	         } catch (Exception e) {  
	            e.printStackTrace();  
	         }  
	      }  
	        
	      /** 
	       * 跟客户端Socket进行通信 
	      * @throws Exception 
	       */  
	      private void handleSocket() throws Exception {  
	         BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));  
	         StringBuilder sb = new StringBuilder();  
	         String temp;  
	         int index;
	         JSONObject message;
	 		List<String> cid;
			String title, text;
			String result = "false";
	         while ((temp=br.readLine()) != null) {  
	            System.out.println(temp);  
	            if ((index = temp.indexOf("eof")) != -1) {//遇到eof时就结束接收  
	             sb.append(temp.substring(0, index));  
	                break;  
	            }  
	            sb.append(temp);  
	         }  
	         System.out.println("客户端: " + sb.toString());
	         message = JSONObject.parseObject(sb.toString());
	         cid = (List<String>) message.get("cid");
	         title = message.getString("title");
	         text = message.getString("text");
	         PushtoList.push(cid, title, text);
	         result = "true";
	         
	         //读完后写一句  
	       Writer writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");  
	         writer.write(result);  
	         writer.write("eof\n");  
	         writer.flush();  
	         writer.close();  
	         br.close();  
	         socket.close();  
	      }  
	   }  
		

}
