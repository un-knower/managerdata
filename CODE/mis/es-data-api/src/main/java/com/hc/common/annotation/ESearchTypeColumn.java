package com.hc.common.annotation;
 
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 构建为elasticsearch
 * 方便使用的jsonBuilder对象
 *@author huangcheng
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ESearchTypeColumn {
	DateFormat format() default DateFormat.none;
	
	String type() default "";
	
	boolean index() default true;

	String pattern() default "";

//	boolean store() default false;

	boolean fielddata() default false;

	String searchAnalyzer() default "";

	String analyzer() default "";
	
	String not_analyzed() default "";

//	String[] ignoreFields() default {};
//
//	boolean includeInParent() default false;
 
}
