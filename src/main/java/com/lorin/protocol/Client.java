package com.lorin.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Request request = new Request();
		request.setCommand("HELLO");
		request.setCommandLength(request.getCommand().length());
		
		Socket socket = new Socket("127.0.0.1",4567);
		OutputStream output = socket.getOutputStream();
		//发送请求
		ProtocolUtil.writeRequest(output,request);
		
		InputStream input = socket.getInputStream();
		Response response = ProtocolUtil.readResponse(input);
		socket.shutdownOutput();
		socket.shutdownInput();
	}
}
