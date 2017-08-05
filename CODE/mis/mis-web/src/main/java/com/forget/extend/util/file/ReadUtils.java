package com.forget.extend.util.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadUtils {
	private static final Logger logger = LoggerFactory.getLogger(ReadUtils.class);
	
	public static File f = null;
	public static int count = 0;
	public static void main(String args[]) {
		String path = "F:\\管理大数据项目资料整理\\";
//		String path = "F:\\管理大数据项目资料整理\\人力资源\\陕煤7期\\陕西煤业化工技术研究院人事管理制度.doc";
		File file = new File(path);
		if(file.isDirectory()) {
			File fileList[] = file.listFiles();
			for(int i=0; i<fileList.length; i++) {
				File newFile = fileList[i];
				f = new File("D:\\tmp\\NlpForget\\analysis\\data\\datatest_"+newFile.getName()+".txt");
				fileFile(newFile);
			}
		} else {
			f = new File("D:\\tmp\\NlpForget\\analysis\\data\\datatest.txt");
			fileFile(file);
		}
		
		System.out.println("读取文件数量："+count);
	}
	
	public static void fileFile(File file) {
		if(file.isDirectory()) {
			File[] filelist = file.listFiles();
			for(int i=0; i<filelist.length; i++) {
				File filenew = filelist[i];
				if(filenew.isFile() && !filenew.getName().contains("~$")) {
					writeFile(filenew);
				} else if(filenew.isDirectory()) {
					fileFile(filenew);
				}
			}
		} else {
			writeFile(file);
		}
	}
	
	public static void writeFile(File file) {
		try {
			List<Map<String, Object>> contentWord = readData(file.getAbsolutePath());
			if(contentWord!=null) {
				writeInFileByfb(contentWord);
			}
		} catch (Exception e) {
			logger.error("writeFile("+file+")",e);
		}
	}
	
	public static List<Map<String,Object>> readData(String filePath) {
		List<Map<String,Object>> resultList = null;
//		if (filePath.toLowerCase().endsWith("docx")) {
//			resultList = ReaderWord.readDataDocx(filePath);
//		} else if (filePath.toLowerCase().endsWith("doc")) {
//			resultList = ReaderWord.readDataDoc(filePath);
//		} else if (filePath.toLowerCase().endsWith(".pdf")) {
//			resultList = ReaderPdf.readPdf(filePath);
//		}
		if(filePath.toLowerCase().endsWith(".docx") || filePath.toLowerCase().endsWith(".doc")) {
    		resultList = ReaderWord.readContent(filePath);
    	} else if (filePath.toLowerCase().endsWith(".pdf")) {
			resultList = ReaderPdf.readContent(filePath);
		}
    	
        return resultList;
	}

	public static void writeInFileByfb(List<Map<String, Object>> contentWord) {
		count ++;
//		FileWriter fw = null;
//		BufferedWriter bw = null;
		OutputStreamWriter writerStream = null;
		
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			writerStream = new OutputStreamWriter(new FileOutputStream(f,true),"utf-8");
			
			BufferedWriter writer = new BufferedWriter(writerStream);
			// fw = new FileWriter(f.getAbsoluteFile(), true); // true表示可以追加新内容
			// fw = new FileWriter(f.getAbsoluteFile()); //表示不追加
			// bw = new BufferedWriter(fw);
			for (int i = 0; i < contentWord.size(); i++) {
				writer.write((String) contentWord.get(i).get("text"));
				writer.newLine();
				writer.flush();
			}
			writer.close();
			//bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
