package com.forget.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class ReadTxtContent {

	/*
	 * 读取文件内容   去除标签  和  url
	 */
	 public static String readTxtFile(String filePath){
        try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                StringBuffer lineTxt1 = new StringBuffer();
                String line=null;
                while((line=bufferedReader.readLine()) != null){
                	lineTxt1.append(line);
                }
                String lineTxt = lineTxt1.toString();
                //替换所有链接
                String string2 = lineTxt.replaceAll("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?","");
                //替换所有标签
                string2 = lineTxt.replaceAll("</?[a-zA-Z]+[^><]*>","");
              
               /* String string = "";
                List<String> list = new ArrayList<String>();
    			for (int j = 0; j < string2.length(); j++) {
    				string = lineTxt.substring(j, j+1);
    				list.add(string);
    			}*/
                read.close();
                return string2;
        }else{
            System.out.println("找不到指定的文件");
            return null;
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return null;
        }
    }

		 public static void main(String[] args) {
			String str = "+++++读取文件内容出错，大家好。我是:xxx;heng'aoxingl'aidaozheli!";
			appendContext2File("D:\\txt_n.txt", str);
		}
		
		/* 
		 * 向文本文件中追加内容
		 */
		public static void appendContext2File(String filePath,String context){
			if(filePath != null && !filePath.equals("")){
				File file = new File(filePath);
				//判断文件是否存在
				if(!file.exists()){
					try {
						//创建文件
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				 BufferedWriter out = null;     
		        try {     
		            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));     
		            out.write(context);     
		        } catch (Exception e) {     
		            e.printStackTrace();     
		        } finally {     
		            try {     
		                if(out != null){  
		                    out.close();     
		                }  
		            } catch (IOException e) {     
		                e.printStackTrace();     
		            }     
		        }     
			}
		} 
		/*//读取文件夹下的文件内容
		public static void file(){
			String path = "D:\\forget";
			File file = new File(path);
			if(file.exists()){
				if(file.isDirectory()){
					File[] listFiles = file.listFiles();
					for (int i = 0; i < listFiles.length; i++) {
						if(listFiles[i].isFile()){
							
					
				}else if(file.isFile()){
		*/		
}


