package com.forget.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


/*
 * 将对象从文件中反序列化出
 */
public class ReadObject2Deserialize {

	// 从文件反序列化到对象
	public static Object deserialize(String filePath) {
		try {
			// 创建一个对象输入流，从文件读取对象
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					filePath));
			Object readObject = in.readObject();
			in.close();
			return readObject;
		} catch (Exception x) {
			System.out.println(x.toString());
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public <T> T readObject(String filePath) {

		T t = null;
		try {
			@SuppressWarnings("resource")
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					filePath));
			t = (T) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return t;
	}
	
}

