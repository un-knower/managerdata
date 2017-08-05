package org.pcloud.spring.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 全局公用参数存储类
 * 
 * Example：
 * 		SysDataManager sysDataManager = new SysDataManager().addSysData("sys.xxx1","aa").addSysData("sys.xxx2","bb");
 * 		webUtilsAdapter.getSession().setAttribute("loginResource",sysDataManager);
 * @author 徐海洋
 *
 */
public class SysDataManager {

	public  SysDataManager() {
			addSysData("sys.nowTime", "Reflection@org.pcloud.common.data.PublicData/getVal/system.nowTime");
			addSysData("sys.uuid", "Reflection@org.pcloud.common.data.PublicData/getVal/system.uuid");
	}

	private List<SysData> list = new ArrayList<SysData>();

	public SysDataManager addSysData(String name, String value) {
		SysData sysData = new SysData();
		sysData.setName(name);
		sysData.setValue(value);
		list.add(sysData);
		return this;
	}
	
	public List<SysData> getList() {
		return list;
	}

	public Map<String,String> getMap() throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		for (SysData sysData : list) {
			String value =  sysData.getValue();
			if(value.startsWith("Reflection@")){
				value = value.replaceAll("Reflection@", "");
				String[] reflectionData = value.split("/");
				if(reflectionData.length == 2){
					value = invokeMethod(Class.forName(reflectionData[0]).newInstance(), reflectionData[1], new Class<?>[]{}, new Object[]{}).toString();
				}else{
					value = invokeMethod(Class.forName(reflectionData[0]).newInstance(), reflectionData[1], new Class<?>[]{String.class}, new Object[]{reflectionData[2]}).toString();
				}
			}
			map.put(sysData.getName(), value);
		}
		return map;
	}
	

    /**
     * 直接调用对象方法, 无视private/protected修饰符. 用于一次性调用的情况.
     */
    public  Object invokeMethod(Object obj, String methodName, Class<?>[] parameterTypes, final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("没有发现 方法 [" + methodName + "] 在目标类  [" + obj + "] 中");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
     * 
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object...
     * args)
     */
    public  Method getAccessibleMethod(Object obj, String methodName, final Class<?>... parameterTypes) {

        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

                method.setAccessible(true);

                return method;

            } catch (NoSuchMethodException e) {
                // Nothing to do here , go to superclass
            }
        }
        return null;
    }

    /**
     * 将反射时的checked exception转换为unchecked exception.
     */
    public  RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException("反射异常.", e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException("反射异常.", ((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("意外的异常截取.", e);
    }
    
	
}