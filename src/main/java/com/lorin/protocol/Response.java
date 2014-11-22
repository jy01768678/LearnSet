package com.lorin.protocol;

public class Response {
	private byte encode;
	
	private String response;
	
	private int responseLenght;

	public int getResponseLenght() {
		return responseLenght;
	}

	public void setResponseLenght(int responseLenght) {
		this.responseLenght = responseLenght;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public byte getEncode() {
		return encode;
	}

	public void setEncode(byte encode) {
		this.encode = encode;
	}

	
}
