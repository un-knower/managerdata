package com.forget.extend.util.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	public static void main(String args[]) {
		// String excel2010 = "D:\\tmp\\test.xlsx";
//		String excel2010 = "D:\\tmp\\管理大数据指标体系（（测试版本）.xls";
		String excel2010 = "D:\\tmp\\文档类别批量上传模板.xlsx";
		List<Map<String, String>> list1 = null;
		try {
			list1 = readExcel(excel2010);
			if (list1 != null) {
				for (Map<String, String> map : list1) {
					System.out.println(map);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Map<String, String>> readExcel(String path)
			throws IOException {
		if (path == null || "".equals(path)) {
			return null;
		} else {
			String postfix = getPostfix(path);
			if (!"".equals(postfix)) {
				if ("xls".equals(postfix)) {
					return readXls(path);
				} else if ("xlsx".equals(postfix)) {
					return readXlsx(path);
				}
			} else {
				System.out.println(path + " : Not the Excel file!");
			}
		}
		return null;
	}

	public static List<Map<String, String>> readXlsx(String path)
			throws IOException {
		InputStream is = new FileInputStream(path);
		@SuppressWarnings("resource")
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		Map<String, String> map;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					map = new HashMap<String, String>();
					XSSFCell no = xssfRow.getCell(0);
					if (no != null) {
						XSSFCell name = xssfRow.getCell(1);
						XSSFCell age = xssfRow.getCell(2);
						XSSFCell score = xssfRow.getCell(3);
						map.put("target1", getValue(no));
						map.put("target2", getValue(name));
						map.put("target3", getValue(age));
						map.put("target4", getValue(score));
						list.add(map);
					}
				}
			}
		}
		return list;
	}

	public static List<Map<String, String>> readXls(String path)
			throws IOException {
		InputStream is = new FileInputStream(path);
		@SuppressWarnings("resource")
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		Map<String, String> map;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					map = new HashMap<String, String>();
					HSSFCell no = hssfRow.getCell(0);
					if (no != null) {
						HSSFCell name = hssfRow.getCell(1);
						HSSFCell age = hssfRow.getCell(2);
						HSSFCell score = hssfRow.getCell(3);
						map.put("target1", getValue(no));
						map.put("target2", getValue(name));
						map.put("target3", getValue(age));
						map.put("target4", getValue(score));
						list.add(map);
					}

				}
			}
		}
		return list;
	}

	@SuppressWarnings("static-access")
	private static String getValue(XSSFCell xssfRow) {
		if(xssfRow == null) {
			return "";
		}
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfRow.getNumericCellValue());
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}

	@SuppressWarnings("static-access")
	private static String getValue(HSSFCell hssfCell) {
		if (hssfCell == null) {
			return "";
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	public static String getPostfix(String path) {
		if (path == null || "".equals(path.trim())) {
			return "";
		}
		if (path.contains(".")) {
			return path.substring(path.lastIndexOf(".") + 1, path.length());
		}
		return "";
	}
}
