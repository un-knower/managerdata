package com.forget.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/*
 * 将对象序列化到文件中
 */
public class SaveObject2Serialize {


	// 序列化对象到文件
	public static void serialize(String filePath,Object object) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(filePath));

			out.writeObject(object); // 序列化一个会员对象
			out.close();
		} catch (Exception x) {
			System.out.println(x.toString());
		}

	}

	//直接保存到文件
	public void saveObject(Object o,String filePath) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(
					new FileOutputStream(filePath));

			out.writeObject(o);
		} catch (IOException e) {
			throw new RuntimeException("保存文件到磁盘出错");
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				throw new RuntimeException("关闭流出错");
			}
		}
		System.out.println("写入成功");
	}
	
	
	public static void main(String[] args) throws Exception {
		String path = "D:\\OSS.txt";
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
		    inputStream = new FileInputStream(path);
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        System.out.println(line);
		    }
		    // note that Scanner suppresses exceptions
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		} finally {
		    if (inputStream != null) {
		        inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}

	}
}
