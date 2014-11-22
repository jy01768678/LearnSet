package com.lorin.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProtocolUtil {

	public static void writeRequest(OutputStream output, Request request) throws IOException {
		//将request请求发送给服务器端
		output.write(request.getEncode());
		output.write(ByteUtil.int2ByteArray(request.getCommandLength()));
		if(Encode.UTF8.getValue() == request.getEncode()){
			output.write(request.getCommand().getBytes("UTF-8"));
		}else {
			output.write(request.getCommand().getBytes("GBK"));
		}
		output.flush();
	}

	public static Response readResponse(InputStream input) throws IOException {
		//读取编码
		byte[] encodeByte = new byte[1];
		input.read(encodeByte);
		byte encode = encodeByte[0];
		
		//读取命令长度
		byte[] responseLenghtByte = new byte[4];
		input.read(responseLenghtByte);
		int responseLength = ByteUtil.byte2Int(responseLenghtByte);
		
		//读取命令
		byte[] reseponseBytes = new byte[responseLength];
		input.read(reseponseBytes);
		String responseStr = "";
		if(Encode.UTF8.getValue() == encode){
			responseStr = new String(reseponseBytes,"UTF-8");
		}else {
			responseStr = new String(reseponseBytes,"GBK");
		}
		Response res = new Response();
		res.setEncode(encode);
		res.setResponse(responseStr);
		res.setResponseLenght(responseLength);
		return res;
	}

	public static Request readRequest(InputStream input) throws IOException {
		//读取编码
		byte[] encodeByte = new byte[1];
		input.read(encodeByte);
		byte encode = encodeByte[0];
		
		//读取命令长度
		byte[] commandLenghtByte = new byte[4];
		input.read(commandLenghtByte);
		int commandLenght = ByteUtil.byte2Int(commandLenghtByte);
		
		//读取命令
		byte[] commandBytes = new byte[commandLenght];
		input.read(commandBytes);
		String command = "";
		if(Encode.UTF8.getValue() == encode){
			command = new String(commandBytes,"UTF-8");
		}else {
			command = new String(commandBytes,"GBK");
		}
		
		//组装返回请求
		Request request = new Request();
		request.setCommand(command);
		request.setCommandLength(commandLenght);
		request.setEncode(encode);
		
		return request;
	}

	public static void writeResponse(OutputStream output, Response response) throws IOException {
		output.write(response.getEncode());
//		output.wirte(response.getResponseLenght());//会截取低8位进行传输，丢弃高24位
		output.write(ByteUtil.int2ByteArray(response.getResponseLenght()));
		if(Encode.UTF8.getValue() == response.getEncode()){
			output.write(response.getResponse().getBytes("UTF-8"));
		}else {
			output.write(response.getResponse().getBytes("GBK"));
		}
		output.flush();
	}

	
}
