package org.pcloud.common.el;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pcloud.common.el.segment.Segment;
import org.pcloud.common.el.segment.impl.PlainTextSegment;
import org.pcloud.common.el.segment.impl.VariableSegment;
import org.pcloud.common.properties.PropertiesHelper;



public class EL {

	private transient String[] templates;
	private transient List<String> parmList = new LinkedList<String>();
	private transient Map<String, String> variables = new HashMap<String, String>();
	private transient Map<String, String> dynamicFieldMap = null;

	public EL(final String propertiesName, final String key, final String... path) throws Exception {
		setText(propertiesName, key, path);
	}

	public void setText(final String propertiesName, final String key,
			final String... path) throws Exception {
		this.templates = PropertiesHelper.getInstance()
				.readPropertiesFile(propertiesName, key, path).split("⚚");
	}
	
	public EL(String text) throws Exception {
		this.templates = text.split("⚚");
	}
	
	public String getFlag(){
		return "⚚";
	}
	
	public List<Segment> parseSegments(String sqlTemplate) {
		final List<Segment> segments = new ArrayList<Segment>();
		final int index = collectSegments(segments, sqlTemplate);
		addTail(segments, index, sqlTemplate);
		return segments;
	}
	
	public Map<String, String> getDynamicField() {
		if(dynamicFieldMap != null){
			dynamicFieldMap.clear();
			dynamicFieldMap = null;
		}
		dynamicFieldMap = new HashMap<String,String>();
		for (int i = 0; i < templates.length; i++) {
			parseSegments(templates[i]);
		}
		return dynamicFieldMap;
	}

	private int collectSegments(List<Segment> segs, String sqlTemplate) {
		Pattern pattern = Pattern.compile("\\$\\{[^}]*\\}");
		Matcher matcher = pattern.matcher(sqlTemplate);
		int index = 0;
		while (matcher.find()) {
			addPrecedingPlainText(segs, matcher, index, sqlTemplate);
			addVarisble(segs, matcher, sqlTemplate);
			index = matcher.end();
		}
		return index;
	}

	private void addVarisble(List<Segment> segs, Matcher matcher,
			String sqlTemplate) {
		String name = sqlTemplate.substring(matcher.start() + 2,
				matcher.end() - 1);
		parmList.add(variables.get(name));
		if(dynamicFieldMap != null){
			dynamicFieldMap.put(name, name);
		}
		segs.add(new VariableSegment(name));
	}

	private void addPrecedingPlainText(List<Segment> segs, Matcher matcher,
			int index, String sqlTemplate) {
		if (index != matcher.start()) {
			segs.add(new PlainTextSegment(sqlTemplate.substring(index,
					matcher.start())));
		}
	}

	private void addTail(List<Segment> segs, int index, String sqlTemplate) {
		if (index < sqlTemplate.length()) {
			segs.add(new PlainTextSegment(sqlTemplate.substring(index)));
		}
	}
	
	public void clearParmList() {
		parmList.clear();
		parmList = new LinkedList<String>();
	}

	public Map<String, Object[]> getSqlSegmentInfo(String... idx) {
		Map<String, Object[]> result = new LinkedHashMap<String, Object[]>();
		for (int i = 0; i < templates.length; i++) {
			String sql = "";
			List<Segment> segments = parseSegments(templates[i]);
			for (int j = 0; j < segments.size(); j++) {
				sql += segments.get(j).evaluate(this.variables);
			}
			StringBuffer flag = new StringBuffer();
			for (int j = 0; idx.length != 0 && j < Integer.parseInt(idx[0]) ; j++) {
				flag.append(" ");
			}
			flag.append(sql);
			result.put(flag.toString(), parmList.toArray());
			clearParmList();
		}
		return result;
	}
	
	public String[] getPartReplacedSqlInfo() {
		String[] result = new String[templates.length];
		for (int i = 0; i < templates.length; i++) {
			String sql = "";
			List<Segment> segments = parseSegments(templates[i]);
			for (int j = 0; j < segments.size(); j++) {
				sql += segments.get(j).evaluate(this.variables,"PartReplaced");
			}
			result[i] =sql;
		}
		return result;
	}

	public void clearTemplateMap() {
		clearMap(this.variables);
	}

	public void clearMap(Map<?, ?> map) {
		map.clear();
		map = null;
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}
	

	public String[] getTemplates() {
		return templates;
	}

	public void setTemplates(String[] templates) {
		this.templates = templates;
	}
}
