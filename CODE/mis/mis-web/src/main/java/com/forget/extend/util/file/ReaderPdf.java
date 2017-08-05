package com.forget.extend.util.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class ReaderPdf {
	private static final Logger logger = LoggerFactory.getLogger(ReaderPdf.class);

	public static void main(String[] args) {
		String filePath = 
				"C:\\Users\\ThinkPad\\Desktop\\中储发展股份有限公司-124\\中储发展股份有限公司-组织-董事会审计委员会工作细则20071031.pdf";
		
		filePath = "F:\\管理大数据项目资料整理\\人力资源\\2003-中航油集团\\项目前期文件\\文章\\人力资源战略\\renweiben2.pdf";
		System.out.println(readContent(filePath));
//		System.out.println(readData(filePath));
	}
	
//	public static List<Map<String, Object>> readContent(String filePath) {
//		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
//		Map<String, Object> resultMap = null;
//		try {
//			PdfReader reader = new PdfReader(filePath); //读取pdf所使用的输出流
//	        int num = reader.getNumberOfPages();//获得页数
//	        String content = "";  //存放读取出的文档内容
//	        for (int i = 1; i < num; i++) {
//	           content += PdfTextExtractor.getTextFromPage(reader, i); //读取第i页的文档内容
//	                  }
//	        System.out.println(content);
//		} catch(Exception e) {
//			logger.error("readContent(" + filePath + ")", e);
//		} finally {
//			
//		}
//		
//		return resultList;
//	}
	
	public static List<Map<String, Object>> readContent(String filePath) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = null;
		PdfReader reader = null;
		PdfReaderContentParser parser = null;
		try {
			reader = new PdfReader(filePath);
			parser = new PdfReaderContentParser(reader);
			TextExtractionStrategy strategy;
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				strategy = parser.processContent(i,new SimpleTextExtractionStrategy());
				resultMap = new HashMap<String,Object>();
				if(StringUtils.isNotBlank(strategy.getResultantText())) {
					resultMap.put("num", 1);
					resultMap.put("text", strategy.getResultantText().trim());// 去除特殊符号
					resultList.add(resultMap);
				}
				
			}
		} catch(Exception e) {
			logger.error("readContent(" + filePath + ") ERROR! : ", e.getMessage());
		} finally {
			if(reader!=null) {
				reader.close();
			}
		}
		return resultList;
	}
	
	public static List<Map<String, Object>> readData(String filePath) {
		List<Map<String, Object>> resultList = null;
		if (filePath.toLowerCase().endsWith(".pdf")) {
			resultList = readPdf(filePath);
		}
		return resultList;
	}

	public static List<Map<String, Object>> readPdf(String filePath) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = null;
		PDDocument document = null;
		
		File file = new File(filePath);
		try {
			// 方式一：
			// InputStream input = null;
			// input = new FileInputStream(file);
			// // 加载 pdf 文档
			// PDFParser parser = new PDFParser(new RandomAccessBuffer(input));
			// parser.parse();
			// document = parser.getPDDocument();

			// 方式二：
			document = PDDocument.load(file);

			// 获取页码
			int pages = document.getNumberOfPages();
			// 读文本内容
			PDFTextStripper stripper = new PDFTextStripper();
			// 设置按顺序输出
			stripper.setSortByPosition(true);
			stripper.setStartPage(1);
			stripper.setEndPage(pages);
			String content = stripper.getText(document);
			if(StringUtils.isNotBlank(content)) {
				resultMap = new HashMap<String,Object>();
				resultMap.put("num", 1);
				resultMap.put("text", content);// 去除特殊符号
				resultList.add(resultMap);
			}
		} catch (Exception e) {
			logger.error("readPdf(" + file.getAbsolutePath() + ")", e);
		} finally {
			if(document != null) {
				try {
					document.close();
				} catch (IOException e) {
					logger.error("document.close()", e);
				}
			}
		}
		return resultList;
	}

}