package com.zkdj.test.dj;

import java.util.Date;
import java.util.List;

import com.ufc.entity.SentenceItem;
import com.ufc.user.utils.SplitUtils;

public class TextSplit {

	public static void main(String[] args) {
		//查询文本
		//String text = "a）可动态扩展。③.收缩的服务器调度系统。（4）、中科。②点击。（4、大幅度。(1)dddd。一、对对对。九.不不不。.dadfasdf";
		String text="构建油茶种质资源数据信息库及资源共享交流交换服务网络平台，对油茶种质资源进行精细化管理，实现油茶种质资源从采集、保存、繁育到推广种植的信息化管理和跟踪，推动油茶种质资源的交流交换，为油茶种质资源评价与创新提供依据，为油茶产业链溯源提供数据支撑。构建油茶种质资源数据信息库及资源共享交流交换服务网络平台，对油茶种质资源进行精细化管理，实现油茶种质资源从采集、保存、繁育到推广种植的信息化管理和跟踪，推动油茶种质资源的交流交换，为油茶种质资源评价与创新提供依据，为油茶产业链溯源提供数据支撑。构建油茶种质资源数据信息库及资源共享交流交换服务网络平台，对油茶种质资源进行精细化管理，实现油茶种质资源从采集、保存、繁育到推广种植的信息化管理和跟踪，推动油茶种质资源的交流交换，为油茶种质资源评价与创新提供依据，为油茶产业链溯源提供数据支撑。";
		System.out.println("========== 文本断句 开始 ==========");
		System.out.println(new Date());
		List<String>  list = SplitUtils.SentenceSplit(text);
		for(String item:list){
			System.out.println(item);
		}
		System.out.println(new Date());
		System.out.println("========== 文本断句 结束 ==========");
		//比较内容
		String content ="孵化器将以《国家科技企业孵化器“十二五”发展规划纲要》为指导，坚持“以人为本、科技创新、诚信共赢”的原则，以市场为导向，以科技成果转化和高新技术企业孵化为核心，以观念、技术、体制创新为突破口，以民间资本为支撑，加大园区基础设施建设，研究并确立一套行之有效的孵化服务管理体制和运作机制，建设一个机制灵活、优势明显、特色鲜明的民营科技企业孵化器，并争取在几年内获得国家级科技企业孵化器的认定。"; 
		SentenceItem sentence=new SentenceItem(text,content);
		System.out.println("相似度："+sentence.getSimilar());
		System.out.println("高亮内容："+sentence.getColorText());
		
		System.out.println((int)(2*1.0 / 51 * 100));
	}
}
