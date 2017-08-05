package org.pcloud.common.el.segment;

import java.util.Map;

public interface Segment {
	Object evaluate(Map<String, String> variables,String... status);
}
