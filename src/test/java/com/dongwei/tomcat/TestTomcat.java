package com.dongwei.tomcat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TestTomcat {
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8080);
		Socket socket = serverSocket.accept();
		System.out.println("");
		InputStream inputStream = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer stringBuffer = new StringBuffer();
		String tmp = "";
		while ((tmp = reader.readLine()) !=null && tmp.length()>0) {
			stringBuffer.append(tmp);
			stringBuffer.append("\r\n");
		}
//		OutputStreamWriter out = new OutputStreamWriter(out)
		OutputStream out = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
		writer.println("HTTP/1.1 200 OK");
		writer.println("Content-Type: text/html;charset=utf-8");
		writer.println();
		writer.flush();
		FileInputStream fileInputStream = new FileInputStream(new File(""));
		byte[] buf = new byte[1024];
		int length = 0;
		while ((length = fileInputStream.read(buf)) != -1) {
			out.write(buf, 0, length);
		}
		fileInputStream.close();
		out.close();
		writer.close();
		System.out.println("响应完成");
	}
	
}
