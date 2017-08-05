package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Snippet {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		 new ClassPathXmlApplicationContext("spring/spring-utils.xml");
	}
}

