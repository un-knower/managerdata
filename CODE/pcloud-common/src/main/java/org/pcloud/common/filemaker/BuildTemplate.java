package org.pcloud.common.filemaker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BuildTemplate {

	/***
	 * 模板名称
	 */
	private String _templateName;

	/***
	 * 模板路径
	 */
	private String _templatePath;

	/***
	 * 生成文件的名称
	 */
	private String _temporaryTemplateName;

	/***
	 * 生成文件路径
	 */
	private String _temporaryTemplatePath;
	
	/***
	 * jar包名称
	 */
	private String _jarName = "cheetah-template-generate-0.0.1-SNAPSHOT";

	/**
	 * 私有构造方法防止外部创建
	 */
	@SuppressWarnings("unused")
	private BuildTemplate() {
	}

	public BuildTemplate(String templateNameParam, String templatePathParam,
			String temporaryTemplateNameParam, String temporaryTemplatePathParam, String... jarName) {
		this._templateName = templateNameParam;
		this._templatePath = templatePathParam;
		this._temporaryTemplateName = temporaryTemplateNameParam;
		this._temporaryTemplatePath = temporaryTemplatePathParam;
		if(jarName != null && jarName.length == 1){
			this._jarName = jarName[0];
		}
	}

	public void buildTemplate(Object t, String loadType) throws Exception {
		createTemplateFile(t, "Obj", loadType);
	}

	public void createTemplateFile(Object entitys, String beanName,
			String loadType) throws UnsupportedEncodingException,
			FileNotFoundException, Exception, IOException {
		File file = new File(this._temporaryTemplatePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		Writer fwriter = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(this._temporaryTemplatePath
						+ File.separator + _temporaryTemplateName), "utf-8"));
		createTemplate(fwriter, entitys, beanName, loadType);
		fwriter.flush();
		fwriter.close();
	}

	public Element getRoleElementObject(Document document) {
		Element root = document.getRootElement();
		return root;
	}

	public String getElementText(Element root, String elementName) {
		String elementText = root.element(elementName).getText().trim();
		return elementText;
	}

	public Document getFileDocumentObject(File file) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(file);
		return document;
	}

	public void createTemplate(Writer writer, Object entitys, String beanName,
			String loadType) throws Exception {
		 
			LableFilter lableFilter = new LableFilter();
			Map<String, Object> context = new HashMap<String, Object>();
			context.put(beanName, entitys);
			context.put("vmUtil", lableFilter);
			if (loadType.equals("path")) {
				mergePath(context, writer);
			} else if (loadType.equals("jar")) {
				mergeJar(context, writer);
			}
	}

	public Element createTemplate(File file) throws Exception {
		Document document = getFileDocumentObject(file);
		return getRoleElementObject(document);
	}

	public void mergePath(Map<String, Object> context, Writer writer)
			throws Exception {
		VelocityContext vcontext = new VelocityContext(context);
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, this._templatePath);
		ve.init();
		ve.mergeTemplate(_templateName, "utf-8", vcontext, writer);
	}

	public void mergeJar(Map<String, Object> context, Writer writer)
			throws Exception {
		VelocityContext vcontext = new VelocityContext(context);
		VelocityEngine ve = new VelocityEngine();
		// 设置velocity资源加载方式为jar
		ve.setProperty("resource.loader", "jar");
		// 设置velocity资源加载方式为file时的处理类
		ve.setProperty("jar.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.JarResourceLoader");
		// 设置jar包所在的位置
		ve.setProperty("jar.resource.loader.path", "jar:file:" + _templatePath
				+File.separator+ "WEB-INF"+File.separator+"lib"+File.separator+_jarName+".jar");
		ve.init();
		ve.mergeTemplate(_templateName, "utf-8", vcontext, writer);
	}
	
	
}
