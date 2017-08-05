package com.kjtt.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
public class ExcelUtils {
	private static final String EXCEL_XLS = "xls";  
    private static final String EXCEL_XLSX = "xlsx";  
	private static HSSFWorkbook workbook = null;  
	static List<String> list = new ArrayList<>();
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public static void ReadXlsx() throws IOException{
		InputStream stream = new FileInputStream("/Users/lll/Downloads/table.xlsx");  
	    XSSFWorkbook xssfWorkbook = new XSSFWorkbook(stream);  
	    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);  
	   
	    int rowstart = xssfSheet.getFirstRowNum();  
	    int rowEnd = xssfSheet.getLastRowNum();  
	    for(int i=rowstart;i<=rowEnd;i++)  
	    {  
	      XSSFRow row = xssfSheet.getRow(i);  
	      if(null == row) continue;  
	      int cellStart = row.getFirstCellNum();  
	      int cellEnd = row.getLastCellNum();  
	   
	      for(int k=cellStart;k<=cellEnd;k++)  
	      {  
	        XSSFCell cell = row.getCell(k);  
	        if(null==cell) continue;  
	   
	        String element = "";
	        switch (cell.getCellType())  
	        {  
	          case HSSFCell.CELL_TYPE_NUMERIC: // 数字  
	            System.out.print(cell.getNumericCellValue()  
	                + "\t");  
	            element=cell.getNumericCellValue()+"";
	            break;  
	          case HSSFCell.CELL_TYPE_STRING: // 字符串  
	            System.out.print(cell.getStringCellValue()  
	                + "\t");  
	            element=cell.getStringCellValue();
	            break;  
	          case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean  
	            System.out.println(cell.getBooleanCellValue()  
	                + "\t");
	            element=cell.getBooleanCellValue()+"";
	            break;  
	          case HSSFCell.CELL_TYPE_FORMULA: // 公式  
	            System.out.print(cell.getCellFormula() + "\t");  
	            element=cell.getCellFormula()+"";
	            break;  
	          case HSSFCell.CELL_TYPE_BLANK: // 空值  
	            System.out.println(" ");  
	            break;  
	          case HSSFCell.CELL_TYPE_ERROR: // 故障  
	            System.out.println(" ");  
	            break;  
	          default:  
	            System.out.print("未知类型  ");  
	            break;  
	        }  
	        if(i==0){
	        	getHeader(k, element);
	        }
	      }  
	      System.out.print("\n");  
	    }  
	  }
	public static void getHeader(int index,String element){
		list.add(index, element);
	}
	
	
    /** 
     * 判断文件是否存在. 
     * @param fileDir  文件路径 
     * @return 
     */  
    public static boolean fileExist(String fileDir){  
         boolean flag = false;  
         File file = new File(fileDir);  
         flag = file.exists();  
         return flag;  
    }  
    /** 
     * 判断文件的sheet是否存在. 
     * @param fileDir   文件路径 
     * @param sheetName  表格索引名 
     * @return 
     */  
    public static boolean sheetExist(String fileDir,String sheetName) throws Exception{  
         boolean flag = false;  
         File file = new File(fileDir);  
         if(file.exists()){    //文件存在  
            //创建workbook  
             try {  
                workbook = new HSSFWorkbook(new FileInputStream(file));  
                //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
                HSSFSheet sheet = workbook.getSheet(sheetName);    
                if(sheet!=null)  
                    flag = true;  
            } catch (Exception e) {  
                throw e;
            }   
              
         }else{    //文件不存在  
             flag = false;  
         }  
         return flag;  
    } 
    /** 
     * 创建新excel. 
     * @param fileDir  excel的路径 
     * @param sheetName 要创建的表格索引 
     * @param titleRow excel的第一行即表格头 
     */  
    public static void createExcel(String fileDir,String sheetName,String titleRow[]) throws Exception{  
        //创建workbook  
        workbook = new HSSFWorkbook();  
        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
        HSSFSheet sheet1 = workbook.createSheet(sheetName);    
        //新建文件  
        FileOutputStream out = null;  
        try {  
            //添加表头  
            HSSFRow row = workbook.getSheet(sheetName).createRow(0);    //创建第一行    
            for(short i = 0;i < titleRow.length;i++){  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleRow[i]);  
            }  
            out = new FileOutputStream(fileDir);  
            workbook.write(out);  
        } catch (Exception e) {  
            throw e;
        } finally {    
            try {    
                out.close();    
            } catch (IOException e) {    
                e.printStackTrace();  
            }    
        }    
    }  
    /** 
     * 删除文件. 
     * @param fileDir  文件路径 
     */  
    public static boolean deleteExcel(String fileDir) {  
        boolean flag = false;  
        File file = new File(fileDir);  
        // 判断目录或文件是否存在    
        if (!file.exists()) {  // 不存在返回 false    
            return flag;    
        } else {    
            // 判断是否为文件    
            if (file.isFile()) {  // 为文件时调用删除文件方法    
                file.delete();  
                flag = true;  
            }   
        }  
        return flag;  
    }  
    /** 
     * 往excel中写入(已存在的数据无法写入). 
     * @param fileDir    文件路径 
     * @param sheetName  表格索引 
     * @param object 
     * @throws Exception 
     */  
    public static void writeToExcel(String fileDir,String sheetName,List<Map> mapList) throws Exception{  
    	OutputStream out = null;
    	//创建workbook  
        File file = new File(fileDir);  
        Workbook workBook = getWorkbok(file);
        Sheet sheet = workBook.getSheetAt(0);  
        //删除原有数据，除了属性列   
        int rowNumber = sheet.getLastRowNum();  // 第一行从0开始算  
        Row row0 = sheet.getRow(0);  
        for (int i = 1; i <= rowNumber; i++) {  
            Row row = sheet.getRow(i);  
            sheet.removeRow(row);  
        }  
        //往Excel中写新数据  
        for (int j = 0; j < mapList.size(); j++) {  
            // 创建一行：从第二行开始，跳过属性列  
            Row row = sheet.createRow(j + 1);  
            Map dataMap = mapList.get(j);  
            String id = dataMap.get("编号").toString();  
            String xsd = dataMap.get("相似度").toString();  
            String number = dataMap.get("项目编号").toString(); 
            String zb = dataMap.get("考核指标").toString(); 
            //总列数
            int count=row0.getLastCellNum();  
            for (int k = 0; k <= count; k++) {  
	            Cell cell1 = row.createCell(0);  
	            cell1.setCellValue(id);  
	            
	            Cell cell2 = row.createCell(1);  
	            cell2.setCellValue(xsd);  
	      
	            Cell cell3 = row.createCell(2);  
	            cell3.setCellValue(number); 
	            
	            Cell  cell4= row.createCell(3);  
	            cell4.setCellValue(zb);  
            }  
        }  
        // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效  
        out =  new FileOutputStream(fileDir);  
        workBook.write(out); 
           
    }  
    /** 
     * 判断Excel的版本,获取Workbook 
     * @param in 
     * @param filename 
     * @return 
     * @throws IOException 
     */  
    public static Workbook getWorkbok(File file) throws IOException{  
        Workbook wb = null;  
        FileInputStream in = new FileInputStream(file);  
        if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003  
            wb = new HSSFWorkbook(in);  
        }else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010  
            wb = new XSSFWorkbook(in);  
        }  
        return wb;  
    }  
    
	public static void main(String[] args) {
		//读取.xlsx
		try {
			//ExcelUtils.ReadXlsx();
			ExcelUtils.readXls("/Users/lll/Downloads/table.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//创建新Excel
//		String fileDir="D:/广西科技厅项目.xls";
//		String sheetName="sheet1";
//		String[] titleRow={"编号","相似度","项目编号","考核指标"};
//		try {
//			createExcel(fileDir, sheetName, titleRow);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//Excel数据写入
//		try {
//			List<Map> mapList = new ArrayList<>();
//			for(int i=0;i<20;i++){
//				Map<String,String>  map =new HashMap<>();
//				map.put("编号", "100_"+i);
//				map.put("相似度", "1"+i);
//				map.put("项目编号", "编号918_00"+i);
//				map.put("考核指标", "指标_00"+i);
//				map.put("备注", "备注"+i);
//				mapList.add(map);
//			}
//			writeToExcel(fileDir, sheetName, mapList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	
	public static  List<Map<String,String>> readXls(String path) {

        HSSFWorkbook hssfWorkbook = null;
        try {
            InputStream is = new FileInputStream(path);
            hssfWorkbook = new HSSFWorkbook(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
      
        Map<String,String> map =null;
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        if (hssfWorkbook != null) {
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
                        map = new HashMap<String,String>();
                        HSSFCell no = hssfRow.getCell(0);
                        HSSFCell name = hssfRow.getCell(1);
                        HSSFCell age = hssfRow.getCell(2);
                        HSSFCell score = hssfRow.getCell(3);
                        System.out.println(no);
                        System.out.println(name);
                        System.out.println(age); 
                        System.out.println(score);
//                        studentBean.setNo(getValue(no));
//                        studentBean.setName(getValue(name));
//                        studentBean.setAge(getValue(age));
//                        studentBean.setScore(Float.valueOf(getValue(score)));
//                        list.add(studentBean);
                    }
                }
            }
        }
        return list;
    }

}
