package com.example.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileUtil {

	/**
	 * 读取txt文件里的内容
	 * @param fileUrl	文件路径
	 * @return
	 */
	public static List<String> readTxtFile(String fileUrl){
		List<String> list = new ArrayList<String>();
		try {
			File file = new File(fileUrl);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String lineTxt = null;
			//读取文件中的记录并添加到list里
			while ((lineTxt = br.readLine()) != null) {
				list.add(lineTxt);
			}
			return list;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	//往txt里写内容
	public static void writeTxtFile(List<String> connents,String filePath){
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filePath);
			
			for(String connent : connents){
				fos.write((connent + "\r\n").getBytes());
			}
			if(fos != null){
				fos.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
