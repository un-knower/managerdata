package com.ufc.user.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.pcloud.common.el.EL;
import org.pcloud.common.json.JsonHelper;
import org.pcloud.common.json.RespResult;
import org.pcloud.spring.web.WebFileHander;
import org.pcloud.spring.web.WebUtilsAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ufc.user.utils.file.ExcelUtils;


/**
 * 文档管理Controller
 * @author ThinkPad
 *
 */
@RequestMapping(value="updownload")
@Controller
public class UpdownloadController extends BaseMethodController{
	private static final Logger logger = LoggerFactory.getLogger(UpdownloadController.class);
	
	@Autowired
	 public WebUtilsAdapter webUtilsAdapter;

	@RequestMapping(value="upload")
	@ResponseBody
	public String fileupload (HttpServletRequest request) throws Exception{
		Map<String, Map<String, Object[]>>  sqlInfoMap = new LinkedHashMap<String, Map<String, Object[]>> ();
		try {
			String person = "admin";// 操作人
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			String time = sdf.format(new Date());
			
			String fileAdress = params.get("add_detail");
			if(StringUtils.isNotBlank(fileAdress)) {
				List<Map<?,?>> file = JsonHelper.string2List_Map(fileAdress);
				String id = null;
				String fileName = null;
				String fileNewName = null;
				String fileSize = null;
				String fileSuffix = null;
				String add_typeId = null;
				String add_time = null;
				String add_industry = null;
				String add_company = null;
				
				EL sqlTemplate = null;
				Map<String,String> map = null;
				for(int i=0; i<file.size(); i++) {
					id = UUID.randomUUID().toString();
					fileName = (String) file.get(i).get("name");// 文件名称
//					filePath = (String) file.get(i).get("filePath");// 文件存储权目录
//					filePath = filePath.replace("\\", "\\\\");
					fileNewName = (String) file.get(i).get("newName");//
					fileSize = (String) file.get(i).get("size");
					fileSuffix = (String) file.get(i).get("suffix");
					add_typeId = (String) params.get("add_typeId");
					add_time = (String) params.get("add_time");
					add_industry = (String) params.get("add_industry");;
					add_company = (String) params.get("add_company");;
					
					map = new HashMap<String,String>();
					map.put("id", id);
					map.put("fileName", fileName);
//					map.put("filePath", filePath);
					map.put("fileNewName", fileNewName);
					map.put("fileSize", fileSize);
					map.put("fileSuffix", fileSuffix);
					map.put("add_typeId", add_typeId);
					map.put("add_time", add_time);
					map.put("add_industry", add_industry);
					map.put("add_company", add_company);
					
					map.put("createtime", time);
					map.put("createperson", person);
					map.put("updatetime", time);
					map.put("updateperson", person);
					
					sqlTemplate = new EL("file", "insertFile", webUtilsAdapter.getBasePath() + "sql");
					sqlTemplate.setVariables(map);
					
					if (sqlInfoMap.get("mysqlTemplate") != null) {
						sqlInfoMap.get("mysqlTemplate").putAll(sqlTemplate.getSqlSegmentInfo(i + ""));
					} else {
						sqlInfoMap.put("mysqlTemplate", sqlTemplate.getSqlSegmentInfo(i + ""));
					}
				}
				jtaManager.saveOrupdate(sqlInfoMap);
			}
			
		} catch (Exception e) {
			throw new Exception("上传附件出错！系统提示："+e.getMessage());
		}
		return RespResult.getInstance().setInfo("status", "success").toJson();
	}
	
//	@RequestMapping(value="download")
//	public void download (HttpServletRequest request) throws Exception{
//		System.out.println("123");
//	}
	
	
	@RequestMapping(value="uploadType")
	@ResponseBody
	public String targetInsert(HttpServletRequest request) throws Exception{
		Map<String, Map<String, Object[]>>  sqlInfoMap = new LinkedHashMap<String, Map<String, Object[]>> ();
		try {
			String person = "admin";// 操作人
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			String time = sdf.format(new Date());
			
			params.put("createPerson", person);
			params.put("createTime", time);
			params.put("updatePerson", person);
			params.put("updateTime", time);
			
			List<Map<String, Object>> fileTargetList = queryFileTarget();// 获取数据库全部指标
			Map<String, Object> map = null;
			String target_id = null;
			String target_name = null;
			String target_level = null;
			/**
			 * 077b0c6c-c852-4575-9b16-2c67b61f42c6=亮点案例
			 * 3cd67813-f684-4a06-b31e-0a429520d04d=具体举措
			 */
			Map<String,Map<String, Object>> targetmap = new HashMap<String,Map<String, Object>>();
			Map<String,String> targetContentmap = new HashMap<String,String>();
			for (int i = 0; i < fileTargetList.size(); i++) {
				map = fileTargetList.get(i);
				target_id = (String) map.get("id");
//				target_name = (String) map.get("targetname");
//				target_level = (String) map.get("targetlevel");
				targetmap.put(target_id, map);
			}
			for (int i = 0; i < fileTargetList.size(); i++) {
				map = fileTargetList.get(i);
				target_id = (String) map.get("id");
				target_name = (String) map.get("typename");
				target_level = (String) map.get("typelevel");
				if(target_level.equals("1")) {
					targetContentmap.put(target_name, target_id);
//					targetContentmap.put(targetmap.get(target_id).get("targetname"), value);
				} else if(target_level.equals("2")) {
					String parent_id = (String) map.get("parentid");
					String targetTotalName = (String)targetmap.get(parent_id).get("typename") + "_" + target_name;
					targetContentmap.put(targetTotalName, target_id);
				} else if(target_level.equals("3")) {
					String parent_id = (String) map.get("parentid");// 2
					String parent_id_1 = (String)targetmap.get(parent_id).get("parentid");
					String targetTotalName = (String)targetmap.get(parent_id_1).get("typename") + "_" + (String)targetmap.get(parent_id).get("typename") + "_" +target_name;
					targetContentmap.put(targetTotalName, target_id);
				} else if(target_level.equals("4")) {
					String parent_id = (String) map.get("parentid");// 3
					String parent_id_2 = (String)targetmap.get(parent_id).get("parentid");// 2
					String parent_id_1 = (String)targetmap.get(parent_id_2).get("parentid");// 1
					String targetTotalName = (String)targetmap.get(parent_id_1).get("typename") + "_" + (String)targetmap.get(parent_id_2).get("typename") + "_" + (String)targetmap.get(parent_id).get("typename") + "_" + target_name;
					targetContentmap.put(targetTotalName, target_id);
				}
			}
			
			System.out.println(targetContentmap);
			
			List<Map<String,String>> needInsert = new ArrayList<Map<String,String>>();
			
			String dir = WebFileHander.downLoadDir + File.separator + "file_directory";// 上传文件路径
			String fileAdress = params.get("add_detail");
			if(StringUtils.isNotBlank(fileAdress)) {
				List<Map<?,?>> file = JsonHelper.string2List_Map(fileAdress);
				String name = null;
				String newName = null;
				String suffix = null;
				String size = null;
				String filepath = null;
				for(int i=0; i<file.size(); i++) {
					name = (String) file.get(i).get("name");
					size = (String) file.get(i).get("size");
					if(size == null || size.equals("") || size.equals("0")) {
//						logger.info("File[{}] size[{}]", name, size);
						continue;
					} else {
						newName = (String) file.get(i).get("newName");
						suffix = (String) file.get(i).get("suffix");
						filepath = dir + File.separator + newName + suffix;
						List<Map<String, String>> excelmap = ExcelUtils.readExcel(filepath);
						Map<String, String> emap = null;
						String tlevel = null;
						String tname = null;
						String tparentid = null;
						String target1 = null;
						String target2 = null;
						String target3 = null;
						String target4 = null;
						String target1_id = null;
						String target2_id = null;
						String target3_id = null;
						String target4_id = null;
						for(int j=0; j<excelmap.size(); j++) {
							emap = excelmap.get(j);
							
							target1 = emap.get("target1");
							if(StringUtils.isBlank(target1)) {
								
							} else {
								if(targetContentmap.containsKey(target1)) {
									target1_id = targetContentmap.get(target1);
								} else {
									tlevel = "1";
									tparentid = "0";
									target1_id = UUID.randomUUID().toString();
									targetContentmap.put(target1,target1_id);
									setInsertMap(target1_id,target1,tlevel,tparentid,needInsert);// 记录将要插入数据库的数据
								}
							}
							target2 = emap.get("target2");
							if(StringUtils.isBlank(target2)) {
								
							} else {
								String key = target1+"_"+target2;
								if(targetContentmap.containsKey(key)) {
									target2_id = targetContentmap.get(key);
								} else {
									tlevel = "2";
									tparentid = target1_id;
									target2_id = UUID.randomUUID().toString();
									targetContentmap.put(key,target2_id);
									setInsertMap(target2_id,target2,tlevel,tparentid,needInsert);// 记录将要插入数据库的数据
								}
							}
							target3 = emap.get("target3");
							if(StringUtils.isBlank(target3)) {
								
							} else {
								String key = target1+"_"+target2+"_"+target3;
								if(targetContentmap.containsKey(key)) {
									target3_id = targetContentmap.get(key);
								} else {
									tlevel = "3";
									tparentid = target2_id;
									target3_id = UUID.randomUUID().toString();
									targetContentmap.put(key,target3_id);
									setInsertMap(target3_id,target3,tlevel,tparentid,needInsert);// 记录将要插入数据库的数据
								}
							}
							target4 = emap.get("target4");
							if(StringUtils.isBlank(target4)) {
								
							} else {
								String key = target1+"_"+target2+"_"+target3+"_"+target4;
								if(targetContentmap.containsKey(key)) {
									target4_id = targetContentmap.get(key);
								} else {
									tlevel = "4";
									tparentid = target3_id;
									target4_id = UUID.randomUUID().toString();
									targetContentmap.put(key,target4_id);
									setInsertMap(target4_id,target4,tlevel,tparentid,needInsert);// 记录将要插入数据库的数据
								}
							}
								
						
						
						
//						System.out.println("excelmap : "+excelmap);
//						logger.info("File[{}] size[{}] FilePath[{}]", name, size, filepath);
					}
						
						System.out.println("needInsert : "+needInsert);
						
						EL sqlTemplate = null;
						Map<String,String> tempmap = null;
						for(int k=0; k<needInsert.size(); k++) {
							tempmap = needInsert.get(k);
							tempmap.put("createTime", time);
							tempmap.put("createPerson", person);
							tempmap.put("updateTime", time);
							tempmap.put("updatePerson", person);
							
							sqlTemplate = new EL("file", "insertFileTypes", webUtilsAdapter.getBasePath() + "sql");
							sqlTemplate.setVariables(tempmap);
							
							if (sqlInfoMap.get("mysqlTemplate") != null) {
								sqlInfoMap.get("mysqlTemplate").putAll(sqlTemplate.getSqlSegmentInfo(k + ""));
							} else {
								sqlInfoMap.put("mysqlTemplate", sqlTemplate.getSqlSegmentInfo(k + ""));
							}
						}
						jtaManager.saveOrupdate(sqlInfoMap);
				}// else 
				}
			}
				
				
				
				
/*				
				EL sqlTemplate = null;
				Map<String,String> map = null;
				for(int i=0; i<file.size(); i++) {
					id = UUID.randomUUID().toString();
					fileName = (String) file.get(i).get("name");// 文件名称
//					filePath = (String) file.get(i).get("filePath");// 文件存储权目录
//					filePath = filePath.replace("\\", "\\\\");
					fileNewName = (String) file.get(i).get("newName");//
					fileSize = (String) file.get(i).get("size");
					fileSuffix = (String) file.get(i).get("suffix");
//					fileTarget = (String) params.get("s2");
					
					map = new HashMap<String,String>();
					map.put("id", id);
					map.put("fileName", fileName);
//					map.put("filePath", filePath);
					map.put("fileNewName", fileNewName);
					map.put("fileSize", fileSize);
					map.put("fileSuffix", fileSuffix);
//					map.put("s2", fileTarget);
					
					map.put("createtime", time);
					map.put("createperson", person);
					
					sqlTemplate = new EL("file", "insertFile", webUtilsAdapter.getBasePath() + "sql");
					sqlTemplate.setVariables(map);
					
					if (sqlInfoMap.get("mysqlTemplate") != null) {
						sqlInfoMap.get("mysqlTemplate").putAll(sqlTemplate.getSqlSegmentInfo(i + ""));
					} else {
						sqlInfoMap.put("mysqlTemplate", sqlTemplate.getSqlSegmentInfo(i + ""));
					}
				}
				jtaManager.saveOrupdate(sqlInfoMap);

			}
*/			
		} catch (Exception e) {
			throw new Exception("上传附件出错！系统提示："+e.getMessage());
		}
		return RespResult.getInstance().setInfo("status", "success").toJson();
	}
	
	public List<Map<String, Object>> queryFileTarget() throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, String> params = webUtilsAdapter.getParameters();
		EL sqlModuleTemp = new EL("file", "selectFileType2ListAll", webUtilsAdapter.getBasePath() + "sql");
		sqlModuleTemp.setVariables(params);
		result = jtaManager.query("mysqlTemplate", sqlModuleTemp.getSqlSegmentInfo());
		return result;
	}
	
	/**
	 * 构造插入数据库的map
	 * @param id
	 * @param targetname
	 * @param targetlevel
	 * @param parentid
	 * @return
	 */
	public void setInsertMap(String id,String targetname,String targetlevel,String parentid,List<Map<String,String>> needInsert) {
		Map<String,String> tempMap = new HashMap<String,String>();
		tempMap.put("id", id);
		tempMap.put("typeName", targetname);
		tempMap.put("typeLevel", targetlevel);
		tempMap.put("parentId", parentid);
		
		needInsert.add(tempMap);
	}
}
