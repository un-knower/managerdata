package com.kjtt.test.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.pcloud.common.date.DateHelper;
import org.pcloud.common.el.EL;
import org.pcloud.common.json.JsonHelper;
import org.pcloud.common.json.RespResult;
import org.pcloud.spring.jdbc.JtaManager;
import org.pcloud.spring.web.WebUtilsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kjtt.test.util.PasswordHelper;
import com.ufc.shiro.service.PermissionService;



@Component
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private JtaManager jtaManager;
	@Autowired
	private WebUtilsAdapter webUtilsAdapter;

	@Override
	public List<String> queryAllPermissionInfo(String username)
			throws Exception {
		List<String> permList = new ArrayList<String>();
		permList.add("member:add");
		permList.add("member:delete");
		permList.add("admin:manage");
		return permList;
	}

	@Override
	public Map<String, Object> queryUserInfo(String username) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String,String>params =webUtilsAdapter.getParameters();
		params.put("username",username);
		EL sqlModuleTemp = new EL("user","user_infomation",webUtilsAdapter.getBasePath()+"sql");
		sqlModuleTemp.setVariables(params);
		result=	jtaManager.query("mysqlTemplate", sqlModuleTemp.getSqlSegmentInfo()).get(0);
		String  UserInfo = RespResult.getInstance().setInfo("id", result.get("id")).toJson();
		webUtilsAdapter.getSession().setAttribute("UserInfo", UserInfo);
		return result;
	}

	
	
	//密码修改
	public String setOrUpdate(Map<String,String> params) {
		try {
			Map<String, Object> result = queryUserInfo(params.get("username"));
			String mainpass = PasswordHelper.getPassword(params.get("username"), params.get("old_password"),
					result.get("salt").toString());
			if (mainpass.equals(result.get("password").toString())) {
				String password = PasswordHelper.getPassword(params.get("username"),
						params.get("password"), result.get("salt").toString());
				params.put("password", password);
				EL sqlModuleTemp = new EL("user","updateUserInfo",webUtilsAdapter.getBasePath()+"sql");
			    sqlModuleTemp.setVariables(params);
				jtaManager.saveOrupdate("mysqlTemplate", sqlModuleTemp.getSqlSegmentInfo());
				return RespResult.getInstance().setInfo("status", "success").toJson();
			}else{
				return RespResult.getInstance().setInfo("status", "fail").toJson();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RespResult.getInstance().setInfo("status", "fail").toJson();
		}
	}
	
	//	修改用户信息
	public void Update(Map<String,String> params) {
		try {
			 Map<String, Object> result = queryUserInfo(params.get("username"));
			 String password =null;
			 Map<String, Map<String, Object[]>>  sqlInfoMap = new LinkedHashMap<String, Map<String, Object[]>> ();
			 if(params.get("password")==null||params.get("password")==""){
				 password =(String) result.get("password");
			 }else{
				password = PasswordHelper.getPassword(params.get("username")
						,params.get("password"), result.get("salt").toString());}
				params.put("password", password);
				EL sqlModuleTemp = new EL(params.get("pFile"),params.get("pKey"),webUtilsAdapter.getBasePath()+"sql");
				sqlModuleTemp.setVariables(params);
				sqlInfoMap.put("mysqlTemplate",  sqlModuleTemp.getSqlSegmentInfo());
				jtaManager.saveOrupdate("mysqlTemplate", sqlModuleTemp.getSqlSegmentInfo());
				
				EL deleteRoles = new EL("user","deleteRoles",webUtilsAdapter.getBasePath()+"sql");
				deleteRoles.setVariables(params);
				sqlInfoMap.put("mysqlTemplate",  deleteRoles.getSqlSegmentInfo());
				jtaManager.saveOrupdate("mysqlTemplate", deleteRoles.getSqlSegmentInfo());
				String[] roleId =params.get("update_role").split(",");
				for(int i=0;i<roleId.length; i++){
					Map<String,String> roleTable =new HashMap<String,String>(); 
					roleTable.put("id", UUID.randomUUID().toString());
					roleTable.put("ts_user_id", params.get("id"));
					roleTable.put("ts_role_id", roleId[i]);
					roleTable.put("create_date",DateHelper.currentDatetime());
					roleTable.put("update_date",DateHelper.currentDatetime());
					EL sqlModel=new EL("user","insert_role",webUtilsAdapter.getBasePath()+"sql");
					sqlModel.setVariables(roleTable);
					sqlInfoMap.put("mysqlTemplate", sqlModel.getSqlSegmentInfo());
					jtaManager.saveOrupdate("mysqlTemplate", sqlModel.getSqlSegmentInfo());
				}
				//jtaManager.saveOrupdate(sqlInfoMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   /*功能：添加用户
    * 添加用户基本信息判断用户角色是否为空
    * 
    * */
	public void addUser(Map<String,String> params,Map<String,String> sysparam){
		Map<String,String> setValues =PasswordHelper.crateNewUser(params);
		EL sqlModuleTemp= null;
		try {
			sqlModuleTemp = new EL("user","insert_user",webUtilsAdapter.getBasePath()+"sql");
			setValues.putAll(sysparam);
			String[] roleId =params.get("role").split(",");
			sqlModuleTemp.setVariables(setValues);
			jtaManager.saveOrupdate("mysqlTemplate", sqlModuleTemp.getSqlSegmentInfo());
			if(roleId.length !=0){
				for(int i=0;i<roleId.length; i++){
					Map<String,String> roleTable =new HashMap<String,String>(); 
					roleTable.put("id", UUID.randomUUID().toString());
					roleTable.put("ts_user_id", sysparam.get("sys.uuid"));
					roleTable.put("ts_role_id", roleId[i]);
					roleTable.put("create_date", sysparam.get("sys.nowTime"));
					roleTable.put("update_date", sysparam.get("sys.nowTime"));
					EL sqlModel=new EL("user","insert_role",webUtilsAdapter.getBasePath()+"sql");
					sqlModel.setVariables(roleTable);
					jtaManager.saveOrupdate("mysqlTemplate", sqlModel.getSqlSegmentInfo());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//用户信息
	public String userAllInfo(Map<String,String>  params) throws Exception{
		EL userInfo  =new EL("user","view_user",webUtilsAdapter.getBasePath()+"sql");
		EL userDetail = new EL("user","user_detail",webUtilsAdapter.getBasePath()+"sql");
		EL allroles = new EL("user","query_role",webUtilsAdapter.getBasePath()+"sql");
		userInfo.setVariables(params);
		userDetail.setVariables(params);
		allroles.setVariables(params);
		List<Map<String, Object>> userInfoList = jtaManager.query("mysqlTemplate", userInfo.getSqlSegmentInfo());
		List<Map<String, Object>> userDetailList =  jtaManager.query("mysqlTemplate", userDetail.getSqlSegmentInfo());
		List<Map<String, Object>> rolesList =  jtaManager.query("mysqlTemplate", allroles.getSqlSegmentInfo());
		return RespResult.getInstance().setInfo("status", "success").setInfo("userInfoList", userInfoList).setInfo("userDetailList", userDetailList).setInfo("rolesList", rolesList).toJson();
	}
	
	
	public String ishave(Map<String,String>  params) throws Exception{
		EL username  =new EL("user","query_username",webUtilsAdapter.getBasePath()+"sql");
		username.setVariables(params);
		List<Map<String, Object>> userInfoList = jtaManager.query("mysqlTemplate", username.getSqlSegmentInfo());
		int flag ;
		if (userInfoList.size() !=0) {
			flag=1;
		}else{
			flag=0;
		}
		System.out.println(flag);
		return RespResult.getInstance().setInfo("status", flag).toJson();
	}

}