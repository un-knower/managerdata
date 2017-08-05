package org.pcloud.spring.web;

public class SysData {

	// 系统全局参数名称 如：sys.uuid
	private String name;

	// 系统全局参数值 如：aaaa-bbbb-cccc-dddd...
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}