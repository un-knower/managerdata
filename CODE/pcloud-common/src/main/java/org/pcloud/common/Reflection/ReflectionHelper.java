package org.pcloud.common.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
 
/**
 * 反射工具类.
 * 
 * 提供访问私有变量,获取泛型类型Class, 提取集合中元素的属性, 转换字符串到对象等Util函数.
 * 
 * @author 徐海洋   
 */
@SuppressWarnings({"unchecked" ,"rawtypes"})
public class ReflectionHelper {
	private static Map<String,Class<?>> baseType = null;
	static{
		baseType = new HashMap<String,Class<?>>();
    	baseType.put("class java.lang.Integer", int.class);
    	baseType.put("class java.lang.Byte", byte.class);
    	baseType.put("class java.lang.Boolean", boolean.class);
    	baseType.put("class java.lang.Char", char.class);
    	baseType.put("class java.lang.Float", float.class);
    	baseType.put("class java.lang.Long", long.class);
    	baseType.put("class java.lang.Double", double.class);
    	baseType.put("class java.lang.Short", short.class);
	}
	
    /**
     * 调用类方法.
     * @param obj
     * 			类对象实例
     * @param methodName
     * 			方法名称
     * @return
     * 		返回方法的返回值
     */
    public static Object invokeClassMethod(Object obj, String methodName) {
        return invokeMethod(obj, methodName, new Class[] {}, new Object[] {});
    }

    /**
     * 调用类的Getter方法.
     * @param obj
     * 			类对象实例
     * @param propertyName
     * 			类成员变量的名称 如：private String userId; 这里赋值就是 userId；
     * 			最后执行就是：obj.getUserId();
     * @return
     * 		返回方法的返回值
     */
    public static Object invokeGetterMethod(Object obj, String propertyName) {
        String getterMethodName = "get" + capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, new Class[] {}, new Object[] {});
    }

    /**
     * 调用Setter方法.使用value的Class来查找Setter方法.
     * 
     * 根据 set 赋值的方法参数类型 如 setUserId(int i) 中的 i 的类型  调用类的Getter方法.
     * @param obj
     * 			类对象实例
     * @param propertyName
     * 			类成员变量的名称 如：private String userId; 这里赋值就是 userId；
     * 			最后执行就是：obj.setUserId 这个方法，因为可能会出现多个setUserId方法，只是参数类型不一样，所以我们就需要再根据 第三个参数 value 的类型来判断调用哪一个;
     * @param value
     * 			传入的方法参数
     */
    public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
        invokeSetterMethod(obj, propertyName, value, null);
    }

    /**
     * 调用Setter方法.使用propertyType来制定参数类型查找Setter方法.
     * 
     * @param obj
     * 			类对象实例
     * @param propertyName
     * 			类成员变量的名称 如：private String userId; 这里赋值就是 userId；
     * 			最后执行就是：obj.setUserId 这个方法，因为可能会出现多个setUserId方法，只是参数类型不一样，所以我们就需要再根据 第四个参数 propertyType来判断调用哪一个;
     * @param value
     * 			传入的方法参数
     * @param propertyType
     * 			调用的set方法的参数类型
     * 		
     */
    public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
    
        Class<?> type = propertyType != null ? propertyType : value.getClass();
        Class<?> st = baseType.get(type.toString()) ;
        if(st != null){
        	type = st;
        }
        String setterMethodName = "set" + capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class[] { type }, new Object[] { value });
    }

    /**
     *  直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     * @param obj
     * 			类对象实例
     * @param fieldName
     * 			对象属性名称
     * @return
     * 			返回对应对象属性的值
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("没有发现 方法 [" + fieldName + "] 在目标类  [" + obj + "] 中");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *  直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     * @param obj
     * 			类对象实例
     * @param fieldName
     * 			对象属性名称
     * @value
     * 		 	对应对象属性的值
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("没有发现 方法 [" + fieldName + "] 在目标类  [" + obj + "] 中");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
    }

    /***
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问,如向上转型到Object仍无法找到, 返回null.
     * @param obj
     * 			类对象实例
     * @param fieldName
     * 			对象属性名称
     * @return
     */
    public static Field getAccessibleField(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符. 
     * @param obj
     * 			类对象实例
     * @param methodName
     * 			类方法名称
     * @param parameterTypes
     * 			类方法参数的类型数组，按照顺序依次装载，如 setUser(int userId, String userName), parameterTypes = new Class[]{int.class,String.class}
     * @param args
     * 			对象传入方法的参数数组
     * @return
     * 		返回方法调用结果值
     */
    public static Object invokeMethod(Object obj, String methodName, Class<?>[] parameterTypes, final Object[] args) {
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
     * 循环向上转型, 获取对象的DeclaredMethod，即获取类方法对象,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
     * @param obj
     * 			类对象实例
     * @param methodName
     * 			类方法名称
     * @param parameterTypes
     * 			类方法参数的类型数组，按照顺序依次装载，如 setUser(int userId, String userName), parameterTypes = new Class[]{int.class,String.class}
     * @return
     * 		返回类对应方法的method对象
     */
    public static Method getAccessibleMethod(Object obj, String methodName, final Class<?>... parameterTypes) {
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
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
     * 如public UserDao extends BaseDao<UserInfo>
     * @param clazz
     *           userDao.class
     * @return UserInfo.class
     */
	public static <T> Class<T> getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
     * 
     * 如public UserDao extends BaseDao<User,Long>
     * 
     * @param clazz
     *            userDao.class 
     * @param index
     *            <User,Long> 这个里面的坐标为 user 在0位 Long在1为
     * @return Long.class 或者 User.class
     */
    public static Class getSuperClassGenricType(Class clazz, int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
           System.out.println("父类 " + clazz.getSimpleName() + " 并非泛型");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
        		System.out.println("父类 " + clazz.getSimpleName() + "不存在 " + index + " 这个下标位的泛型对象 ,最大下标位为 " + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            System.out.println("父类 " + clazz.getSimpleName() + " 的" + index + " 下标位上的对象的数据类型并非通用类型");
            return Object.class;
        }

        return (Class) params[index];
    }

    /***
     * 将一个map集合信息柱状成Bean信息
     * 
     * @param map
     *            数据集合
     * @param clazz
     *            接受集合信息的对象
     * @return Bean对象
     * @throws Exception
     *             全局异常
     */
    public static Object parseObject(Map<String, Object> map, Class clazz) throws Exception {
        Object obj = clazz.newInstance();
        Field[] fs = clazz.getDeclaredFields();
        for (Field f : fs) {
            String value = String.valueOf(map.get(f.getName().toLowerCase()));
            if (value == null) {
            } else {
                String methodName = "set" + capitalize(f.getName());
                Class[] cl = new Class[1];
                cl[0] = f.getType();
                Method md = clazz.getDeclaredMethod(methodName, cl);
                md.invoke(obj, value);
            }
        }
        return obj;
    }

    /***
     * 将一个list集合信息柱状成Bean信息的集合
     * 
     * @param list
     *            数据集合
     * @param clazz
     *            接受集合信息的对象
     * @return List<Bean>对象
     * @throws Exception
     *             全局异常
     */
    public static List<Object> parseCollectionOfObject(List<Map<String, Object>> list, Class clazz) throws Exception {
        List<Object> result = new LinkedList<Object>();
        for (Map<String, Object> map : list) {
            Object obj = clazz.newInstance();
            Field[] fs = clazz.getDeclaredFields();
            for (Field f : fs) {
                String value = String.valueOf(map.get(f.getName().toLowerCase()));
                if (value == null) {
                } else {
                    String methodName = "set" + capitalize(f.getName());
                    Class[] cl = new Class[1];
                    cl[0] = f.getType();
                    Method md = clazz.getDeclaredMethod(methodName, cl);
                    md.invoke(obj, value);
                }
            }
            result.add(obj);
            obj = null;
        }
        return result;
    }

    /**
     * 将反射时的checked exception转换为unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
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
    
    public static String capitalize(String name) {
    	 StringBuffer sb = new StringBuffer();
    	 if(name != null){
    		 sb.append(name.substring(0,1).toUpperCase()).append(name.substring(1));
    	 }
    	 return sb.toString();
    }
}
