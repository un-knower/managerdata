package org.pcloud.spring.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pcloud.common.json.JsonHelper;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
 
public class WebFileHander {

	/***
	 * 获取目录下指定名称指定后缀的文件名称数组
	 * @param file
	 * 			文件夹目录流对象
	 * @param modelName
	 * 			文件名称
	 * @param type
	 * 			文件的后缀如 .json .txt
	 * @return []
	 */
	public static List<String> getAllFileNameInDirectory(File file, String fileName, String type) {
		List<String> fileNameList = new LinkedList<String>();
		File[] files = file.listFiles();
		if (files == null)
			return fileNameList;
		List<File> fileList = new ArrayList<File>();
		for (File f : files) {
			fileList.add(f);
		}
		Collections.sort(fileList, new Comparator<File>() {
			public int compare(File o1, File o2) {
				if (o1.isDirectory() && o2.isFile())
					return -1;
				if (o1.isFile() && o2.isDirectory())
					return 1;
				return o2.getName().compareTo(o1.getName());
			}
		});
		for (File f : fileList) {
			if (!f.isDirectory() && f.getName().endsWith(type)) {
				if (fileName != null && f.getName().indexOf(fileName) != -1) {
					fileNameList.add(f.getName());
				}
				if (fileName == null) {
					fileNameList.add(f.getName());
				}
			}
		}
		return fileNameList;
	}
	
	
	
    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
    		boolean  flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
    public static final String downLoadDir = "D:\\tmp\\download";// 文件存储路径
	@SuppressWarnings("unchecked")
	public static void uploadFile(MultipartHttpServletRequest multipartRequest, Map<String, String> params, WebUtilsAdapter webUtilsAdapter) throws Exception {
//		String dir = multipartRequest.getSession().getServletContext().getRealPath(File.separator)+"file_directory";
		String dir = downLoadDir + File.separator + "file_directory";
		
		Map<String,List<Map<String,Object>>> fileInfo = new HashMap<String,List<Map<String,Object>>>();
				
		MultiValueMap<String, MultipartFile> fileMap = multipartRequest.getMultiFileMap();
		List<String> fileFieldNameList = new ArrayList<String>();
		
		for (Entry<String, List<MultipartFile>> entity : fileMap.entrySet()) {
			
			String bindFieldName = entity.getKey();
			List<MultipartFile> listmf = entity.getValue();
			
			fileFieldNameList.add(bindFieldName);
			
			for (MultipartFile mf : listmf) {
				if (mf.getSize() > 0) {
					String fileKey = UUID.randomUUID().toString().replaceAll("-", "");
					String fileName = mf.getOriginalFilename();
					String suffix = fileName.substring(fileName.lastIndexOf("."));
					String newFileName = fileKey + suffix;
					
					Map<String,Object> fileObj = new HashMap<String,Object>();
					fileObj.put("name",  fileName);
					BigDecimal filesize = new BigDecimal(mf.getSize());  
			        BigDecimal megabyte = new BigDecimal(1024 * 1024);  
			        float returnValue = filesize.divide(megabyte, 3, BigDecimal.ROUND_UP).floatValue();  
			          
					fileObj.put("size", returnValue+"");
					fileObj.put("newName",  fileKey);
					fileObj.put("suffix",  suffix);
					
					List<Map<String,Object>> fileList = null;
					if(fileInfo.get(bindFieldName) == null){
						fileList = new ArrayList<Map<String,Object>>();
					}else{
						fileList = fileInfo.get(bindFieldName);
					}
					
					fileList.add(fileObj);
					fileInfo.put(bindFieldName, fileList);

					File uploadFile = new File(dir + File.separator + newFileName);
					
					// 判断目录是否存在
					if(!uploadFile.getParentFile().exists()) {
						uploadFile.getParentFile().mkdirs();
					}
					
					FileCopyUtils.copy(mf.getBytes(), uploadFile);
				}else{
					if(fileInfo.get(bindFieldName) == null){
						fileInfo.put(bindFieldName, new ArrayList<Map<String,Object>>());
					}
				}
			}
		}
		
		for (int i = 0; i < fileFieldNameList.size(); i++) {
			String fieldName = fileFieldNameList.get(i);
			String[] historyFiles = multipartRequest.getParameterValues("history_"+fieldName);
			List<Map<String,Object>> fileList = fileInfo.get(fieldName);
			
			if(historyFiles != null && historyFiles.length != 0){
				if(fileList == null){
					fileList = new ArrayList<Map<String,Object>>();
				}
				
				for (String fileObj : historyFiles) {
					fileList.add((Map<String, Object>) JsonHelper.string2Map(fileObj));
				}
				fileInfo.put(fieldName,fileList);
			}
			
			String[] deleteFiles = multipartRequest.getParameterValues("delete_"+fieldName);
			
			if(deleteFiles != null && deleteFiles.length != 0){
				for (String fileObjStr : deleteFiles) {
					Map<?,?>  fileObj = JsonHelper.string2Map(fileObjStr);
					File file = new File(dir + File.separator +fileObj.get("newName").toString()+ fileObj.get("suffix").toString());
					if (file.exists()) {
						file.delete();
					}
				}
			}
		}
		
		for (Map.Entry<String,List<Map<String,Object>>> entity : fileInfo.entrySet()) {
			params.put(entity.getKey(), toJson(entity.getValue()));
		}
	}
	
	//文件下载 主要方法
	  public static  void download(HttpServletRequest request,  HttpServletResponse response, String storeName,String downloadName
	       ) throws Exception {  
	    
	    request.setCharacterEncoding("UTF-8");  
	    BufferedInputStream bis = null;  
	    BufferedOutputStream bos = null;  
	  
	    //获取项目根目录
//	    String ctxPath = request.getSession().getServletContext().getRealPath("");  
	    String ctxPath = downLoadDir;
	    		
	    //获取下载文件露肩
	    String downLoadPath = ctxPath+File.separator+"file_directory"+File.separator+ storeName;  
	  
	    //获取文件的长度
	    long fileLength = new File(downLoadPath).length();  

	    //设置文件输出类型
	    response.setCharacterEncoding("utf-8");
		response.setContentType("application/octet-stream");   
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(downloadName.getBytes("utf-8"), "ISO8859-1" ) + "\"");
	    //设置输出长度
	    response.setHeader("Content-Length", String.valueOf(fileLength)); 
	    
	    //获取输入流
	    bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
	    //输出流
	    bos = new BufferedOutputStream(response.getOutputStream());  
	    byte[] buff = new byte[2048];  
	    int bytesRead;  
	    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	      bos.write(buff, 0, bytesRead);  
	    }  
	    bos.flush();
	    //关闭流
	    bis.close();  
	    bos.close();  
	  }  
	  
	  public static String toJson(Object obj) {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonStr = null;
			try {
				objectMapper.setSerializationInclusion(Include.NON_NULL);
				jsonStr = objectMapper.writeValueAsString(obj);
			} catch (Throwable t) {
				
			}
			return jsonStr;
		}

}  