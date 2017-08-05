package test;
import java.util.HashMap;
import java.util.Map;

import org.pcloud.common.filemaker.BuildTemplate;

public class FileMaskTest {
 
	 
	
	public static void main(String[] args) throws Exception {
		String path = "/Users/admin/Documents/workspace/cheetah-template-generate/src/main/resources/";
		 Map<String,Object> pageOptionMap = new HashMap<String,Object>();
		 pageOptionMap.put("generated_page_html","test");
		 BuildTemplate pageOptionVmTemplate = new BuildTemplate("vm/page/page_html.vm",path,"page.html",path);
		 pageOptionVmTemplate.buildTemplate(pageOptionMap,"path");
	}

	 
}
