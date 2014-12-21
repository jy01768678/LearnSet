package com.lorin.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class FileTypeJudge {

	/**
	 * 二进制转换成16进制
	 * @param bytes
	 * @return
	 */
	private static String byte2hex(byte[] bytes){
		StringBuilder hex = new StringBuilder();
		for(int i=0;i<bytes.length;i++){
			String temp = Integer.toHexString(bytes[i] & 0xFF);
			if(temp.length() == 1){
				hex.append("0");
			}
			hex.append(temp.toLowerCase());
		}
		return hex.toString();
	}
	
	private static String getFileHeader(String filePath) throws IOException {
		byte[] b = new byte[28];//每个文件的magic word 各不相同
		InputStream is = null;
		is = new FileInputStream(filePath);
		is.read(b,0,28);
		is.close();
		return byte2hex(b);
	}
	
	private static FileType getType(String filePath) throws IOException{
		String fileHead = getFileHeader(filePath);
		if(fileHead == null || fileHead.length() ==0){
			return null;
		}
		
		fileHead = fileHead.toUpperCase();
		FileType[] fileTypes = FileType.values();
		for(FileType fileType : fileTypes){
			if(fileHead.startsWith(fileType.getValue())){
				return fileType;
			}
		}
		return null;
	}
}
