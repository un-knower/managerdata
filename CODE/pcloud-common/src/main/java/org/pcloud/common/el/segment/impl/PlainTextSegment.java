package org.pcloud.common.el.segment.impl;

import java.util.Map;

import org.pcloud.common.el.segment.Segment;

public class PlainTextSegment implements Segment {
    private String text;

    public PlainTextSegment(String textplain) {
        this.text = textplain;
    }

    public Object evaluate(Map<String, String> variables,String... status) {
        if (variables.get(this.text) == null) {
            return this.text;
        }
        return variables.get(this.text);
    }
}
